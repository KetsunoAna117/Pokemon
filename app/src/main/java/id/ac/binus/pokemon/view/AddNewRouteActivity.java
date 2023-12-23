package id.ac.binus.pokemon.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.RouteController;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.view.newRouteActivityFragment.FetchingDataFragment;
import id.ac.binus.pokemon.view.newRouteActivityFragment.NewRouteInformationFragment;

public class AddNewRouteActivity extends AppCompatActivity {
    private int counter;
    private ArrayList<Pokemon> pokemonFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_route);

        counter = 0;
        pokemonFound = new ArrayList<>();
        putLoadingProgress();
        getPokemonDataFromApi();

    }

    private void getPokemonDataFromApi(){
        Log.d("DEBUG", "get pokemon data event");
        RouteController controller = new RouteController();
        controller.getRoutePokemon(this);
    }

    public void onPokemonReceivedEvent(Pokemon pokemon){
        counter++;
        pokemonFound.add(pokemon);
        putLoadingProgress();
        Log.d("DEBUG", "onPokemonReceivedEvent: addNewRoute received pokemon: " + pokemon.getName());

        if(counter >= 6){
            // change fragment after all fetch data completed
            NewRouteInformationFragment fragment = new NewRouteInformationFragment(pokemonFound);
            getSupportFragmentManager().beginTransaction().replace(R.id.add_new_route_fragment, fragment).commit();
        }

    }

    private void putLoadingProgress(){
        FetchingDataFragment fetchingDataFragment = new FetchingDataFragment(counter);
        getSupportFragmentManager().beginTransaction().replace(R.id.add_new_route_fragment, fetchingDataFragment).commit();
    }
}