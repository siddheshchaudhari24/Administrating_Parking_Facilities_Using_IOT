package com.siddhesh.majorproject;

public class User {

    private String id;
    String Full_Name;
    String Arrival_Time;
    String Departure_Time;
    String Arrival_Date;
    String Parking_Status;
    String Location;
    String Slot_Number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public void setFull_Name(String full_Name) {
        Full_Name = full_Name;
    }

    public String getArrival_Time() {
        return Arrival_Time;
    }

    public void setArrival_Time(String arrival_Time) {
        Arrival_Time = arrival_Time;
    }

    public String getDeparture_Time() {
        return Departure_Time;
    }

    public void setDeparture_Time(String departure_Time) {
        Departure_Time = departure_Time;
    }

    public String getArrival_Date() {
        return Arrival_Date;
    }

    public void setArrival_Date(String arrival_Date) {
        Arrival_Date = arrival_Date;
    }

    public String getParking_Status() {
        return Parking_Status;
    }

    public void setParking_Status(String parking_Status) {
        Parking_Status = parking_Status;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getSlot_Number() {
        return Slot_Number;
    }

    public void setSlot_Number(String slot_Number) {
        Slot_Number = slot_Number;
    }

    public User(String id, String full_Name, String arrival_Time, String departure_Time, String arrival_Date, String parking_Status, String location, String slot_Number) {
        this.id = id;
        Full_Name = full_Name;
        Arrival_Time = arrival_Time;
        Departure_Time = departure_Time;
        Arrival_Date = arrival_Date;
        Parking_Status = parking_Status;
        Location = location;
        Slot_Number = slot_Number;
    }

    public User() {
    }
}
