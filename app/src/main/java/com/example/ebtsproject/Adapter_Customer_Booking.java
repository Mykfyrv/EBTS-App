package com.example.ebtsproject;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import java.util.HashMap;
import java.util.Map;

public class Adapter_Customer_Booking extends RecyclerView.Adapter<Adapter_Customer_Booking.MyViewHolder> {

    private ArrayList<Model_Book> mList;
    private Context context;

    public Adapter_Customer_Booking (Context context, ArrayList<Model_Book> mList){
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemlist_customer_booking, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_Book model = mList.get(position);
        Glide.with(context).load(mList.get(position).getImageUrl()).into(holder.imageView);
        holder.name.setText(model.getName());
        holder.location.setText(model.getLocation());
        holder.rate.setText(model.getRate());
        holder.guest.setText(model.getGuest());
        holder.availability.setText(model.getAvailability());
        holder.amenities.setText(model.getAmenities());
        holder.spaces.setText(model.getSpaces());
        holder.description.setText(model.getDescription());
        holder.c_name.setText(model.getUser_fName());
        holder.c_phone.setText(model.getUser_phone());
        holder.c_email.setText(model.getUser_email());
        holder.c_guest.setText(model.getUser_guest());
        holder.c_dateIn.setText(model.getUser_dateIn());
        holder.c_dateOut.setText(model.getUser_dateOut());
        holder.status.setText(model.getStatus());
        holder.primaryKey.setText(model.getPrimaryKey());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        Button btnCancelReserve;
        ProgressBar progressBar;
        TextView name, rate, location, guest, availability, amenities, spaces, description, c_name, c_email, c_phone, c_guest, c_dateIn, c_dateOut, status, primaryKey;
        AlertDialog.Builder builder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView       = itemView.findViewById(R.id.m_image);
            name            = itemView.findViewById(R.id.tv_name);
            rate            = itemView.findViewById(R.id.tv_rate);
            location        = itemView.findViewById(R.id.tv_location);
            guest           = itemView.findViewById(R.id.tv_guest);
            availability    = itemView.findViewById(R.id.tv_availability);
            amenities       = itemView.findViewById(R.id.tv_amenities);
            spaces          = itemView.findViewById(R.id.tv_spaces);
            description     = itemView.findViewById(R.id.tv_description);
            c_name          = itemView.findViewById(R.id.tv_cName);
            c_email         = itemView.findViewById(R.id.tv_cEmail);
            c_phone         = itemView.findViewById(R.id.tv_cPhone);
            c_guest         = itemView.findViewById(R.id.tv_cGuest);
            c_dateIn        = itemView.findViewById(R.id.tv_cDateIn);
            c_dateOut       = itemView.findViewById(R.id.tv_cDateOut);
            status          = itemView.findViewById(R.id.tv_status);
            primaryKey       = itemView.findViewById(R.id.tv_primaryKey);
            btnCancelReserve = itemView.findViewById(R.id.btnCancelReserve);
            progressBar      = itemView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);

            Session_Management sessionManagement = new Session_Management(itemView.getContext());
            String userLogin = sessionManagement.getSession();

            //Disable Cancel Reservation Button
            FirebaseDatabase.getInstance().getReference().child("B - Admin").child("Confirmed")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(name.getText().toString()).child(primaryKey.getText().toString()).getValue() != null){
                                btnCancelReserve.setEnabled(false);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

            btnCancelReserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder = new AlertDialog.Builder(itemView.getContext(), R.style.AlertDialogCustom);
                    builder.setTitle("Cancel Reservation")
                            .setMessage("Are you sure you want to cancel this Reservation?")
                            .setCancelable(true)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseDatabase.getInstance().getReference("B - Admin").child("Pending")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    //Reservation Cancelled by User
                                                    if (snapshot.child(name.getText().toString()).child(primaryKey.getText().toString()).getValue() != null){
                                                        FirebaseDatabase.getInstance().getReference("D - Summary").child(name.getText().toString())
                                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                        progressBar.setVisibility(View.VISIBLE);
                                                                        Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                                                                        Object totalPending = map.get("pending");
                                                                        int newTotalPending = Integer.parseInt(String.valueOf(totalPending));
                                                                        Integer finalTotalPending = newTotalPending - 1;
                                                                        HashMap<String, Object> summaryMap = new HashMap<>();
                                                                        summaryMap.put("pending", finalTotalPending.toString());
                                                                        ////Delete Booking Information , Update Pending
                                                                        FirebaseDatabase.getInstance().getReference("D - Summary").child(name.getText().toString()).updateChildren(summaryMap);
                                                                        FirebaseDatabase.getInstance().getReference().child("B - Admin").child("Pending").child(name.getText().toString()).child(primaryKey.getText().toString()).removeValue();
                                                                        FirebaseDatabase.getInstance().getReference().child("C - Customer").child(userLogin).child("Booking").child(primaryKey.getText().toString()).removeValue();
                                                                        //Switch to another fragment in adapter
                                                                        Toast.makeText(itemView.getContext(), "Reservation Cancelled!", Toast.LENGTH_SHORT).show();
                                                                        Fragment fragment = new Customer_Home_Fragment();
                                                                        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                                                                        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                                                                    }
                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                    }
                                                                });
                                                    }
                                                    else {
                                                        //Reservation Cancelled by Admin
                                                        progressBar.setVisibility(View.VISIBLE);
                                                        //Delete Booking Information
                                                        FirebaseDatabase.getInstance().getReference().child("C - Customer").child(userLogin).child("Booking").child(primaryKey.getText().toString()).removeValue();
                                                        //Switch to another fragment in adapter
                                                        Toast.makeText(itemView.getContext(), "Reservation Cancelled", Toast.LENGTH_SHORT).show();
                                                        Fragment fragment = new Customer_Home_Fragment();
                                                        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                                                        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
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
