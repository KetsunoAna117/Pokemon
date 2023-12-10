package id.ac.binus.pokemon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.AuthenticationController;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Trainer;

public class LoginActivity extends AppCompatActivity {

    ImageView imgData;
    EditText userData, passData;
    Button loginBtn, regisBtn;
    DatabaseReference userRef, mDatabase;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgData = findViewById(R.id.pokeLogo);
        userData = findViewById(R.id.userET);
        passData = findViewById(R.id.passET);
        loginBtn = findViewById(R.id.loginBtn);
        regisBtn = findViewById(R.id.regisBtn);

        imgData.setImageResource(R.drawable.pokemon_logo);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loginUser() {
        String user = userData.getText().toString().trim();
        String pass = passData.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthenticationController.checkUserExist(user, new AuthenticationController.UserExistenceCallback() {
            @Override
            public void onResult(Boolean userExists) {
                if (userExists) {
                    AuthenticationController.userLogin(user, pass, new AuthenticationController.UserExistenceCallback() {
                        @Override
                        public void onResult(Boolean passCorrect) {
                            if (passCorrect) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "User not found in the database.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}