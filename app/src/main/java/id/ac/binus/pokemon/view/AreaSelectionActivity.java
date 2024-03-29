package id.ac.binus.pokemon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Vector;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.AdventureController;
import id.ac.binus.pokemon.utils.MediaPlayerSingleton;
import id.ac.binus.pokemon.listener.OnRouteSwitchListener;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Route;
import id.ac.binus.pokemon.view.adapter.AreaAdapter;

public class AreaSelectionActivity extends AppCompatActivity implements OnRouteSwitchListener, NavigationBarView.OnItemSelectedListener {
    ListView area_selection_list_view;
    private BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_selection);
        putAllRoutesData();

        nav = findViewById(R.id.bottom_nav);
        nav.setOnItemSelectedListener(this);

        Menu menu = nav.getMenu();
        menu.getItem(0).setChecked(false);
        menu.getItem(1).setChecked(true);
    }

    private void putAllRoutesData(){
        area_selection_list_view = (ListView) findViewById(R.id.area_selection_list_view);
        AdventureController controller = new AdventureController();
        Vector<Route> routes = AdventureController.getAllRoutes(this);

        AreaAdapter areaAdapter = new AreaAdapter(this, R.layout.activity_area_adapter, routes, this);
        area_selection_list_view.setAdapter(areaAdapter);
    }

    @Override
    public void onRouteSwitched(Route route) {
        Intent homeIntent = new Intent(AreaSelectionActivity.this, AdventureActivity.class);
        startActivity(homeIntent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnHome:
                Intent homeIntent = new Intent(AreaSelectionActivity.this, MainActivity.class);
                startActivity(homeIntent);
                item.setChecked(true);
                return true;
            case R.id.mnBackpack:
                Intent backpackIntent = new Intent(AreaSelectionActivity.this, BackpackActivity.class);
                startActivity(backpackIntent);
                item.setChecked(true);
                return true;
            case R.id.mnLogout:
                if(AdventureController.getEnemyPokemon() == null){
                    MediaPlayerSingleton.getInstance().stopMediaPlayer();
                    TrainerController.setActiveTrainerData(null);
                    TrainerController.setMainInitFlag(true);
                    Intent mainIntent = new Intent(AreaSelectionActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                }
                else{
                    Toast.makeText(this, "Can't logout! You are still fighting an enemy!",
                            Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return false;
    }
}