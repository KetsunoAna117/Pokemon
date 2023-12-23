package id.ac.binus.pokemon.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

import id.ac.binus.pokemon.listener.OnPokemonLoadedListener;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.Route;
import id.ac.binus.pokemon.utils.Helper;
import id.ac.binus.pokemon.view.AddNewRouteActivity;
import id.ac.binus.pokemon.view.MainActivity;

public class RouteController {
    public void getRoutePokemon(AddNewRouteActivity callback){
        for(int i=0; i<6; i++){
            Integer randomPokemonDexId = Helper.getRandomNumber(1, 649);
            PokeApiService.getWildPokemonDataFromAPIByName(randomPokemonDexId.toString(), new OnPokemonLoadedListener() {
                @Override
                public void onPokemonReceived(Pokemon pokemon) {
                    callback.onPokemonReceivedEvent(pokemon);
                }

                @Override
                public void onStarterReceived(Pokemon pokemon) {

                }
            });
        }
    }

    public static String insertNewRoute(Context context, String routeName, int minLevel, int maxLevel, ArrayList<Pokemon> pokemonFound){
        // check if route name is empty
        if(routeName.isEmpty()){
            return "Route name cannot be empty!";
        }

        SQLiteHelper db = new SQLiteHelper(context);

        // check if route name already exist
        // if already exist, don't insert
        Route routeFound = db.getRouteByRouteName(routeName);
        if(routeFound != null){
            return "Error! Route has been created, Please choose another route name";
        }

        db.insertRoute(routeName, minLevel, maxLevel);
        for(Pokemon pokemon : pokemonFound){
            db.insertPokemonForRoute(routeName, pokemon.getName());
        }

        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        return "";
    }
}
