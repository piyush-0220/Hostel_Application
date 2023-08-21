package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FeeDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Fee Details");
    }
}