package com.example.ebtsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class Admin_Listing_Edit extends AppCompatActivity {

    EditText name, rate, location, guest, dateIn, dateOut, amenities, spaces, description;
    String  s_name, s_rate, s_location, s_guest, s_dateIn, s_dateOut, s_amenities, s_spaces, s_description, primaryKey;
    ProgressBar progressBar;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_listing_edit);

        name        = (EditText) findViewById(R.id.et_name);
        location    = (EditText) findViewById(R.id.et_loc);
        rate        = (EditText) findViewById(R.id.et_rate);
        guest       = (EditText) findViewById(R.id.et_guest);
        dateIn      = (EditText) findViewById(R.id.et_dateIn);
        dateOut     = (EditText) findViewById(R.id.et_dateOut);
        amenities   = (EditText) findViewById(R.id.et_amenities);
        spaces      = (EditText) findViewById(R.id.et_spaces);
        description = (EditText) findViewById(R.id.et_description);
        btnUpdate   = (Button) findViewById(R.id.btnUpdate);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        TextWatcher textWatcher = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "ddmmyyyy";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals(current)) {
                    String clean = charSequence.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int x = 2; x <= cl && x < 6; x += 2) {
                        sel++;
                    }

                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<2021)?2021:(year>2025)?2025:year;
                        cal.set(Calendar.YEAR, year);

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dateOut.setText(current);
                    dateOut.setSelection(sel < current.length() ? sel : current.length());

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        dateOut.addTextChangedListener(textWatcher);
        Bundle details = getIntent().getExtras();

        if(details != null){
            s_name      = details.getString("name");
            s_rate      = details.getString("rate");
            s_location  = details.getString("location");
            s_guest     = details.getString("guest");
            s_dateIn    = details.getString("dateIn");
            s_dateOut   = details.getString("dateOut");
            s_amenities = details.getString("amenities");
            s_spaces    = details.getString("spaces");
            s_description = details.getString("description");
            primaryKey  = details.getString("primaryKey");

            name.setText(s_name);
            location.setText(s_location);
            rate.setText(s_rate);
            guest.setText(s_guest);
            dateIn.setText(s_dateIn);
            dateOut.setText(s_dateOut);
            amenities.setText(s_amenities);
            spaces.setText(s_spaces);
            description.setText(s_description);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String new_name         = name.getText().toString().trim();
                    String new_location     = location.getText().toString().trim();
                    String new_rate         = rate.getText().toString().trim();
                    String new_guest        = guest.getText().toString().trim();
                    String new_dateIn       = dateIn.getText().toString().trim();
                    String new_dateOut      = dateOut.getText().toString().trim();
                    String new_amenities    = amenities.getText().toString().trim();
                    String new_spaces       = spaces.getText().toString().trim();
                    String new_description  = description.getText().toString().trim();
                    progressBar.setVisibility(View.VISIBLE);
                    HashMap<String, Object> list = new HashMap<>();
                    list.put("name", new_name);
                    list.put("location", new_location);
                    list.put("rate", new_rate);
                    list.put("guest", new_guest);
                    list.put("dateIn", new_dateIn);
                    list.put("dateOut", new_dateOut);
                    list.put("amenities", new_amenities);
                    list.put("spaces", new_spaces);
                    list.put("description", new_description);
                    FirebaseDatabase.getInstance().getReference("A - List").child(name.getText().toString()).updateChildren(list);
                    Toast.makeText(Admin_Listing_Edit.this, "Edit Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Admin_Listing_Edit.this, zAdmin_Drawer.class));
                }
            });


        }




    }
}