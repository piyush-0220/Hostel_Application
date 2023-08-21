package com.example.hostel;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class user_support_fragment extends Fragment {


    public user_support_fragment() {
        // Required empty public constructor
    }

    private String user_name_key, user_fname_key, user_phone_key, hostel_name, user_id_key;
    private ArrayList<user_request_fragment_data> arrayList;
    private user_request_fragment_Adapter adapter;
    private DatabaseReference reference, dbref;
    private final static String apj_hostel = "APJ Boys Hostel";
    private final static String sv_hostel = "S V Boys Hostel";
    private final static String girls_hostel = "K C Girls Hostel";
    private RecyclerView recyclerView;
//    private Dialog dialog;
    private TextView user_support_empty_note;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_support_fragment, container, false);
        user_support_empty_note = view.findViewById(R.id.user_support_empty_notes);
//        dialog = new Dialog(user_support_fragment.this.getActivity());
//        dialog.setContentView(R.layout.dialog);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        dialog.setCancelable(false);
//        dialog.show();


        user_request_activity a = (user_request_activity) getActivity();
        Bundle r = a.getMyData_user();
        user_name_key = r.getString("name");
        user_fname_key = r.getString("parent_name");
        user_phone_key = r.getString("phone");
        user_id_key = r.getString("userId");
        recyclerView = view.findViewById(R.id.user_support_rv);
        arrayList = new ArrayList<>();
        adapter = new user_request_fragment_Adapter(arrayList, user_support_fragment.this.getActivity());
        adapter.notifyDataSetChanged();
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("Hostel");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {

                            for (DataSnapshot snapshot3 : snapshot2.child("student").getChildren()) {
                                student_room_data user_room_data = snapshot3.getValue(student_room_data.class);
                                if (user_room_data.getSt_name().equals(user_name_key) || user_room_data.getParent_name().equals(user_fname_key) && user_room_data.getSt_phone().equals(user_phone_key)) {
                                    user_room_request_data user_room_request_data = new user_room_request_data(user_room_data.getSt_name(), user_room_data.getSt_phone(), user_room_data.getParent_name(), user_room_data.getHostel_name(), user_room_data.getRoom_no());
                                    if (user_room_request_data != null) {
                                        recyclerView.setVisibility(View.VISIBLE);
                                        user_support_empty_note.setVisibility(View.GONE);
                                        String st_hostel_x = user_room_request_data.getHostel_name();
                                        fetch_leave(st_hostel_x);

                                    } else {

                                       // dialog.dismiss();

                                    }

                                    break;
                                }


                            }
                        }
                    }

                    //dialog.dismiss();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                   // dialog.dismiss();
                }
            });
        } catch (Exception e) {

        }


        return view;
    }

    private void fetch_leave(String st_hostel_x) {
        if (st_hostel_x.equals(apj_hostel) || st_hostel_x.equals(sv_hostel)) {
            hostel_name = "Boys Hostel";
            fetch_request(hostel_name);

        } else if (st_hostel_x.equals(girls_hostel)) {
            hostel_name = "Girls Hostel";
            fetch_request(hostel_name);
        }
    }

    private void fetch_request(String hostel_name) {
       // dialog.show();
        dbref = FirebaseDatabase.getInstance().getReference().child("Request").child(hostel_name).child("Support").child(user_id_key);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    user_request_fragment_data data = snapshot1.getValue(user_request_fragment_data.class);
                    arrayList.add(data);
                }
             //   dialog.dismiss();
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(user_support_fragment.this.getActivity()));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            //    dialog.dismiss();
            }
        });

    }

}