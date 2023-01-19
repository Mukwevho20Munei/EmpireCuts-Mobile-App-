package com.example.empirecuts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //Object of DatabaseReference
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://empirecuts-56a62-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText customerNames = findViewById(R.id.customer_names);
        final EditText barberName = findViewById(R.id.barber_name);
        final EditText appointmentDate = findViewById(R.id.appointment_date);
        final EditText appointmentTime = findViewById(R.id.appointment_time);

        final Button bookingButton = findViewById(R.id.booking_button);

        //When the 'Add Booking' button is pressed
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String customerName = customerNames.getText().toString();
                final String barber = barberName.getText().toString();
                final String bookingDate = appointmentDate.getText().toString();
                final String bookingTime = appointmentTime.getText().toString();

                //Check if details are entered
                if (customerName.isEmpty() || barber.isEmpty() || bookingDate.isEmpty() || bookingTime.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all information", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Saving the data to the database
                    databaseReference.child("bookings").child(customerName).child("barber").setValue(barber);
                    databaseReference.child("bookings").child(customerName).child("booking date").setValue(bookingDate);
                    databaseReference.child("bookings").child(customerName).child("booking time").setValue(bookingTime);

                    //Display message
                    Toast.makeText(MainActivity.this, "Booking successful!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}