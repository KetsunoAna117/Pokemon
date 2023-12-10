package id.ac.binus.pokemon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.BackpackController;
import id.ac.binus.pokemon.controller.OnPokemonItemClickedListener;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.items.Item;

public class BackpackActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, OnPokemonItemClickedListener {
    private BottomNavigationView nav;
    private ListView backpack_items_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpack);

        nav = findViewById(R.id.bottom_nav);
        nav.setOnItemSelectedListener(this);

        putActivePokemonData();
        putBackpackData();

        Menu menu = nav.getMenu();
        menu.getItem(0).setChecked(false);
        menu.getItem(2).setChecked(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnHome:
                Intent homeIntent = new Intent(BackpackActivity.this, MainActivity.class);
                startActivity(homeIntent);
                item.setChecked(true);
                return true;
            case R.id.mnAdventure:
                Intent adventureIntent = new Intent(BackpackActivity.this, AdventureActivity.class);
                startActivity(adventureIntent);
                item.setChecked(true);
                return true;
        }

        return false;
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

    private void putBackpackData(){
        Log.d("DEBUG", "put backpack data called");
        backpack_items_listview = (ListView) findViewById(R.id.backpack_items_listview);
        Vector<Item> backpacks = BackpackController.getTrainerBackpackData();

        ItemAdapter adapter = new ItemAdapter(this, R.layout.activity_item_adapter, backpacks, this);
        backpack_items_listview.setAdapter(adapter);
    }

    @Override
    public void onItemClickedEvent(Item selectedItem) {
        Pokemon activePokemon = TrainerController.getActiveTrainerData().getActivePokemon();

        Boolean status = BackpackController.useItem(selectedItem, activePokemon);
        if(status){
            Toast.makeText(this, "Success",
                    Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Failed to use item",
                    Toast.LENGTH_LONG).show();
        }
        Log.d("DEBUG", "item: " + selectedItem.getName() + " quantity: " + selectedItem.getQuantity());

        putActivePokemonData();
        putBackpackData();
    }
}