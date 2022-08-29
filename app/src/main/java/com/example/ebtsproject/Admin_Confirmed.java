package com.example.ebtsproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Admin_Confirmed extends AppCompatActivity {

    ImageView iv_image;
    TextView name, location, rate, guest, availability, amenities, spaces, description, totalPayment,
            c_name, c_email, c_phone, c_guest, c_dateIn, c_dateOut;
    String s_name, s_location, s_rate, s_guest, s_availability, s_amenities, s_spaces, s_description, s_totalPayment,
            sc_name, sc_email, sc_phone, sc_guest, sc_dateIn, sc_dateOut, primaryKey, imageUrl, sc_user;
    Button btnEmail, btnDelete;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_confirmed);

        iv_image    = (ImageView) findViewById(R.id.m_image);
        name        = (TextView) findViewById(R.id.tv_name);
        location    = (TextView) findViewById(R.id.tv_location);
        rate        = (TextView) findViewById(R.id.tv_rate);
        guest       = (TextView) findViewById(R.id.tv_guest);
        availability= (TextView) findViewById(R.id.tv_availability);
        amenities   = (TextView) findViewById(R.id.tv_amenities);
        spaces      = (TextView) findViewById(R.id.tv_spaces);
        description = (TextView) findViewById(R.id.tv_description);
        totalPayment    = (TextView) findViewById(R.id.tv_totalPay);

        c_name   = (TextView) findViewById(R.id.tv_cName);
        c_email  = (TextView) findViewById(R.id.tv_cEmail);
        c_phone  = (TextView) findViewById(R.id.tv_cPhone);
        c_guest  = (TextView) findViewById(R.id.tv_cGuest);
        c_dateIn = (TextView) findViewById(R.id.tv_cDateIn);
        c_dateOut = (TextView) findViewById(R.id.tv_cDateOut);

        btnEmail = (Button) findViewById(R.id.btnEmail);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        Bundle details = getIntent().getExtras();

        if (details != null){
            s_name      = details.getString("name");
            s_rate      = details.getString("rate");
            s_location  = details.getString("location");
            s_guest     = details.getString("guest");
            s_availability = details.getString("availability");
            s_amenities    = details.getString("amenities");
            s_spaces       = details.getString("spaces");
            s_description  = details.getString("description");
            s_totalPayment = details.getString("totalPayment");
            sc_name        = details.getString("c_name");
            sc_email       = details.getString("c_email");
            sc_phone       = details.getString("c_phone");
            sc_guest       = details.getString("c_guest");
            sc_dateIn      = details.getString("c_dateIn");
            sc_dateOut     = details.getString("c_dateOut");
            sc_user        = details.getString("c_user");
            primaryKey     = details.getString("primaryKey");
            imageUrl       = details.getString("imageUrl");

            name.setText(s_name);
            location.setText(s_location);
            rate.setText(s_rate);
            guest.setText(s_guest);
            availability.setText(s_availability);
            amenities.setText(s_amenities);
            spaces.setText(s_spaces);
            description.setText(s_description);
            totalPayment.setText(s_totalPayment);
            c_name.setText(sc_name);
            c_email.setText(sc_email);
            c_phone.setText(sc_phone);
            c_guest.setText(sc_guest);
            c_dateIn.setText(sc_dateIn);
            c_dateOut.setText(sc_dateOut);
            Glide.with(Admin_Confirmed.this).load(imageUrl).into(iv_image);

            btnEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder = new AlertDialog.Builder(Admin_Confirmed.this, R.style.AlertDialogCustom);
                    builder.setTitle("Email Notification")
                            .setMessage("Do you want to Notify the User?")
                            .setCancelable(true)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        Intent email = new Intent(Intent.ACTION_SEND);
                                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{sc_email});
                                        email.putExtra(Intent.EXTRA_SUBJECT, "EBTS Booking Reservation at " + s_name);
                                        email.putExtra(Intent.EXTRA_TEXT, "Good Day, " + sc_name + "!\n" +
                                                "\n" +
                                                "Thank you for your booking! \n" +
                                                "\n" +
                                                "This email is to inform you that your reservation at " + s_name + " from " + sc_dateIn +  " to " + sc_dateOut + " is already confirmed. \n" +
                                                "\n" +
                                                "The next step is to process your reservation fee, this cost Php 300.00 and is non-refundable, this will be deducted from the total cost of your bill. You may send your payment through gcash: [number] or bank transfer: [name of bank, account number]. Once you have already processed your payment kindly reply to this email with the attachment of your proof of payment. \n" +
                                                "\n" +
                                                "Once the admin has already confirmed your payment, you will be receiving a text message or email with regards to the confirmation of your payment and stay. \n" +
                                                "\n" +
                                                "If you have any inquiries with regards to your stay you may contact us at +63 965 354 6415 or send us a message at our facebook page facebook.com/eBoardinghouseTransientStaycation. Any changes with your booking will be entertained 2 days prior to your check-in date. \n" +
                                                "\n" +
                                                "We are delighted to have you as our guest/s. Enjoy your stay in your “feel-like home”!\n" +
                                                "\n" +
                                                "\n" +
                                                "\n" +
                                                "Best regards, \n" +
                                                "\n" +
                                                "EBTS Boardinghouse Transient Staycation\n ");

                                        email.setType("message/rfc822");
                                        startActivityForResult(Intent.createChooser(email, "Choose an Email client :"), 0);
                                    }
                                    catch (android.content.ActivityNotFoundException exception){
                                        Toast.makeText(Admin_Confirmed.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                    }
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

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder = new AlertDialog.Builder(Admin_Confirmed.this, R.style.AlertDialogCustom);
                    builder.setTitle("Delete Record")
                            .setMessage("Are you sure you want to delete this Record?")
                            .setCancelable(true)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseDatabase.getInstance().getReference("D - Summary").child(name.getText().toString())
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                                                    Object totalReserve = map.get("confirm");
                                                    int newTotalReserve = Integer.parseInt(String.valueOf(totalReserve));
                                                    Integer finalTotalReserve = newTotalReserve - 1;
                                                    HashMap<String, Object> summaryMap = new HashMap<>();
                                                    summaryMap.put("confirm", finalTotalReserve.toString());
                                                    FirebaseDatabase.getInstance().getReference("D - Summary").child(name.getText().toString()).updateChildren(summaryMap);
                                                    FirebaseDatabase.getInstance().getReference("B - Admin").child("Confirmed").child(s_name).child(primaryKey).removeValue();
                                                    FirebaseDatabase.getInstance().getReference("C - Customer").child(sc_user).child("Booking").child(primaryKey).removeValue();
                                                    Toast.makeText(Admin_Confirmed.this, "Record Successfully Deleted!", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Admin_Confirmed.this, zAdmin_Drawer.class);
                                                    startActivity(intent);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode  == 0) { // Activity.RESULT_OK
            Intent intentGoToMenu = new Intent(Admin_Confirmed.this, zAdmin_Drawer.class);
            startActivity(intentGoToMenu);
            finish();
        }
    }
}