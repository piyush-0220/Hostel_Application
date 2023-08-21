package com.example.hostel;

public class DataModel {
    private String bed_no_1, room_no_1, key, hostel_name;
    private long people;

    public DataModel() {
    }


    public DataModel(String bed_no_1, String room_no_1, String key, String hostel_name,long people) {
        this.bed_no_1 = bed_no_1;
        this.people=people;
        this.room_no_1 = room_no_1;
        this.key = key;
        this.hostel_name = hostel_name;


    }

    public long getPeople() {
        return people;
    }

    public void setPeople(long people) {
        this.people = people;
    }

    public String getBed_no_1() {
        return bed_no_1;
    }

    public void setBed_no_1(String bed_no_1) {
        this.bed_no_1 = bed_no_1;
    }

    public String getRoom_no_1() {
        return room_no_1;
    }

    public void setRoom_no_1(String room_no_1) {
        this.room_no_1 = room_no_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHostel_name() {
        return hostel_name;
    }

    public void setHostel_name(String hostel_name) {
        this.hostel_name = hostel_name;
    }
}
