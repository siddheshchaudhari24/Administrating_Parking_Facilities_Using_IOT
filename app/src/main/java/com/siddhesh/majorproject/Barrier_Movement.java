package com.siddhesh.majorproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Barrier_Movement extends AppCompatActivity {

    DatabaseReference dbRef1,dbRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrier_movement);

        Button up = findViewById(R.id.upbarrier);
        Button down = findViewById(R.id.downbarrier);
        Button stop = findViewById(R.id.stopBarrier);
        Button home = findViewById(R.id.home);
        String Location =getIntent().getStringExtra("Location_Name");
        String Slot =  getIntent().getStringExtra("Slot_Num");

        dbRef1 = FirebaseDatabase.getInstance().getReference().child(Location).child(Slot).child("Motion").child("Clk");
        dbRef2 = FirebaseDatabase.getInstance().getReference().child(Location).child(Slot).child("Motion").child("AClk");

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef1.setValue(1);
                dbRef2.setValue(0);
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef2.setValue(1);
                dbRef1.setValue(0);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef1.setValue(0);
                dbRef2.setValue(0);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goback=new Intent(Barrier_Movement.this,slot_Activity.class);
                startActivity(goback);
                finish();
            }
        });
    }
}