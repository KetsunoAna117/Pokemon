package id.ac.binus.pokemon.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.Vector;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.AdventureController;
import id.ac.binus.pokemon.controller.BackpackController;
import id.ac.binus.pokemon.controller.MediaPlayerSingleton;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Route;

public class MainActivity extends AppCompatActivity {
    private Integer pokemonCounter;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(TrainerController.getActiveTrainerData() == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        else{
            playMusic();

            loadingProgressBar = findViewById(R.id.main_loading_progress_bar);
            loadingProgressBar.setMax(6);

            Vector<Route> getRoutes = AdventureController.getAllRoutes();
            AdventureController.setActiveRoute(getRoutes.get(0));
            AdventureController.setEnemyPokemon(null);

            BackpackController.loadTrainerBackpackData();

            getPokemonDataFromApi();
        }
    }

    private void renderProgressBar(Integer progress){
        loadingProgressBar.setProgress(progress, true);
    }

    private void getPokemonDataFromApi(){
        Log.d("DEBUG", "get pokemon data event");
        TrainerController controller = new TrainerController();
        controller.getTrainerPokemon(this);
        pokemonCounter = 0;

    }

    public void onPokemonReceivedEvent() {
        Log.d("DEBUG", "onPokemonReceived event main");
        pokemonCounter++;
        renderProgressBar(pokemonCounter);

        if(pokemonCounter > 0){
            TrainerController.getActiveTrainerData().setActivePokemon(TrainerController.getActiveTrainerData().getParty().get(0));
            // After get done, load home
            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(homeIntent);
        }
    }

    private void playMusic(){
        MediaPlayerSingleton mediaPlayerSingleton = MediaPlayerSingleton.getInstance();
        mediaPlayerSingleton.initializeMediaPlayer(this, R.raw.evergrandecity);
        mediaPlayerSingleton.startMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayerSingleton mediaPlayerSingleton = MediaPlayerSingleton.getInstance();
        mediaPlayerSingleton.releaseMediaPlayer();
    }

}