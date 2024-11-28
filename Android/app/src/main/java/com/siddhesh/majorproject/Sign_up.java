package com.siddhesh.majorproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Sign_up extends AppCompatActivity {

    TextInputEditText getEmail1,getPassword1,repassword;

    Button signIn1 ,signup1;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    public void onStart(){
        // Check if user is signed in (non-null) and update UI accordingly.
        super.onStart();
        FirebaseUser currentUser1=mAuth.getCurrentUser();
        if (currentUser1!=null){
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            getIntent().putExtra("fromSignup",false);
            startActivity(i);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        getEmail1=findViewById(R.id.Email1);
        getPassword1= findViewById(R.id.pass1);
        repassword= findViewById(R.id.repass);
        signIn1=findViewById(R.id.signin1);
        signup1=findViewById(R.id.signup1);

        signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,pass,repass;
                email=String.valueOf(getEmail1.getText());
                pass=String.valueOf(getPassword1.getText());
                repass=String.valueOf(repassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Sign_up.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)){
                    Toast.makeText(Sign_up.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(repass)) {
                    Toast.makeText(Sign_up.this, "Passwords don't match. Please enter them again.", Toast.LENGTH_SHORT).show();
                    return;
                }





                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(Sign_up.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Sign_up.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("role").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("email", email);

                            userInfo.put("isUser", "1");

                            df.set(userInfo);
                            signIn1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        mAuth.signOut();
                                        Intent intent = new Intent(Sign_up.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    } catch (Exception e) {
                                        Toast.makeText(Sign_up.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        System.out.println(e.getMessage());
                                        Log.d("logout", e.getMessage());

                                    }

                                }
                            });

                        }
                        else {
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Sign_up.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });


            }



        });

    }
}