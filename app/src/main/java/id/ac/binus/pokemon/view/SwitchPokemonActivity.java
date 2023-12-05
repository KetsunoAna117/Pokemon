package id.ac.binus.pokemon.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.Locale;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.AdventureController;
import id.ac.binus.pokemon.controller.OnPokemonButtonSwitchListener;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Pokemon;

public class SwitchPokemonActivity extends AppCompatActivity implements OnPokemonButtonSwitchListener {
    private ListView mypokemonList;
    private Button switch_pokemon_cancel_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_pokemon);

        putCaughtPokemonData();
        putAllHomeData();
    }

    private void putCaughtPokemonData(){
        ImageView pokemonSprite = (ImageView) findViewById(R.id.switch_pokemon_catched_pokemon_sprite);
        TextView pokemonLvl = (TextView) findViewById(R.id.switch_pokemon_catched_pokemon_level);
        TextView pokemonName = (TextView) findViewById(R.id.switch_pokemon_catched_pokemon_name);
        TextView pokemonType = (TextView) findViewById(R.id.switch_pokemon_catched_pokemon_type);
        TextView pokemonHp = (TextView) findViewById(R.id.switch_pokemon_catched_pokemon_hp);
        ProgressBar pokemonHpBar = (ProgressBar) findViewById(R.id.switch_pokemon_catched_pokemon_hpbar);
        pokemonHpBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        TextView pokemonAttackStats = (TextView) findViewById(R.id.switch_pokemon_catched_pokemon_attack);
        pokemonAttackStats.setTextColor(Color.RED);

        Pokemon p = AdventureController.getEnemyPokemon();

        Picasso.get().load(p.getSprites().getFrontSprite()).into(pokemonSprite);
        pokemonLvl.setText("Lv." + p.getLevel());
        pokemonName.setText(p.getName().toUpperCase(Locale.ROOT));
        pokemonType.setText(p.getTypes().get(0).getTypeName().getName().toUpperCase(Locale.ROOT));
        pokemonHp.setText("HP: " + p.getHp() + " / " + p.getMaxHp());
        pokemonHpBar.setMax(p.getMaxHp());
        pokemonHpBar.setProgress(p.getHp());
        pokemonAttackStats.setText(p.getAttackStats() + " ATK");
    }

    private void putTrainerPokemonDataToAdapter(){
        mypokemonList = (ListView) findViewById(R.id.mypokemon_list);
        LinkedList<Pokemon> party = TrainerController.getActiveTrainerData().getParty();

        MyPokemonAdapter adapter = new MyPokemonAdapter(this, R.layout.activity_my_pokemon_adapter, party, this);
        mypokemonList.setAdapter(adapter);
    }

    private void putActivePokemonData(){
        ImageView pokemonSprite = (ImageView) findViewById(R.id.mypokemon_active_sprite);
        TextView pokemonLvl = (TextView) findViewById(R.id.mypokemon_active_level);
        TextView pokemonName = (TextView) findViewById(R.id.mypokemon_active_name);
        TextView pokemonType = (TextView) findViewById(R.id.mypokemon_active_type);
        TextView pokemonHp = (TextView) findViewById(R.id.mypokemon_active_hp);
        ProgressBar pokemonHpBar = (ProgressBar) findViewById(R.id.mypokemon_active_hp_hpbar);
        pokemonHpBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        TextView pokemonAttackStats = (TextView) findViewById(R.id.mypokemon_active_attack);
        pokemonAttackStats.setTextColor(Color.RED);

        Picasso.get().load(TrainerController.getActiveTrainerData().getActivePokemon().getSprites().getFrontSprite()).into(pokemonSprite);
        pokemonLvl.setText("Lv." + TrainerController.getActiveTrainerData().getActivePokemon().getLevel());
        pokemonName.setText(TrainerController.getActiveTrainerData().getActivePokemon().getName().toUpperCase(Locale.ROOT));
        pokemonType.setText(TrainerController.getActiveTrainerData().getActivePokemon().getTypes().get(0).getTypeName().getName().toUpperCase(Locale.ROOT));
        pokemonHp.setText("HP: " + TrainerController.getActiveTrainerData().getActivePokemon().getHp() + " / " + TrainerController.getActiveTrainerData().getActivePokemon().getMaxHp());
        pokemonHpBar.setMax(TrainerController.getActiveTrainerData().getActivePokemon().getMaxHp());
        pokemonHpBar.setProgress(TrainerController.getActiveTrainerData().getActivePokemon().getHp());
        pokemonAttackStats.setText(TrainerController.getActiveTrainerData().getActivePokemon().getAttackStats() + " ATK");

    }

    public void putAllHomeData(){
        putTrainerPokemonDataToAdapter();
        putActivePokemonData();

        switch_pokemon_cancel_button = (Button) findViewById(R.id.switch_pokemon_cancel_button);
        switch_pokemon_cancel_button.setOnClickListener(event -> {
            AdventureController.setEnemyPokemon(null);
            Intent intent = new Intent(SwitchPokemonActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onPokemonSwitchButtonClick(Pokemon selectedPokemon) {
        TrainerController.getActiveTrainerData().setActivePokemon(selectedPokemon);
        putAllHomeData();
    }

    @Override
    public void onPokemonReleaseButtonClick(Pokemon selectedPokemon) {
        Pokemon caughtPokemon = AdventureController.getEnemyPokemon();
        if(selectedPokemon == TrainerController.getActiveTrainerData().getActivePokemon()){
            TrainerController.getActiveTrainerData().setActivePokemon(caughtPokemon);
        }
        TrainerController.getActiveTrainerData().getParty().add(caughtPokemon);
        TrainerController.removeTrainerPokemon(selectedPokemon);

        Intent intent = new Intent(SwitchPokemonActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}