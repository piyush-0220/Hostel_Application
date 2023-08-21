package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
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

import org.w3c.dom.Text;

public class user_support_activity extends AppCompatActivity {
    private String user_name_key, user_fname_key, user_phone_key, user_id_key;
    private DatabaseReference dbreference, db;

    private EditText name_support, room_no_support, category_support, pending_support, desc_support;
    private AppCompatButton submit_btn;
    private final static String apj_hostel = "APJ Boys Hostel";
    private final static String sv_hostel = "S V Boys Hostel";
    private final static String girls_hostel = "K C Girls Hostel";
    private String st_name_x, st_parent_x, st_phone_x, st_hostel_x, st_room_x;
    private TextView support_empty_note;
    private LinearLayout ll_layout_support;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_support);
        getSupportActionBar().setTitle("Help & Support");
        name_support = findViewById(R.id.name_edt_support);
        room_no_support = findViewById(R.id.room_no_support);
        category_support = findViewById(R.id.user_category_support);
        pending_support = findViewById(R.id.pending_edt_support);
        desc_support = findViewById(R.id.description_edt_support);
        submit_btn = findViewById(R.id.submit_btn_support);
        support_empty_note = findViewById(R.id.support_empty_notes);
        ll_layout_support = findViewById(R.id.ll_layout_suppot);


        Intent intent = getIntent();
        user_name_key = intent.getStringExtra("name");
        user_fname_key = intent.getStringExtra("parent_name");
        user_phone_key = intent.getStringExtra("phone");
        user_id_key = intent.getStringExtra("userId");
        db = FirebaseDatabase.getInstance().getReference().child("Request");


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
                                    support_empty_note.setVisibility(View.GONE);
                                    ll_layout_support.setVisibility(View.VISIBLE);

                                    st_name_x = user_room_request_data.getSt_name();
                                    st_parent_x = user_room_request_data.getParent_name();
                                    st_phone_x = user_room_request_data.getSt_phone();
                                    st_hostel_x = user_room_request_data.getHostel_name();
                                    st_room_x = user_room_request_data.getRoom_no();

                                    name_support.setText(st_name_x);
                                    name_support.setEnabled(false);
                                    room_no_support.setText(st_room_x);
                                    room_no_support.setEnabled(false);
                                    pending_support.setText("Pending");
                                    pending_support.setEnabled(false);


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

            String name_var = name_support.getText().toString();
            String room_no_var = room_no_support.getText().toString();
            String category = category_support.getText().toString();
            String status = pending_support.getText().toString();
            String desc_var = desc_support.getText().toString();
            if(category.isEmpty() || desc_var.isEmpty()) {
                String unq_key = db.push().getKey();
                user_room_request_data user_room_request_data = new user_room_request_data(name_var, st_phone, st_parent, hostel_name, room_no_var, category, status, desc_var, unq_key, user_id_key);

                db.child("Boys Hostel").child("Support").child(user_id_key).child(unq_key).setValue(user_room_request_data);
                Toast.makeText(this, "Help request Uploaded successfully", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(this, "Plz enter something!!", Toast.LENGTH_SHORT).show();
            }

        } else if (hostel_name.equals(girls_hostel)) {
            String name_var = name_support.getText().toString();
            String room_no_var = room_no_support.getText().toString();
            String category = category_support.getText().toString();
            String status = pending_support.getText().toString();
            String desc_var = desc_support.getText().toString();

            user_room_request_data user_room_request_data = new user_room_request_data(name_var, st_phone, st_parent, hostel_name, room_no_var, category, status, desc_var);
            String unq_key = db.push().getKey();
            db.child("Girls Hostel").child("Support").child(user_id_key).child(unq_key).setValue(user_room_request_data);
            Toast.makeText(this, "Help request Uploaded successfully", Toast.LENGTH_SHORT).show();

        }

    }
}
