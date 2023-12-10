package id.ac.binus.pokemon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.AuthenticationController;
import id.ac.binus.pokemon.controller.BackpackController;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.items.Item;

public class StarterActivity extends AppCompatActivity {

    private ListView starter_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        putStarterData();
    }

    private void putStarterData(){
        Log.d("DEBUG", "put starter data called");
        starter_listview = (ListView) findViewById(R.id.lvData);
        ArrayList<Pokemon> starters = TrainerController.getStarterList();

        StarterAdapter adapter = new StarterAdapter(this, R.layout.activity_starter_adapter, starters);
        starter_listview.setAdapter(adapter);

        starter_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Pokemon chosen = starters.get(i);

                Intent intent = getIntent();

                String user = intent.getStringExtra("user");
                String pass = intent.getStringExtra("pass");
                String gender = intent.getStringExtra("gender");

                String pokeName = chosen.getName();
                Integer pokeLvl = chosen.getLevel();
                String pokeType = chosen.getTypes().get(0).getTypeName().getName();
                Integer pokeAtk = chosen.getAttackStats();
                Integer pokeMaxHP = chosen.getMaxHp();
                Integer pokeHP = chosen.getMaxHp();

                AuthenticationController.newUser(user, pass, gender, pokeName, pokeLvl, pokeType, pokeAtk, pokeMaxHP, pokeHP, new AuthenticationController.UserExistenceCallback() {
                    @Override
                    public void onResult(Boolean bool) {
                        if(bool){
                            Toast.makeText(StarterActivity.this, "Trainer Data successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StarterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });

                finish();

            }
        });
    }

}