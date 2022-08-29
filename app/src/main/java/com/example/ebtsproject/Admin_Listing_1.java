package com.example.ebtsproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Admin_Listing_1 extends AppCompatActivity {

    Button btnContinue;
    ImageView imageView;
    EditText name, rate, location, guest, dateIn, dateOut;
    Uri imageUri;
    Drawable drawable;
    ProgressBar progressBar;
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_listing_1);

        imageView       = findViewById(R.id.imgPicture);
        drawable        = imageView.getDrawable();
        name            = findViewById(R.id.et_name);
        rate            = findViewById(R.id.et_rate);
        location        = findViewById(R.id.et_loc);
        guest           = findViewById(R.id.et_guest);
        dateIn          = findViewById(R.id.et_dateIn);
        dateOut         = findViewById(R.id.et_dateOut);
        btnContinue     = findViewById(R.id.btnContinue);
        progressBar     = findViewById(R.id.progressBar);
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

        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        dateIn.setText(date);
        dateOut.addTextChangedListener(textWatcher);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView.getDrawable() == drawable && TextUtils.isEmpty(name.getText().toString()) && TextUtils.isEmpty(rate.getText().toString()) && TextUtils.isEmpty(location.getText().toString())
                        && TextUtils.isEmpty(guest.getText().toString()) && TextUtils.isEmpty(dateOut.getText().toString())){
                    Toast.makeText(Admin_Listing_1.this, "Please an Select Image", Toast.LENGTH_SHORT).show();
                    name.setError("This Field is Required.");
                    rate.setError("This Field is Required.");
                    location.setError("This Field is Required.");
                    guest.setError("This Field is Required.");
                    dateOut.setError("This Field is Required.");
                }
                else if (TextUtils.isEmpty(name.getText().toString()) && TextUtils.isEmpty(rate.getText().toString()) && TextUtils.isEmpty(location.getText().toString()) &&
                        TextUtils.isEmpty(guest.getText().toString()) && TextUtils.isEmpty(dateOut.getText().toString())){
                    name.setError("This Field is Required.");
                    rate.setError("This Field is Required.");
                    location.setError("This Field is Required.");
                    guest.setError("This Field is Required.");
                    dateOut.setError("This Field is Required.");
                }
                else if (TextUtils.isEmpty(rate.getText().toString()) && TextUtils.isEmpty(location.getText().toString()) && TextUtils.isEmpty(guest.getText().toString()) &&
                        TextUtils.isEmpty(dateOut.getText().toString())){
                    rate.setError("This Field is Required.");
                    location.setError("This Field is Required.");
                    guest.setError("This Field is Required.");
                    dateOut.setError("This Field is Required.");
                }
                else if (TextUtils.isEmpty(location.getText().toString()) && TextUtils.isEmpty(guest.getText().toString()) && TextUtils.isEmpty(dateOut.getText().toString())){
                    location.setError("This Field is Required.");
                    guest.setError("This Field is Required.");
                    dateOut.setError("This Field is Required.");
                }
                else if (TextUtils.isEmpty(guest.getText().toString()) && TextUtils.isEmpty(dateOut.getText().toString())){
                    guest.setError("This Field is Required.");
                    dateOut.setError("This Field is Required.");
                }
                else if (TextUtils.isEmpty(dateOut.getText().toString())){
                    dateOut.setError("This Field is Required.");
                }
                else if (TextUtils.isEmpty(guest.getText().toString())){
                    guest.setError("This Field is Required.");
                }
                else if (TextUtils.isEmpty(location.getText().toString())){
                    location.setError("This Field is Required.");
                }
                else if (TextUtils.isEmpty(rate.getText().toString())){
                    rate.setError("This Field is Required.");
                }
                else if (TextUtils.isEmpty(name.getText().toString())){
                    name.setError("This Field is Required.");
                }
                else if (imageView.getDrawable() == drawable){
                    Toast.makeText(Admin_Listing_1.this, "Please an Select Image", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadToFirebase(imageUri);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadToFirebase(Uri uri){
        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("List(Holder)").child(name.getText().toString());
        FirebaseDatabase.getInstance().getReference("A - List").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(name.getText().toString()).getValue() != null){
                        Toast.makeText(Admin_Listing_1.this, "Already Exist!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                    StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
                    fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String name = Admin_Listing_1.this.name.getText().toString().trim();
                                    String rate = Admin_Listing_1.this.rate.getText().toString().trim();
                                    String location = Admin_Listing_1.this.location.getText().toString().trim();
                                    String guest = Admin_Listing_1.this.guest.getText().toString().trim();
                                    String dateIn = Admin_Listing_1.this.dateIn.getText().toString().trim();
                                    String dateOut = Admin_Listing_1.this.dateOut.getText().toString().trim();
                                    Model_List model = new Model_List(name, rate, location, uri.toString(), guest, dateIn, dateOut);
                                    root.setValue(model);
                                    Bundle info = new Bundle();
                                    info.putString("name", name);
                                    info.putString("imageUrl", uri.toString());
                                    Intent intent = new Intent(Admin_Listing_1.this, Admin_Listing_2.class);
                                    intent.putExtras(info);
                                    startActivity(intent);
                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Admin_Listing_1.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}