package com.siddhesh.majorproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder>{

    Context context;
    ArrayList<Location> locationArrayList;
    public String LocationName;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyAdapter2(Context context, ArrayList<Location> locationArrayList) {
        this.context = context;
        this.locationArrayList = locationArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.locations,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Location l = locationArrayList.get(i);

        myViewHolder.locationName.setText(l.Location_Name);


        myViewHolder.locationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationName= l.Location_Name;
                // Open SlotActivity
                Intent intent = new Intent(context, Parking_layout.class);
                intent.putExtra("Location_Name", LocationName);
                context.startActivity(intent);
            }
        });

        myViewHolder.mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap(l.Location_Link);
            }
        });





    }




    @Override
    public int getItemCount() {
        return locationArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView locationName;
        Button mapBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            locationName = itemView.findViewById(R.id.locationName);
            mapBtn = itemView.findViewById(R.id.mapBtn);


        }
    }

    private void openMap(String link){

        try {
            Uri uri = Uri.parse(link);
            if (link != null && !link.isEmpty()) {
                // Create an intent to view the location in Google Maps
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                // Start the activity with the intent
                context.startActivity(intent);
            } else {
                // Inform the user that the link is empty
                Toast.makeText(context, "Map link is empty", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // Handle any exception that occurs while opening the map
            Log.e("MyAdapter2", "Error opening map: " + e.getMessage());
            Toast.makeText(context, "Error opening map", Toast.LENGTH_SHORT).show();
        }
    }

}
