package com.example.ebtsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Admin_Listing_2 extends AppCompatActivity {

    CheckBox tv, airConditioner, desk, wifi, closet, washingMachine, netflix, ref, utensils;
    CheckBox kitchen, washer, dryer, parking, pool, gym;
    EditText description;
    Button btnComplete;
    ArrayList<String> aResult;
    ArrayList<String> sResult;
    TextView resultAme, resultSpa;
    String name, imageUrl;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_listing_2);

        resultAme   = findViewById(R.id.resultAme);
        resultSpa   = findViewById(R.id.resultSpa);
        tv          = findViewById(R.id.cbTV);
        airConditioner = findViewById(R.id.cbAir);
        desk           = findViewById(R.id.cbDesk);
        wifi           = findViewById(R.id.cbWiFi);
        closet         = findViewById(R.id.cbCloset);
        washingMachine = findViewById(R.id.cbWash);
        netflix        = findViewById(R.id.cbNetflix);
        ref            = findViewById(R.id.cbRef);
        utensils       = findViewById(R.id.cbUtensils);
        kitchen        = findViewById(R.id.cbKit);
        washer         = findViewById(R.id.cbLWash);
        dryer          = findViewById(R.id.cbLDry);
        parking        = findViewById(R.id.cbPark);
        pool           = findViewById(R.id.cbPool);
        gym            = findViewById(R.id.cbGym);
        description     = findViewById(R.id.et_description);
        btnComplete     = findViewById(R.id.btnComplete);
        progressBar     = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        Bundle info = getIntent().getExtras();
        aResult = new ArrayList<>();
        sResult = new ArrayList<>();

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv.isChecked())
                    aResult.add("TV");
                else
                    aResult.remove("TV");
            }
        });

        airConditioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (airConditioner.isChecked())
                    aResult.add("Air Conditioner");
                else
                    aResult.remove("Air Conditioner");
            }
        });

        desk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (desk.isChecked())
                    aResult.add("Desk");
                else
                    aResult.remove("Desk");
            }
        });

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wifi.isChecked())
                    aResult.add("WiFi");
                else
                    aResult.remove("WiFi");
            }
        });

        closet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (closet.isChecked())
                    aResult.add("Closet");
                else
                    aResult.remove("Closet");
            }
        });

        washingMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (washingMachine.isChecked())
                    aResult.add("Washing Machine");
                else
                    aResult.remove("Washing Machine");
            }
        });

        netflix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (netflix.isChecked())
                    aResult.add("Netflix");
                else
                    aResult.remove("Netflix");
            }
        });

        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ref.isChecked())
                    aResult.add("Refrigerator");
                else
                    aResult.remove("Refrigerator");
            }
        });

        utensils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (utensils.isChecked())
                    aResult.add("Utensils");
                else
                    aResult.remove("Utensils");
            }
        });

        kitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kitchen.isChecked())
                    sResult.add("Kitchen");
                else
                    sResult.remove("Kitchen");
            }
        });

        washer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (washer.isChecked())
                    sResult.add("Washer");
                else
                    sResult.remove("Washer");
            }
        });

        dryer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dryer.isChecked())
                    sResult.add("Dryer");
                else
                    sResult.remove("Dryer");
            }
        });

        parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (parking.isChecked())
                    sResult.add("Parking");
                else
                    sResult.remove("Parking");
            }
        });

        pool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pool.isChecked())
                    sResult.add("Pool");
                else
                    sResult.remove("Pool");
            }
        });

        gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gym.isChecked())
                    sResult.add("Gym");
                else
                    sResult.remove("Gym");
            }
        });

        if (info != null) {
        name = info.getString("name");
        imageUrl = info.getString("imageUrl");

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tv.isChecked() && !airConditioner.isChecked() && !desk.isChecked()
                        && !wifi.isChecked() && !closet.isChecked()&& !washingMachine.isChecked()
                        && !netflix.isChecked() && !ref.isChecked() && !utensils.isChecked()
                        && !kitchen.isChecked() && !washer.isChecked() && !dryer.isChecked()
                        && !parking.isChecked() && !pool.isChecked() && !gym.isChecked()){
                    Toast.makeText(Admin_Listing_2.this, "Please Select Amenities and Spaces", Toast.LENGTH_SHORT).show();
                }
                else if (!tv.isChecked() && !airConditioner.isChecked() && !desk.isChecked()
                        && !wifi.isChecked() && !closet.isChecked() && !washingMachine.isChecked()
                        && !netflix.isChecked() && !ref.isChecked() && !utensils.isChecked()){
                    Toast.makeText(Admin_Listing_2.this, "Please Select Amenities", Toast.LENGTH_SHORT).show();
                }
                else if (!kitchen.isChecked() && !washer.isChecked() && !dryer.isChecked()
                        && !parking.isChecked() && !pool.isChecked() && !gym.isChecked()){
                    Toast.makeText(Admin_Listing_2.this, "Please Select Spaces", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseDatabase.getInstance().getReference().child("List(Holder)").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            StringBuilder stringBuilder1 = new StringBuilder();
                            for (String a : aResult)
                                stringBuilder1.append(a).append(", ");
                            resultAme.setText(stringBuilder1.toString());
                            String amenities = resultAme.getText().toString();

                            StringBuilder stringBuilder2 = new StringBuilder();
                            for (String s : sResult)
                                stringBuilder2.append(s).append(", ");
                            resultSpa.setText(stringBuilder2.toString());
                            String spaces = resultSpa.getText().toString();

                            if (spaces.endsWith(", ") && amenities.endsWith(", ")) {
                                amenities = amenities.substring(0, amenities.length() - 2);
                                spaces = spaces.substring(0, spaces.length() - 2);
                            }

                            String key = UUID.randomUUID().toString().replace("-", "").substring(0, 5);

                            HashMap<String, Object> userMap = new HashMap<>();
                            userMap.put("primaryKey", key);
                            userMap.put("amenities", amenities);
                            userMap.put("spaces", spaces);
                            userMap.put("description", description.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("List(Holder)").child(name).updateChildren(userMap);

                            FirebaseDatabase.getInstance().getReference().child("List(Holder)").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    HashMap<String, Object> infoMap = new HashMap<>();
                                    infoMap.put("name", name);
                                    infoMap.put("imageUrl", imageUrl);
                                    infoMap.put("pending", "0");
                                    infoMap.put("confirm", "0");
                                    progressBar.setVisibility(View.VISIBLE);
                                    FirebaseDatabase.getInstance().getReference().child("D - Summary").child(name).setValue(infoMap);
                                    FirebaseDatabase.getInstance().getReference("A - List").child(name).setValue(snapshot.getValue());
                                    FirebaseDatabase.getInstance().getReference().child("List(Holder)").child(name).removeValue();
                                    Toast.makeText(Admin_Listing_2.this, "Upload Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Admin_Listing_2.this, zAdmin_Drawer.class));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        }); }
    }

    @Override
    public void onBackPressed() {
        return;
    }

}