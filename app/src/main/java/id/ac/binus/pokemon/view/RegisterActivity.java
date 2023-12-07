package id.ac.binus.pokemon.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.TrainerController;

public class RegisterActivity extends AppCompatActivity {
    ImageView imgData;
    EditText userData, passData;
    Button btn;

    private Integer pokemonCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgData = findViewById(R.id.pokeLogo);
        userData = findViewById(R.id.userET);
        passData = findViewById(R.id.passET);
        btn = findViewById(R.id.btn);

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

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(RegisterActivity.this, StarterActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("pass", pass);
            startActivity(intent);
        }
    }
}