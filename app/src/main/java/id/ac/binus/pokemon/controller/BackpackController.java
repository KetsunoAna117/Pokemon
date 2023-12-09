package id.ac.binus.pokemon.controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.Trainer;
import id.ac.binus.pokemon.model.items.HpUp;
import id.ac.binus.pokemon.model.items.Item;
import id.ac.binus.pokemon.model.items.Potion;
import id.ac.binus.pokemon.model.items.Protein;
import id.ac.binus.pokemon.model.items.RareCandy;
import id.ac.binus.pokemon.model.items.Revive;

public class BackpackController {
    private static HashMap<Integer, Item> backpack;
    static FirebaseDatabase db = FirebaseDatabase.getInstance("https://pokemon-f8040-default-rtdb.asia-southeast1.firebasedatabase.app/");
    static String user = TrainerController.getActiveTrainerData().getName() + "'s backpack";
    static DatabaseReference itemRef = db.getReference().child(user);

    public static Vector<Item> getTrainerBackpackData(){

        Collection<Item> values = backpack.values();
        return new Vector<Item>(values);
    }
    public static void loadTrainerBackpackData(){

        backpack = new HashMap<>();

        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        Integer itemId = childSnapshot.child("itemId").getValue(Integer.class);
                        Integer itemQuantity = childSnapshot.child("itemQuantity").getValue(Integer.class);
                        switch(itemId){
                            case 1:
                                backpack.put(1, new HpUp(itemQuantity));
                                break;
                            case 2:
                                backpack.put(2, new Potion(itemQuantity));
                                break;
                            case 3:
                                backpack.put(3, new Protein(itemQuantity));
                                break;
                            case 4:
                                backpack.put(4, new RareCandy(itemQuantity));
                                break;
                            case 5:
                                backpack.put(5, new Revive(itemQuantity));
                                break;

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    public static Integer getItemIndexFromBackpack(Item selectedItem){
//        Integer index = -1;
//        for(int i=0; i<backpack.size(); i++){
//            if(backpack.get(i).equals(selectedItem)){
//                return i;
//            }
//        }
//
//        return index;
//    }

    public static Boolean useItem(Item selectedItem, Pokemon selectedPokemon){
        if(selectedItem.getQuantity() > 0){
            Boolean statusIsTrue = selectedItem.useItem(selectedPokemon);

            Integer qty = selectedItem.getQuantity() - 1;

            if(statusIsTrue){

                itemRef.child(selectedItem.getName()).child("itemQuantity").setValue(qty);

                selectedItem.setQuantity(qty);

                return true;
            }
            return false;
        }
        return false;
    }

    public static Item getItemReward(){
        Integer dropItemId = Helper.getRandomNumber(1, backpack.size());
        Log.d("DEBUG", "DROP ITEM RANDOMIZED ID: " + dropItemId);
        Item selectedItem = backpack.get(dropItemId);
        if(selectedItem != null){

            Integer qty = selectedItem.getQuantity() + 1;

            selectedItem.setQuantity(qty);

            itemRef.child(selectedItem.getName()).child("itemQuantity").setValue(qty);

            return selectedItem;
        }
        return null;
    }
}
