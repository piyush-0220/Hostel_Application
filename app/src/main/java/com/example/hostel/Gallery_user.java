package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Gallery_user extends AppCompatActivity {
    private String user_name_key, user_fname_key, user_phone_key, st_hostel_x, hostel_name;
    private DatabaseReference reference, db;
    private final static String apj_hostel = "APJ Boys Hostel";
    private final static String sv_hostel = "S V Boys Hostel";
    private final static String girls_hostel = "K C Girls Hostel";
    private RecyclerView others;
    private GalleryAdapter adapter;
    private TextView room_empty_notes;
    private LinearLayout rv_layout;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_user);
        getSupportActionBar().setTitle("Hostel Gallery");
        others = findViewById(R.id.otherss);
        room_empty_notes = findViewById(R.id.gallery_empty_notes);
        rv_layout = findViewById(R.id.rv_layout);
        dialog = new Dialog(Gallery_user.this);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        Intent intent = getIntent();
        user_name_key = intent.getStringExtra("name");
        user_fname_key = intent.getStringExtra("parent_name");
        user_phone_key = intent.getStringExtra("phone");

        try {
            db = FirebaseDatabase.getInstance().getReference().child("Hostel");
            reference = FirebaseDatabase.getInstance().getReference().child("Gallery");
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dialog.show();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {

                            for (DataSnapshot snapshot3 : snapshot2.child("student").getChildren()) {
                                student_room_data user_room_data = snapshot3.getValue(student_room_data.class);
                                if (user_room_data.getSt_name().equals(user_name_key) || user_room_data.getParent_name().equals(user_fname_key) && user_room_data.getSt_phone().equals(user_phone_key)) {

                                    st_hostel_x = user_room_data.getHostel_name();
                                    getOthers(st_hostel_x);
                                    break;
                                } else {
                                    dialog.dismiss();
                                    rv_layout.setVisibility(View.GONE);
                                    room_empty_notes.setVisibility(View.VISIBLE);

                                }


                            }

                        }
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

    private void getOthers(String h) {

        if (h.equals(apj_hostel) || h.equals(sv_hostel)) {
            hostel_name = "Boys Hostel";
        } else if (h.equals(girls_hostel)) {
            hostel_name = "Girls Hostel";
        }

        reference = FirebaseDatabase.getInstance().getReference().child("Gallery").child(hostel_name);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> imageList = new ArrayList<>();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String data = (String) snapshot1.getValue();
                    imageList.add(0, data);
                }
                adapter = new GalleryAdapter(Gallery_user.this, imageList);
                if (imageList.size() > 0) {
                    dialog.show();
                    rv_layout.setVisibility(View.VISIBLE);
                    room_empty_notes.setVisibility(View.GONE);
                    others.setLayoutManager(new GridLayoutManager(Gallery_user.this, 4));
                    others.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    rv_layout.setVisibility(View.GONE);
                    room_empty_notes.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(Gallery_user.this, "Something went wrong !", Toast.LENGTH_SHORT).show();

            }
        });

    }
}