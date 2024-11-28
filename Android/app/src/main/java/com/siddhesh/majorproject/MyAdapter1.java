package com.siddhesh.majorproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder> {
    Context context;
    ArrayList<User> userArrayList;


    public MyAdapter1(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        User user = userArrayList.get(i);

        myViewHolder.fullName.setText(user.Full_Name);
        myViewHolder.arrivalDate.setText(user.Arrival_Date);
        myViewHolder.arrivalTime.setText(user.Arrival_Time);
        myViewHolder.departureTime.setText(user.Departure_Time);
        myViewHolder.location2.setText(user.Location);
        myViewHolder.slotNo2.setText(user.Slot_Number);
        List<User> userList = new ArrayList<>();

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, arrivalDate, arrivalTime,departureTime,location2,slotNo2;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.fullName);
            arrivalDate = itemView.findViewById(R.id.arrivalDate);
            arrivalTime = itemView.findViewById(R.id.arrivalTime);
            departureTime = itemView.findViewById(R.id.departureTime);
            location2 = itemView.findViewById(R.id.location2);
            slotNo2 = itemView.findViewById(R.id.slotNo2);


        }
    }


}