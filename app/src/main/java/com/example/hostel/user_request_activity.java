package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class user_request_activity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView title_toolbar_tv;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String user_name_key, user_fname_key, user_phone_key, user_id_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request);
        settoolbar();
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);


        Intent intent = getIntent();
        user_name_key = intent.getStringExtra("name");
        user_fname_key = intent.getStringExtra("parent_name");
        user_phone_key = intent.getStringExtra("phone");
        user_id_key = intent.getStringExtra("userId");


        viewPager_user_fragmentAdapter adapter = new viewPager_user_fragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void settoolbar() {

        toolbar = findViewById(R.id.toolbar1);
        title_toolbar_tv = findViewById(R.id.title_toolbar);
        title_toolbar_tv.setText("My Requests");
    }

    // Send data From Parent Activity to chil Fragment
    public Bundle getMyData_user() {
        Bundle bundle = new Bundle();
        bundle.putString("name", user_name_key);
        bundle.putString("parent_name", user_fname_key);
        bundle.putString("phone", user_phone_key);
        bundle.putString("userId", user_id_key);
        return bundle;
    }
}