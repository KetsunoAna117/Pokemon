package id.ac.binus.pokemon.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import id.ac.binus.pokemon.R;

public class LoginActivity extends AppCompatActivity {

    ImageView imgData;
    EditText userData, passData;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgData = findViewById(R.id.pokeLogo);
        userData = findViewById(R.id.userET);
        passData = findViewById(R.id.passET);
        btn = findViewById(R.id.btn);

        imgData.setImageResource(R.drawable.pokemon_logo);

    }
}