package com.siddhesh.majorproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity {

    Button AdminLogut,ViewUsers,reuestBtn1,AddParking;
    FirebaseAuth mAuth;

    User user;
    public String departureTime;
    FirebaseFirestore fStore;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        AdminLogut=findViewById(R.id.AdminLogout);
        ViewUsers=findViewById(R.id.ViewUsers);
        reuestBtn1=findViewById(R.id.reuestBtn1);
        AddParking=findViewById(R.id.AddParking);

        mAuth = FirebaseAuth.getInstance();

        fStore = FirebaseFirestore.getInstance();

        reuestBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, RequestList.class);
                startActivity(i);
            }
        });


        ViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(AdminActivity.this, UserList.class);
                    startActivity(i);

            }
        });

        AddParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, Location_Form.class);
                startActivity(i);
            }
        });


        AdminLogut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mAuth.signOut();
                    Intent intent=new Intent(AdminActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e){
                    Toast.makeText(AdminActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(e.getMessage());
                    Log.d("logout", Objects.requireNonNull(e.getMessage()));
                }

            }
        });



    }


}