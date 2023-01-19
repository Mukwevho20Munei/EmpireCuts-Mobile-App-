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

public class SignUp extends AppCompatActivity {

    //Object of DatabaseReference
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://empirecuts-56a62-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText signUpEmail = findViewById(R.id.signup_email);
        final EditText contactNumber = findViewById(R.id.contact);
        final EditText signUpPassword = findViewById(R.id.signup_password);

        final Button signUpButton = findViewById(R.id.signup_button);
        final TextView loginRedirect = findViewById(R.id.loginRedirectText);

        //When SignUp button is pressed
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = signUpEmail.getText().toString();
                final String number = contactNumber.getText().toString();
                final String password = signUpPassword.getText().toString();

                //Check if info is entered
                if (email.isEmpty() || number.isEmpty() || password.isEmpty()) {
                    //Display the error message
                    Toast.makeText(SignUp.this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Check if the number wasn't registered before
                            if (snapshot.hasChild(number)) {
                                Toast.makeText(SignUp.this, "Already registered.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //Saving the data to the database
                                databaseReference.child("users").child(number).child("email").setValue(email);
                                databaseReference.child("users").child(number).child("password").setValue(password);

                                //Display message
                                Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, Login.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        //When the text "Already have an account" is pressed
        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });
    }
}