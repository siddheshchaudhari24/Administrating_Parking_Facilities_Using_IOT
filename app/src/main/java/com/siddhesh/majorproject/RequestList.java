package com.siddhesh.majorproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RequestList extends AppCompatActivity {

    RecyclerView recyclerView2;
    ArrayList<Request> requestArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_list);

        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        requestArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(RequestList.this,requestArrayList);
        recyclerView2.setAdapter(myAdapter);

        fStore = FirebaseFirestore.getInstance();

        fetchDataFromFirestore();

    }

    private void fetchDataFromFirestore() {
        fStore.collection("UserRequests")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("Firestore Error", "Listen failed: " + e);
                            return;
                        }

                        if (queryDocumentSnapshots != null) {
                            for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    String documentId = dc.getDocument().getId();
                                    // Get the request object
                                    Request request = dc.getDocument().toObject(Request.class);
                                    // Set the ID of the request object
                                    request.setId(documentId);

                                    requestArrayList.add(request);
                                    //requestArrayList.add(dc.getDocument().toObject(Request.class));
                                }
                            }

                            // Notify adapter after all data is added
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}