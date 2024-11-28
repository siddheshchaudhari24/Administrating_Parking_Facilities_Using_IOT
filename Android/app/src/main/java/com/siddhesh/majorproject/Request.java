package com.siddhesh.majorproject;

public class Request {

    String id;
    String Full_Name;
    String Arrival_Time;
    String Departure_Time;
    String Arrival_Date;
    String Email_Id;
    String Phone_no;
    String Vehicle_No;
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

    public String getEmail_Id() {
        return Email_Id;
    }

    public void setEmail_Id(String email_Id) {
        Email_Id = email_Id;
    }

    public String getPhone_no() {
        return Phone_no;
    }

    public void setPhone_no(String phone_no) {
        Phone_no = phone_no;
    }

    public String getVehicle_No() {
        return Vehicle_No;
    }

    public void setVehicle_No(String vehicle_No) {
        Vehicle_No = vehicle_No;
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

    public Request( String full_Name, String arrival_Time, String departure_Time, String arrival_Date, String email_Id, String phone_no, String vehicle_No, String location, String slot_Number) {

        Full_Name = full_Name;
        Arrival_Time = arrival_Time;
        Departure_Time = departure_Time;
        Arrival_Date = arrival_Date;
        Email_Id = email_Id;
        Phone_no = phone_no;
        Vehicle_No = vehicle_No;
        Location = location;
        Slot_Number = slot_Number;
    }

    public Request() {
    }

}
