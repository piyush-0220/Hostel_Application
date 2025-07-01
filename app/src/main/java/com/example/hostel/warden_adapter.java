package com.example.hostel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class warden_adapter extends RecyclerView.Adapter<warden_adapter.ViewHolder> {
    private Context context;
    private ArrayList<warden_data> list;

    public warden_adapter(Context context, ArrayList<warden_data> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public warden_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.warden_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull warden_adapter.ViewHolder holder, int position) {
        warden_data warden_data = list.get(position);
        holder.warden_name.setText(warden_data.getWarden_name());
        holder.warden_name_.setText(warden_data.getWarden_name());
        holder.warden_email.setText(warden_data.getWarden_email());
        holder.warden_phone.setText(warden_data.getWarden_phone());
        holder.warden_hostel_name.setText(warden_data.getWarden_hostel());
        holder.warden_post_name.setText(warden_data.getWarden_post());
        holder.warden_post_name_.setText(warden_data.getWarden_post());
        try
        {
            if(warden_data.getWarden_image().equals(""))
        {

            holder.warden_dp.setBackgroundResource(R.drawable.dp);

        } else {

            Glide.with(context).load(warden_data.getWarden_image()).into(holder.warden_dp);
        }
        }catch (Exception e)
        {
            e.printStackTrace();
        }


        holder.card_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,Update_warden.class);
                intent.putExtra("w_name",warden_data.getWarden_name());
                intent.putExtra("w_email",warden_data.getWarden_email());
                intent.putExtra("w_post",warden_data.getWarden_post());
                intent.putExtra("w_phone",warden_data.getWarden_phone());
                intent.putExtra("w_hostel",warden_data.getWarden_hostel());
                intent.putExtra("w_image",warden_data.getWarden_image());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView warden_name, warden_email, warden_phone, warden_hostel_name, warden_post_name,warden_name_,warden_post_name_;
        private CardView card_edit;
        private ImageView warden_dp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            warden_name = itemView.findViewById(R.id.warden_name);
            warden_email = itemView.findViewById(R.id.warden_email);
            warden_phone = itemView.findViewById(R.id.warden_phone);
            warden_hostel_name = itemView.findViewById(R.id.warden_hostel_name);
            warden_post_name = itemView.findViewById(R.id.warden_post_name);
            warden_name_ = itemView.findViewById(R.id.warden_name_);
            card_edit=itemView.findViewById(R.id.card_edit);
            warden_post_name_=itemView.findViewById(R.id.warden_post_name_);
            warden_dp=itemView.findViewById(R.id.warden_dp);
        }
    }
}
