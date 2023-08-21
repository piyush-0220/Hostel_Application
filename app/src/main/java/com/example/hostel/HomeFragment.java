package com.example.hostel;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostel.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment {
//    private ArrayList<String> images;
//    private SliderAdapter adapter;
//    private SliderView sliderView;
private Toolbar toolbar;
private TextView title_toolbar_tv,home_empty_note;
private FloatingActionButton fab_main;
private  DatabaseReference dbref,dbreff;
private Admin_room_adapter admin_room_adapter;
private RecyclerView recyclerView;
private ArrayList<DataModel> arrayList;
public  String hostel_name_toolbar,admin_email_var;
private final static String apj_hostel="APJ Boys Hostel";
private final static String sv_hostel="S V Boys Hostel";
private final static String girls_hostel="K C Girls Hostel";
private ProgressBar pd;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_home, container, false);



        // getemail from activity
        Admin admin=(Admin) getActivity();
        Bundle data=admin.getMyData();
        admin_email_var=data.getString("email_key");
        settoolbar(view);

        fab_main=view.findViewById(R.id.fab_main);
        recyclerView=view.findViewById(R.id.recycler);
        pd=view.findViewById(R.id.pd_home);
        home_empty_note=view.findViewById(R.id.home_empty_notes);





        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeFragment.this.getActivity(),Add_room.class);
                intent.putExtra("hostel",title_toolbar_tv.getText().toString());
                startActivity(intent);
            }
        });

   return  view;

    }



    private void load_data() {
        dbref=FirebaseDatabase.getInstance().getReference().child("Hostel").child(hostel_name_toolbar);
        arrayList=new ArrayList<>();
        admin_room_adapter=new Admin_room_adapter(HomeFragment.this.getActivity(),arrayList);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                admin_room_adapter.notifyDataSetChanged();


                for(DataSnapshot dataSnapshot : snapshot.getChildren() ){
                    DataModel model = dataSnapshot.getValue(DataModel.class);
                    arrayList.add(model);
                }
                if(arrayList.size() >0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    pd.setVisibility(View.GONE);
                    recyclerView.setAdapter(admin_room_adapter);
                    admin_room_adapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(new GridLayoutManager(HomeFragment.this.getActivity(), 2));
                }else {
                    home_empty_note.setVisibility(View.VISIBLE);
                   recyclerView.setVisibility(View.GONE);
                    pd.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.setVisibility(View.GONE);
                home_empty_note.setVisibility(View.VISIBLE);
                Toast.makeText(HomeFragment.this.getActivity(), "Something went wrong, Plaese try again!! "+error, Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void settoolbar(View view) {
        toolbar=view.findViewById(R.id.toolbar1);
        title_toolbar_tv=view.findViewById(R.id.title_toolbar);

        if(admin_email_var.equals("boyshostel1984@gmail.com")) {
            title_toolbar_tv.setText(apj_hostel);
            hostel_name_toolbar = apj_hostel;
            load_data();
            toolbar.inflateMenu(R.menu.menu);
            toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));
        }else if (admin_email_var.equals("girlshostel1984@gmail.com")){
            title_toolbar_tv.setText(girls_hostel);
            hostel_name_toolbar=girls_hostel;
            load_data();
        }
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.menu_sv) {
          title_toolbar_tv.setText(sv_hostel);
          hostel_name_toolbar=sv_hostel;
          load_data();


        } else if (menuItem.getItemId() == R.id.menu_apj) {
          title_toolbar_tv.setText(apj_hostel);
          hostel_name_toolbar=apj_hostel;
          load_data();

        }
        return true;
    }

}