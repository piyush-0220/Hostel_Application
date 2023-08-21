package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

public class Add_room extends AppCompatActivity {
    ArrayList<DataModel> arrayList = new ArrayList<>();
    private EditText bed_no, room_no;
    private Button add_btn;
    private String b, r;
    private ProgressBar pd;

    DatabaseReference databaseReference, dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        bed_no = findViewById(R.id.bed_edt);
        room_no = findViewById(R.id.room_edt);
        add_btn = findViewById(R.id.add_btn);
        pd=findViewById(R.id.pd_add_room);


        Intent intent1 = getIntent();
        String hostel_name = intent1.getStringExtra("hostel");
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b = bed_no.getText().toString();
                r = room_no.getText().toString();

                if(b.isEmpty() || r.isEmpty()) {
                    Toast.makeText(Add_room.this, "Please enter something!!", Toast.LENGTH_SHORT).show();
                }
                else {
                     pd.setVisibility(View.VISIBLE);
                     add_btn.setVisibility(View.GONE);

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Hostel");
                    final String uniquekey = databaseReference.push().getKey();
                    DataModel dataModel = new DataModel(b, r, uniquekey, hostel_name, 0);
                    dbref = databaseReference.child(hostel_name);
                    dbref.child(uniquekey).setValue(dataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                pd.setVisibility(View.GONE);
                                add_btn.setVisibility(View.VISIBLE);
                                Toast.makeText(Add_room.this, "Room added successfully!!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                pd.setVisibility(View.GONE);
                                add_btn.setVisibility(View.VISIBLE);
                                Toast.makeText(Add_room.this, "Please try again !!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.setVisibility(View.GONE);
                            add_btn.setVisibility(View.VISIBLE);
                            Toast.makeText(Add_room.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });


    }
}