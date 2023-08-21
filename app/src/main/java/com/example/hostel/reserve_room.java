package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class reserve_room extends AppCompatActivity {

    private Toolbar toolbar;
    private String room_key, updt_room_key, updt_bed_key;
    public String hostel_name_keyy, key__;
    private TextView title_toolbar_tv,reserve_empty_notes;
    private DatabaseReference dbref;
    private student_room_recycler student_room_recycler;
    private RecyclerView recyclerView;
    private ArrayList<student_room_data> arrayList;
    private ActionMenuItemView add;
    private int bed,people;
    private ProgressBar pd_reserve;
    private LinearLayout ll_reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_room);
        Intent intent1 = getIntent();
        key__ = intent1.getStringExtra("key");
        hostel_name_keyy = intent1.getStringExtra("hostel_name_key");
        room_key = intent1.getStringExtra("room_key");
        people=intent1.getIntExtra("people",-1);
        String bed_key = intent1.getStringExtra("bed_key");
        bed = Integer.parseInt(bed_key);
        toolbar = findViewById(R.id.toolbar1);
        title_toolbar_tv = findViewById(R.id.title_toolbar);
        pd_reserve=findViewById(R.id.pd_reserve);
        ll_reserve=findViewById(R.id.ll_reserve);
        reserve_empty_notes=findViewById(R.id.reserve_empty_notes);
        title_toolbar_tv.setText("Room Detail");
        toolbar.inflateMenu(R.menu.room_detail_menu);
        toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));
        recyclerView = findViewById(R.id.recycler);
        add = (ActionMenuItemView) findViewById(R.id.menu_add);
        fetch_reserve_st();


    }

    public void fetch_reserve_st() {


        dbref = FirebaseDatabase.getInstance().getReference().child("Hostel").child(hostel_name_keyy).child(key__);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        student_room_recycler = new student_room_recycler(reserve_room.this, arrayList);
        student_room_recycler.notifyDataSetChanged();


        dbref.child("student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    student_room_data model = dataSnapshot.getValue(student_room_data.class);
                    arrayList.add(model);
                }
                if(arrayList.size() >0) {
                    ll_reserve.setVisibility(View.VISIBLE);
                    pd_reserve.setVisibility(View.GONE);
                    recyclerView.setAdapter(student_room_recycler);
                    student_room_recycler.notifyDataSetChanged();
                    if (arrayList.size() >= bed) {
                        add.setVisibility(View.GONE);

                    }
                }else
                {
                    pd_reserve.setVisibility(View.GONE);
                    reserve_empty_notes.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(reserve_room.this, "Error" + error, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean onMenuItemClick(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.menu_add) {
            Intent i = new Intent(this, add_student_room.class);
            i.putExtra("key_value", key__);
            i.putExtra("hostel_key", hostel_name_keyy);
            i.putExtra("room_keyy", room_key);
            i.putExtra("people",people);
            startActivity(i);
        } else if (menuItem.getItemId() == R.id.menu_edit) {
            update_room_data();

        } else if (menuItem.getItemId() == R.id.menu_delete) {
            if (arrayList.size() > 0) {
                Toast.makeText(this, "Cannot delete room because room is not empty  ", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete room!!");
                builder.setMessage("Are you sure you want to delete this room!!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(reserve_room.this, Admin.class));
                                finishAffinity();
                                Toast.makeText(reserve_room.this, "Room Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                return false;

            }
        }
        return false;


    }

    private void update_room_data() {
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                updt_room_key = String.valueOf(snapshot.child("room_no_1").getValue());
                updt_bed_key = String.valueOf(snapshot.child("bed_no_1").getValue());

                Intent intent_1 = new Intent(reserve_room.this, update_room.class);
                intent_1.putExtra("updt_room_key", updt_room_key);
                intent_1.putExtra("updt_bed_key", updt_bed_key);
                intent_1.putExtra("updt_hostel_key", hostel_name_keyy);
                intent_1.putExtra("updt_unique_key", key__);
                startActivity(intent_1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}