package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class add_student_room extends AppCompatActivity {


    private EditText st_name_edt, st_phone_edt, st_hostel_fee_edt, st_parent_name_edt, st_parent_phone_edt, st_address_edt;
    private AppCompatButton st_add_btn;
    private DatabaseReference db, dbref;
    private String name_var, phone_var, parent_name, parent_phone, hostel_fee, address_var;
    private ProgressBar pd_add;
    private final static String apj_hostel="APJ Boys Hostel";
    private final static String sv_hostel="S V Boys Hostel";
    private final static String girls_hostel="K C Girls Hostel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_room);
        st_name_edt = findViewById(R.id.st_name_edt);
        st_phone_edt = findViewById(R.id.st_phone_edt);
        st_parent_name_edt = findViewById(R.id.st_parent_name_edt);
        st_parent_phone_edt = findViewById(R.id.st_parent_phone_edt);
        st_hostel_fee_edt = findViewById(R.id.st_hostel_fee_edt);
        st_address_edt = findViewById(R.id.st_address_edt);
        st_add_btn = findViewById(R.id.st_add_btn);
        pd_add=findViewById(R.id.pd_add_st);
       Intent intent2 = getIntent();
        String key_intent = intent2.getStringExtra("key_value");
        String hostel_name = intent2.getStringExtra("hostel_key");
        String room_key_var = intent2.getStringExtra("room_keyy");


//        if(hostel_name.equals(apj_hostel) || hostel_name.equals(sv_hostel))
//        {
//            String hostel_child = "Boys Hostel";
//
//        } else if (hostel_name.equals(girls_hostel)) {
//            String hostel_name_child ="Girls Hostel";
//            db = FirebaseDatabase.getInstance().getReference().child("Hostel").child(hostel_name_child).child(hostel_name);
//            dbref = db.child(key_intent);
//        }


        db = FirebaseDatabase.getInstance().getReference().child("Hostel").child(hostel_name);
            dbref = db.child(key_intent);

        st_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_var = st_name_edt.getText().toString();
                phone_var = st_phone_edt.getText().toString();
                parent_name = st_parent_name_edt.getText().toString();
                parent_phone = st_parent_phone_edt.getText().toString();
                hostel_fee = st_hostel_fee_edt.getText().toString();
                address_var = st_address_edt.getText().toString();
                if(name_var.isEmpty() || phone_var.isEmpty() || parent_name.isEmpty() || parent_phone.isEmpty() || hostel_fee.isEmpty() || address_var.isEmpty())
                {
                    Toast.makeText(add_student_room.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    }
                else {

                    pd_add.setVisibility(View.VISIBLE);
                    st_add_btn.setVisibility(View.GONE);



                    String uniquekey = db.push().getKey();
                    student_room_data studentRoomData = new student_room_data(name_var, phone_var, parent_name, parent_phone, hostel_fee, address_var, hostel_name, room_key_var, uniquekey);
                    dbref.child("student").child(uniquekey).setValue(studentRoomData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                dbref.child("student").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            long a = snapshot.getChildrenCount();
                                            HashMap map = new HashMap<>();
                                            map.put("people", a);
                                            dbref.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    if (task.isSuccessful()) {
                                                        pd_add.setVisibility(View.GONE);
                                                        st_add_btn.setVisibility(View.VISIBLE);
                                                        Toast.makeText(add_student_room.this, "Student add successfully", Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        pd_add.setVisibility(View.GONE);
                                                        st_add_btn.setVisibility(View.VISIBLE);
                                                        Toast.makeText(add_student_room.this, "Please try again !!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    pd_add.setVisibility(View.GONE);
                                                    st_add_btn.setVisibility(View.VISIBLE);
                                                    Toast.makeText(add_student_room.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        pd_add.setVisibility(View.GONE);
                                        st_add_btn.setVisibility(View.VISIBLE);
                                        Toast.makeText(add_student_room.this, "Something went wong", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            pd_add.setVisibility(View.GONE);
                            st_add_btn.setVisibility(View.VISIBLE);
                            Toast.makeText(add_student_room.this, "Something went wong", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });


    }
}