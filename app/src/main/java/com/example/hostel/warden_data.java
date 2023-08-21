package com.example.hostel;

public class warden_data {
    private String warden_name, warden_email, warden_hostel, warden_phone, warden_post,warden_image;

    public warden_data() {
    }

    public warden_data(String warden_name, String warden_email, String warden_hostel, String warden_phone, String warden_post,String warden_image) {
        this.warden_name = warden_name;
        this.warden_email = warden_email;
        this.warden_hostel = warden_hostel;
        this.warden_phone = warden_phone;
        this.warden_post = warden_post;
        this.warden_image = warden_image;
    }
    public warden_data(String warden_name, String warden_email, String warden_hostel, String warden_phone, String warden_post) {
        this.warden_name = warden_name;
        this.warden_email = warden_email;
        this.warden_hostel = warden_hostel;
        this.warden_phone = warden_phone;
        this.warden_post = warden_post;

    }

    public String getWarden_image() {
        return warden_image;
    }

    public void setWarden_image(String warden_image) {
        this.warden_image = warden_image;
    }

    public String getWarden_name() {
        return warden_name;
    }

    public void setWarden_name(String warden_name) {
        this.warden_name = warden_name;
    }

    public String getWarden_email() {
        return warden_email;
    }

    public void setWarden_email(String warden_email) {
        this.warden_email = warden_email;
    }

    public String getWarden_hostel() {
        return warden_hostel;
    }

    public void setWarden_hostel(String warden_hostel) {
        this.warden_hostel = warden_hostel;
    }

    public String getWarden_phone() {
        return warden_phone;
    }

    public void setWarden_phone(String warden_phone) {
        this.warden_phone = warden_phone;
    }

    public String getWarden_post() {
        return warden_post;
    }

    public void setWarden_post(String warden_post) {
        this.warden_post = warden_post;
    }
}
