//package com.example.hostel;
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import java.util.ArrayList;
//public class admin_service_fragment_Adapter extends
//        RecyclerView.Adapter<admin_service_fragment_Adapter.ViewHolder> {
//    private ArrayList<user_room_request_data> list;
//    private Context context;
//    public admin_service_fragment_Adapter(ArrayList<user_room_request_data> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }
//    @NonNull
//    @Override
//    public admin_service_fragment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
//            viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.request_format, parent, false);
//        return new ViewHolder(view);
//        @Override
//        public void onBindViewHolder(@NonNull admin_service_fragment_Adapter.ViewHolder holder, int position) {
//            user_room_request_data model = list.get(position);
//            holder.type_tv.setText(model.getType());
//            holder.description_tv.setText(model.getDesc());
//            holder.status_tv.setText(model.getStatus());
//            if(model.getStatus().equals("Approved"))
//            {
//                holder.relative_l.setBackgroundResource(R.color.green);
//            }
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(context,UpdateStatus.class);
//                    intent.putExtra("key_",model.getKey());
//                    intent.putExtra("hostel","Girls Hostel");
//                    intent.putExtra("father",model.getParent_name());
//                    intent.putExtra("room",model.getRoom_no());
//                    intent.putExtra("h_name",model.getHostel_name());
//                    intent.putExtra("type",model.getType());
//                    intent.putExtra("desc",model.getDesc());
//                    intent.putExtra("phone",model.getSt_phone());
//                    intent.putExtra("status",model.getStatus());
//                    intent.putExtra("date_from",model.getDate_from());
//                    intent.putExtra("date_to",model.getDate_to());
//                    intent.putExtra("userid",model.getUserid());
//                    intent.putExtra("id",2);
//                    context.startActivity(intent);
//                }
//            })
//            @Override
//            public int getItemCount() {
//                return list.size()
//                public class ViewHolder extends RecyclerView.ViewHolder {
//                    private TextView type_tv, description_tv, status_tv;
//                    private RelativeLayout relative_l;
//                    public ViewHolder(@NonNull View itemView) {
//                        super(itemView);
//                        type_tv = itemView.findViewById(R.id.type_tv);
//                        description_tv = itemView.findViewById(R.id.desc_tv);
//                        status_tv = itemView.findViewById(R.id.status_tv);
//                        relative_l = itemView.findViewById(R.id.relative_l);
//                    }
//                }
//
//    public class ViewHolder {
//    }

package com.example.hostel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class admin_service_fragment_Adapter extends RecyclerView.Adapter<admin_service_fragment_Adapter.ViewHolder> {
    private ArrayList<user_room_request_data> list;
    private Context context;

    public admin_service_fragment_Adapter(ArrayList<user_room_request_data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public admin_service_fragment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_format, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull admin_service_fragment_Adapter.ViewHolder holder, int position) {
        user_room_request_data model = list.get(position);
        holder.type_tv.setText(model.getType());
        holder.description_tv.setText(model.getDesc());
        holder.status_tv.setText(model.getStatus());
        if(model.getStatus().equals("Approved"))
        {
            holder.relative_l.setBackgroundResource(R.color.green);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,UpdateStatus.class);
                intent.putExtra("key_",model.getKey());
                intent.putExtra("hostel","Girls Hostel");
                intent.putExtra("request","Support");
                intent.putExtra("name",model.getSt_name());
                intent.putExtra("father",model.getParent_name());
                intent.putExtra("room",model.getRoom_no());
                intent.putExtra("h_name",model.getHostel_name());
                intent.putExtra("type",model.getType());
                intent.putExtra("desc",model.getDesc());
                intent.putExtra("phone",model.getSt_phone());
                intent.putExtra("status",model.getStatus());
                intent.putExtra("date_from",model.getDate_from());
                intent.putExtra("date_to",model.getDate_to());
                intent.putExtra("userid",model.getUserid());
                intent.putExtra("id",3);

                context.startActivity(intent);

            }
        });


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
