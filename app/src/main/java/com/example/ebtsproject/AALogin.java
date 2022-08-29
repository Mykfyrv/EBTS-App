package com.example.ebtsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AALogin extends AppCompatActivity {

    EditText username, password;
    TextView register;
    Button btnLogin;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> map = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        Session_Management sessionManagement = new Session_Management(AALogin.this);
        String userID = sessionManagement.getSession();

        if(!userID.equals("-1")){
            switch (userID) {
                case "Admin": {
                    Intent intent = new Intent(AALogin.this, zAdmin_Drawer.class);
                    startActivity(intent);
                    break;
                }
                default: {
                    Intent intent = new Intent(AALogin.this, zCustomer_Drawer.class);
                    startActivity(intent);
                    break;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Session_Management sessionManagement = new Session_Management(AALogin.this);
        String userID = sessionManagement.getSession();
        if(userID.equals("-1")){
            Intent intent = new Intent(AALogin.this, AALogin.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aalogin);

        register = findViewById(R.id.lbl_signUp);
        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.et_user);
        password = findViewById(R.id.et_pass);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), zCustomer_Intro.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private void validate(String userName, String userPass){
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();

        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
            this.username.setError("Username is Required");
            this.password.setError("Password is Required");
            return;
        }
        else if (TextUtils.isEmpty(username)){
            this.username.setError("Username is Required");
        }
        else if (TextUtils.isEmpty(password)){
            this.password.setError("Password is Required");
        }
        else if (password.length() < 6 || password.length() > 16){
            this.password.setError("Password must be 6 to 16 Characters");
        }
        else if (username != "" && password != ""){
            db.collection("admin_account").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot data : task.getResult()){
                            map.add(data.getString("username"));
                            list.add(data.getString("password"));
                        }
                        String a1 = map.get(0);
                        String a2 = list.get(0);
                        if (a1.equalsIgnoreCase(username) && a2.equalsIgnoreCase(password)){
                            Session_User user =new Session_User("Admin", "Admin");
                            Session_Management sessionManagement = new Session_Management(AALogin.this);
                            sessionManagement.saveSession(user);
                            Intent intent = new Intent(AALogin.this, zAdmin_Drawer.class);
                            startActivity(intent);
                            return;
                        }
                        else {
                            db.collection("accounts").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()){
                                        if (task.getResult().exists()){
                                            String b1 = task.getResult().getString("UserName");
                                            String b2 = task.getResult().getString("Password");
                                            if (b1.equalsIgnoreCase(username) && b2.equalsIgnoreCase(password)){
                                                Session_User user =new Session_User(username, username);
                                                Session_Management sessionManagement = new Session_Management(AALogin.this);
                                                sessionManagement.saveSession(user);
                                                Intent intent = new Intent(AALogin.this, zCustomer_Drawer.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(AALogin.this, "Invalid Account!", Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(AALogin.this, "Invalid Account!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AALogin.this, "Invalid Account!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });
        }
    }
}