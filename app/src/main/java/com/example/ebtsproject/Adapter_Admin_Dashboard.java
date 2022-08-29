package com.example.ebtsproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Adapter_Admin_Dashboard extends RecyclerView.Adapter<Adapter_Admin_Dashboard.MyViewHolder>{

    private ArrayList<Model_Summary> mList;
    private Context context;

    public Adapter_Admin_Dashboard(Context context, ArrayList<Model_Summary> mList){
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemlist_admin_dashboard, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_Summary model = mList.get(position);
        Glide.with(context).load(mList.get(position).getImageUrl()).into(holder.imageView);
        holder.confirmReservation.setText(model.getConfirm());
        holder.pendingReservation.setText(model.getPending());
        holder.name.setText(model.getName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name, imageUrl, confirmReservation, pendingReservation, pending, confirm;
        Button btnPendingRes, btnConfirmRes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView          = itemView.findViewById(R.id.m_image);
            name               = itemView.findViewById(R.id.tv_cName);
            imageUrl           = itemView.findViewById(R.id.tv_imageUrl);
            confirmReservation = itemView.findViewById(R.id.tv_cDateOut);
            pendingReservation = itemView.findViewById(R.id.tv_pendingReservation);
            btnPendingRes      = itemView.findViewById(R.id.btnPendingRes);
            btnConfirmRes      = itemView.findViewById(R.id.btnConfirmRes);
            pending            = itemView.findViewById(R.id.tv_labelPending);
            confirm            = itemView.findViewById(R.id.tv_labelConfirm);

            btnPendingRes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle details = new Bundle();
                    details.putString("name", name.getText().toString());
                    Fragment fragment = new Admin_Pending_Fragment();
                    fragment.setArguments(details);
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }
            });

            btnConfirmRes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle details = new Bundle();
                    details.putString("name", name.getText().toString());
                    Fragment fragment = new Admin_Confirmed_Fragment();
                    fragment.setArguments(details);
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }
            });


        }
    }

}
