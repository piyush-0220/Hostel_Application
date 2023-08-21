package com.example.hostel;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GalleryFragment extends Fragment {

    private Toolbar toolbar;
    private TextView title_toolbar_tv,gallery_empty_notes;
    private RecyclerView others;
    private GalleryAdapter adapter;
    private DatabaseReference reference;
    private String admin_email_k, a_e_k;
    private ProgressBar pd;

    //ProgressBar progressbar;
    public GalleryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        others = view.findViewById(R.id.otherss);
        gallery_empty_notes=view.findViewById(R.id.gallery_empty_notes);
        pd=view.findViewById(R.id.pd_gallery);



        settoolbar(view);
        Admin a = (Admin) getActivity();
        Bundle r = a.getMyData();
        admin_email_k = r.getString("email_key");
        if (admin_email_k.equals("boyshostel1984@gmail.com")) {
            a_e_k = "Boys Hostel";
        } else if (admin_email_k.equals("girlshostel1984@gmail.com")) {
            a_e_k = "Girls Hostel";
        }


        getOthers();


        return view;
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_add_image) {
            Intent intent = new Intent(GalleryFragment.this.getActivity(), uploadImage.class);
            intent.putExtra("admin_email_k", admin_email_k);
            startActivity(intent);
        }
        return true;
    }

    private void getOthers() {
        pd.setVisibility(View.VISIBLE);
        reference = FirebaseDatabase.getInstance().getReference().child("Gallery").child(a_e_k);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> imageList = new ArrayList<>();
                imageList.clear();


                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String data = (String) snapshot1.getValue();
                    imageList.add(0, data);
                }
                if(imageList.size() > 0) {
                    others.setVisibility(View.VISIBLE);
                    pd.setVisibility(View.GONE);
                    adapter = new GalleryAdapter(getContext(), imageList);
                    others.setLayoutManager(new GridLayoutManager(getContext(), 4));
                    others.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else
                {
                    others.setVisibility(View.GONE);
                    pd.setVisibility(View.GONE);
                    gallery_empty_notes.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.setVisibility(View.GONE);
                gallery_empty_notes.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Database error don't get meassage", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void settoolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar1);
        title_toolbar_tv = view.findViewById(R.id.title_toolbar);
        title_toolbar_tv.setText("Gallery");
        toolbar.inflateMenu(R.menu.gallery_menu);
        toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));

    }
}