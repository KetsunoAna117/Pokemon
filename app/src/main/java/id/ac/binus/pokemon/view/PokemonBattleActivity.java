package id.ac.binus.pokemon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.AdventureController;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Pokemon;

public class PokemonBattleActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_battle);

        putEnemyPokemonData();
        putAllyPokemonData();

        nav = findViewById(R.id.bottom_nav);
        nav.setOnItemSelectedListener(this);

        Menu menu = nav.getMenu();
        menu.getItem(0).setChecked(false);
        menu.getItem(1).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnHome:
                Intent homeIntent = new Intent(PokemonBattleActivity.this, HomeActivity.class);
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
        ImageView pokemonSprite = (ImageView) findViewById(R.id.pokemon_enemy_sprite);
        TextView pokemonLvl = (TextView) findViewById(R.id.pokemon_enemy_level);
        TextView pokemonName = (TextView) findViewById(R.id.pokemon_enemy_name);
        TextView pokemonType = (TextView) findViewById(R.id.pokemon_enemy_type);
        TextView pokemonHp = (TextView) findViewById(R.id.pokemon_enemy_hp);
        ProgressBar pokemonHpBar = (ProgressBar) findViewById(R.id.pokemon_enemy_hp_hpbar);
        pokemonHpBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        TextView pokemonAttackStats = (TextView) findViewById(R.id.pokemon_enemy_attack);
        pokemonAttackStats.setTextColor(Color.RED);

        Pokemon pokemon = AdventureController.getEnemyPokemon();
        Picasso.get().load(pokemon.getSprites().getFrontSprite()).into(pokemonSprite);
        pokemonLvl.setText("Lv." + pokemon.getLevel());
        pokemonName.setText(pokemon.getName());
        pokemonType.setText(pokemon.getTypes().get(0).getTypeName().getName());
        pokemonHp.setText("HP: " + pokemon.getHp() + " / " + pokemon.getMaxHp());
        pokemonHpBar.setMax(pokemon.getMaxHp());
        pokemonHpBar.setProgress(pokemon.getHp());
        pokemonAttackStats.setText(pokemon.getAttackStats() + " ATK");
    }

    private void putAllyPokemonData(){
        ImageView pokemonSprite = (ImageView) findViewById(R.id.pokemon_ally_sprite);
        TextView pokemonLvl = (TextView) findViewById(R.id.pokemon_ally_level);
        TextView pokemonName = (TextView) findViewById(R.id.pokemon_ally_name);
        TextView pokemonType = (TextView) findViewById(R.id.pokemon_ally_type);
        TextView pokemonHp = (TextView) findViewById(R.id.pokemon_ally_hp);
        ProgressBar pokemonHpBar = (ProgressBar) findViewById(R.id.pokemon_ally_hp_hpbar);
        pokemonHpBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        TextView pokemonAttackStats = (TextView) findViewById(R.id.pokemon_ally_attack);
        pokemonAttackStats.setTextColor(Color.RED);

        Pokemon pokemon = TrainerController.getActiveTrainerData().getActivePokemon();
        Picasso.get().load(pokemon.getSprites().getBackSprite()).into(pokemonSprite);
        pokemonLvl.setText("Lv." + pokemon.getLevel());
        pokemonName.setText(pokemon.getName());
        pokemonType.setText(pokemon.getTypes().get(0).getTypeName().getName());
        pokemonHp.setText("HP: " + pokemon.getHp() + " / " + pokemon.getMaxHp());
        pokemonHpBar.setMax(pokemon.getMaxHp());
        pokemonHpBar.setProgress(pokemon.getHp());
        pokemonAttackStats.setText(pokemon.getAttackStats() + " ATK");
    }
}