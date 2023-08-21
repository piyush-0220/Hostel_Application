package com.example.hostel;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Fee_admin_recycler extends RecyclerView.Adapter<Fee_admin_recycler.fee_admin_ViewHolder> {
    private Context context;
    private ArrayList<fee_admin_data> fee_list;


    public Fee_admin_recycler(Context context, ArrayList<fee_admin_data> fee_list) {
        this.context = context;
        this.fee_list = fee_list;
    }

    @NonNull
    @Override
    public fee_admin_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fee_list_rv, parent, false);
        return new fee_admin_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull fee_admin_ViewHolder holder, int position) {
        fee_admin_data fee_admin_data = fee_list.get(position);
        holder.st_name_fee.setText(fee_admin_data.getSt_name());
        holder.st_room_fee.setText(fee_admin_data.getRoom_no());
        holder.st_hostel_fee.setText(fee_admin_data.getHostel_name());



        holder.st_status_fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeeFragment feeFragment=new FeeFragment();
                feeFragment.feeStatus(fee_admin_data.getSt_name(), fee_admin_data.getRoom_no(), fee_admin_data.getHostel_name(), fee_admin_data.getParent_name(), fee_admin_data.getSt_phone(), fee_admin_data.getHostel_fee(),fee_admin_data.getSt_key(),context);


            }
        });


    }

    @Override
    public int getItemCount() {
        return fee_list.size();
    }

    public class fee_admin_ViewHolder extends RecyclerView.ViewHolder {
        private TextView st_name_fee, st_room_fee, st_status_fee,st_hostel_fee;

        public fee_admin_ViewHolder(@NonNull View itemView) {
            super(itemView);
            st_name_fee = itemView.findViewById(R.id.st_name_fee);
            st_room_fee = itemView.findViewById(R.id.st_room_fee);
            st_status_fee = itemView.findViewById(R.id.st_status_fee);
            st_hostel_fee = itemView.findViewById(R.id.st_hostel_fee);

        }
    }
}
