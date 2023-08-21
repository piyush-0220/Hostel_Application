package com.example.hostel;

public class User {
    String name, email, parent_name, phone, password, user_id,user_img;

    public User() {
    }

    public User(String name, String email, String parent_name, String phone) {
        this.name = name;
        this.email = email;
        this.parent_name = parent_name;
        this.phone = phone;
    }


    public User(String name, String email, String parent_name, String phone, String password, String user_id, String user_img) {
        this.name = name;
        this.email = email;
        this.parent_name = parent_name;
        this.phone = phone;
        this.password = password;
        this.user_id = user_id;
        this.user_img = user_img;

    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}