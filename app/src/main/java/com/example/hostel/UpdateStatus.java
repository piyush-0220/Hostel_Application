package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UpdateStatus extends AppCompatActivity {
private String hostel,key,request,name,fname,phone,room,type,datefrom,dateto,desc,status,h_name,userid;
    private Dialog dialog;
private DatabaseReference db;
private TextView up_name,up_father,up_phone,up_room,up_hostel,up_datefrom,up_dateto,up_status_pending,up_desc,up_type;
private AppCompatButton up_status;
private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);
        up_name = findViewById(R.id.up_name);
        up_father = findViewById(R.id.up_father);
        up_phone = findViewById(R.id.up_phone);
        up_room = findViewById(R.id.up_room);
        up_hostel = findViewById(R.id.up_hostel);
        up_desc = findViewById(R.id.up_desc);
        up_type = findViewById(R.id.up_category);
        up_desc = findViewById(R.id.up_desc);
        up_status_pending = findViewById(R.id.up_status_pending);
        up_datefrom = findViewById(R.id.up_datefrom);
        up_dateto = findViewById(R.id.up_dateto);
        up_status = findViewById(R.id.up_status);

        dialog = new Dialog(UpdateStatus.this);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);

        Intent intent = getIntent();
        key = intent.getStringExtra("key_");
        hostel = intent.getStringExtra("hostel");
        request = intent.getStringExtra("request");
        name = intent.getStringExtra("name");
        fname = intent.getStringExtra("father");
        phone = intent.getStringExtra("phone");
        room = intent.getStringExtra("room");
        h_name = intent.getStringExtra("h_name");
        type = intent.getStringExtra("type");
        desc = intent.getStringExtra("desc");
        status = intent.getStringExtra("status");
        datefrom = intent.getStringExtra("date_from");
        dateto = intent.getStringExtra("date_to");
        userid = intent.getStringExtra("userid");
        i = intent.getIntExtra("id", -1);


//        db= FirebaseDatabase.getInstance().getReference().child("Request").child(hostel).child(request);
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                    for (DataSnapshot snapshot2:snapshot1.child(key).getChildren()) {
////                        user_room_request_data data = snapshot2.getValue(user_room_request_data.class);
////                        if (data != null) {
////                            if (data.getStatus().equals("Pending")) {
//
//                                String name=snapshot2.child("st_name").getValue().toString();
//                                String fname=snapshot2.child("parent_name").getValue().toString();
//                                String phone=snapshot2.child("st_phone").getValue().toString();
//                                String hostel=snapshot2.child("hostel_name").getValue().toString();
//                                String room=snapshot2.child("room_no").getValue().toString();
//                                String type=snapshot2.child("type").getValue().toString();
//                                String datefrom=snapshot2.child("date_from").getValue().toString();
//                                String dateto=snapshot2.child("date_to").getValue().toString();
//                                String desc=snapshot2.child("desc").getValue().toString();
//                                String status=snapshot2.child("status").getValue().toString();
//
        if (i == 1) {
            up_name.setText(name);
            up_father.setText(fname);
            up_phone.setText(phone);
            up_hostel.setText(h_name);
            up_room.setText(room);
            up_type.setText(type);
            up_datefrom.setText(datefrom);
            up_dateto.setText(dateto);
            up_desc.setText(desc);
            up_status_pending.setText(status);
            up_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status = "Approved";
                    HashMap map = new HashMap<>();
                    map.put("status", status);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Request").child(hostel).child(request).child(userid).child(key);
                    databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdateStatus.this, "Successfully update ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//
                }
            });

        } else if (i == 2) {
            up_datefrom.setVisibility(View.GONE);
            up_dateto.setVisibility(View.GONE);
            up_name.setText(name);
            up_father.setText(fname);
            up_phone.setText(phone);
            up_hostel.setText(h_name);
            up_room.setText(room);
            up_type.setText(type);
//            up_datefrom.setText(datefrom);
//            up_dateto.setText(dateto);
            up_desc.setText(desc);
            up_status_pending.setText(status);
            up_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status = "Approved";
                    HashMap map = new HashMap<>();
                    map.put("status", status);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Request").child(hostel).child(request).child(userid).child(key);
                    databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdateStatus.this, "Successfully update ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//
                }
            });
        } else if (i == 3) {
            up_datefrom.setVisibility(View.GONE);
            up_dateto.setVisibility(View.GONE);
            up_name.setText(name);
            up_father.setText(fname);
            up_phone.setText(phone);
            up_hostel.setText(h_name);
            up_room.setText(room);
            up_type.setText(type);
//            up_datefrom.setText(datefrom);
//            up_dateto.setText(dateto);
            up_desc.setText(desc);
            up_status_pending.setText(status);
            up_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status = "Approved";
                    HashMap map = new HashMap<>();
                    map.put("status", status);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Request").child(hostel).child(request).child(userid).child(key);
                    databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdateStatus.this, "Successfully update ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//
                }
            });
        }
    }
////                        }else
////                        {
////                            Toast.makeText(UpdateStatus.this, "Something went wrong", Toast.LENGTH_SHORT).show();
////                        }
//                    }
//                }
//                dialog.dismiss();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UpdateStatus.this, "Sorry! Database not fetch data ", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });


}