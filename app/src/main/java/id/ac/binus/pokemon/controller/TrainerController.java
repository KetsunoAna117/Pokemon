package id.ac.binus.pokemon.controller;

import java.util.LinkedList;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.Trainer;

public class TrainerController {
    private static Trainer activeTrainerData;

    public static Trainer getActiveTrainerData() {
        return activeTrainerData;
    }

    public static void setActiveTrainerData(Trainer activeTrainerData) {
        TrainerController.activeTrainerData = activeTrainerData;
    }

    public static LinkedList<String> getTrainerPokemon(Integer trainerId){
        // TODO mockup data, fill with SQL here
        LinkedList<String> pokemonIdList = new LinkedList<String>();
        pokemonIdList.add("glaceon");
        pokemonIdList.add("aggron");
        pokemonIdList.add("breloom");
        pokemonIdList.add("flygon");
        pokemonIdList.add("gardevoir");
        pokemonIdList.add("swampert");

        return pokemonIdList;
    }

}
