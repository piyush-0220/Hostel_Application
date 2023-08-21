package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    private EditText forgetemail;
    private AppCompatButton forgetbutton;
    private String email;
    private FirebaseAuth auth;
    private Dialog dialog;
    private static final String KEY_EMAIL = "email_a";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String SHARE_PREF_NAME = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        sharedPreferences = this.getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        auth = FirebaseAuth.getInstance();
        dialog = new Dialog(ForgetPassword.this);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);


        forgetbutton = findViewById(R.id.forget_button);
        forgetemail = findViewById(R.id.forget_email);
        forgetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate_forget_Data();
            }
        });

    }

            private void validate_forget_Data() {
                email = forgetemail.getText().toString();
                if (email.isEmpty()) {
                    forgetemail.setError("Enter email!");
            forgetemail.requestFocus();
        } else if (email.equals("boyshostel1984@gmail.com") || email.equals("girlshostel1984@gmail.com")) {
            Toast.makeText(this, "Sorry! The password for this email can not be reset", Toast.LENGTH_SHORT).show();

        } else {
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            forgetpass();
        }

    }

    @Override
    public void onBackPressed() {
        editor.putString(KEY_EMAIL, null);
        editor.commit();
        Intent intent = new Intent(ForgetPassword.this, Login.class);
        startActivity(intent);
        finish();
        super.onBackPressed();

    }

    private void forgetpass() {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(ForgetPassword.this, "please check your email! ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgetPassword.this, Login.class));
                    finish();
                } else {
                    dialog.dismiss();
                    Toast.makeText(ForgetPassword.this, "Something went wrong! Please try again later", Toast.LENGTH_SHORT).show();

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