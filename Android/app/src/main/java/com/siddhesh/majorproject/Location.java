package com.siddhesh.majorproject;

public class Location {
    String Location_Name;
    String Location_Link;


    public Location() {
    }

    public String getLocation_Name() {
        return Location_Name;
    }

    public void setLocation_Name(String location_Name) {
        Location_Name = location_Name;
    }

    public String getLocation_Link() {
        return Location_Link;
    }

    public void setLocation_Link(String location_Link) {
        Location_Link = location_Link;
    }


    public Location(String location_Name, String location_Link) {
        Location_Name = location_Name;
        Location_Link = location_Link;
    }
}
