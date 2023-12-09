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
import id.ac.binus.pokemon.controller.BackpackController;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.items.Item;

public class StarterActivity extends AppCompatActivity {

    private ListView starter_listview;
    FirebaseDatabase db;
    DatabaseReference userRef, pokeRef, itemRef;

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

                HashMap<String, Object> userData = new HashMap<>();
                userData.put("user", user);
                userData.put("pass", pass);
                userData.put("gender", gender);

//                Starter
                HashMap<String, Object> pokeData = new HashMap<>();
                pokeData.put("pokemonName", pokeName);
                pokeData.put("pokemonLevel", pokeLvl);
                pokeData.put("pokemonType", pokeType);
                pokeData.put("pokemonAttack", pokeAtk);
                pokeData.put("pokemonMaxHP", pokeMaxHP);
                pokeData.put("pokemonHP", pokeHP);

//                Item
                HashMap<String, Object> hpUpData = new HashMap<>();
                hpUpData.put("itemId", 1);
                hpUpData.put("itemQuantity", 0);

                HashMap<String, Object> potionData = new HashMap<>();
                potionData.put("itemId", 2);
                potionData.put("itemQuantity", 0);

                HashMap<String, Object> proteinData = new HashMap<>();
                proteinData.put("itemId", 3);
                proteinData.put("itemQuantity", 0);

                HashMap<String, Object> rareCandyData = new HashMap<>();
                rareCandyData.put("itemId", 4);
                rareCandyData.put("itemQuantity", 0);

                HashMap<String, Object> reviveData = new HashMap<>();
                reviveData.put("itemId", 5);
                reviveData.put("itemQuantity", 0);

                db = FirebaseDatabase.getInstance("https://pokemon-f8040-default-rtdb.asia-southeast1.firebasedatabase.app/");
                userRef = db.getReference("users");
                pokeRef = db.getReference(user + "'s pokemon");
                itemRef = db.getReference(user + "'s backpack");


                pokeRef.child(pokeName).setValue(pokeData);
                itemRef.child("Hp Up").setValue(hpUpData);
                itemRef.child("Potion").setValue(potionData);
                itemRef.child("Protein").setValue(proteinData);
                itemRef.child("Rare Candy").setValue(rareCandyData);
                itemRef.child("Revive").setValue(reviveData);

                userRef.child(user).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(StarterActivity.this, "Trainer Data successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StarterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

                finish();

            }
        });
    }


}