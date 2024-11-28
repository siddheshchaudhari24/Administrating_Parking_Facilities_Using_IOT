package com.siddhesh.majorproject;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    Context mContext;
    ArrayList<Request> requestArrayList;

    FirebaseFirestore db;

    public MyAdapter(RequestList context, ArrayList<Request> requestArrayList) {
        this.context = context;
        this.requestArrayList = requestArrayList;
        db = FirebaseFirestore.getInstance();
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.request, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") int i) {

        Request request = requestArrayList.get(i);
        myViewHolder.fullName.setText(request.Full_Name);
        myViewHolder.arrivalDate.setText(request.Arrival_Date);
        myViewHolder.arrivalTime.setText(request.Arrival_Time);
        myViewHolder.depatureTime.setText(request.Departure_Time);
        myViewHolder.location1.setText(request.Location);
        myViewHolder.slotNo1.setText(request.Slot_Number);

       String requestId = request.getId();

       myViewHolder.acceptBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                transferData(request);
                //updateSlots();
                scheduleDepartureWork(request);

                updateRequestStatus(request.getId(), "Accepted");

                Intent intent1 = new Intent(context,AdminActivity.class);
                context.startActivity(intent1);


            }

        });

       myViewHolder.denyBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               deleteRequest(request);

               updateRequestStatus(request.getId(), "Denied");

               Intent intent12 = new Intent(context,AdminActivity.class);
               context.startActivity(intent12);


           }
       });


    }




    @Override
    public int getItemCount() {
        return requestArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

       // public TextView status;
        TextView fullName,arrivalDate,arrivalTime,depatureTime,location1,slotNo1;
        Button acceptBtn,denyBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.fullName);
            arrivalDate = itemView.findViewById(R.id.arrivalDate);
            arrivalTime = itemView.findViewById(R.id.arrivalTime);
            depatureTime = itemView.findViewById(R.id.depatureTime);
            location1 = itemView.findViewById(R.id.location1);
            slotNo1 = itemView.findViewById(R.id.slotNo1);
            acceptBtn = itemView.findViewById(R.id.acceptBtn);
            denyBtn = itemView.findViewById(R.id.denyBtn);
        }
    }
    private void transferData(Request request) {
        // Transfer data for the specific request
        Map<String, Object> newData = new HashMap<>();
        newData.put("Full_Name", request.Full_Name);
        newData.put("Arrival_Time", request.Arrival_Time);
        newData.put("Departure_Time", request.Departure_Time);
        newData.put("Arrival_Date", request.Arrival_Date);
        newData.put("Email_Id", request.Email_Id);
        newData.put("Phone_no", request.Phone_no);
        newData.put("Vehicle_No", request.Vehicle_No);
        newData.put("Location", request.Location);
        newData.put("Slot_Number", request.Slot_Number);

        // Add the new document to the "User" collection
        db.collection("User")
                .add(newData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Document added with ID: " + documentReference.getId());

                        // Update the slot status to true
                        String location = request.Location;
                        String slotNumber = request.Slot_Number;


                        //intent.putExtra("Location_Name", location);
                        //intent.putExtra("Slot_Number", slotNumber);

                        db.collection("Slots")
                                .document(location)
                                .update("S" + slotNumber, true)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "Slot status updated to true for location: " + location + ", slot number: " + slotNumber);

                                        // Delete the original document from the source collection
                                        //deleteOriginalDocument(request);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "Error updating slot status", e);
                                        // Handle error updating slot status
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding document", e);
                        Log.e(TAG, "Firestore operation failed: " + e.getMessage(), e);
                        // Handle error adding document
                    }
                });
    }

    private void scheduleDepartureWork(Request request) {
        try {
            // Get the current date and time
            Calendar calendar = Calendar.getInstance();

            // Set today's date as the departure date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String todayDate = dateFormat.format(calendar.getTime());

            // Concatenate today's date with the departure time from the request
            String departureDateTimeString = todayDate + " " + request.Departure_Time;

            // Parse the departure date and time string into a Date object
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date departureDateTime = dateTimeFormat.parse(departureDateTimeString);

            // Get the departure time in milliseconds
            long departureTimeMillis = departureDateTime.getTime();
            Log.d(TAG, "Scheduled departure time: " + new Date(departureTimeMillis));

            // Enqueue work only if the departure time is in the future
            if (departureTimeMillis > System.currentTimeMillis()) {
                long delay = departureTimeMillis - System.currentTimeMillis();

                // Build work request
                Data inputData = new Data.Builder()
                        .putString("Location", request.Location)
                        .putString("Slot_Number", request.Slot_Number)
                        .build();

                OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(UpdateSlotStatusWorker.class)
                        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                        .setInputData(inputData)
                        .build();

                // Enqueue work
                WorkManager.getInstance(context).enqueue(workRequest);
                Log.d(TAG, "Departure work scheduled for location: " + request.Location + ", slot number: " + request.Slot_Number);
            } else {
                // Update the slot status to false when departure time has passed
                FirebaseHelper.updateSlotStatus(request.Location, request.Slot_Number, false);
                Log.e(TAG, "Departure time has already passed.");
            }
        } catch (Exception e) {
            // Handle any exceptions
            Log.e(TAG, "Error scheduling departure work", e);
        }
    }




    private long getMillisFromStringDateTime(String dateTime) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = format.parse(dateTime);
        if (date != null) {
            return date.getTime();
        } else {
            throw new ParseException("Error parsing date", 0);
        }
    }

    private void updateRequestStatus(String requestId, String status) {
        // Construct a reference to the RequestStatus document
        DocumentReference requestStatusRef = db.collection("RequestStatus").document(requestId);

        // Create a map to update the status
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("status", status);

        // Update the status
        requestStatusRef.update(updateData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Request status updated successfully: " + status);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error updating request status", e);
                    }
                });
    }


    private void deleteRequest(Request request) {
        Log.d(TAG, "Deleting request with ID: " + request.getId());
        // Get the Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Construct a reference to the request document
        DocumentReference requestRef = db.collection("UserRequests").document(request.getId());
        // Delete the request document
        requestRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Request deleted successfully");
                        // Remove the deleted request from the list and notify the adapter
                        int position = requestArrayList.indexOf(request);
                        if (position != -1) {
                            requestArrayList.remove(position);
                            notifyItemRemoved(position);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error deleting request", e);
                        // Handle error
                        Toast.makeText(context, "Failed to delete request", Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
