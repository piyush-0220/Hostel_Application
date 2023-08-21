package com.example.hostel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class room_detail_adapter extends RecyclerView.Adapter<room_detail_adapter.ViewHolder> {
    private Context context;
    private ArrayList<student_room_data> list;

    public room_detail_adapter(Context context, ArrayList<student_room_data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public room_detail_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_detail_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull room_detail_adapter.ViewHolder holder, int position) {
        student_room_data user_room_data = list.get(position);
        holder.room_name.setText(user_room_data.getSt_name());
        holder.room_fname.setText(user_room_data.getParent_name());
        holder.room_hostel_name.setText(user_room_data.getHostel_name());
        holder.room_no.setText(user_room_data.getRoom_no());
        holder.room_location.setText(user_room_data.getAddress());
        holder.room_phone.setText(user_room_data.getSt_phone());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView room_name, room_fname, room_phone, room_location, room_no, room_hostel_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            room_name = itemView.findViewById(R.id.room_name_tv);
            room_fname = itemView.findViewById(R.id.room_fname_tv);
            room_phone = itemView.findViewById(R.id.room_phone_tv);
            room_location = itemView.findViewById(R.id.room_location_tv);
            room_no = itemView.findViewById(R.id.room_no_tv);
            room_hostel_name = itemView.findViewById(R.id.room_hostel_name_tv);

        }
    }


}
