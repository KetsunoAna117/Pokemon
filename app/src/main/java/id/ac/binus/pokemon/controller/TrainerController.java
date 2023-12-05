package id.ac.binus.pokemon.controller;

import android.util.Log;

import java.util.LinkedList;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.Trainer;
import id.ac.binus.pokemon.view.MainActivity;

public class TrainerController implements OnPokemonLoadedListener {
    private static Trainer activeTrainerData;
    private MainActivity listener;

    public static Trainer getActiveTrainerData() {
        return activeTrainerData;
    }

    public static void setActiveTrainerData(Trainer activeTrainerData) {
        TrainerController.activeTrainerData = activeTrainerData;
    }

    public void getTrainerPokemon(MainActivity mainListener){
        this.listener = mainListener;

        // TODO mockup data, fill with SQL here
        LinkedList<String> pokemonIdList = new LinkedList<String>();
        pokemonIdList.add("glaceon");
        pokemonIdList.add("aggron");
        pokemonIdList.add("breloom");
        pokemonIdList.add("flygon");
        pokemonIdList.add("gardevoir");
        pokemonIdList.add("swampert");

        for(String name: pokemonIdList){
            // TODO LEVEL, MAX HP, AND ATTACK STATS SHOULD BE REPLACED USING THE DATA FROM DATABASE
            PokeApiService.getCapturedPokemonDataFromAPIByName(name, this, 100, 50,  5);
        }
    }

    public static void removeTrainerPokemon(Pokemon toDeletePokemon){
        // TODO ADD SQL LOGIC TO DELETE POKEMON HERE
        activeTrainerData.getParty().remove(toDeletePokemon);
    }

    @Override
    public void onPokemonReceived(Pokemon pokemon) {
        Log.d("DEBUG", "onPokemonReceived event");
        TrainerController.getActiveTrainerData().getParty().add(pokemon);
        listener.onPokemonReceivedEvent();
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
}
