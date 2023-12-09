package id.ac.binus.pokemon.controller;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Vector;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.Route;
import id.ac.binus.pokemon.view.HomeActivity;
import id.ac.binus.pokemon.view.SwitchPokemonActivity;

public class AdventureController{
    private static Route activeRoute = null;
    private static Pokemon enemyPokemon;

    public static Route getActiveRoute() {
        return activeRoute;
    }

    public static void setActiveRoute(Route activeRoute) {
        AdventureController.activeRoute = activeRoute;
    }

    public static Vector<Route> getAllRoutes(){
        // TODO Add SQL to get Routes saved in database
        Vector<Route> routes = new Vector<Route>();

        Vector<String> pokemonList = new Vector<String>();
        pokemonList.add("charmander");
        pokemonList.add("squirtle");
        pokemonList.add("bulbasaur");

        routes.add(new Route("Route 1", 1, 10, pokemonList));

        pokemonList = new Vector<String>();
        pokemonList.add("rayquaza");
        pokemonList.add("arceus");

        routes.add(new Route("Route 2", 20, 50, pokemonList));

        return routes;
    }

    public static void findEnemy(OnPokemonLoadedListener onPokemonLoadedListener){
        if(enemyPokemon == null){
            Integer randomPokemonIndex = Helper.getRandomNumber(0, getActiveRoute().getAreaPokemonList().size() - 1);
            PokeApiService.getWildPokemonDataFromAPIByName(getActiveRoute().getAreaPokemonList().get(randomPokemonIndex), onPokemonLoadedListener);
        }
    }

    public static Pokemon getEnemyPokemon() {
        return enemyPokemon;
    }

    public static void setEnemyPokemon(Pokemon enemyPokemon) {
        AdventureController.enemyPokemon = enemyPokemon;
    }

    public Boolean catchEnemyPokemonEvent(Pokemon pokemon){
        if(TrainerController.getActiveTrainerData().getParty().size() >= 6){
            Log.d("DEBUG", "party is full!");
            return false;
        }

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://pokemon-f8040-default-rtdb.asia-southeast1.firebasedatabase.app/");

        String user = TrainerController.getActiveTrainerData().getName();

        DatabaseReference pokeRef = db.getReference(user + "'s pokemon");

        DatabaseReference newChildRef = pokeRef.push();
        String key = newChildRef.getKey();

        pokemon.setPokemonId(key);

        HashMap<String, Object> pokeData = new HashMap<>();
        pokeData.put("pokemonName", pokemon.getName());
        pokeData.put("pokemonLevel", pokemon.getLevel());
        pokeData.put("pokemonType", pokemon.getTypes().get(0).getTypeName().getName());
        pokeData.put("pokemonAttack", pokemon.getAttackStats());
        pokeData.put("pokemonMaxHP", pokemon.getMaxHp());
        pokeData.put("pokemonHP", pokemon.getHp());

        pokeRef.child(key).setValue(pokeData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                TrainerController.getActiveTrainerData().getParty().add(pokemon);
                AdventureController.setEnemyPokemon(null);
            }
        });

        return true;
    }

    public static Double onPokemonAttackEvent(Pokemon attacking, Pokemon target, ProgressBar targetProgressBar){
        Double multiplier = calculateDamage(attacking.getTypes().get(0).getTypeName().getName(), target.getTypes().get(0).getTypeName().getName());
        Double damage = attacking.getAttackStats() * multiplier;

        Integer hpChange = target.getHp() - damage.intValue();
        if(hpChange > 0){
            targetProgressBar.setProgress(hpChange, true);
            target.setHp(hpChange);
        }
        else {
            targetProgressBar.setProgress(0, true);
            target.setHp(0);
        }
        return multiplier;

    }

    public static Double calculateDamage(String typeAttack, String typeDefend){
        // TODO Add Type Matchup table in SQLLite and write here
        return 1.0;
    }



}
