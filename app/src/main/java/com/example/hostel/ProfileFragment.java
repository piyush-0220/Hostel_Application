package com.example.hostel;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    private Toolbar toolbar;


    private TextView title_toolbar_tv;
    private String admin_email_k;
    private static final String KEY_EMAIL = "email_a";
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private warden_adapter adapter;
    private String warden_n, warden_e, warden_mobile, warden_hostel, warden_post;
    private ArrayList<warden_data> arrayList = new ArrayList<>();
    private ActionMenuItemView add;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String SHARE_PREF_NAME = "Login";
    public String admin_email_warden_key;
    private TextView empty_warden;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = ProfileFragment.this.getActivity().getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Admin a = (Admin) getActivity();
        Bundle r = a.getMyData();


        admin_email_k = r.getString("email_key");
        if (admin_email_k.equals("boyshostel1984@gmail.com")) {
           admin_email_warden_key = "Boys Hostel";
        } else if (admin_email_k.equals("girlshostel1984@gmail.com")) {
            admin_email_warden_key= "Girls Hostel";
        }

        settoolbar(view);
        recyclerView = view.findViewById(R.id.warden_rv);
        empty_warden=view.findViewById(R.id.empty_warden);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("warden");
        adapter = new warden_adapter(ProfileFragment.this.getActivity(), arrayList);
        databaseReference.child(admin_email_warden_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                adapter.notifyDataSetChanged();
                    if(snapshot.exists()) {
                        warden_e = snapshot.child("warden_email").getValue().toString();
                        warden_hostel = snapshot.child("warden_hostel").getValue().toString();
                        warden_n = snapshot.child("warden_name").getValue().toString();
                        warden_mobile = snapshot.child("warden_phone").getValue().toString();
                        warden_post = snapshot.child("warden_post").getValue().toString();
                       String warden_image=snapshot.child("warden_image") .getValue().toString();
                        warden_data warden_data = new warden_data(warden_n, warden_e, warden_hostel, warden_mobile, warden_post,warden_image);
                        arrayList.add(warden_data);
                        if (arrayList.size() > 0) {
                            add.setVisibility(View.GONE);
                            rv_unpaid();
                        }
                    }else {

                            empty_warden.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);

                    }

                }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileFragment.this.getActivity(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void settoolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar1);
        title_toolbar_tv = view.findViewById(R.id.title_toolbar);
        title_toolbar_tv.setText("About us");
        toolbar.inflateMenu(R.menu.add_image);
        add = (ActionMenuItemView) view.findViewById(R.id.menu_add_);
        toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_add_) {
            Intent intent = new Intent(ProfileFragment.this.getActivity(), warden_detail.class);
            intent.putExtra("admin_email_k", admin_email_k);
            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.menu_logout) {
            editor.putString(KEY_EMAIL, null);
            editor.commit();
            Intent intent = new Intent(ProfileFragment.this.getActivity(), Login.class);
            startActivity(intent);
            getActivity().finish();
        }
        return true;
    }

    private void rv_unpaid() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}