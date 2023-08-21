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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class update_room extends AppCompatActivity {
    private EditText updt_bed_no, updt_room_no;
    private AppCompatButton updt_btn;
    private ProgressBar pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);
        updt_bed_no = findViewById(R.id.updt_bed_edt);
        updt_room_no = findViewById(R.id.updt_room_edt);
        pd=findViewById(R.id.pd_updt);
        updt_btn = findViewById(R.id.updt_btn);
        Intent i_1 = getIntent();
        String updt_room_var = i_1.getStringExtra("updt_room_key");
        String updt_bed_var = i_1.getStringExtra("updt_bed_key");
        String updt_hostel_var = i_1.getStringExtra("updt_hostel_key");
        String updt_unique_key = i_1.getStringExtra("updt_unique_key");
        updt_room_no.setText(updt_room_var);
        updt_bed_no.setText(updt_bed_var);

        updt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bed_var = updt_bed_no.getText().toString();
                String room_var = updt_room_no.getText().toString();
                if(bed_var.isEmpty() || room_var.isEmpty())
                {
                    Toast.makeText(update_room.this, "All fields are required !!", Toast.LENGTH_SHORT).show();
                }
                else {
                    updadtedata(bed_var, room_var, updt_hostel_var, updt_unique_key);
                }

            }
        });

    }

    private void updadtedata(String bed_var, String room_var, String updt_hostel_var, String updt_unique_key) {
        pd.setVisibility(View.VISIBLE);
        updt_btn.setVisibility(View.GONE);
        HashMap map = new HashMap<>();
        map.put("room_no_1", room_var);
        map.put("bed_no_1", bed_var);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Hostel").child(updt_hostel_var).child(updt_unique_key);
        databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    pd.setVisibility(View.GONE);
                    updt_btn.setVisibility(View.VISIBLE);
                    Toast.makeText(update_room.this, "Successfully update", Toast.LENGTH_SHORT).show();

                }else {
                    pd.setVisibility(View.GONE);
                    updt_btn.setVisibility(View.VISIBLE);
                    Toast.makeText(update_room.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.setVisibility(View.GONE);
                updt_btn.setVisibility(View.VISIBLE);
                Toast.makeText(update_room.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}