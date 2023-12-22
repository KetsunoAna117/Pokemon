package id.ac.binus.pokemon.controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.Trainer;
import id.ac.binus.pokemon.view.MainActivity;
import id.ac.binus.pokemon.view.RegisterActivity;

public class TrainerController implements OnPokemonLoadedListener {
    private static Boolean mainInitFlag = true;
    private static ArrayList<Pokemon> starterList;
    private static Trainer activeTrainerData;
    private static Integer activeTrainerPartySize;
    private MainActivity mainListener;
    private RegisterActivity starterListener;
    private static DatabaseReference userRef, pokeRef;
    private static FirebaseDatabase db = FirebaseDatabase.getInstance("https://pokemon-f8040-default-rtdb.asia-southeast1.firebasedatabase.app/");

    public interface OnPartySizeFetched {
        void onResult();
    }

    public interface OnTrainerActivePokemonFecthed{
        void onResult();
    }

    public static Trainer getActiveTrainerData() {
        return activeTrainerData;
    }

    public static void setActiveTrainerData(Trainer activeTrainerData) {
        TrainerController.activeTrainerData = activeTrainerData;
    }

    public void getTrainerPokemon(MainActivity mainListener){
        this.mainListener = mainListener;

        String user = activeTrainerData.getName() + "'s pokemon";

        pokeRef = db.getReference().child(user);

        pokeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String pokemonId = childSnapshot.getKey();
                        String pokemonName = childSnapshot.child("pokemonName").getValue(String.class);
                        Integer pokemonLevel = childSnapshot.child("pokemonLevel").getValue(Integer.class);
                        Integer pokemonAttack = childSnapshot.child("pokemonAttack").getValue(Integer.class);
                        Integer pokemonHp = childSnapshot.child("pokemonHP").getValue(Integer.class);
                        Integer pokemonMaxHP = childSnapshot.child("pokemonMaxHP").getValue(Integer.class);
                        PokeApiService.getCapturedPokemonDataFromAPIByName(pokemonId, pokemonName, TrainerController.this, pokemonLevel, pokemonMaxHP, pokemonHp, pokemonAttack);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getStarterPokemon(RegisterActivity starterListener){
        this.starterListener = starterListener;

        starterList = new ArrayList<>();

        LinkedList<String> pokemonIdList = new LinkedList<String>();
        pokemonIdList.add("mudkip");
        pokemonIdList.add("treecko");
        pokemonIdList.add("torchic");

        for(String name: pokemonIdList){
            PokeApiService.getStarterPokemonDataFromAPIByName(name, this, 10, 10,  5);
        }
    }

    public static void changeActivePokemon(Pokemon toActivePokemon){

        String user = TrainerController.getActiveTrainerData().getName();
        activeTrainerData.setActivePokemon(toActivePokemon);

        userRef = db.getReference("users");

        userRef.child(user).child("activePokemonId").setValue(toActivePokemon.getPokemonId());
    }

    public static void removeTrainerPokemon(Pokemon toDeletePokemon){
        activeTrainerData.getParty().remove(toDeletePokemon);

        String user = TrainerController.getActiveTrainerData().getName();

        pokeRef = db.getReference(user + "'s pokemon");
        userRef = db.getReference("users");

        pokeRef.child(toDeletePokemon.getPokemonId()).removeValue();

        userRef.child(user).child("partySize").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer partySize = snapshot.getValue(Integer.class);

                userRef.child(user).child("partySize").setValue(partySize - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // switch active pokemon to next pokemon
        Pokemon toActivePokemon = activeTrainerData.getParty().get(0);
        changeActivePokemon(toActivePokemon);
    }

    @Override
    public void onPokemonReceived(Pokemon pokemon) {
        Log.d("DEBUG", "onPokemonReceived event");
        TrainerController.getActiveTrainerData().getParty().add(pokemon);
        mainListener.onPokemonReceivedEvent();
    }

    @Override
    public void onStarterReceived(Pokemon pokemon) {
        Log.d("DEBUG", "onStarterReceived event");

        starterList.add(pokemon);

        starterListener.onStarterReceivedEvent();
    }


    public static void addTrainerExp(){
        Integer currentExp = activeTrainerData.getExp();
        Integer maxExp = activeTrainerData.getBaseExp();
        Integer newExp, newBaseExp = 0, newLevel = 0;
        String user = activeTrainerData.getName();

        if(currentExp + 5 >= maxExp){
            // Trainer Level Up!
            newLevel = activeTrainerData.getLevel() + 1;
            activeTrainerData.setLevel(newLevel);
//            Integer bonusExp = currentExp + 5 - activeTrainerData.getBaseExp();
            newExp = currentExp + 5 - activeTrainerData.getBaseExp();

            newBaseExp = maxExp + 5;
            activeTrainerData.setBaseExp(newBaseExp);
//            activeTrainerData.setExp(bonusExp);
            activeTrainerData.setExp(newExp);
        }
        else{
            newExp = currentExp + 5;
            activeTrainerData.setExp(newExp);
        }

        Integer finalNewBaseExp = newBaseExp;
        Integer finalNewLevel = newLevel;

        userRef = db.getReference().child("users").child(user);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userRef.child("exp").setValue(newExp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("DEBUG", "Trainer exp updated successfully");
                            } else {
                                Log.e("ERROR", "Failed to update trainer exp", task.getException());
                            }
                        }
                    });
                    if(finalNewBaseExp != 0 && finalNewLevel != 0){
                        userRef.child("level").setValue(finalNewLevel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("DEBUG", "Trainer level updated successfully");
                                } else {
                                    Log.e("ERROR", "Failed to update trainer level", task.getException());
                                }
                            }
                        });
                        userRef.child("baseExp").setValue(finalNewBaseExp).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("DEBUG", "Trainer base exp updated successfully");
                                } else {
                                    Log.e("ERROR", "Failed to update trainer base exp", task.getException());
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    public static ArrayList<Pokemon> getStarterList() {
        return starterList;
    }

    public static Boolean getMainInitFlag(){
        return mainInitFlag;
    }

    public static void setMainInitFlag(Boolean flag){
        mainInitFlag = flag;
    }

    public static void initTrainerPartyPokemonSize(OnPartySizeFetched callback) {
        userRef = db.getReference().child("users").child(getActiveTrainerData().getName());
        Log.d("DEBUG", "Database Path: " + userRef);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer partySize = snapshot.child("partySize").getValue(Integer.class);
                TrainerController.setActiveTrainerPartySize(partySize);
                Log.d("DEBUG", "party size: " + getActiveTrainerPartySize());
                callback.onResult();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DEBUG", "ERROR");
            }
        });
    }

    public static void setActiveTrainerPartySize(Integer size){
        TrainerController.activeTrainerPartySize = size;
    }
    public static Integer getActiveTrainerPartySize(){
        return activeTrainerPartySize;
    }

    public static void initActiveTrainerPokemonFromDatabase(OnTrainerActivePokemonFecthed callback){
        userRef = db.getReference().child("users").child(getActiveTrainerData().getName());
        Log.d("DEBUG", "Database Path: " + userRef);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String activePokemonId = snapshot.child("activePokemonId").getValue(String.class);
                LinkedList<Pokemon> pokemons = getActiveTrainerData().getParty();
                for(Pokemon p : pokemons){
                    if(p.getPokemonId().equals(activePokemonId)){
                        getActiveTrainerData().setActivePokemon(p);
                        Log.d("DEBUG", "party size: " + getActiveTrainerPartySize());
                        callback.onResult();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DEBUG", "ERROR");
            }
        });




    }
}
