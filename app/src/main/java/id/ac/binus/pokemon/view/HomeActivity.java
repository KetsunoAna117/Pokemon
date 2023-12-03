package id.ac.binus.pokemon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.OnPokemonButtonSwitchListener;
import id.ac.binus.pokemon.controller.OnPokemonLoadedListener;
import id.ac.binus.pokemon.controller.PokeApiService;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Pokemon;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, OnPokemonButtonSwitchListener {
    private BottomNavigationView nav;
    private TextView trainerName, trainerLevel, trainerExp;
    private ImageView trainerProfilePicture;
    private ProgressBar expBar;
    private ListView mypokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        onPokemonButtonClick();

        nav = findViewById(R.id.bottom_nav);
        nav.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnAdventure:
                Intent adventureIntent = new Intent(HomeActivity.this, AdventureActivity.class);
                startActivity(adventureIntent);
                item.setChecked(true);
                return true;
            case R.id.mnBackpack:
                Intent backpackIntent = new Intent(HomeActivity.this, BackpackActivity.class);
                startActivity(backpackIntent);
                item.setChecked(true);
                return true;
        }

        return false;
    }

    @SuppressLint("SetTextI18n")
    private void putTrainerData(){
        trainerName = (TextView) findViewById(R.id.trainer_name);
        trainerLevel = (TextView) findViewById(R.id.trainer_level);
        trainerExp = (TextView) findViewById(R.id.trainer_exp);
        trainerProfilePicture = (ImageView) findViewById(R.id.trainer_profile_picture);
        expBar = (ProgressBar) findViewById(R.id.trainer_exp_exp_bar);

        trainerName.setText("Trainer " + TrainerController.getActiveTrainerData().getName());
        trainerExp.setText("Exp. " +TrainerController.getActiveTrainerData().getExp().toString() + " / " + TrainerController.getActiveTrainerData().getBaseExp().toString());
        trainerLevel.setText("Lv." + TrainerController.getActiveTrainerData().getLevel().toString());

        trainerProfilePicture.setImageResource(TrainerController.getActiveTrainerData().getProfilePicture());
        expBar.setMax(TrainerController.getActiveTrainerData().getBaseExp());
        expBar.setProgress(TrainerController.getActiveTrainerData().getExp());
        expBar.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
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
        pokemonName.setText(TrainerController.getActiveTrainerData().getActivePokemon().getName());
        pokemonType.setText(TrainerController.getActiveTrainerData().getActivePokemon().getTypes().get(0).getTypeName().getName());
        pokemonHp.setText("HP: " + TrainerController.getActiveTrainerData().getActivePokemon().getHp() + " / " + TrainerController.getActiveTrainerData().getActivePokemon().getMaxHp());
        pokemonHpBar.setMax(TrainerController.getActiveTrainerData().getActivePokemon().getMaxHp());
        pokemonHpBar.setProgress(TrainerController.getActiveTrainerData().getActivePokemon().getHp());
        pokemonAttackStats.setText(TrainerController.getActiveTrainerData().getActivePokemon().getAttackStats() + " ATK");

    }



    @Override
    public void onPokemonButtonClick() {
        putTrainerData();
        putTrainerPokemonDataToAdapter();
        putActivePokemonData();
    }

}