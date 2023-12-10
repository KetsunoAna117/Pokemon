package id.ac.binus.pokemon.controller;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.model.Trainer;
import id.ac.binus.pokemon.view.LoginActivity;
import id.ac.binus.pokemon.view.RegisterActivity;
import id.ac.binus.pokemon.view.StarterActivity;

public class AuthenticationController {
    static DatabaseReference userRef, pokeRef, itemRef;
    static FirebaseDatabase db = FirebaseDatabase.getInstance("https://pokemon-f8040-default-rtdb.asia-southeast1.firebasedatabase.app/");

    public interface UserExistenceCallback {
        void onResult(Boolean bool);
    }
    public static void checkUserExist(String user, UserExistenceCallback callback){
        final Boolean[] userExists = new Boolean[1];

        userRef = db.getReference().child("users").child(user);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exists = snapshot.exists();
                callback.onResult(exists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onResult(false);
            }
        });
    }

    public static void userLogin(String user, String pass, UserExistenceCallback callback){

        userRef = db.getReference().child("users").child(user);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String passWord = snapshot.child("pass").getValue(String.class);
                boolean passCorrect = pass.equals(passWord);

                if(passCorrect){
                    String gender = snapshot.child("gender").getValue(String.class);
                    Integer level = snapshot.child("level").getValue(Integer.class);
                    Integer exp = snapshot.child("exp").getValue(Integer.class);
                    Integer baseExp = snapshot.child("baseExp").getValue(Integer.class);
                    if(gender.equals("male")){
                        TrainerController.setActiveTrainerData(new Trainer(user, "Male", R.drawable.male_trainer, level, exp, baseExp));
                    }
                    else if(gender.equals("female")) {
                        TrainerController.setActiveTrainerData(new Trainer(user, "Female", R.drawable.female_trainer, level, exp, baseExp));
                    }
                }
                callback.onResult(passCorrect);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onResult(false);
            }
        });
    }

    public static void newUser(String user, String pass, String gender, String pokeName, Integer pokeLvl, String pokeType, Integer pokeAtk, Integer pokeMaxHp, Integer pokeHp, UserExistenceCallback callback){

        userRef = db.getReference("users");
        pokeRef = db.getReference(user + "'s pokemon");
        itemRef = db.getReference(user + "'s backpack");

//                Starter
        DatabaseReference newChildRef = pokeRef.push();
        String key = newChildRef.getKey();

        HashMap<String, Object> pokeData = new HashMap<>();
        pokeData.put("pokemonName", pokeName);
        pokeData.put("pokemonLevel", pokeLvl);
        pokeData.put("pokemonType", pokeType);
        pokeData.put("pokemonAttack", pokeAtk);
        pokeData.put("pokemonMaxHP", pokeMaxHp);
        pokeData.put("pokemonHP", pokeHp);

//        User
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("user", user);
        userData.put("pass", pass);
        userData.put("gender", gender);
        userData.put("level", 1);
        userData.put("exp", 0);
        userData.put("baseExp", 20);
        userData.put("partySize", 1);
        userData.put("activePokemonId", key);

//                Item
        HashMap<String, Object> hpUpData = createItemData(1);
        HashMap<String, Object> potionData = createItemData(2);
        HashMap<String, Object> proteinData = createItemData(3);
        HashMap<String, Object> rareCandyData = createItemData(4);
        HashMap<String, Object> reviveData = createItemData(5);

        pokeRef.child(key).setValue(pokeData);
        itemRef.child("Hp Up").setValue(hpUpData);
        itemRef.child("Potion").setValue(potionData);
        itemRef.child("Protein").setValue(proteinData);
        itemRef.child("Rare Candy").setValue(rareCandyData);
        itemRef.child("Revive").setValue(reviveData);

        userRef.child(user).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.onResult(true);
            }
        });
    }

    private static HashMap<String, Object> createItemData(int itemId) {
        HashMap<String, Object> itemData = new HashMap<>();
        itemData.put("itemId", itemId);
        itemData.put("itemQuantity", 0);
        return itemData;
    }
}
