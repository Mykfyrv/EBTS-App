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

import java.util.ArrayList;

public class Adapter_Admin_Pending extends RecyclerView.Adapter<Adapter_Admin_Pending.MyViewHolder>{

    private ArrayList<Model_Book> mList;
    private Context context;

    public Adapter_Admin_Pending(Context context, ArrayList<Model_Book> mList){
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemlist_admin_pending, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_Book model = mList.get(position);
        Glide.with(context).load(mList.get(position).getImageUrl()).into(holder.imageView);
        holder.name.setText(model.getName());
        holder.rate.setText(model.getRate());
        holder.location.setText(model.getLocation());
        holder.guest.setText(model.getGuest());
        holder.availability.setText(model.getAvailability());
        holder.amenities.setText(model.getAmenities());
        holder.spaces.setText(model.getSpaces());
        holder.description.setText(model.getDescription());
        holder.totalPayment.setText(model.getUser_total_pay());
        holder.c_name.setText(model.getUser_fName());
        holder.c_email.setText(model.getUser_email());
        holder.c_phone.setText(model.getUser_phone());
        holder.c_guest.setText(model.getUser_guest());
        holder.c_dateIn.setText(model.getUser_dateIn());
        holder.c_dateOut.setText(model.getUser_dateOut());
        holder.c_user.setText(model.getUser_name());
        holder.location.setText(model.getLocation());
        holder.primaryKey.setText(model.getPrimaryKey());
        holder.imageUrl.setText(model.getImageUrl());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name, location, rate, guest, dateIn, dateOut, availability, amenities, spaces, description, totalPayment, c_name, c_email, c_phone, c_guest, c_dateIn, c_dateOut,
                primaryKey, imageUrl, c_user;
        Button btnDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView       = itemView.findViewById(R.id.m_image);
            name            = itemView.findViewById(R.id.tv_name);
            rate            = itemView.findViewById(R.id.tv_rate);
            location        = itemView.findViewById(R.id.tv_location);
            guest           = itemView.findViewById(R.id.tv_guest);
            dateIn          = itemView.findViewById(R.id.tv_dateIn);
            dateOut         = itemView.findViewById(R.id.tv_dateOut);
            availability    = itemView.findViewById(R.id.tv_availability);
            amenities       = itemView.findViewById(R.id.tv_amenities);
            spaces          = itemView.findViewById(R.id.tv_spaces);
            description     = itemView.findViewById(R.id.tv_description);
            totalPayment    = itemView.findViewById(R.id.tv_totalPay);
            c_name          = itemView.findViewById(R.id.tv_cName);
            c_email         = itemView.findViewById(R.id.tv_cEmail);
            c_phone         = itemView.findViewById(R.id.tv_cPhone);
            c_guest         = itemView.findViewById(R.id.tv_cGuest);
            c_dateIn        = itemView.findViewById(R.id.tv_cDateIn);
            c_dateOut       = itemView.findViewById(R.id.tv_cDateOut);
            c_user          = itemView.findViewById(R.id.tv_userName);
            primaryKey      = itemView.findViewById(R.id.tv_primaryKey);
            imageUrl        = itemView.findViewById(R.id.tv_imageUrl);
            btnDetails      = itemView.findViewById(R.id.btnPendingRes);

            btnDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle details = new Bundle();
                    details.putString("name", name.getText().toString());
                    details.putString("rate", rate.getText().toString());
                    details.putString("location", location.getText().toString());
                    details.putString("guest", guest.getText().toString());
                    details.putString("dateIn", dateIn.getText().toString());
                    details.putString("dateOut", dateOut.getText().toString());
                    details.putString("availability", availability.getText().toString());
                    details.putString("amenities", amenities.getText().toString());
                    details.putString("spaces", spaces.getText().toString());
                    details.putString("description", description.getText().toString());
                    details.putString("totalPayment", totalPayment.getText().toString());
                    details.putString("c_name", c_name.getText().toString());
                    details.putString("c_email", c_email.getText().toString());
                    details.putString("c_phone", c_phone.getText().toString());
                    details.putString("c_guest", c_guest.getText().toString());
                    details.putString("c_dateIn", c_dateIn.getText().toString());
                    details.putString("c_dateOut", c_dateOut.getText().toString());
                    details.putString("c_user", c_user.getText().toString());
                    details.putString("imageUrl", imageUrl.getText().toString());
                    details.putString("primaryKey", primaryKey.getText().toString());
                    Intent intent = new Intent(view.getContext(), Admin_Pending.class);
                    intent.putExtras(details);
                    view.getContext().startActivity(intent);

                }
            });


        }
    }

}
