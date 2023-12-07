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
        db = FirebaseDatabase.getInstance("https://pokemon-f8040-default-rtdb.asia-southeast1.firebasedatabase.app/");

        userRef = db.getReference().child("users").child(user);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String userName = snapshot.child("user").getValue(String.class);
                    String passWord = snapshot.child("pass").getValue(String.class);

                    if(pass.equals(passWord)){
                        String gender = snapshot.child("gender").getValue(String.class);

                        assert gender != null;

                        if(gender.equals("male")){
                            TrainerController.setActiveTrainerData(new Trainer(userName, "Male", R.drawable.male_trainer, 1));
                        }
                        else if(gender.equals("female")){
                            TrainerController.setActiveTrainerData(new Trainer(userName, "Female", R.drawable.female_trainer, 1));
                        }

                        TrainerController.getActiveTrainerData().setExp(0);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "User not found in the database.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Toast.makeText(LoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}