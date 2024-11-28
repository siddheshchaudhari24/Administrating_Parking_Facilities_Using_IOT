package com.siddhesh.majorproject;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDashboard extends AppCompatActivity {

    Button viewlocations;
    TextView rStatus;
    Button logout;
    FirebaseAuth mAuth;


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_dashboard);

        viewlocations = findViewById(R.id.viewlocations);
        rStatus = findViewById(R.id.rStatus);
        logout = findViewById(R.id.logout);

        mAuth = FirebaseAuth.getInstance();



        // Set the button click listener directly on the adapter

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Query Firestore for the request status based on the user's email
            db.collection("RequestStatus")
                    .document(email)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                // Retrieve the request status from the document
                                String status = documentSnapshot.getString("status");
                                if (status != null) {
                                    Log.e(TAG, "Fetching request status");
                                    // Set the retrieved status to the TextView
                                    rStatus.setText(status);
                                } else {
                                    Log.e(TAG, "Error fetching request status");
                                }
                            } else {
                                Log.e(TAG, "Document not found");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle any errors
                            Log.e(TAG, "Error fetching request status: " + e.getMessage());

                        }
                    });
        }




        viewlocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), slot_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mAuth.signOut();
                    Intent intent=new Intent(UserDashboard.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e){
                    Toast.makeText(UserDashboard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(e.getMessage());
                    Log.d("logout",e.getMessage());
                }

            }
        });


    }



}
