package com.example.hostel;

public class student_room_data {
    private String st_name, st_phone, parent_name, parent_phone, hostel_fee, address, hostel_name, room_no, st_key;

    public student_room_data(String st_name, String st_phone, String parent_name, String parent_phone, String hostel_fee, String address, String hostel_name, String room_no, String st_key) {
        this.st_name = st_name;
        this.st_phone = st_phone;
        this.parent_name = parent_name;
        this.parent_phone = parent_phone;
        this.hostel_fee = hostel_fee;
        this.address = address;
        this.hostel_name = hostel_name;
        this.room_no = room_no;
        this.st_key = st_key;
    }

    public student_room_data() {

    }

    public String getSt_key() {
        return st_key;
    }

    public String getRoom_no() {
        return room_no;
    }

    public String getHostel_name() {
        return hostel_name;
    }

    public String getSt_name() {
        return st_name;
    }

    public String getSt_phone() {
        return st_phone;
    }

    public String getParent_name() {
        return parent_name;
    }

    public String getParent_phone() {
        return parent_phone;
    }

    public String getHostel_fee() {
        return hostel_fee;
    }

    public String getAddress() {
        return address;
    }
}
