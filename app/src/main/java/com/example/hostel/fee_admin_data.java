package com.example.hostel;

public class fee_admin_data {
    private String st_name, room_no, fee_status, hostel_name, parent_name, st_phone, hostel_fee,st_key;

    public fee_admin_data() {
    }

    public fee_admin_data(String st_name, String room_no, String hostel_name, String parent_name, String st_phone,String hostel_fee,String st_key,String fee_status) {
        this.st_name = st_name;
        this.room_no = room_no;
        this.hostel_name = hostel_name;
        this.parent_name = parent_name;
        this.st_phone = st_phone;
        this.hostel_fee=hostel_fee;
       this.st_key=st_key;
        this.fee_status=fee_status;

    }

    public fee_admin_data(String st_name, String room_no, String hostel_name, String parent_name, String st_phone ,String fee_status) {
        this.st_name = st_name;
        this.room_no = room_no;
        this.hostel_name = hostel_name;
        this.parent_name = parent_name;
        this.st_phone = st_phone;
        this.fee_status=fee_status;


    }

    public String getFee_status() {
        return fee_status;
    }

    public void setFee_status(String fee_status) {
        this.fee_status = fee_status;
    }

    public String getSt_key() {
        return st_key;
    }

    public void setSt_key(String st_key) {
        this.st_key = st_key;
    }

    public String getHostel_fee() {
        return hostel_fee;
    }

    public void setHostel_fee(String hostel_fee) {
        this.hostel_fee = hostel_fee;
    }

    public String getHostel_name() {
        return hostel_name;
    }

    public void setHostel_name(String hostel_name) {
        this.hostel_name = hostel_name;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getSt_phone() {
        return st_phone;
    }

    public void setSt_phone(String st_phone) {
        this.st_phone = st_phone;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }


}
