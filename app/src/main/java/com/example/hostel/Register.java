package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    private EditText reg_name, reg_email, reg_password, reg_phone, reg_parent_name;
    private AppCompatButton register_btn;
    private String name, email, password, phone, parent_name;
    private FirebaseAuth auth;
    private DatabaseReference dbref, reference;

    private TextView loginBtn;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();     //auth
        reference = FirebaseDatabase.getInstance().getReference();
        dialog = new Dialog(Register.this);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);

        loginBtn = findViewById(R.id.loginBtn);
        reg_name = findViewById(R.id.reg_name);
        reg_password = findViewById(R.id.reg_password);
        reg_email = findViewById(R.id.reg_email);
        reg_phone = findViewById(R.id.reg_phone);
        reg_parent_name = findViewById(R.id.reg_parent_name);
        register_btn = findViewById(R.id.register_btn);
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openLogin();
//            }
//        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });


    }

    private void openLogin() {
        startActivity(new Intent(Register.this, Login.class));
        finish();
    }


    //auth
   /* @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser() != null){
            openMain();
        }
    }

    private void openMain() {

        startActivity(new Intent(this, Login.class));
        finish();

    }*/

    private void validateData() {
        name = reg_name.getText().toString();
        email = reg_email.getText().toString();
        password = reg_password.getText().toString();
        phone = reg_phone.getText().toString();
        parent_name = reg_parent_name.getText().toString();
        if (name.isEmpty()) {
            reg_name.setError(" Enter name!");
            reg_name.requestFocus();
        } else if (email.isEmpty()) {
            reg_email.setError(" Enter email!");
            reg_email.requestFocus();

        } else if (parent_name.isEmpty()) {
            reg_parent_name.setError("Enter parent name!");
            reg_parent_name.requestFocus();

        } else if (phone.isEmpty()) {
            reg_phone.setError("Enter Phone!");
            reg_phone.requestFocus();
        } else if (password.isEmpty()) {
            reg_password.setError("Enter password");
            reg_password.requestFocus();
        } else {
            Createuser();
        }
    }

    private void Createuser() {
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    uploadData(task.getResult().getUser());

                } else {
                    dialog.dismiss();
                    Toast.makeText(Register.this, "" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();

            }
        });


    }

    private void uploadData(FirebaseUser firebaseUser) {


        dbref = reference.child("User");



        User user = new User(name, email, parent_name, phone, password, firebaseUser.getUid(),"");
        dbref.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(Register.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();


                } else {
                    dialog.dismiss();
                    Toast.makeText(Register.this, "Something went wrong! please try again", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();


            }
        });

    }
}