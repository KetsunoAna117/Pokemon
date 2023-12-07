package id.ac.binus.pokemon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.TrainerController;

public class RegisterActivity extends AppCompatActivity {
    ImageView imgData;
    EditText userData, passData;
    Button btn;
    RadioGroup radioGender;
    DatabaseReference userRef;
    FirebaseDatabase db;
    private Integer pokemonCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgData = findViewById(R.id.pokeLogo);
        userData = findViewById(R.id.userET);
        passData = findViewById(R.id.passET);
        btn = findViewById(R.id.btn);
        radioGender = findViewById(R.id.radioGender);

        imgData.setImageResource(R.drawable.pokemon_logo);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPokemonDataFromApi();
            }
        });

    }

    private void getPokemonDataFromApi(){
        Log.d("DEBUG", "get starter pokemon data event");
        TrainerController controller = new TrainerController();
        controller.getStarterPokemon(this);
        pokemonCounter = 0;
    }


    public void onStarterReceivedEvent() {
        Log.d("DEBUG", "onStarterReceived event main");
        pokemonCounter++;

        if(pokemonCounter == 3){
            String user = userData.getText().toString().trim();
            String pass = passData.getText().toString().trim();

            int selectedGenderId = radioGender.getCheckedRadioButtonId();
            RadioButton selectedGender = findViewById(selectedGenderId);
            String gender = selectedGender != null ? selectedGender.getText().toString().toLowerCase(Locale.ROOT) : "";

            if (user.isEmpty() || pass.isEmpty() || selectedGenderId == -1) {
                Toast.makeText(RegisterActivity.this, "Please enter email, password, and gender", Toast.LENGTH_SHORT).show();
                return;
            }

            db = FirebaseDatabase.getInstance("https://pokemon-f8040-default-rtdb.asia-southeast1.firebasedatabase.app/");

            userRef = db.getReference().child("users").child(user);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(RegisterActivity.this, "User already exists in the database.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(RegisterActivity.this, StarterActivity.class);
                        intent.putExtra("user", user);
                        intent.putExtra("pass", pass);
                        intent.putExtra("gender", gender);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                    Toast.makeText(RegisterActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}