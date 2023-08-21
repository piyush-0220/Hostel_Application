package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Update_student extends AppCompatActivity {
    private String name,parent_name,st_phone,parent_phone,address,hostel_fee,hostel_key,child_key,st_key;
    private DatabaseReference db;
    private EditText st_name_edt, st_phone_edt, st_hostel_fee_edt, st_parent_name_edt, st_parent_phone_edt, st_address_edt;
    private AppCompatButton st_add_btn;
    private String name_var, phone_var, parent_name_var, parent_phone_var, hostel_fee_var, address_var;
    private ProgressBar pd_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
        st_name_edt = findViewById(R.id.st_name_updt);
        st_phone_edt = findViewById(R.id.st_phone_updt);
        st_parent_name_edt = findViewById(R.id.st_parent_name_updt);
        st_parent_phone_edt = findViewById(R.id.st_parent_phone_updt);
        st_hostel_fee_edt = findViewById(R.id.st_hostel_fee_updt);
        st_address_edt = findViewById(R.id.st_address_updt);
        st_add_btn = findViewById(R.id.st_updt_btn);
        pd_add=findViewById(R.id.pd_updt_st);



        Intent intent2 = getIntent();
         name = intent2.getStringExtra("get_name");
         parent_name = intent2.getStringExtra("get_parent");
         st_phone = intent2.getStringExtra("get_phone");
         parent_phone = intent2.getStringExtra("get_parent_phone");
         address = intent2.getStringExtra("get_Address");
        hostel_fee = intent2.getStringExtra("get_hostel_fee");
        hostel_key = intent2.getStringExtra("get_hostel_key");
        child_key = intent2.getStringExtra("get_st_key");
        st_key = intent2.getStringExtra("get_student_key");
        st_name_edt.setText(name);
        st_phone_edt.setText(st_phone);
        st_parent_name_edt.setText(parent_name);
        st_parent_phone_edt.setText(parent_phone);
        st_address_edt.setText(address);
        st_hostel_fee_edt.setText(hostel_fee);
        st_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseDatabase.getInstance().getReference().child("Hostel").child(hostel_key).child(child_key).child("student").child(st_key);
                name_var = st_name_edt.getText().toString();
                phone_var = st_phone_edt.getText().toString();
                parent_name_var = st_parent_name_edt.getText().toString();
                parent_phone_var = st_parent_phone_edt.getText().toString();
                hostel_fee_var = st_hostel_fee_edt.getText().toString();
                address_var = st_address_edt.getText().toString();

                if(name_var.isEmpty() || phone_var.isEmpty() || parent_name_var.isEmpty() || parent_phone_var.isEmpty() || hostel_fee_var.isEmpty() || address_var.isEmpty())
                {
                    Toast.makeText(Update_student.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                    pd_add.setVisibility(View.VISIBLE);
                    st_add_btn.setVisibility(View.GONE);
                    HashMap map = new HashMap();
                    map.put("st_name",name_var);
                    map.put("st_phone",phone_var);
                    map.put("st_parent_name",parent_name_var);
                    map.put("st_parent_phone",parent_phone_var);
                    map.put("st_hostel_fee",hostel_fee_var);
                    map.put("st_address",address_var);

                    db.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            pd_add.setVisibility(View.GONE);
                            st_add_btn.setVisibility(View.VISIBLE);
                            Toast.makeText(Update_student.this, "Student update successfully", Toast.LENGTH_SHORT).show();
                        }
                    });




                }
            }
        });






    }
}