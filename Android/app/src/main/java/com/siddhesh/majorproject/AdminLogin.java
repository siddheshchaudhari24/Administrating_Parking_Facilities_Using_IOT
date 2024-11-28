package com.siddhesh.majorproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class AdminLogin extends AppCompatActivity {

    TextInputEditText getemail, getpassword;

    Button signin, home;

    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


         mAuth = FirebaseAuth.getInstance();
         getemail = findViewById(R.id.varad1); // Change to use EditText
         getpassword = findViewById(R.id.varad2); // Change to use EditText
         signin = findViewById(R.id.signin);
        home = findViewById(R.id.home);
         mAuth=FirebaseAuth.getInstance();
         fStore=FirebaseFirestore.getInstance();

         home.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(AdminLogin.this,Selection.class));
                 finish();
             }
         });



//        mDatabase = FirebaseDatabase.getInstance().getReference().child("role");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(getemail.getText().toString(),getpassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(AdminLogin.this, "Admin Login Successfully", Toast.LENGTH_SHORT).show();
                        checkAdmin(authResult.getUser().getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }

    private void checkAdmin(String uid) {
        DocumentReference df=fStore.collection("role").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override//!=null
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess:"+documentSnapshot.getData());

                    if (documentSnapshot.getString("isAdmin") != null) {
                        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                       Toast.makeText(AdminLogin.this, "You are not Authorised to Login as Admin", Toast.LENGTH_SHORT).show();
                    }

            }
        });

    }



    }
