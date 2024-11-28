package com.siddhesh.majorproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Payment extends AppCompatActivity {

    private ImageView imgView,uploadIMG;
    private Button select,upload,fb;

    private static final int PICK_IMAGE_REQUEST = 100;

    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        String Location =getIntent().getStringExtra("Location_Name");
        String Slot =  getIntent().getStringExtra("Slot_Num");
        uploadIMG = findViewById(R.id.uploadimg);
        select = findViewById(R.id.button);
        fb = findViewById(R.id.fb);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBarrierMovement=new Intent(Payment.this, Barrier_Movement.class);
                goBarrierMovement.putExtra("Slot_Num", Slot);
                goBarrierMovement.putExtra("Location_Name",Location);
                startActivity(goBarrierMovement);
                finish();
                Toast.makeText(Payment.this, "Booking Successful...", Toast.LENGTH_SHORT).show();
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseImage();

            }
        });

//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                uploadImage();
//
//            }
//        });


    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Toast.makeText(this, "Image uploaded successflly", Toast.LENGTH_SHORT).show();
            imageUri = data.getData();
            uploadIMG.setImageURI(imageUri);
            uploadImage();
        }
    }

    private void uploadImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        uploadIMG.setImageURI(null);
                        Toast.makeText(Payment.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(Payment.this,"Failed to Upload",Toast.LENGTH_SHORT).show();


                    }
                });
    }

//    void uploadImage(){
//        if (filePath != null){
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());
//            storageReference.putFile(filePath).addOnS
//        }
//    }


   
}