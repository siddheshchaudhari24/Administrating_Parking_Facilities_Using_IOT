package com.siddhesh.majorproject;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseHelper {

    private static final String TAG = "FirebaseHelper";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

   public static void updateSlotStatus(String location, String slotNumber, boolean status) {
        db.collection("Slots")
                .document(location)
                .update("S" + slotNumber, status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Slot status updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error updating slot status", e);
                    }
                });
    }
}

