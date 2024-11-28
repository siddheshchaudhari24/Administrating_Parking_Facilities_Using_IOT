package com.siddhesh.majorproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Parking_layout extends AppCompatActivity {
    ImageView car1, car2, car3, car4, car5, car6, car7, car8, car9, car10, car11, car12;
    TextView p1,p2,p3,p4,p5,p6;

public  String x;
  private   String locationName;
    ImageView redcar1;
  ImageView redcar2;
  ImageView redcar3;
  ImageView redcar4;
  ImageView redcar5;
  ImageView redcar6;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_layout);

        setContentView(R.layout.activity_parking_layout);
        car1 = findViewById(R.id.car1);
        car2 = findViewById(R.id.car2);
        car3 = findViewById(R.id.car3);
        car4 = findViewById(R.id.car4);
        car5 = findViewById(R.id.car5);
        car6 = findViewById(R.id.car6);

        redcar1 = findViewById(R.id.redcar1);
        redcar2 = findViewById(R.id.redcar2);
        redcar3 = findViewById(R.id.redcar3);
        redcar4 = findViewById(R.id.redcar4);
        redcar5 = findViewById(R.id.redcar5);
        redcar6 = findViewById(R.id.redcar6);

        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        p3 = findViewById(R.id.p3);
        p4 = findViewById(R.id.p4);
        p5 = findViewById(R.id.p5);
        p6 = findViewById(R.id.p6);

         locationName = getIntent().getStringExtra("Location_Name");


        DocumentReference documentReference = db.collection("Slots").document(locationName);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                try {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            // Retrieve the slot status
                            boolean s1 = documentSnapshot.getBoolean("S1");
                            String stringValue1 = String.valueOf(s1);
                            if(stringValue1.equals("true")){
                                p1.setText("Booked");
                            }else{
                                p1.setText("Available");
                            }
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                } catch (Exception e) {
                    Log.e("Error", "Error occurred: " + e);
                    Toast.makeText(getApplicationContext(), "Exception Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                try {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            // Retrieve the slot status
                            boolean s2 = documentSnapshot.getBoolean("S2");
                            String stringValue2 = String.valueOf(s2);
                            if(stringValue2.equals("true")){
                                p2.setText("Booked");
                            }else{
                                p2.setText("Available");
                            }
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                } catch (Exception e) {
                    Log.e("Error", "Error occurred: " + e);
                    Toast.makeText(getApplicationContext(), "Exception Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                try {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            // Retrieve the slot status
                            boolean s3 = documentSnapshot.getBoolean("S3");
                            String stringValue3 = String.valueOf(s3);
                            if(stringValue3.equals("true")){
                                p3.setText("Booked");
                            }else{
                                p3.setText("Available");
                            }
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                } catch (Exception e) {
                    Log.e("Error", "Error occurred: " + e);
                    Toast.makeText(getApplicationContext(), "Exception Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                try {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            // Retrieve the slot status
                            boolean s4 = documentSnapshot.getBoolean("S4");
                            String stringValue4 = String.valueOf(s4);
                            if(stringValue4.equals("true")){
                                p4.setText("Booked");
                            }else{
                                p4.setText("Available");
                            }
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                } catch (Exception e) {
                    Log.e("Error", "Error occurred: " + e);
                    Toast.makeText(getApplicationContext(), "Exception Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                try {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            // Retrieve the slot status
                            boolean s5 = documentSnapshot.getBoolean("S5");
                            String stringValue5 = String.valueOf(s5);
                            if(stringValue5.equals("true")){
                                p5.setText("Booked");
                            }else{
                                p5.setText("Available");
                            }
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                } catch (Exception e) {
                    Log.e("Error", "Error occurred: " + e);
                    Toast.makeText(getApplicationContext(), "Exception Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                try {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            // Retrieve the slot status
                            boolean s6 = documentSnapshot.getBoolean("S6");
                            String stringValue6 = String.valueOf(s6);
                            if(stringValue6.equals("true")){
                                p6.setText("Booked");
                            }else{
                                p6.setText("Available");
                            }
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                } catch (Exception e) {
                    Log.e("Error", "Error occurred: " + e);
                    Toast.makeText(getApplicationContext(), "Exception Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });



        Intent intent = getIntent();
        boolean bookingStatus = intent.getBooleanExtra("bookingStatus", false);
        String bookedCar = intent.getStringExtra("bookedCar");

        // Check if booking is successful and a car is booked
        if (bookingStatus && bookedCar != null) {
            switch (bookedCar) {
                case "car1":
                    String s1 = p1.getText().toString();
                    if(s1.equals("Booked")) {

                        car1.setClickable(false);

                    }
                    break;
                case "car2":
                    String s2 = p2.getText().toString();
                    if(s2.equals("Booked")) {

                        car2.setClickable(false);

                    }
                    break;
                case "car3":
                    String s3 =p3.getText().toString();
                    if(s3.equals("Booked")) {

                        car3.setClickable(false);

                    }
                    break;
                case "car4":
                    String s4 =p4.getText().toString();
                    if(s4.equals("Booked")) {

                        car4.setClickable(false);

                    }
                    break;
                case "car5":
                    String s5 =p5.getText().toString();
                    if(s5.equals("Booked")) {

                        car5.setClickable(false);

                    }
                    break;
                case "car6":
                    String s6 =p6.getText().toString();
                    if(s6.equals("Booked")) {

                        car6.setClickable(false);

                    }
                    break;
            }
        } else {
            // Show all cars as available
            showAvailableCars();
        }
    }



    private void showAvailableCars() {
        // Show all cars as available
        car1.setVisibility(View.VISIBLE);
        car2.setVisibility(View.VISIBLE);
        car3.setVisibility(View.VISIBLE);
        car4.setVisibility(View.VISIBLE);
        car5.setVisibility(View.VISIBLE);
        car6.setVisibility(View.VISIBLE);


        // Set click listeners for all cars to open form
        car1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=locationName;
                x="1";
                String s1=p1.getText().toString();
                if(s1.equals("Available")) {
                    Intent intent = new Intent(Parking_layout.this, User_and_Vechical_Info.class);
                    intent.putExtra("Slot_Num", x);
                    intent.putExtra("Location_Name",name);
                    startActivity(intent);
                    finish();


                }

            }
        });

        car2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=locationName;
                x="2";
                String s2=p2.getText().toString();
                if(s2.equals("Available")) {
                    Intent intent = new Intent(Parking_layout.this, User_and_Vechical_Info.class);
                    intent.putExtra("Slot_Num", x);
                    intent.putExtra("Location_Name", name);
                    startActivity(intent);
                    finish();
                }

            }
        });

        car3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=locationName;
                x="3";
                String s3=p3.getText().toString();
                if(s3.equals("Available")) {
                    Intent intent = new Intent(Parking_layout.this, User_and_Vechical_Info.class);
                    intent.putExtra("Slot_Num", x);
                    intent.putExtra("Location_Name",name);
                    startActivity(intent);
                    finish();


                }
            }
        });

        car4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=locationName;
                x="4";
                String s4=p4.getText().toString();
                if(s4.equals("Available")) {
                    Intent intent = new Intent(Parking_layout.this, User_and_Vechical_Info.class);
                    intent.putExtra("Slot_Num", x);
                    intent.putExtra("Location_Name",name);
                    startActivity(intent);
                    finish();


                }
            }
        });

        car5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=locationName;
                x="5";
                String s5=p5.getText().toString();
                if(s5.equals("Available")) {
                    Intent intent = new Intent(Parking_layout.this, User_and_Vechical_Info.class);
                    intent.putExtra("Slot_Num", x);
                    intent.putExtra("Location_Name",name);
                    startActivity(intent);
                    finish();


                }
            }
        });

        car6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=locationName;
                x="6";
                String s6=p6.getText().toString();
                if(s6.equals("Available")) {
                    Intent intent = new Intent(Parking_layout.this, User_and_Vechical_Info.class);
                    intent.putExtra("Slot_Num", x);
                    intent.putExtra("Location_Name",name);
                    startActivity(intent);
                    finish();


                }
            }
        });
    }

}