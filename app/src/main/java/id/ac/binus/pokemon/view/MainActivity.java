package id.ac.binus.pokemon.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.LinkedList;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.OnPokemonLoadedListener;
import id.ac.binus.pokemon.controller.PokeApiService;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.Trainer;

public class MainActivity extends AppCompatActivity implements OnPokemonLoadedListener {
    private Integer pokemonCounter;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingProgressBar = findViewById(R.id.main_loading_progress_bar);
        loadingProgressBar.setMax(6);

        TrainerController.setActiveTrainerData(new Trainer("Hans", "Male", R.drawable.male_trainer));
        TrainerController.getActiveTrainerData().setExp(8);
        getPokemonDataFromApi();
    }

    private void renderProgressBar(Integer progress){
        loadingProgressBar.setProgress(progress, true);
    }

    private void getPokemonDataFromApi(){
        pokemonCounter = 0;
        LinkedList<String> pokemonNameList = TrainerController.getTrainerPokemon(TrainerController.getActiveTrainerData().getTrainedId());
        for(String name: pokemonNameList){
            PokeApiService.getPokemonByName(name, this);
        }
    }

    @Override
    public void onPokemonReceived(Pokemon pokemon) {
        TrainerController.getActiveTrainerData().getParty().add(pokemon);
        pokemonCounter++;
        renderProgressBar(pokemonCounter);

        if(pokemonCounter == 6){
            TrainerController.getActiveTrainerData().setActivePokemon(TrainerController.getActiveTrainerData().getParty().get(0));
            // After get done, load home
            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(homeIntent);
        }
    }

}