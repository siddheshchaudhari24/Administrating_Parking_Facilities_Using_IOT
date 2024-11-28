package com.siddhesh.majorproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Location_Form extends AppCompatActivity  {

    EditText lname,link;
    Button AddParking;
    FirebaseFirestore fstore;



String a;
    Date dTime;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_location_form);

        lname = findViewById(R.id.lname);
        link = findViewById(R.id.link);
        AddParking = findViewById(R.id.AddParking);

        fstore = FirebaseFirestore.getInstance();

        // Set the adapter to the RecyclerView

        AddParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Lname = lname.getText().toString();
                String Link = link.getText().toString();

                Map<String, Object> location = new HashMap<>();

                location.put("Location_Name", Lname);
                location.put("Location_Link", Link);


                fstore.collection("Locations")
                        .add(location)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                String locationName = location.get("Location_Name").toString();


                                Map<String, Object> slot = new HashMap<>();
                                slot.put("S1", false);
                                slot.put("S2", false);
                                slot.put("S3", false);
                                slot.put("S4", false);
                                slot.put("S5", false);
                                slot.put("S6", false);

                                fstore.collection("Slots").document(locationName)
                                        .set(slot)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getApplicationContext(), "Location and Slots added successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(), "Slots Failed to Add", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }


                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Location_Form.this, "Location Failed to Add", Toast.LENGTH_SHORT).show();
                            }
                        });

                Intent intent = new Intent(Location_Form.this, AdminActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }


}