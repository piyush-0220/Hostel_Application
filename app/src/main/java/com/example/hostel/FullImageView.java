package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class FullImageView extends AppCompatActivity {
    private PhotoView imageView;
    private Toolbar toolbar;
    private TextView title_toolbar_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);
        settoolbar();
        String image;
        image = getIntent().getStringExtra("image");
        imageView = findViewById(R.id.imageView);


        Glide.with(this).load(image).into(imageView);
    }

    private void settoolbar() {
        toolbar = findViewById(R.id.toolbar1);
        title_toolbar_tv = findViewById(R.id.title_toolbar);
        title_toolbar_tv.setText("Full Image ");


    }
}