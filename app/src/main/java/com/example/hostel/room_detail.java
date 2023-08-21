package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class room_detail extends AppCompatActivity {
    private TextView room_empty_note;
    private ArrayList<student_room_data> arrayList;
    private room_detail_adapter adapter;
    private RecyclerView recyclerView;
    private DatabaseReference dbreference, db;
    private String user_name_key, user_fname_key, user_phone_key;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Room Details");
        dialog = new Dialog(room_detail.this);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        recyclerView = findViewById(R.id.room_user_detail_recycler);
        room_empty_note = findViewById(R.id.room_empty_notes);


        Intent intent = getIntent();
        user_name_key = intent.getStringExtra("name");
        user_fname_key = intent.getStringExtra("parent_name");
        user_phone_key = intent.getStringExtra("phone");


        try {
            dbreference = FirebaseDatabase.getInstance().getReference().child("Hostel");
            dbreference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    arrayList = new ArrayList<>();
                    adapter = new room_detail_adapter(room_detail.this, arrayList);
                    arrayList.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {

                            for (DataSnapshot snapshot3 : snapshot2.child("student").getChildren()) {
                                student_room_data user_room_data = snapshot3.getValue(student_room_data.class);
                                if (user_room_data.getSt_name().equals(user_name_key) || user_room_data.getParent_name().equals(user_fname_key) && user_room_data.getSt_phone().equals(user_phone_key)) {

                                    arrayList.add(user_room_data);

                                } else {
                                    dialog.dismiss();

                                }


                            }
                        }
                    }
                    if (arrayList.size() > 0) {
                        dialog.dismiss();
                        room_empty_note.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(room_detail.this));
                        adapter.notifyDataSetChanged();

                    } else {
                        dialog.dismiss();
                        room_empty_note.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    dialog.dismiss();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Sorry! Database Error", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


}