package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ApplyLeave extends AppCompatActivity {
    private TextView room_empty_note;
    private LinearLayout linear1;
    private String user_name_key, user_fname_key, user_phone_key, user_id_key;
    private DatabaseReference dbreference, db;
    private EditText select_type, date_from, date_to, status, desc;
    private AppCompatButton submit_btn;
    private final static String apj_hostel = "APJ Boys Hostel";
    private final static String sv_hostel = "S V Boys Hostel";
    private final static String girls_hostel = "K C Girls Hostel";
    private String st_name_x, st_parent_x, st_phone_x, st_hostel_x, st_room_x;
    private Dialog dialog;
    private MyCalendar calendar;

    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);
        getSupportActionBar().setTitle("Leave Request");
        calendar = new MyCalendar();
        dialog = new Dialog(ApplyLeave.this);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);

        room_empty_note = findViewById(R.id.leave_empty_notes);
//        ll_layout = findViewById(R.id.ll_layout);
        select_type = findViewById(R.id.type_edt);
        date_from = findViewById(R.id.date_from);
        linear1=findViewById(R.id.linear1);
        date_to = findViewById(R.id.date_to);
        status = findViewById(R.id.pending_edt);
        desc = findViewById(R.id.description_edt);
        submit_btn = findViewById(R.id.submit_btn);


        Intent intent = getIntent();
        user_name_key = intent.getStringExtra("name");
        user_fname_key = intent.getStringExtra("parent_name");
        user_phone_key = intent.getStringExtra("phone");
        user_id_key = intent.getStringExtra("userId");
        db = FirebaseDatabase.getInstance().getReference().child("Request");
        select_type.setInputType(InputType.TYPE_NULL);
        select_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog1 = new Dialog(ApplyLeave.this);
                dialog1.setContentView(R.layout.custom_dialog_type);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));


                TextView txt_sick = dialog1.findViewById(R.id.txt_sick);

                txt_sick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String t = txt_sick.getText().toString();

                        select_type.setText(t);
                        dialog1.dismiss();
                        //  Toast.makeText(MainActivity.this, t, Toast.LENGTH_SHORT).show();

                    }
                });

                TextView txt_holiday = dialog1.findViewById(R.id.txt_holiday);
                txt_holiday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String p = txt_holiday.getText().toString();

                        select_type.setText(p);
                        dialog1.dismiss();
                    }
                });

                TextView txt_other = dialog1.findViewById(R.id.txt_other);
                txt_other.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String q = txt_other.getText().toString();
                        select_type.setText(q);
                        dialog1.dismiss();
                    }
                });

                dialog1.show();
            }
        });


        try {
            dbreference = FirebaseDatabase.getInstance().getReference().child("Hostel");
            dbreference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dialog.show();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {

                            for (DataSnapshot snapshot3 : snapshot2.child("student").getChildren()) {
                                student_room_data user_room_data = snapshot3.getValue(student_room_data.class);
                                if (user_room_data.getSt_name().equals(user_name_key) || user_room_data.getParent_name().equals(user_fname_key) && user_room_data.getSt_phone().equals(user_phone_key)) {
                                    user_room_request_data user_room_request_data = new user_room_request_data(user_room_data.getSt_name(), user_room_data.getSt_phone(), user_room_data.getParent_name(), user_room_data.getHostel_name(), user_room_data.getRoom_no());
                                    if (user_room_request_data != null) {
                                        dialog.dismiss();
                                        room_empty_note.setVisibility(View.GONE);
                                        linear1.setVisibility(View.VISIBLE);
                                        st_name_x = user_room_request_data.getSt_name();
                                        st_parent_x = user_room_request_data.getParent_name();
                                        st_phone_x = user_room_request_data.getSt_phone();
                                        st_hostel_x = user_room_request_data.getHostel_name();
                                        st_room_x = user_room_request_data.getRoom_no();
                                        status.setText("Pending");
                                        status.setEnabled(false);

                                    } else {
                                        dialog.dismiss();


                                    }


                                }


                            }
                        }
                    }
                    dialog.dismiss();

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    dialog.dismiss();

                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Sorry! Database Error", Toast.LENGTH_SHORT).show();
        }
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadata(st_name_x, st_phone_x, st_parent_x, st_hostel_x, st_room_x);
            }
        });


        date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 1;
                showDatePickerDialog();
            }
        });

        date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 2;
                showDatePickerDialog();

            }
        });


    }

    private void showDatePickerDialog() {


        MyCalendar calendar = new MyCalendar();
        calendar.show(getSupportFragmentManager(), "");
        calendar.setOnCalendarOkClickListener(this::onCalendarOkClicked);


    }

    private void onCalendarOkClicked(int year, int month, int day) {
        calendar.setDate(year, month, day);
        if (i == 1) {
            date_from.setText(calendar.getDate());
        } else if (i == 2) {
            date_to.setText(calendar.getDate());
        }

    }


    private void uploadata(String st_name, String st_phone, String st_parent, String hostel_name, String st_room_no) {
        dialog.show();
        if (hostel_name.equals(apj_hostel) || hostel_name.equals(sv_hostel)) {

            String select_user = select_type.getText().toString();
            String date_from_var = date_from.getText().toString();
            String date_to_var = date_to.getText().toString();
            String desc_var = desc.getText().toString();
            String status_var = status.getText().toString();
            String unq_key = db.push().getKey();
            user_room_request_data user_room_request_data = new user_room_request_data(st_name, st_phone, st_parent, hostel_name, st_room_no, select_user, date_from_var, date_to_var, desc_var, status_var,unq_key,user_id_key);

            db.child("Boys Hostel").child("Leave").child(user_id_key).child(unq_key).setValue(user_room_request_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        Toast.makeText(ApplyLeave.this, "Leave request Uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(ApplyLeave.this, "Please try again later", Toast.LENGTH_SHORT).show();
                }
            });


        } else if (hostel_name.equals(girls_hostel)) {
            dialog.show();
            String select_user = select_type.getText().toString();
            String date_from_var = date_from.getText().toString();
            String date_to_var = date_to.getText().toString();
            String desc_var = desc.getText().toString();
            String status_var = status.getText().toString();
            String unq_key = db.push().getKey();
            user_room_request_data user_room_request_data = new user_room_request_data(st_name, st_phone, st_parent, hostel_name, st_room_no, select_user, date_from_var, date_to_var, desc_var, status_var,unq_key,user_id_key);

            db.child("Girls Hostel").child("Leave").child(user_id_key).child(unq_key).setValue(user_room_request_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        Toast.makeText(ApplyLeave.this, "Leave request Uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(ApplyLeave.this, "Please try again later", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public void onBackPressed() {
        dialog.dismiss();
        super.onBackPressed();
    }
}