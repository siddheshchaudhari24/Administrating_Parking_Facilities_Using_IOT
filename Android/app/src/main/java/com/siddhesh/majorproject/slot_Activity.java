package com.siddhesh.majorproject;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class slot_Activity extends AppCompatActivity {


    FirebaseFirestore fStore;
    ArrayList<Location> locationArrayList;
    MyAdapter2 myAdapter2;
    RecyclerView loctions;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot);


        loctions = findViewById(R.id.locations);
        loctions.setHasFixedSize(true);
        loctions.setLayoutManager(new LinearLayoutManager(this));

        locationArrayList = new ArrayList<>();
        myAdapter2 = new MyAdapter2(slot_Activity.this,locationArrayList);
        loctions.setAdapter(myAdapter2);

        fStore = FirebaseFirestore.getInstance();

        fetchDataFromFirestore();


    }

    private void fetchDataFromFirestore() {
        fStore.collection("Locations")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Location location = document.toObject(Location.class);
                                locationArrayList.add(location);
                            }
                            // Notify adapter after all data is added
                            myAdapter2.notifyDataSetChanged();
                        } else {
                            Log.d("Firestore Data", "No documents found.");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore Error", "Error fetching documents: " + e);
                        // Handle error fetching data
                    }
                });
    }


}