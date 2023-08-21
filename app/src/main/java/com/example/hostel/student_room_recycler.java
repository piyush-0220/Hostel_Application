package com.example.hostel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class student_room_recycler extends RecyclerView.Adapter<student_room_recycler.student_room> {

    private Context context;
    private ArrayList<student_room_data> list;   //beacause database store only url;

    public student_room_recycler(Context context, ArrayList<student_room_data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public student_room onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_room_booking, parent, false);
        return new student_room(view);
    }

    @Override
    public void onBindViewHolder(@NonNull student_room_recycler.student_room holder, int position) {
        student_room_data student_room_data = list.get(position);
        holder.st_name_var.setText(student_room_data.getSt_name());
        holder.st_phone_var.setText(student_room_data.getSt_phone());
        holder.parent_name_var.setText(student_room_data.getParent_name());
        holder.parent_phone_var.setText(student_room_data.getParent_phone());
        holder.hostel_fee_var.setText(student_room_data.getHostel_fee());
        holder.address_var.setText(student_room_data.getAddress());
        holder.delete_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hostel_keyy = ((reserve_room) context).hostel_name_keyy;
                String st_kyy = ((reserve_room) context).key__;
                DatabaseReference dbreference = FirebaseDatabase.getInstance().getReference().child("Hostel").child(hostel_keyy).child(st_kyy).child("student").child(student_room_data.getSt_key());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete student!!");
                builder.setMessage("Are you sure you want to delete this student!!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbreference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void unused) {
                                ((reserve_room) context).fetch_reserve_st();
                                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Hostel").child(hostel_keyy).child(st_kyy);
                                db.child("student").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            long a= snapshot.getChildrenCount();
                                            HashMap map = new HashMap<>();
                                            map.put("people", a);
                                            db.updateChildren(map);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                Toast.makeText(context, "Delete student successfully", Toast.LENGTH_SHORT).show();
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


            }
        });

        holder.dial_st.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent dial = new Intent(Intent.ACTION_DIAL);
                dial.setData(Uri.parse("tel: " + student_room_data.getSt_phone()));
                context.startActivity(dial);
            }
        });

        holder.edit_st_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hostel_keyy = ((reserve_room) context).hostel_name_keyy;
                String st_kyy = ((reserve_room) context).key__;
                Intent intent= new Intent(context,Update_student.class);
                intent.putExtra("get_name",student_room_data.getSt_name());
                intent.putExtra("get_parent",student_room_data.getParent_name());
                intent.putExtra("get_phone",student_room_data.getSt_phone());
                intent.putExtra("get_parent_phone",student_room_data.getParent_phone());
                intent.putExtra("get_Address",student_room_data.getAddress());
                intent.putExtra("get_hostel_fee",student_room_data.getHostel_fee());
                intent.putExtra("get_hostel_key",hostel_keyy);
                intent.putExtra("get_st_key",st_kyy);
                intent.putExtra("get_student_key",student_room_data.getSt_key());

                context.startActivity(intent);

            }
        });


        holder.dial_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dial = new Intent(Intent.ACTION_DIAL);
                dial.setData(Uri.parse("tel: " + student_room_data.getParent_phone()));
                context.startActivity(dial);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class student_room extends RecyclerView.ViewHolder {

        private TextView st_name_var, st_phone_var, parent_phone_var, parent_name_var, hostel_fee_var, address_var;
        private ImageView delete_st, dial_st, dial_parent,edit_st_;

        public student_room(@NonNull View itemView) {
            super(itemView);
            st_phone_var = itemView.findViewById(R.id.st_phone_tv);
            st_name_var = itemView.findViewById(R.id.st_name_tv);
            parent_name_var = itemView.findViewById(R.id.parent_name_tv);
            parent_phone_var = itemView.findViewById(R.id.parent_phone_tv);
            hostel_fee_var = itemView.findViewById(R.id.st_hostel_fee_tv);
            address_var = itemView.findViewById(R.id.address_tv);
            delete_st = itemView.findViewById(R.id.delete_st);
            dial_st = itemView.findViewById(R.id.dial_st);
            edit_st_ = itemView.findViewById(R.id.edit_st_);
            dial_parent = itemView.findViewById(R.id.dial_parent);

        }
    }
}
