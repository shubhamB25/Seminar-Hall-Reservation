package com.example.seminarhall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addHall extends AppCompatActivity {

    EditText editTextHallName;
    EditText editTextHallSize;
    Button addToDb;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hall);
        editTextHallName = findViewById(R.id.Hall_Name);
        editTextHallSize = findViewById(R.id.Hall_Size);
        addToDb = findViewById(R.id.Add_a);
        databaseReference = FirebaseDatabase.getInstance().getReference("Halls");

        addToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHall();
            }
        });
    }

    private void addHall() {

        String hallName= editTextHallName.getText().toString().trim();
        String size_string = editTextHallSize.getText().toString().trim();
        int size = Integer.parseInt(size_string);

        //checking if the value is provided
        if (!TextUtils.isEmpty(hallName)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseReference.push().getKey();

            //creating an Hall Object
            Hall hall = new Hall( hallName, size,id);

            //Saving to database
            databaseReference.child(id).setValue(hall);

            //setting edittext to blank again
            editTextHallName.setText("");
            editTextHallSize.setText("");


            //displaying a success toast
            Toast.makeText(this, "Hall Added to Database", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast

            Toast.makeText(this, "Please Correct Values ", Toast.LENGTH_LONG).show();
        }
    }
}