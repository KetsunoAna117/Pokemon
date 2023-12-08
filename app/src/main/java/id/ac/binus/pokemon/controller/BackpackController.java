package id.ac.binus.pokemon.controller;

import android.util.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.items.HpUp;
import id.ac.binus.pokemon.model.items.Item;
import id.ac.binus.pokemon.model.items.Potion;
import id.ac.binus.pokemon.model.items.Protein;
import id.ac.binus.pokemon.model.items.RareCandy;
import id.ac.binus.pokemon.model.items.Revive;

public class BackpackController {
    private static HashMap<Integer, Item> backpack;
    public static Vector<Item> getTrainerBackpackData(Integer trainerId){
        // TODO ADD SQL TO GET BACKPACK ITEM BY TRAINER ID

        Collection<Item> values = backpack.values();
        return new Vector<Item>(values);
    }

    // TODO this is just for mockup data, delete this later.
    private static Boolean initFlag = false;
    public static void initItem(){
        if(!initFlag){
            backpack = new HashMap<>();
            Item potion = new Potion(99);
            Item protein = new Protein(99);
            Item rareCandy = new RareCandy( 5);
            Item hpup = new HpUp(99);
            Item revive = new Revive(10);

            backpack.put(potion.getId(), potion);
            backpack.put(protein.getId(), protein);
            backpack.put(rareCandy.getId(), rareCandy);
            backpack.put(hpup.getId(), hpup);
            backpack.put(revive.getId(), revive);

            initFlag = true;
        }
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
            if(statusIsTrue){
                selectedItem.setQuantity(selectedItem.getQuantity() - 1);
                return true;
            }
            return false;
        }
        return false;
    }

    public static Item getItemReward(){
        Integer dropItemId = Helper.getRandomNumber(1, backpack.size());
        Log.d("DEBUG", "DROP ITEM RANDOMIZED ID: " + dropItemId);
        if(backpack.containsKey(dropItemId)){
            Item selectedItem = backpack.get(dropItemId);
            selectedItem.setQuantity(selectedItem.getQuantity() + 1);
            return selectedItem;
        }
        return null;

//        for(Item i: backpack){
//            if(i.getId().equals(dropItemId)){
//                i.setQuantity(i.getQuantity() + 1);
//                return i;
//            }
//        }

    }
}
