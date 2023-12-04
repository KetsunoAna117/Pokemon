package id.ac.binus.pokemon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Vector;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.AdventureController;

public class AdventureActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    private BottomNavigationView nav;
    private TextView adventure_activity_current_route, adventure_activity_current_route_pokemon;
    private MaterialButton adventure_activity_switch_route_button;
    private  Button adventure_activity_find_pokemon_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure);

        nav = findViewById(R.id.bottom_nav);
        nav.setOnItemSelectedListener(this);

        Menu menu = nav.getMenu();
        menu.getItem(0).setChecked(false);
        menu.getItem(1).setChecked(true);

        putView();
        declareButtonClickEvent();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnHome:
                Intent homeIntent = new Intent(AdventureActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                item.setChecked(true);
                return true;
            case R.id.mnBackpack:
                Intent backpackIntent = new Intent(AdventureActivity.this, BackpackActivity.class);
                startActivity(backpackIntent);
                item.setChecked(true);
                return true;
        }
        return false;
    }

    private void putView(){
        if(AdventureController.getActiveRoute() == null){
            Intent areaSelectionIntent = new Intent(AdventureActivity.this, AreaSelectionActivity.class);
            startActivity(areaSelectionIntent);
        }
        else{
            adventure_activity_current_route = (TextView) findViewById(R.id.adventure_activity_current_route);
            adventure_activity_current_route_pokemon = (TextView) findViewById(R.id.adventure_activity_current_route_pokemon);
            adventure_activity_switch_route_button = (MaterialButton) findViewById(R.id.adventure_activity_switch_route_button);

            adventure_activity_current_route.setText(AdventureController.getActiveRoute().getRouteName());
            Vector<String> pokemonList = AdventureController.getActiveRoute().getAreaPokemonList();
            for(int i=0; i<pokemonList.size(); i++){
                if(i < pokemonList.size() - 1){
                    adventure_activity_current_route_pokemon.append(pokemonList.get(i) + ", ");
                }
                else{
                    adventure_activity_current_route_pokemon.append(pokemonList.get(i));
                }
            }

            adventure_activity_switch_route_button.setBackgroundColor(Color.WHITE);
            adventure_activity_switch_route_button.setTextColor(Color.BLUE);
            adventure_activity_switch_route_button.setStrokeColor(ColorStateList.valueOf(Color.BLUE));
            adventure_activity_switch_route_button.setStrokeWidth(3);
            adventure_activity_switch_route_button.setOnClickListener(view -> {
                Intent areaSelectionIntent = new Intent(AdventureActivity.this, AreaSelectionActivity.class);
                startActivity(areaSelectionIntent);
            });

        }
    }

    private void declareButtonClickEvent(){
        adventure_activity_find_pokemon_button = (Button) findViewById(R.id.adventure_activity_find_pokemon_button);
        adventure_activity_find_pokemon_button.setOnClickListener(view -> {
            Intent battleIntent = new Intent(AdventureActivity.this, PokemonBattleActivity.class);
            startActivity(battleIntent);
        });
    }
}