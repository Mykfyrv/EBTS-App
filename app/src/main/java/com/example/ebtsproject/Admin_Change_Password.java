package com.example.ebtsproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Admin_Change_Password extends Fragment {

     EditText cPassword,nPassword,cfPassword;
     Button btnChange;
     ProgressBar progressBar;
     FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cPassword   = (EditText) view.findViewById(R.id.et_cPassword);
        nPassword   = (EditText) view.findViewById(R.id.et_nPassword);
        cfPassword  = (EditText) view.findViewById(R.id.et_cfPassword);
        btnChange   = (Button) view.findViewById(R.id.btnChange);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChange(cPassword.getText().toString().trim(), nPassword.getText().toString().trim() ,cfPassword.getText().toString().trim());
            }
        });
    }

    private void setChange(String c_Password, String n_Password, String cf_Password){
        if(TextUtils.isEmpty(c_Password) && TextUtils.isEmpty(n_Password) && TextUtils.isEmpty(cf_Password)){
            cPassword.setError("This Field is Required.");
            nPassword.setError("This Field is Required.");
            cfPassword.setError("This Field is Required.");
            return;
        }
        else if(TextUtils.isEmpty(n_Password) && TextUtils.isEmpty(cf_Password)){
            nPassword.setError("This Field is Required.");
            cfPassword.setError("This Field is Required.");
            return;
        }
        else if(TextUtils.isEmpty(c_Password) && TextUtils.isEmpty(cf_Password)){
            cPassword.setError("This Field is Required.");
            cfPassword.setError("This Field is Required.");
            return;
        }
        else if(TextUtils.isEmpty(c_Password) && TextUtils.isEmpty(n_Password)){
            cPassword.setError("This Field is Required.");
            nPassword.setError("This Field is Required.");
            return;
        }
        else if(TextUtils.isEmpty(c_Password)){
            cPassword.setError("This Field is Required.");
            return;
        }
        else if(TextUtils.isEmpty(n_Password)){
            nPassword.setError("This Field is Required.");
            return;
        }
        else if(TextUtils.isEmpty(cf_Password)){
            cfPassword.setError("This Field is Required.");
            return;
        }
        else if(n_Password.length() < 6 || n_Password.length() > 16) {
            nPassword.setError("Password must be 6 to 16 Characters");
        }
        else if(!n_Password.equals(cf_Password)) {
            cfPassword.setError("Password Mismatch!");
        }
        else if (!"".equals(cPassword) && nPassword.equals(nPassword) && nPassword.length() >= 6 && nPassword.length() <=16) {
            progressBar.setVisibility(View.VISIBLE);
            db.collection("admin_account").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot data : task.getResult()){
                            String username = data.getString("username");
                            String password = data.getString("password");
                            if (username.equalsIgnoreCase("Admin")){
                                if (!c_Password.equals(password)){
                                    cPassword.setError("Incorrect Password");
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    db.collection("admin_account").document("admin_account").update("password", n_Password)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(Admin_Change_Password.this.getActivity(), "Password Change", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(Admin_Change_Password.this.getActivity(), zAdmin_Drawer.class));
                                                    } else {
                                                        Toast.makeText(Admin_Change_Password.this.getActivity(), "Error : " + task.getException(), Toast.LENGTH_SHORT).show();
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Admin_Change_Password.this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            });
        }
    }

}
