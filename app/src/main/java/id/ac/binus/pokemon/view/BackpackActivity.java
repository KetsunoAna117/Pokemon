package id.ac.binus.pokemon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import id.ac.binus.pokemon.R;

public class BackpackActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpack);

        nav = findViewById(R.id.bottom_nav);
        nav.setOnItemSelectedListener(this);

        Menu menu = nav.getMenu();
        menu.getItem(0).setChecked(false);
        menu.getItem(2).setChecked(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnHome:
                Intent homeIntent = new Intent(BackpackActivity.this, HomeActivity.class);
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
}