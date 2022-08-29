package com.example.ebtsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Customer_CheckAvailability_Activity extends AppCompatActivity {

    ImageView iv_image;
    TextView name, rate, location, guest, avail, amenities, spaces, description, currentDate;
    String s_name, s_rate, s_location, s_guest, s_dateIn, s_dateOut, s_amenities, s_spaces, s_description, s_image;
    EditText c_guest, c_dateIn, c_dateOut;
    Button btnReserve;
    ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference accounts;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_checkavailability_activity);

        iv_image    = (ImageView) findViewById(R.id.iv_image);
        name        = (TextView) findViewById(R.id.tv_cName);
        rate        = (TextView) findViewById(R.id.tv_rate);
        location    = (TextView) findViewById(R.id.tv_location);
        guest       = (TextView) findViewById(R.id.tv_guest);
        avail       = (TextView) findViewById(R.id.tv_avail);
        amenities   = (TextView) findViewById(R.id.tv_amenities);
        spaces      = (TextView) findViewById(R.id.tv_spaces);
        description = (TextView) findViewById(R.id.tv_description);
        currentDate = (TextView) findViewById(R.id.tv_CurrentDate);
        c_guest     = (EditText) findViewById(R.id.et_guest);
        c_dateIn    = (EditText) findViewById(R.id.et_dateIn);
        c_dateOut   = (EditText) findViewById(R.id.et_dateOut);
        btnReserve  = (Button) findViewById(R.id.btnReserve);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        currentDate.setText(date);

        Session_Management sessionManagement = new Session_Management(Customer_CheckAvailability_Activity.this);
        String userLogin = sessionManagement.getSession();
        String key = UUID.randomUUID().toString().replace("-","").substring(0,5);


        //edittext format
        TextWatcher dateInTextWatcher = new TextWatcher() {
            private String inCurrent = "";
            private String inFormat = "ddmmyyyy";
            private Calendar inCal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals(inCurrent)) {
                    String clean = charSequence.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = inCurrent.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int x = 2; x <= cl && x < 6; x += 2) {
                        sel++;
                    }

                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + inFormat.substring(clean.length());
                    }else{
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        inCal.set(Calendar.MONTH, mon-1);
                        year = (year<2021)?2021:(year>2025)?2025:year;
                        inCal.set(Calendar.YEAR, year);

                        day = (day > inCal.getActualMaximum(Calendar.DATE))? inCal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }
                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    inCurrent = clean;
                    c_dateIn.setText(inCurrent);
                    c_dateIn.setSelection(sel < inCurrent.length() ? sel : inCurrent.length());
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        TextWatcher dateOutTextWatcher = new TextWatcher() {
            private String outCurrent = "";
            private String outFormat = "ddmmyyyy";
            private Calendar outCal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals(outCurrent)) {
                    String clean = charSequence.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = outCurrent.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int x = 2; x <= cl && x < 6; x += 2) {
                        sel++;
                    }

                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + outFormat.substring(clean.length());
                    }else{
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        outCal.set(Calendar.MONTH, mon-1);
                        year = (year<2021)?2021:(year>2025)?2025:year;
                        outCal.set(Calendar.YEAR, year);

                        day = (day > outCal.getActualMaximum(Calendar.DATE))? outCal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }
                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    outCurrent = clean;
                    c_dateOut.setText(outCurrent);
                    c_dateOut.setSelection(sel < outCurrent.length() ? sel : outCurrent.length());
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        c_dateIn.addTextChangedListener(dateInTextWatcher);
        c_dateOut.addTextChangedListener(dateOutTextWatcher);

        //retrieve bundle
        Bundle details = getIntent().getExtras();
        if (details != null){
            //retrieve bundle
            s_name = details.getString("name");
            s_rate = details.getString("rate");
            s_location = details.getString("location");
            s_guest = details.getString("guest");
            s_dateIn = details.getString("dateIn");
            s_dateOut = details.getString("dateOut");
            String newAvailability = s_dateIn + " to " + s_dateOut;
            s_amenities = details.getString("amenities");
            s_spaces = details.getString("spaces");
            s_description = details.getString("description");
            s_image = details.getString("imageUrl");
            String newAvail = s_dateIn + " to " + s_dateOut;
            //display details from bundle
            Glide.with(Customer_CheckAvailability_Activity.this).load(s_image).into(iv_image);
            name.setText(s_name);
            rate.setText(s_rate);
            location.setText(s_location);
            guest.setText(s_guest);
            avail.setText(newAvail);
            amenities.setText(s_amenities);
            spaces.setText(s_spaces);
            description.setText(s_description);

            //retrieve the user's information
            accounts = db.collection("accounts").document(userLogin);
            accounts.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    String fullName = task.getResult().getString("FullName");
                    String email    = task.getResult().getString("Email");
                    String phone    = task.getResult().getString("Phone");
                    btnReserve.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String dateIn = c_dateIn.getText().toString();
                            String dateOut = c_dateOut.getText().toString();
                            String guest = c_guest.getText().toString();

                            if (TextUtils.isEmpty(dateIn) && TextUtils.isEmpty(dateOut) && TextUtils.isEmpty(guest)) {
                                c_dateIn.setError("This Field is Required.");
                                c_dateOut.setError("This Field is Required.");
                                c_guest.setError("This Field is Required.");
                                return;
                            } else if (TextUtils.isEmpty(dateOut) && TextUtils.isEmpty(guest)) {
                                c_dateOut.setError("This Field is Required.");
                                c_guest.setError("This Field is Required.");
                                return;
                            } else if (TextUtils.isEmpty(dateIn) && TextUtils.isEmpty(guest)) {
                                c_dateIn.setError("This Field is Required.");
                                c_guest.setError("This Field is Required.");
                                return;
                            } else if (TextUtils.isEmpty(dateIn) && TextUtils.isEmpty(dateOut)) {
                                c_dateIn.setError("This Field is Required.");
                                c_dateOut.setError("This Field is Required.");
                                return;
                            } else if (TextUtils.isEmpty(dateIn)) {
                                c_dateIn.setError("This Field is Required.");
                                return;
                            } else if (TextUtils.isEmpty(dateOut)) {
                                c_dateOut.setError("This Field is Required.");
                                return;
                            } else if (TextUtils.isEmpty(guest)) {
                                c_guest.setError("This Field is Required.");
                                return;
                            } else {
                                try {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date DateIn = simpleDateFormat.parse(dateIn);
                                    Date DateOut = simpleDateFormat.parse(dateOut);
                                    Date valid_dateIn = simpleDateFormat.parse(s_dateIn);
                                    Date valid_dateOut = simpleDateFormat.parse(s_dateOut);
                                    Date dateToday = simpleDateFormat.parse(date);

                                    int user_guest = Integer.parseInt(String.valueOf(guest));
                                    long diff = DateOut.getTime() - DateIn.getTime();
                                    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                    int rate = Integer.parseInt(String.valueOf(s_rate));
                                    long totalPay = days * rate;
                                    String new_totalPay = String.valueOf(totalPay);
                                    int valid_guest = Integer.parseInt(String.valueOf(s_guest));

                                    FirebaseDatabase.getInstance().getReference("C - Customer").child(userLogin)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.exists()) {
                                                        Toast.makeText(Customer_CheckAvailability_Activity.this, "Reservation Failed", Toast.LENGTH_SHORT).show();
                                                    } else if (DateIn.before(valid_dateIn)) {
                                                        Toast.makeText(view.getContext(), "Reservation Failed", Toast.LENGTH_SHORT).show();
                                                    } else if (DateIn.after(valid_dateOut)) {
                                                        Toast.makeText(view.getContext(), "Reservation Failed", Toast.LENGTH_SHORT).show();
                                                    } else if (DateOut.before(valid_dateIn)) {
                                                        Toast.makeText(view.getContext(), "Reservation Failed", Toast.LENGTH_SHORT).show();
                                                    } else if (DateOut.after(valid_dateOut)) {
                                                        Toast.makeText(view.getContext(), "Reservation Failed", Toast.LENGTH_SHORT).show();
                                                    } else if (DateOut.before(DateIn)) {
                                                        Toast.makeText(view.getContext(), "Reservation Failed", Toast.LENGTH_SHORT).show();
                                                    } else if (DateIn.before(dateToday)) {
                                                        Toast.makeText(view.getContext(), "Reservation Failed", Toast.LENGTH_SHORT).show();
                                                    } else if (DateOut.before(dateToday)) {
                                                        Toast.makeText(view.getContext(), "Reservation Failed", Toast.LENGTH_SHORT).show();
                                                    } else if (user_guest > valid_guest) {
                                                        Toast.makeText(view.getContext(), "Reservation Failed", Toast.LENGTH_SHORT).show();
                                                    } else if (user_guest == 0) {
                                                        Toast.makeText(view.getContext(), "Reservation Failed", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        builder = new AlertDialog.Builder(Customer_CheckAvailability_Activity.this, R.style.AlertDialogCustom);
                                                        builder.setTitle("Confirm Reservation")
                                                                .setMessage("Your total Reservation cost is " + new_totalPay + " pesos. Please check the Booking Tab and your Email Address" +
                                                                        " for the update of your Reservation.")
                                                                .setCancelable(true)
                                                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        HashMap<String, String> details = new HashMap<>();
                                                                        details.put("imageUrl", s_image);
                                                                        details.put("primaryKey", key);
                                                                        details.put("name", s_name);
                                                                        details.put("rate", s_rate);
                                                                        details.put("location", s_location);
                                                                        details.put("guest", s_guest);
                                                                        details.put("dateIn", s_dateIn);
                                                                        details.put("dateOut", s_dateOut);
                                                                        details.put("availability", newAvailability);
                                                                        details.put("amenities", s_amenities);
                                                                        details.put("spaces", s_spaces);
                                                                        details.put("description", s_description);
                                                                        details.put("user_name", userLogin);
                                                                        details.put("user_fName", fullName);
                                                                        details.put("user_email", email);
                                                                        details.put("user_phone", phone);
                                                                        details.put("user_guest", c_guest.getText().toString());
                                                                        details.put("user_dateIn", c_dateIn.getText().toString());
                                                                        details.put("user_dateOut", c_dateOut.getText().toString());
                                                                        details.put("user_total_pay", new_totalPay);
                                                                        //Store Booking Information
                                                                        progressBar.setVisibility(View.VISIBLE);
                                                                        FirebaseDatabase.getInstance().getReference("C - Customer").child(userLogin).child("Booking").child(key).setValue(details);
                                                                        FirebaseDatabase.getInstance().getReference("B - Admin").child("Pending").child(name.getText().toString()).child(key).setValue(details);
                                                                        //Update Pending
                                                                        FirebaseDatabase.getInstance().getReference("D - Summary").child(name.getText().toString())
                                                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                        Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                                                                                        Object totalPending = map.get("pending");
                                                                                        int newTotalPending = Integer.parseInt(String.valueOf(totalPending));
                                                                                        Integer finalTotalPending = newTotalPending + 1;
                                                                                        HashMap<String, Object> summaryMap = new HashMap<>();
                                                                                        summaryMap.put("pending", finalTotalPending.toString());
                                                                                        FirebaseDatabase.getInstance().getReference("D - Summary").child(name.getText().toString()).updateChildren(summaryMap);
                                                                                        //Update Status
                                                                                        HashMap<String, Object> statusMap = new HashMap<>();
                                                                                        statusMap.put("status", "Pending");
                                                                                        FirebaseDatabase.getInstance().getReference("C - Customer").child(userLogin).child("Booking").child(key).updateChildren(statusMap);
                                                                                    }

                                                                                    @Override
                                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                                    }
                                                                                });
                                                                        //success
                                                                        Toast.makeText(view.getContext(), "Reservation Success!", Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(Customer_CheckAvailability_Activity.this, zCustomer_Drawer.class));
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
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            });
        }
    }
}