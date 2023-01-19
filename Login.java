package com.example.empirecuts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    //Object of DatabaseReference
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://empirecuts-56a62-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText loginNumber = findViewById(R.id.login_number);
        final EditText loginPassword = findViewById(R.id.login_password);

        final Button loginButton = findViewById(R.id.login_button);
        final Button adminButton = findViewById(R.id.admin_button);
        final TextView signUpRedirect = findViewById(R.id.signupRedirectText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String number = loginNumber.getText().toString();
                final String password = loginPassword.getText().toString();

                if (number.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Check if user exists in the database
                            if (snapshot.hasChild(number)) {
                                //Match the password entered with the one in the database
                                final String getPassword = snapshot.child(number).child("password").getValue(String.class);

                                if (getPassword.equals(password)) {
                                    Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(Login.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(Login.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        signUpRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String adminNumber = loginNumber.getText().toString();
                final String adminPassword = loginPassword.getText().toString();

                if (adminNumber.equals("admin123") || adminPassword.equals("admin123")) {
                    startActivity(new Intent(Login.this, List.class));
                }
                else {
                    Toast.makeText(Login.this, "Enter the correct Admin credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}