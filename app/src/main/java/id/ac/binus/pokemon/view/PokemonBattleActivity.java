package id.ac.binus.pokemon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.AdventureController;
import id.ac.binus.pokemon.controller.BackpackController;
import id.ac.binus.pokemon.controller.MediaPlayerSingleton;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.items.Item;

public class PokemonBattleActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private BottomNavigationView nav;
    private ImageView pokemonAllySprite, pokemonEnemySprite;
    private ProgressBar pokemonAllyHpBar, pokemonEnemyHpBar;
    private Button adventure_activity_continue_button,adventure_activity_attack_button,
            adventure_activity_catch_pokemon_button, adventure_activity_run_button;
    private TextView adventure_activity_battle_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_battle);

        playBattleMusic();

        putEnemyPokemonData();
        putAllyPokemonData();
        declareActionMenu();

        nav = findViewById(R.id.bottom_nav);
        nav.setOnItemSelectedListener(this);

        Menu menu = nav.getMenu();
        menu.getItem(0).setChecked(false);
        menu.getItem(1).setChecked(true);

        // Commence battle
        onPlayerTurnEvent();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnHome:
                Intent homeIntent = new Intent(PokemonBattleActivity.this, MainActivity.class);
                startActivity(homeIntent);
                item.setChecked(true);
                return true;
            case R.id.mnBackpack:
                Intent backpackIntent = new Intent(PokemonBattleActivity.this, BackpackActivity.class);
                startActivity(backpackIntent);
                item.setChecked(true);
                return true;
        }
        return false;
    }

    private void putEnemyPokemonData(){
        pokemonEnemySprite = (ImageView) findViewById(R.id.pokemon_enemy_sprite);
        TextView pokemonLvl = (TextView) findViewById(R.id.pokemon_enemy_level);
        TextView pokemonName = (TextView) findViewById(R.id.pokemon_enemy_name);
        TextView pokemonType = (TextView) findViewById(R.id.pokemon_enemy_type);
        TextView pokemonHp = (TextView) findViewById(R.id.pokemon_enemy_hp);
        pokemonEnemyHpBar = (ProgressBar) findViewById(R.id.pokemon_enemy_hp_hpbar);
        pokemonEnemyHpBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        TextView pokemonAttackStats = (TextView) findViewById(R.id.pokemon_enemy_attack);
        pokemonAttackStats.setTextColor(Color.RED);

        Pokemon pokemon = AdventureController.getEnemyPokemon();
        Picasso.get().load(pokemon.getSprites().getFrontSprite()).into(pokemonEnemySprite);
        pokemonLvl.setText("Lv." + pokemon.getLevel());
        pokemonName.setText(pokemon.getName());
        pokemonType.setText(pokemon.getTypes().get(0).getTypeName().getName());
        pokemonHp.setText("HP: " + pokemon.getHp() + " / " + pokemon.getMaxHp());
        pokemonEnemyHpBar.setMax(pokemon.getMaxHp());
        pokemonEnemyHpBar.setProgress(pokemon.getHp());
        pokemonAttackStats.setText(pokemon.getAttackStats() + " ATK");
    }

    private void putAllyPokemonData(){
        pokemonAllySprite = (ImageView) findViewById(R.id.pokemon_ally_sprite);
        TextView pokemonLvl = (TextView) findViewById(R.id.pokemon_ally_level);
        TextView pokemonName = (TextView) findViewById(R.id.pokemon_ally_name);
        TextView pokemonType = (TextView) findViewById(R.id.pokemon_ally_type);
        TextView pokemonHp = (TextView) findViewById(R.id.pokemon_ally_hp);
        pokemonAllyHpBar = (ProgressBar) findViewById(R.id.pokemon_ally_hp_hpbar);
        pokemonAllyHpBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        TextView pokemonAttackStats = (TextView) findViewById(R.id.pokemon_ally_attack);
        pokemonAttackStats.setTextColor(Color.RED);

        Pokemon pokemon = TrainerController.getActiveTrainerData().getActivePokemon();
        Picasso.get().load(pokemon.getSprites().getBackSprite()).into(pokemonAllySprite);
        pokemonLvl.setText("Lv." + pokemon.getLevel());
        pokemonName.setText(pokemon.getName());
        pokemonType.setText(pokemon.getTypes().get(0).getTypeName().getName());
        pokemonHp.setText("HP: " + pokemon.getHp() + " / " + pokemon.getMaxHp());
        pokemonAllyHpBar.setMax(pokemon.getMaxHp());
        pokemonAllyHpBar.setProgress(pokemon.getHp());
        pokemonAttackStats.setText(pokemon.getAttackStats() + " ATK");
    }

    private void declareActionMenu(){
        adventure_activity_continue_button = findViewById(R.id.adventure_activity_continue_button);
        adventure_activity_attack_button = findViewById(R.id.adventure_activity_attack_button);
        adventure_activity_catch_pokemon_button = findViewById(R.id.adventure_activity_catch_pokemon_button);
        adventure_activity_run_button = findViewById(R.id.adventure_activity_run_button);
        adventure_activity_battle_message = findViewById(R.id.adventure_activity_battle_message);
    }

    // EVENT
    public void onPlayerTurnEvent(){
        showPlayerAction();

        adventure_activity_attack_button.setOnClickListener(event -> {
            hidePlayerAction();
            Pokemon attacking = TrainerController.getActiveTrainerData().getActivePokemon();
            Pokemon target = AdventureController.getEnemyPokemon();
            onPokemonAllyAttackEvent(attacking, target, pokemonEnemySprite);
        });

        adventure_activity_catch_pokemon_button.setOnClickListener(event -> {
            onPokemonCatchEvent();
        });

        adventure_activity_run_button.setOnClickListener(event -> {
            onPlayerRunEvent();
        });
    }

    public void onEnemyTurnEvent(){
        hidePlayerAction();

        Pokemon attacking = AdventureController.getEnemyPokemon();
        Pokemon target = TrainerController.getActiveTrainerData().getActivePokemon();

        onPokemonEnemyAttackEvent(attacking, target, pokemonAllySprite);
    }

    public void onPokemonAllyAttackEvent(Pokemon attacking, Pokemon target, ImageView pokemonSprite){
        hidePlayerAction();
        Log.d("DEBUG", "Ally Attack turn");

        onActionUpdateBattleMessage(attacking.getName() + " is attacking " + target.getName());

        adventure_activity_continue_button.setOnClickListener(event -> {
            hidePlayerAction();
            animateTakingDamage(pokemonSprite);
            Double multiplier = AdventureController.onPokemonAttackEvent(attacking, target, pokemonEnemyHpBar);
            putEnemyPokemonData();

            if(multiplier == 2){
                onActionUpdateBattleMessage("It's Super Effective!");
            }
            else if(multiplier == 0){
                onActionUpdateBattleMessage("It has no effect!");
            }
            else if(multiplier == 0.5){
                onActionUpdateBattleMessage("It's not very Effective");
            }

            if(target.getHp() <= 0){
                onPokemonFaintedEvent();
            }
            else{
                onEnemyTurnEvent();
            }
        });
    }

    public void onPokemonEnemyAttackEvent(Pokemon attacking, Pokemon target, ImageView pokemonSprite){
        hidePlayerAction();
        Log.d("DEBUG", "Enemy Attack turn");

        onActionUpdateBattleMessage(attacking.getName() + " is attacking " + target.getName());

        adventure_activity_continue_button.setOnClickListener(event -> {
            hidePlayerAction();
            animateTakingDamage(pokemonSprite);
            Double multiplier = AdventureController.onPokemonAttackEvent(attacking, target, pokemonAllyHpBar);
            putAllyPokemonData();

            if(multiplier == 2){
                onActionUpdateBattleMessage("It's Super Effective!");
            }
            else if(multiplier == 0){
                onActionUpdateBattleMessage("It has no effect!");
            }
            else if(multiplier == 0.5){
                onActionUpdateBattleMessage("It's not very Effective");
            }

            if(target.getHp() <= 0){
                onPokemonFaintedEvent();
            }
            else{
                onPlayerTurnEvent();
            }
        });
    }

    public void onPokemonCatchEvent(){
        hidePlayerAction();

        pokemonEnemySprite.setImageResource(R.drawable.pokeball);
        pokemonEnemySprite.setMaxWidth(50);
        pokemonEnemySprite.setMaxHeight(50);
        onActionUpdateBattleMessage("Successfully Catched Pokemon");
        playVictoryMusic();

        adventure_activity_continue_button.setOnClickListener(event -> {
            AdventureController controller = new AdventureController();
            Boolean status = controller.catchEnemyPokemonEvent(AdventureController.getEnemyPokemon());

            if(!status){
                Intent intent = new Intent(PokemonBattleActivity.this, SwitchPokemonActivity.class);
                startActivity(intent);
            }
            else {
                AdventureController.setEnemyPokemon(null);
                Intent intent = new Intent(PokemonBattleActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });

    }

    public void onPlayerRunEvent(){
        hidePlayerAction();

        onActionUpdateBattleMessage("Got Away Safely");
        adventure_activity_continue_button.setOnClickListener(event -> {
            AdventureController.setEnemyPokemon(null);
            Intent intent = new Intent(PokemonBattleActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }

    public void onPokemonFaintedEvent(){
        adventure_activity_continue_button.setOnClickListener(event_2 -> {
            // if opposing pokemon defeated
            if(AdventureController.getEnemyPokemon().getHp() <= 0){
                playVictoryMusic();
                onActionUpdateBattleMessage(AdventureController.getEnemyPokemon().getName() + " is fainted");
                adventure_activity_continue_button.setOnClickListener((event_3) -> {
                    Item item = BackpackController.getItemReward();
                    if(item != null){
                        onActionUpdateBattleMessage("You win the battle! \n" +
                                "You got 5 Trainer EXP! \n" +
                                "You got 1 " + item.getName().toUpperCase());
                    }
                    else{
                        onActionUpdateBattleMessage("You win the battle! \n" +
                                "You got 5 Trainer EXP! \n" +
                                "You got 1 ERROR");
                    }

                    adventure_activity_continue_button.setOnClickListener(event_4 -> {
                        TrainerController.addTrainerExp();
                        AdventureController.setEnemyPokemon(null);
                        Intent adventureIntent = new Intent(PokemonBattleActivity.this, MainActivity.class);
                        startActivity(adventureIntent);
                    });
                });
            }
//            if ally pokemon defeated
            else if(TrainerController.getActiveTrainerData().getActivePokemon().getHp() <= 0){
                onActionUpdateBattleMessage(TrainerController.getActiveTrainerData().getActivePokemon().getName() + " is fainted");
                adventure_activity_continue_button.setOnClickListener((event_3) -> {
                    onActionUpdateBattleMessage("You lose the battle!");
                    adventure_activity_continue_button.setOnClickListener(event_4 -> {
                        AdventureController.setEnemyPokemon(null);
                        Intent homeIntent = new Intent(PokemonBattleActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                    });

                });
            }
        });
    }

    public void onActionUpdateBattleMessage(String message){
        adventure_activity_battle_message.setText(message);
    }


    public void animateTakingDamage(ImageView imageView) {
        Handler handler = new Handler();

        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (finalI % 2 != 0) {
                        imageView.setVisibility(View.INVISIBLE);
                    } else {
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            }, i * 100);  // Delay each iteration by 100 milliseconds
        }
    }

    private void hidePlayerAction(){
        nav.setEnabled(false);
        adventure_activity_attack_button.setVisibility(View.GONE);
        adventure_activity_catch_pokemon_button.setVisibility(View.GONE);
        adventure_activity_run_button.setVisibility(View.GONE);
        adventure_activity_continue_button.setVisibility(View.VISIBLE);
    }

    private void showPlayerAction(){
        nav.setEnabled(true);
        adventure_activity_attack_button.setVisibility(View.VISIBLE);
        adventure_activity_catch_pokemon_button.setVisibility(View.VISIBLE);
        adventure_activity_run_button.setVisibility(View.VISIBLE);
        adventure_activity_continue_button.setVisibility(View.GONE);
    }

    private void playBattleMusic(){
        MediaPlayerSingleton mediaPlayerSingleton = MediaPlayerSingleton.getInstance();
        if(mediaPlayerSingleton.getCurrentMusic() != R.raw.battle_kanto)
            mediaPlayerSingleton.changeMediaPlayerSource(this, R.raw.battle_kanto);
    }

    private void playVictoryMusic(){
        MediaPlayerSingleton mediaPlayerSingleton = MediaPlayerSingleton.getInstance();
        if(mediaPlayerSingleton.getCurrentMusic() != R.raw.victory_kanto)
            mediaPlayerSingleton.changeMediaPlayerSource(this, R.raw.victory_kanto);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayerSingleton mediaPlayerSingleton = MediaPlayerSingleton.getInstance();
        mediaPlayerSingleton.releaseMediaPlayer();
    }

}