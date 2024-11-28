package com.siddhesh.majorproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User_and_Vechical_Info extends AppCompatActivity {

    EditText fname;
    TextView Tprice;
    EditText phNo;
    EditText emailId;
    EditText vno;
    EditText aDate;
    EditText aTime;
    //TextView dDate;
    EditText dTime;

    TextView location,slot;

    public int hr;
    FirebaseAuth mAuth;

    Button Submit,TpriceB,Pay;
    FirebaseFirestore db;
    ImageView car;
    private boolean shouldChangeImage = false;
    Request request;

    private HashMap<String, Integer> imageMap;
    Spinner arrivalAmPmSpinner, departureAmPmSpinner;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_and_vechical_info);


        db = FirebaseFirestore.getInstance();
        fname = findViewById(R.id.fname);
        phNo = findViewById(R.id.phno);
        emailId = findViewById(R.id.email);
        vno = findViewById(R.id.vno);
        aDate = findViewById(R.id.adate);
        aTime = findViewById(R.id.atime);
        //dDate = findViewById(R.id.ddate);
        dTime = findViewById(R.id.dtime);
        Submit = findViewById(R.id.submit);
        Tprice=findViewById(R.id.tprice);

        TpriceB=findViewById(R.id.TpriceB);
        location=findViewById(R.id.location);
        slot=findViewById(R.id.slot);

        String Location =getIntent().getStringExtra("Location_Name");
        String Slot =  getIntent().getStringExtra("Slot_Num");

        location.setText(Location);
        slot.setText(Slot);

        request = new Request();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Fname = fname.getText().toString();
                String PhNo = phNo.getText().toString();
                String Email = emailId.getText().toString();
                String Vehicle_No = vno.getText().toString();
                String ADate = aDate.getText().toString();
                String ATime = aTime.getText().toString();
                //String DDate = dDate.getText().toString();
                String DTime = dTime.getText().toString();
                String LocationName = location.getText().toString();
                String SlotNumber = slot.getText().toString();



                Map<String, Object> user = new HashMap<>();
                user.put("Full_Name",Fname);
                user.put("Phone_no", PhNo);
                user.put("Email_Id", Email);
                user.put("Vehicle_No", Vehicle_No);
                user.put("Arrival_Time", ATime);
                user.put("Arrival_Date", ADate);
                user.put("Departure_Time", DTime);
                user.put("Location",LocationName);
                user.put("Slot_Number",SlotNumber);


                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();

                    // Query the "role" collection based on the user's ID
                    db.collection("role").document(userId)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        String email = documentSnapshot.getString("email");

                                        // Create a new document in the "RequestStatus" collection with the user's email as the document ID
                                        db.collection("UserRequests").document(email)
                                                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                        Toast.makeText(User_and_Vechical_Info.this, "Request sent successfully", Toast.LENGTH_SHORT).show();
                                                        String requestId = UUID.randomUUID().toString(); // Generate a random UUID as the ID
                                                        request.setId(requestId);

                                                        Map<String, Object> requestStatus = new HashMap<>();
                                                        requestStatus.put("status", "Pending");

                                                        db.collection("RequestStatus").document(email)
                                                                .set(requestStatus)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Log.d("TAG", "Request status document successfully created!");
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Log.w("TAG", "Error creating request status document", e);
                                                                    }
                                                                });
                                                    }
                                                }).
                                                addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(User_and_Vechical_Info.this, "Failed to send request", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                        Log.d("TAG", "Email: " + email);
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error getting documents.", e);
                                }
                            });
                }

                Intent intent = new Intent(User_and_Vechical_Info.this, Payment.class);
                intent.putExtra("Slot_Num", Slot);
                intent.putExtra("Location_Name",Location);
                startActivity(intent);

            }
        });

        TpriceB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calculate_price();
            }

        });

    }

    @SuppressLint("SetTextI18n")
    void Calculate_price(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        try {
            Date arrivalTime = format.parse(aTime.getText().toString());
            Date leavingTime = format.parse(dTime.getText().toString());

            long durationInMillis = leavingTime.getTime() - arrivalTime.getTime();
            int hours = (int) (durationInMillis / (1000 * 60 * 60)); // Convert milliseconds to hours
            hr=hours;

            int price = hours * 10; // Rs. 10 per hour

            Tprice.setText("Price: Rs. " + price);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}







