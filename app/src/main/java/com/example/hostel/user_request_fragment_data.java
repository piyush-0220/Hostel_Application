package com.example.hostel;

public class user_request_fragment_data {
    private String type, desc, date_from, date_to,key;
    private String status;

    public user_request_fragment_data() {
    }

//    public user_request_fragment_data(String leave_type, String desc, String status) {
//        this.leave_type = leave_type;
//        this.desc = desc;
//        this.status = status;
//    }

    public user_request_fragment_data(String type, String status, String date_from, String date_to,String key) {
        this.type = type;
        this.status = status;
        this.date_from = date_from;
        this.date_to = date_to;
        this.key=key;

    }

    public String getKey() {
        return key;
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
}