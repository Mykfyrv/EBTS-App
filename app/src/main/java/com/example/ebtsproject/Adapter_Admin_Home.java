package com.example.ebtsproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adapter_Admin_Home extends RecyclerView.Adapter<Adapter_Admin_Home.MyViewHolder> {

    private ArrayList<Model_List> mList;
    private Context context;

    public Adapter_Admin_Home (Context context, ArrayList<Model_List> mList){
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemlist_admin_home, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_List model = mList.get(position);
        Glide.with(context).load(mList.get(position).getImageUrl()).into(holder.imageView);
        holder.name.setText(model.getName());
        holder.location.setText(model.getLocation());
        holder.rate.setText(model.getRate());
        holder.guest.setText(model.getGuest());
        holder.dateIn.setText(model.getDateIn());
        holder.dateOut.setText(model.getDateOut());
        holder.amenities.setText(model.getAmenities());
        holder.spaces.setText(model.getSpaces());
        holder.description.setText(model.getDescription());
        holder.primaryKey.setText(model.getPrimaryKey());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name, rate, location, guest, dateIn, dateOut, amenities, spaces, description, primaryKey;
        Button btnDelete, btnEdit;
        AlertDialog.Builder builder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.m_image);
            name      = itemView.findViewById(R.id.tv_name);
            rate      = itemView.findViewById(R.id.tv_rate);
            location  = itemView.findViewById(R.id.tv_location);
            guest        = itemView.findViewById(R.id.tv_guest);
            dateIn       = itemView.findViewById(R.id.tv_dateIn);
            dateOut      = itemView.findViewById(R.id.tv_dateOut);
            amenities    = itemView.findViewById(R.id.tv_amenities);
            spaces       = itemView.findViewById(R.id.tv_spaces);
            description  = itemView.findViewById(R.id.tv_description);
            primaryKey   = itemView.findViewById(R.id.tv_primaryKey);
            btnEdit   = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            btnEdit.setOnClickListener(new View.OnClickListener() {
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
                    details.putString("primaryKey", primaryKey.getText().toString());
                    Intent intent = new Intent(view.getContext(), Admin_Listing_Edit.class);
                    intent.putExtras(details);
                    view.getContext().startActivity(intent);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder = new AlertDialog.Builder(itemView.getContext(), R.style.AlertDialogCustom);
                    builder.setTitle("Delete")
                            .setMessage("All the Pending and Confirmed records of this Item will also be deleted, do you want to proceed?")
                            .setCancelable(true)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseDatabase.getInstance().getReference().child("A - List").child(name.getText().toString()).removeValue();
                                    FirebaseDatabase.getInstance().getReference().child("B - Admin").child("Pending").child(name.getText().toString()).removeValue();
                                    FirebaseDatabase.getInstance().getReference().child("B - Admin").child("Confirmed").child(name.getText().toString()).removeValue();
                                    FirebaseDatabase.getInstance().getReference().child("D - Summary").child(name.getText().toString()).removeValue();
                                    Toast.makeText(itemView.getContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(view.getContext(), zAdmin_Drawer.class);
                                    view.getContext().startActivity(intent);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });
        }
    }
}
