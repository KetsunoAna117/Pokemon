package id.ac.binus.pokemon.controller;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.Trainer;
import id.ac.binus.pokemon.view.LoginActivity;
import id.ac.binus.pokemon.view.MainActivity;
import id.ac.binus.pokemon.view.RegisterActivity;
import id.ac.binus.pokemon.view.StarterActivity;
import com.google.firebase.database.DataSnapshot;

public class TrainerController implements OnPokemonLoadedListener {
    private static ArrayList<Pokemon> starterList;
    private static Trainer activeTrainerData;
    private MainActivity mainListener;
    private RegisterActivity starterListener;
    DatabaseReference userRef;
    FirebaseDatabase db;

    public static Trainer getActiveTrainerData() {
        return activeTrainerData;
    }

    public static void setActiveTrainerData(Trainer activeTrainerData) {
        TrainerController.activeTrainerData = activeTrainerData;
    }

    public void getTrainerPokemon(MainActivity mainListener){
        this.mainListener = mainListener;

        db = FirebaseDatabase.getInstance("https://pokemon-f8040-default-rtdb.asia-southeast1.firebasedatabase.app/");

        String user = activeTrainerData.getName() + "'s pokemon";

        userRef = db.getReference().child(user);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String pokemonId = childSnapshot.getKey();
                        String pokemonName = childSnapshot.child("pokemonName").getValue(String.class);
                        Integer pokemonLevel = childSnapshot.child("pokemonLevel").getValue(Integer.class);
                        Integer pokemonAttack = childSnapshot.child("pokemonAttack").getValue(Integer.class);
                        Integer pokemonMaxHP = childSnapshot.child("pokemonMaxHP").getValue(Integer.class);
                        PokeApiService.getCapturedPokemonDataFromAPIByName(pokemonId, pokemonName, TrainerController.this, pokemonLevel, pokemonMaxHP, pokemonAttack);
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

    public static void removeTrainerPokemon(Pokemon toDeletePokemon){
        activeTrainerData.getParty().remove(toDeletePokemon);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://pokemon-f8040-default-rtdb.asia-southeast1.firebasedatabase.app/");

        String user = TrainerController.getActiveTrainerData().getName();

        DatabaseReference pokeRef = db.getReference(user + "'s pokemon");

        pokeRef.child(toDeletePokemon.getPokemonId()).removeValue();
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

        if(currentExp + 5 >= maxExp){
            // Trainer Level Up!
            activeTrainerData.setLevel(activeTrainerData.getLevel() + 1);
            Integer bonusExp = currentExp + 5 - activeTrainerData.getBaseExp();

            activeTrainerData.setBaseExp(maxExp + 5);
            activeTrainerData.setExp(bonusExp);
        }
        else{
            activeTrainerData.setExp(currentExp + 5);
        }

    }

    public static ArrayList<Pokemon> getStarterList() {
        return starterList;
    }
}
