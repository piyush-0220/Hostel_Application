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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ServiceFragment extends Fragment {

    private DatabaseReference dbref;

    private ArrayList<user_room_request_data> arrayList;
    private admin_service_fragment_Adapter adapter;
    private RecyclerView recyclerView;
    private Dialog dialog;
    private TextView Leave_empty_note;

    private String admin_email_k,a_e_k;
    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_service, container, false);
        dialog = new Dialog(ServiceFragment.this.getActivity());
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        recyclerView = view.findViewById(R.id.service_rv);
        arrayList = new ArrayList<>();
        adapter = new admin_service_fragment_Adapter(arrayList, ServiceFragment.this.getActivity());
        Leave_empty_note=view.findViewById(R.id.Service_empty_notes);
        Admin a = (Admin) getActivity();
        Bundle r = a.getMyData();
        admin_email_k = r.getString("email_key");
        if (admin_email_k.equals("boyshostel1984@gmail.com")) {
            a_e_k = "Boys Hostel";
            fetch_request(a_e_k);
        } else if (admin_email_k.equals("girlshostel1984@gmail.com")) {
            a_e_k = "Girls Hostel";
            fetch_request(a_e_k);
        }






        return view;
    }




    private void fetch_request(String a_e_k) {
        dialog.show();
        dbref = FirebaseDatabase.getInstance().getReference().child("Request").child(a_e_k).child("Service");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                adapter.notifyDataSetChanged();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    for (DataSnapshot snapshot2:snapshot1.getChildren()) {
                        user_room_request_data data = snapshot2.getValue(user_room_request_data.class);
                        if (data != null) {

                            if (data.getStatus().equals("Pending")) {
                                Leave_empty_note.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                arrayList.add(0,data);
                            } else if (data.getStatus().equals("Approved")) {
                                Leave_empty_note.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                arrayList.add(data);
                            }
                        }else
                        {
                            Toast.makeText(ServiceFragment.this.getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                dialog.dismiss();
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(ServiceFragment.this.getActivity()));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ServiceFragment.this.getActivity(), "Sorry! Database not fetch data ", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }
}