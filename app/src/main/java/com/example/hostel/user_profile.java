package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class user_profile extends AppCompatActivity {

    private TextView username, username_, useremail_, userparent_, userphone_;
    private String name, parent, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        username = findViewById(R.id.username);
        username_ = findViewById(R.id.username_);
        useremail_ = findViewById(R.id.useremail_);
        userparent_ = findViewById(R.id.userparent_);
        userphone_ = findViewById(R.id.userphone_);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        parent = intent.getStringExtra("parent");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        username_.setText(name);
        username.setText(name);
        useremail_.setText(email);
        userparent_.setText(parent);
        userphone_.setText(phone);

    }
}