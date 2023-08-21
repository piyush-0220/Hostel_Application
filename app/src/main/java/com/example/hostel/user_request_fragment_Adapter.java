package com.example.hostel;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class user_request_fragment_Adapter extends RecyclerView.Adapter<user_request_fragment_Adapter.ViewHolder> {
    private ArrayList<user_request_fragment_data> list;
    private Context context;

    public user_request_fragment_Adapter(ArrayList<user_request_fragment_data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public user_request_fragment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_format, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull user_request_fragment_Adapter.ViewHolder holder, int position) {
        user_request_fragment_data model = list.get(position);
        holder.type_tv.setText(model.getType());
        holder.description_tv.setText(model.getDesc());
        if (model.getStatus().equals("Approved")) {

            holder.relative_l.setBackgroundResource(R.color.green);
            holder.status_tv.setText(model.getStatus());
        } else {
            holder.relative_l.setBackgroundResource(R.color.app_color);
            holder.status_tv.setText(model.getStatus());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView type_tv, description_tv, status_tv;
        private RelativeLayout relative_l;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type_tv = itemView.findViewById(R.id.type_tv);
            description_tv = itemView.findViewById(R.id.desc_tv);
            status_tv = itemView.findViewById(R.id.status_tv);
            relative_l = itemView.findViewById(R.id.relative_l);
        }
    }
}
