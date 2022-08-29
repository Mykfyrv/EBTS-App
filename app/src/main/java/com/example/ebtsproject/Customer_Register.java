package com.example.ebtsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Customer_Register extends AppCompatActivity {

    EditText username, fullName, phone, email, password, confirmPass;
    Button btnReg;
    ProgressBar progressBar;
    FirebaseFirestore db;
    DocumentReference CustomerAcc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_register);

        username    = findViewById(R.id.et_username);
        fullName    = findViewById(R.id.et_fullName);
        phone       = findViewById(R.id.et_phone);
        email       = findViewById(R.id.et_email);
        password    = findViewById(R.id.et_newPassword);
        confirmPass = findViewById(R.id.et_confirmPassword);
        btnReg      = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        db          = FirebaseFirestore.getInstance();
        progressBar.setVisibility(View.GONE);

        FirebaseFirestore.setLoggingEnabled(true);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String name = fullName.getText().toString().trim();
                String phone  = Customer_Register.this.phone.getText().toString().trim();
                String email  = Customer_Register.this.email.getText().toString().trim();
                String password = Customer_Register.this.password.getText().toString().trim();
                String confirm  = Customer_Register.this.confirmPass.getText().toString().trim();

                if (TextUtils.isEmpty(user) && TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) &&
                    TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confirm)) {
                    Customer_Register.this.username.setError("This Field is Required");
                    Customer_Register.this.fullName.setError("This Field is Required");
                    Customer_Register.this.phone.setError("This Field is Required");
                    Customer_Register.this.email.setError("This Field is Required");
                    Customer_Register.this.password.setError("This Field is Required");
                    Customer_Register.this.confirmPass.setError("This Field is Required");
                    return;
                }
                else if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password)
                        && TextUtils.isEmpty(confirm)) {
                    Customer_Register.this.fullName.setError("This Field is Required");
                    Customer_Register.this.phone.setError("This Field is Required");
                    Customer_Register.this.email.setError("This Field is Required");
                    Customer_Register.this.password.setError("This Field is Required");
                    Customer_Register.this.confirmPass.setError("This Field is Required");
                    return;
                }
                else if (TextUtils.isEmpty(phone) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confirm)) {
                    Customer_Register.this.phone.setError("This Field is Required");
                    Customer_Register.this.email.setError("This Field is Required");
                    Customer_Register.this.password.setError("This Field is Required");
                    Customer_Register.this.confirmPass.setError("This Field is Required");
                    return;
                }
                else if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confirm)) {
                    Customer_Register.this.email.setError("This Field is Required");
                    Customer_Register.this.password.setError("This Field is Required");
                    Customer_Register.this.confirmPass.setError("This Field is Required");
                    return;
                }
                else if (TextUtils.isEmpty(password) && TextUtils.isEmpty(confirm)) {
                    Customer_Register.this.password.setError("This Field is Required");
                    Customer_Register.this.confirmPass.setError("This Field is Required");
                    return;
                }
                else if (TextUtils.isEmpty(name)) {
                    Customer_Register.this.fullName.setError("This Field is Required");
                    return;
                }
                else if (TextUtils.isEmpty(phone)) {
                    Customer_Register.this.phone.setError("This Field is Required");
                    return;
                }
                else if (TextUtils.isEmpty(email)) {
                    Customer_Register.this.email.setError("This Field is Required");
                    return;
                }
                else if (TextUtils.isEmpty(password)) {
                    Customer_Register.this.password.setError("This Field is Required");
                    return;
                }
                else if (TextUtils.isEmpty(confirm)) {
                    Customer_Register.this.confirmPass.setError("This Field is Required");
                    return;
                }
                else if (password.length() < 6 || confirm.length() > 16) {
                    Customer_Register.this.password.setError("Password must be 6 to 16 Characters");
                    return;
                }
                else if(!password.equals(confirm)) {
                    Customer_Register.this.password.setError("Password Mismatch!");
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Customer_Register.this.email.setError("Invalid Email Address");
                    return;
                }
                else if (phone.length() < 11 && phone.length() > 12) {
                    Customer_Register.this.phone.setError("Invalid Phone Number");
                    return;
                }
                else if (!"".equals(user) && !"".equals(name) && !"".equals(phone) && !"".equals(email) &&
                        !"".equals(email) && password.equals(confirm) && password.length() >= 6 && password.length() <=16 ){
                        saveToFirestore(user, name, phone, email, password);
                }
            }
        });
    }

    private void saveToFirestore(String user, String name, String phone, String email, String password){
            CustomerAcc = db.collection("accounts").document(user);
            CustomerAcc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        Toast.makeText(Customer_Register.this, "This username has already been use.", Toast.LENGTH_SHORT).show();
                    } else {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("UserName", user);
                        map.put("FullName", name);
                        map.put("Phone", phone);
                        map.put("Email", email);
                        map.put("Password", password);
                        db.collection("accounts").document(user).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressBar.setVisibility(View.VISIBLE);
                                Toast.makeText(Customer_Register.this, "Registration Success!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), AALogin.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Customer_Register.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
    }
}


