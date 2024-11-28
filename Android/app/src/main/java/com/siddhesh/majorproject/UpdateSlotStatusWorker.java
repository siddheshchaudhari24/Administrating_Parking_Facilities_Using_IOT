package com.siddhesh.majorproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.firestore.FirebaseFirestore;

// Worker class to update slot status
public class UpdateSlotStatusWorker extends Worker {
    private static final String CHANNEL_ID = "slot_status_channel";
    private static final int NOTIFICATION_ID = 123;

    public UpdateSlotStatusWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {

        super(context, workerParams);
    }
FirebaseFirestore db = FirebaseFirestore.getInstance();
    @NonNull
    @Override
    public Result doWork() {
        // Get input data
        String location = getInputData().getString("Location");
        String slotNumber = getInputData().getString("Slot_Number");

        // Update slot status to false in Firestore
        if (location != null && slotNumber != null) {
            db.collection("Slots").document(location)
                    .update("S" + slotNumber, false)
                    .addOnSuccessListener(aVoid -> {
                        // Status updated successfully
                        showNotification(location, slotNumber);
                    })
                    .addOnFailureListener(e -> {
                        // Failed to update status
                    });
        }

        return Result.success();
    }

    private void showNotification(String location, String slotNumber) {
        // Create notification channel for devices running Android Oreo or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Slot Status", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.redcar)
                .setContentTitle("Slot Status")
                .setContentText("Slot " + slotNumber + " at " + location + " is now available.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}



