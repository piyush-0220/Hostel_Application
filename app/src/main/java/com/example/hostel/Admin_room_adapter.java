package com.example.hostel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Admin_room_adapter extends RecyclerView.Adapter<Admin_room_adapter.admin_room_ViewAdapter> {
    private Context context;


    private ArrayList<DataModel> list;   //beacause database store only url;

    public Admin_room_adapter(Context context, ArrayList<DataModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public admin_room_ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_rv_detail, parent, false);
        return new admin_room_ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull admin_room_ViewAdapter holder, int position) {
        DataModel model = list.get(position);
        holder.room_no_tv.setText(model.getRoom_no_1());
        holder.bed_no_tv.setText(model.getBed_no_1());
        holder.st_no_tv.setText(String.valueOf(model.getPeople()));
        if(Integer.parseInt(model.getBed_no_1()) == Integer.parseInt(String.valueOf(model.getPeople())))
        {
            holder.status_color_card.setBackgroundResource(R.color.red);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, reserve_room.class);
                intent.putExtra("key", model.getKey());
                intent.putExtra("hostel_name_key", model.getHostel_name());
                intent.putExtra("room_key", model.getRoom_no_1());
                intent.putExtra("bed_key", model.getBed_no_1());
                intent.putExtra("people",model.getPeople());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class admin_room_ViewAdapter extends RecyclerView.ViewHolder {

        private TextView room_no_tv, bed_no_tv, st_no_tv;
        private RelativeLayout status_color_card;

        public admin_room_ViewAdapter(@NonNull View itemView) {
            super(itemView);
            room_no_tv = itemView.findViewById(R.id.room_no);
            bed_no_tv = itemView.findViewById(R.id.bed_no);
            st_no_tv = itemView.findViewById(R.id.st_no);
            status_color_card = itemView.findViewById(R.id.status_color);
        }
    }

}
