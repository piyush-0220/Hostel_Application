package com.example.hostel;

public class user_room_request_data {
    private String st_name, st_phone, parent_name, address, room_no, date_from, date_to, desc, status, type,key,userid;
    public String hostel_name;



    public user_room_request_data(String st_name, String st_phone, String parent_name, String hostel_name, String room_no, String type, String desc, String status, String key,String userid) {
        this.st_name = st_name;
        this.st_phone = st_phone;
        this.parent_name = parent_name;
        this.hostel_name = hostel_name;

        this.room_no = room_no;
        this.type = type;
//        this.date_from = date_from;
//        this.date_to = date_to;
        this.desc = desc;
        this.status = status;
        this.userid=userid;
        this.key=key;


    }
    public user_room_request_data(String st_name, String st_phone, String parent_name, String hostel_name, String room_no, String type, String date_from, String date_to, String desc, String status,String key,String userid) {
        this.st_name = st_name;
        this.st_phone = st_phone;
        this.parent_name = parent_name;
        this.hostel_name = hostel_name;

        this.room_no = room_no;
        this.type = type;
        this.date_from = date_from;
        this.date_to = date_to;
        this.desc = desc;
        this.status = status;
         this.key=key;
         this.userid=userid;


    }

    public user_room_request_data(String st_name, String st_phone, String parent_name, String hostel_name, String room_no, String type, String status, String desc) {
        this.st_name = st_name;
        this.st_phone = st_phone;
        this.parent_name = parent_name;
        this.hostel_name = hostel_name;

        this.room_no = room_no;
        this.type = type;
        this.desc = desc;
        this.status = status;

        //this.key=key;


    }


    public user_room_request_data(String hostel_name)  {
        this.hostel_name = hostel_name;
    }

    public user_room_request_data(String st_name, String st_phone, String parent_name, String hostel_name, String room_no) {
        this.st_name = st_name;
        this.st_phone = st_phone;
        this.parent_name = parent_name;
        this.hostel_name = hostel_name;

        this.room_no = room_no;


    }

    public user_room_request_data() {
    }
    public String getUserid() {
        return userid;
    }
    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public String getSt_phone() {
        return st_phone;
    }

    public void setSt_phone(String st_phone) {
        this.st_phone = st_phone;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getHostel_name() {
        return hostel_name;
    }

    public void setHostel_name(String hostel_name) {
        this.hostel_name = hostel_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }


}
