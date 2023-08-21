package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_service_activity extends AppCompatActivity {
    private String user_name_key, user_fname_key, user_phone_key, user_id_key;
    private DatabaseReference dbreference, db;
    private EditText name_service, room_service, select_type, pending_service, desc_service;
    private AppCompatButton submit_btn;
    private final static String apj_hostel = "APJ Boys Hostel";
    private final static String sv_hostel = "S V Boys Hostel";
    private final static String girls_hostel = "K C Girls Hostel";
    private String st_name_x, st_parent_x, st_phone_x, st_hostel_x, st_room_x;
    private LinearLayout ll_layout_service;
    private TextView service_empty_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_service);
        getSupportActionBar().setTitle("Service Request");
        name_service = findViewById(R.id.name_edt_service);
        room_service = findViewById(R.id.room_no_service);
        select_type = findViewById(R.id.select_service);
        pending_service = findViewById(R.id.pending_edt_service);
        desc_service = findViewById(R.id.description_edt_service);
        submit_btn = findViewById(R.id.submit_btn_service);
        service_empty_note = findViewById(R.id.service_empty_notes);
        ll_layout_service = findViewById(R.id.ll_layout_service);
        select_type.setInputType(InputType.TYPE_NULL);


        Intent intent = getIntent();
        user_name_key = intent.getStringExtra("name");
        user_fname_key = intent.getStringExtra("parent_name");
        user_phone_key = intent.getStringExtra("phone");
        user_id_key = intent.getStringExtra("userId");
        db = FirebaseDatabase.getInstance().getReference().child("Request");
        select_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog1 = new Dialog(user_service_activity.this);
                dialog1.setContentView(R.layout.select_service_layout);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));


                TextView txt_water = dialog1.findViewById(R.id.txt_water);

                txt_water.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String t = txt_water.getText().toString();

                        select_type.setText(t);
                        dialog1.dismiss();
                        //  Toast.makeText(MainActivity.this, t, Toast.LENGTH_SHORT).show();

                    }
                });

                TextView txt_mess = dialog1.findViewById(R.id.txt_mess);
                txt_mess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String p = txt_mess.getText().toString();

                        select_type.setText(p);
                        dialog1.dismiss();
                    }
                });

                TextView txt_fan = dialog1.findViewById(R.id.txt_fan);
                txt_fan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String q = txt_fan.getText().toString();
                        select_type.setText(q);
                        dialog1.dismiss();
                    }
                });
                TextView txt_furniture = dialog1.findViewById(R.id.txt_furniture);
                txt_furniture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = txt_furniture.getText().toString();
                        select_type.setText(a);
                        dialog1.dismiss();
                    }
                });  TextView txt_room = dialog1.findViewById(R.id.txt_room);
                txt_room.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String b = txt_room.getText().toString();
                        select_type.setText(b);
                        dialog1.dismiss();
                    }
                }); TextView txt_other = dialog1.findViewById(R.id.txt_other);
                txt_other.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String c = txt_other.getText().toString();
                        select_type.setText(c);
                        dialog1.dismiss();
                    }
                });

                dialog1.show();
            }
        });


        dbreference = FirebaseDatabase.getInstance().getReference().child("Hostel");
        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {

                        for (DataSnapshot snapshot3 : snapshot2.child("student").getChildren()) {
                            student_room_data user_room_data = snapshot3.getValue(student_room_data.class);
                            if (user_room_data.getSt_name().equals(user_name_key) || user_room_data.getParent_name().equals(user_fname_key) && user_room_data.getSt_phone().equals(user_phone_key)) {
                                user_room_request_data user_room_request_data = new user_room_request_data(user_room_data.getSt_name(), user_room_data.getSt_phone(), user_room_data.getParent_name(), user_room_data.getHostel_name(), user_room_data.getRoom_no());
                                if (user_room_request_data != null) {
                                    ll_layout_service.setVisibility(View.VISIBLE);
                                    service_empty_note.setVisibility(View.GONE);
                                    st_name_x = user_room_request_data.getSt_name();
                                    st_parent_x = user_room_request_data.getParent_name();
                                    st_phone_x = user_room_request_data.getSt_phone();
                                    st_hostel_x = user_room_request_data.getHostel_name();
                                    st_room_x = user_room_request_data.getRoom_no();

                                    name_service.setText(st_name_x);
                                    name_service.setEnabled(false);
                                    room_service.setText(st_room_x);
                                    room_service.setEnabled(false);
                                    pending_service.setText("Pending");
                                    pending_service.setEnabled(false);


                                }

                                break;
                            }


                        }
                    }
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadata(st_name_x, st_phone_x, st_parent_x, st_hostel_x, st_room_x);
            }
        });


    }


    private void uploadata(String st_name, String st_phone, String st_parent, String hostel_name, String st_room_no) {
        if (hostel_name.equals(apj_hostel) || hostel_name.equals(sv_hostel)) {

            String name_var = name_service.getText().toString();
            String room_no_var = room_service.getText().toString();
            String service_due = select_type.getText().toString();
            String status = pending_service.getText().toString();
            String desc_var = desc_service.getText().toString();
            String unq_key = db.push().getKey();
            user_room_request_data user_room_request_data = new user_room_request_data(name_var, st_phone, st_parent, hostel_name, room_no_var, service_due, status, desc_var,unq_key,user_id_key);

            db.child("Boys Hostel").child("Service").child(user_id_key).child(unq_key).setValue(user_room_request_data);
            Toast.makeText(this, "Service request Uploaded successfully", Toast.LENGTH_SHORT).show();


        } else if (hostel_name.equals(girls_hostel)) {
            String name_var = name_service.getText().toString();
            String room_no_var = room_service.getText().toString();
            String service_due = select_type.getText().toString();
            String status = pending_service.getText().toString();
            String desc_var = desc_service.getText().toString();

            user_room_request_data user_room_request_data = new user_room_request_data(name_var, st_phone, st_parent, hostel_name, room_no_var, service_due, status, desc_var);
            String unq_key = db.push().getKey();
            db.child("Girls Hostel").child("Service").child(user_id_key).child(unq_key).setValue(user_room_request_data);
            Toast.makeText(this, "Service request Uploaded successfully", Toast.LENGTH_SHORT).show();

        }

    }
}
