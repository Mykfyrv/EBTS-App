package com.example.ebtsproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adapter_Customer_Home extends RecyclerView.Adapter<Adapter_Customer_Home.MyViewHolder> {

    private ArrayList<Model_List> mList;
    private Context context;

    public Adapter_Customer_Home(Context context, ArrayList<Model_List> mList){
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemlist_customer_home, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_List model = mList.get(position);
        Glide.with(context).load(mList.get(position).getImageUrl()).into(holder.imageView);
        holder.name.setText(model.getName());
        holder.rate.setText(model.getRate());
        holder.location.setText(model.getLocation());
        holder.guest.setText(model.getGuest());
        holder.dateIn.setText(model.getDateIn());
        holder.dateOut.setText(model.getDateOut());
        holder.amenities.setText(model.getAmenities());
        holder.spaces.setText(model.getSpaces());
        holder.description.setText(model.getDescription());
        holder.imageUrl.setText(model.getImageUrl());
        holder.primaryKey.setText(model.getPrimaryKey());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name, rate, location, guest, dateIn, dateOut, amenities, spaces, description, imageUrl, primaryKey;
        Button btnCheckAvail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView    = itemView.findViewById(R.id.m_image);
            name         = itemView.findViewById(R.id.tv_cName);
            rate         = itemView.findViewById(R.id.tv_rate);
            location     = itemView.findViewById(R.id.tv_location);
            guest        = itemView.findViewById(R.id.tv_guest);
            dateIn       = itemView.findViewById(R.id.tv_dateIn);
            dateOut      = itemView.findViewById(R.id.tv_dateOut);
            amenities    = itemView.findViewById(R.id.tv_amenities);
            spaces       = itemView.findViewById(R.id.tv_spaces);
            description  = itemView.findViewById(R.id.tv_description);
            imageUrl     = itemView.findViewById(R.id.tv_url);
            primaryKey   = itemView.findViewById(R.id.tv_pkey);
            btnCheckAvail = itemView.findViewById(R.id.btnCheckAvail);

            btnCheckAvail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle details = new Bundle();
                    details.putString("name", name.getText().toString());
                    details.putString("rate", rate.getText().toString());
                    details.putString("location", location.getText().toString());
                    details.putString("guest", guest.getText().toString());
                    details.putString("dateIn", dateIn.getText().toString());
                    details.putString("dateOut", dateOut.getText().toString());
                    details.putString("amenities", amenities.getText().toString());
                    details.putString("spaces", spaces.getText().toString());
                    details.putString("description", description.getText().toString());
                    details.putString("imageUrl", imageUrl.getText().toString());
                    details.putString("primaryKey", primaryKey.getText().toString());
                    Intent intent = new Intent(view.getContext(), Customer_CheckAvailability_Activity.class);
                    intent.putExtras(details);
                    view.getContext().startActivity(intent);
                }
            });


        }
    }
}
