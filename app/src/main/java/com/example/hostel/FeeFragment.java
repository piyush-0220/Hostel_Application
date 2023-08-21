package com.example.hostel;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class FeeFragment extends Fragment {
    private Toolbar toolbar;
    private TextView title_toolbar_tv;

    public FeeFragment() {
        // Required empty public constructor
    }

    private MyCalendar calendar;
    private TextView month_picker,fee_empty;
    private RelativeLayout r_l_month_pick;
    private RecyclerView recycler_View_unpaid,recycler_view_paid;
    private ArrayList<fee_admin_data> admin_data_list = new ArrayList<>();
    private ArrayList<fee_admin_data> data_list = new ArrayList<>();
    ;
    private Fee_admin_recycler adapter;
    private DatabaseReference databaseReference, dbref, dbref_fees,db1;
    private final static String apj_hostel = "APJ Boys Hostel";
    private final static String sv_hostel = "S V Boys Hostel";
    private final static String girls_hostel="K C Girls Hostel";
    private String fee_hostel_name;
    private LinearLayout unpaid_card, paid_card,ll_fee;
    private String admin_email_k,a_e_k;
    private String s_key,hname,h_gpj,h_sv;

    private TextView unpaid_tv,paid_tv,unpaid_st,paid_st;
    private ProgressBar pd;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String SHARE_PREF_NAME = "Login";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fee, container, false);
        sharedPreferences = FeeFragment.this.getActivity().getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Admin a = (Admin) getActivity();
        Bundle r = a.getMyData();
        admin_email_k = r.getString("email_key");
        if(admin_email_k.equals("girlshostel1984@gmail.com"))
        {
            hname="Girls Hostel";
            h_gpj=girls_hostel;

        }else
        {
            hname="Boys Hostel";
            h_gpj=apj_hostel;


        }



       calendar=new MyCalendar();
        recycler_View_unpaid = view.findViewById(R.id.recycler_view_unpaid);
        recycler_view_paid=view.findViewById(R.id.recycler_view_paid);
        unpaid_card = view.findViewById(R.id.unpaid_card);
        unpaid_st=view.findViewById(R.id.unpaid_st);
        unpaid_tv=view.findViewById(R.id.unpaid_tv);
        paid_st=view.findViewById(R.id.paid_st);
        paid_tv=view.findViewById(R.id.paid_tv);
        fee_empty=view.findViewById(R.id.fee_empty);
        ll_fee=view.findViewById(R.id.ll_fee);
        pd=view.findViewById(R.id.pd_fee);
        paid_card = view.findViewById(R.id.paid_card);
        adapter = new Fee_admin_recycler(FeeFragment.this.getActivity(), admin_data_list);

//        unpaid_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        r_l_month_pick = view.findViewById(R.id.r_l);
        month_picker = view.findViewById(R.id.month_toolbar_tv);
        r_l_month_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();
            }
        });





        settoolbar(view);
         fetch_unpaid_size(calendar.getMonth());
         fetch_paid_size(calendar.getMonth());

        unpaid_card.setBackgroundResource(R.color.white);
        paid_card.setBackgroundResource(R.color.app_color);
        unpaid_tv.setTextColor(R.color.black);
        unpaid_st.setTextColor(R.color.black);
        fetch_unpaid(calendar.getMonth());



unpaid_card.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        unpaid_card.setBackgroundResource(R.color.white);
        paid_card.setBackgroundResource(R.color.app_color);
        unpaid_tv.setTextColor(R.color.black);
        unpaid_st.setTextColor(R.color.black);

    fetch_unpaid(calendar.getMonth());
    }
});

paid_card.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        unpaid_card.setBackgroundResource(R.color.app_color);
        paid_card.setBackgroundResource(R.color.white);
        unpaid_tv.setTextColor(R.color.black);
        unpaid_st.setTextColor(R.color.black);

        fetch_paid(calendar.getMonth());
    }
});





        return view;
    }

//    private void fetch_hostel_name_paid() {
//        if (admin_email_k.equals("boyshostel1984@gmail.com")) {
//            a_e_k =apj_hostel;
//            MyCalendar calendar1 = new MyCalendar();
//            unpaid_card.setBackgroundResource(R.color.app_color);
//            paid_card.setBackgroundResource(R.color.white);
//            unpaid_tv.setTextColor(R.color.black);
//            unpaid_st.setTextColor(R.color.black);
//            fetch_paid(calendar1.getMonth());
//            fetch_paid_sv(calendar1.getMonth());
//        } else if (admin_email_k.equals("girlshostel1984@gmail.com")) {
//            a_e_k =girls_hostel;
//            MyCalendar calendar1 = new MyCalendar();
//            unpaid_card.setBackgroundResource(R.color.app_color);
//            paid_card.setBackgroundResource(R.color.white);
//            unpaid_tv.setTextColor(R.color.black);
//            unpaid_st.setTextColor(R.color.black);
//            fetch_paid(calendar1.getMonth());
//        }
//
//    }
//
//    private void fetch_paid_sv(String month) {
//        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Fee").child(sv_hostel).child(month);
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                admin_data_list.clear();
//                adapter.notifyDataSetChanged();
//                for(DataSnapshot snapshot1 : snapshot.getChildren())
//                {
//                    fee_admin_data d =snapshot1.getValue(fee_admin_data.class);
//                    if(d != null) {
//                        if (d.getFee_status().equals("Paid")) {
//                            admin_data_list.add(d);
//                        }
//                    }
//                }
//                if(admin_data_list.size() >0)
//                {
//                    recycler_View_unpaid.setVisibility(View.GONE);
//                    recycler_view_paid.setVisibility(View.VISIBLE);
//                    paid_st.setText(String.valueOf(admin_data_list.size()));
//                    recycler_view_paid.setLayoutManager(new LinearLayoutManager(FeeFragment.this.getActivity()));
//                    recycler_view_paid.setAdapter(adapter);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void fetch_hostel_name_unpaid() {
//
//        if (admin_email_k.equals("boyshostel1984@gmail.com")) {
//            a_e_k =apj_hostel;
//
//            fetch_unpaid_sv(calendar1.getMonth());
//        } else if (admin_email_k.equals("girlshostel1984@gmail.com")) {
//            a_e_k =girls_hostel;
//            MyCalendar calendar1 = new MyCalendar();
//            unpaid_card.setBackgroundResource(R.color.white);
//            paid_card.
//            unpaid_tv.setTextColor(R.color.black);
//            unpaid_st.setTextColor(R.color.black);
//            fetch_unpaid(calendar1.getMonth());
//        }
//
//
//    }
//
//    private void fetch_unpaid_sv(String month) {
//        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Fee").child(sv_hostel).child(month);
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                admin_data_list.clear();
//                adapter.notifyDataSetChanged();
//
//                if(snapshot.exists())
//                {
//
//
//
//                    for(DataSnapshot snapshot1 : snapshot.getChildren())
//                    {
//                        fee_admin_data d =snapshot1.getValue(fee_admin_data.class);
//                        if(d != null) {
//                            if (d.getFee_status().equals("Unpaid")) {
//                                admin_data_list.add(d);
//                            }
//                        }
//                    }
//                    recycler_View_unpaid.setVisibility(View.VISIBLE);
//                    recycler_view_paid.setVisibility(View.GONE);
//                    unpaid_st.setText(String.valueOf(admin_data_list.size()));
//                    recycler_View_unpaid.setLayoutManager(new LinearLayoutManager(FeeFragment.this.getActivity()));
//                    recycler_View_unpaid.setAdapter(adapter);
//
//                }
//                else {
//                    add_student_unpaid();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void fetch_paid(String month) {


        pd.setVisibility(View.VISIBLE);
        ll_fee.setVisibility(View.GONE);
        fee_empty.setVisibility(View.GONE);
        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Fee").child(hname).child(h_gpj).child(month);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                admin_data_list.clear();
                adapter.notifyDataSetChanged();
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    fee_admin_data d =snapshot1.getValue(fee_admin_data.class);
                    if(d != null) {
                        if (d.getFee_status().equals("Paid")) {
                            admin_data_list.add(d);
                        }
                    }
                }
                if(admin_data_list.size() >0)
                {
                    pd.setVisibility(View.GONE);
                    fee_empty.setVisibility(View.GONE);
                    ll_fee.setVisibility(View.VISIBLE);
                    recycler_View_unpaid.setVisibility(View.GONE);
                    recycler_view_paid.setVisibility(View.VISIBLE);
                    paid_st.setText(String.valueOf(admin_data_list.size()));
                    recycler_view_paid.setLayoutManager(new LinearLayoutManager(FeeFragment.this.getActivity()));
                    recycler_view_paid.setAdapter(adapter);

                }else {
                    pd.setVisibility(View.GONE);
                    fee_empty.setVisibility(View.VISIBLE);
                    ll_fee.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.setVisibility(View.GONE);
                fee_empty.setVisibility(View.VISIBLE);
                ll_fee.setVisibility(View.GONE);
                Toast.makeText(FeeFragment.this.getActivity(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_unpaid(String c) {

        pd.setVisibility(View.VISIBLE);
        ll_fee.setVisibility(View.GONE);
        fee_empty.setVisibility(View.GONE);
        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Fee").child(hname).child(h_gpj).child(c);
             db.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     admin_data_list.clear();
                     adapter.notifyDataSetChanged();

                     if(snapshot.exists())
                     {



                         for(DataSnapshot snapshot1 : snapshot.getChildren())
                         {
                             fee_admin_data d =snapshot1.getValue(fee_admin_data.class);
                             if(d != null) {
                                 if (d.getFee_status().equals("Unpaid")) {
                                     admin_data_list.add(d);
                                 }
                             }
                         }if ((admin_data_list.size() > 0))
                     {
                         pd.setVisibility(View.GONE);
                         ll_fee.setVisibility(View.VISIBLE);
                         recycler_View_unpaid.setVisibility(View.VISIBLE);
                         recycler_view_paid.setVisibility(View.GONE);
                         unpaid_st.setText(String.valueOf(admin_data_list.size()));
                         recycler_View_unpaid.setLayoutManager(new LinearLayoutManager(FeeFragment.this.getActivity()));
                         recycler_View_unpaid.setAdapter(adapter);
                     }else
                     {
                         pd.setVisibility(View.GONE);
                         ll_fee.setVisibility(View.GONE);
                         fee_empty.setVisibility(View.VISIBLE);
                     }


                     }
                     else {
                         add_student_unpaid();
                     }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {
                     pd.setVisibility(View.GONE);
                     fee_empty.setVisibility(View.VISIBLE);
                     ll_fee.setVisibility(View.GONE);
                     Toast.makeText(FeeFragment.this.getActivity(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
                 }
             });
    }
    private void fetch_paid_size(String month) {



        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Fee").child(hname).child(h_gpj).child(month);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                admin_data_list.clear();
                adapter.notifyDataSetChanged();
                paid_st.setText("0");
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    fee_admin_data d =snapshot1.getValue(fee_admin_data.class);
                    if(d != null) {
                        if (d.getFee_status().equals("Paid")) {
                            admin_data_list.add(d);
                        }
                    }
                }
                paid_st.setText(String.valueOf(admin_data_list.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void fetch_unpaid_size(String c) {


        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Fee").child(hname).child(h_gpj).child(c);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                admin_data_list.clear();
                adapter.notifyDataSetChanged();
                unpaid_st.setText("0");

                if (snapshot.exists()) {


                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        fee_admin_data d = snapshot1.getValue(fee_admin_data.class);
                        if (d != null) {
                            if (d.getFee_status().equals("Unpaid")) {
                                admin_data_list.add(d);
                            }
                        }
                    }
                    unpaid_st.setText(String.valueOf(admin_data_list.size()));
                }
            }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    private void add_student_unpaid() {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hostel").child(h_gpj);
        db1=FirebaseDatabase.getInstance().getReference().child("Fee");
        dbref_fees =db1.child(hname).child(h_gpj).child(calendar.getMonth());

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                admin_data_list.clear();


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child("student").getChildren()) {

                        String sn = dataSnapshot1.child("st_name").getValue().toString();
                        String pn = dataSnapshot1.child("parent_name").getValue().toString();
                        String sp = dataSnapshot1.child("st_phone").getValue().toString();
                        String sr = dataSnapshot1.child("room_no").getValue().toString();
                        String sh = dataSnapshot1.child("hostel_name").getValue().toString();
                         s_key=dataSnapshot1.child("st_key").getValue().toString();
                        String h_fee=dataSnapshot1.child("hostel_fee").getValue().toString();
                        fee_admin_data admin_data1 = new fee_admin_data(sn,sr,sh,pn,sp,h_fee,s_key,"Unpaid");
                        if (admin_data1 != null) {

                               dbref_fees.child(s_key).setValue(admin_data1);



                        }


                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FeeFragment.this.getActivity(), "Error" + error, Toast.LENGTH_SHORT).show();

            }
        });

    }







    private void showCalendar() {
        MyCalendar calendar = new MyCalendar();
        calendar.show(getFragmentManager(), "");
        calendar.setOnCalendarOkClickListener(this::onCalendarOkClicked);

    }


    private void onCalendarOkClicked(int year, int month, int day) {
        calendar.setDate(year, month, day);
        month_picker.setText(calendar.getMonth());


        fetch_unpaid(calendar.getMonth());
        fetch_paid_size(calendar.getMonth());
        fetch_unpaid_size(calendar.getMonth());

    }

    private void settoolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar1);
        title_toolbar_tv = view.findViewById(R.id.title_toolbar);
        month_picker.setText(calendar.getMonth());
        title_toolbar_tv.setText("Fee Details");
    }


    public void feeStatus(String st_name, String room_no, String hostel_name, String parent_name, String st_phone, String hostel_fee,String st_key,Context context) {
        if (hostel_name.equals(apj_hostel) || hostel_name.equals(sv_hostel)) {

            fee_hostel_name = apj_hostel;
            hname="Boys Hostel";
            showDialog(st_name, room_no, fee_hostel_name, parent_name, st_phone, hostel_fee,st_key,context);

        } else if (hostel_name.equals(girls_hostel)) {
            fee_hostel_name = girls_hostel;
            hname="Girls Hostel";
            showDialog(st_name, room_no, hostel_name, parent_name, st_phone, hostel_fee,st_key,context);
        }


    }

    private void showDialog(String st_name, String room_no, String hostel_name, String parent_name, String st_phone, String hostel_fee,String st_k,Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(st_name);
        builder.setMessage("Are you sure that the student has paid mess fee?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MyCalendar calendar1 = new MyCalendar();
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Fee").child(hname).child(hostel_name).child(calendar1.getMonth()).child(st_k);
                HashMap map= new HashMap();
                map.put("fee_status","Paid");
                db.updateChildren(map);






            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}