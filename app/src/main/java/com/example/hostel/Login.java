package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

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


public class Login extends AppCompatActivity {
    private EditText userEmail, userPassword;
    private TextView signup_tv, forget;
    private AppCompatButton loginBtn;
    private String email_var, password_var;
    public SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String SHARE_PREF_NAME = "Login";
    private static final String KEY_EMAIL = "email_a";
    private static final String KEY_PASSWORD = "password_a";
    private FirebaseAuth auth;
    private ProgressBar pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            openMain();
            finish();
        }


        userEmail = findViewById(R.id.log_email);
        userPassword = findViewById(R.id.log_password);
        loginBtn = findViewById(R.id.log_button);
        signup_tv = findViewById(R.id.signupBtn);
        forget = findViewById(R.id.forget);
        pd=findViewById(R.id.pd_log);

        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        String email_admin = sharedPreferences.getString(KEY_EMAIL, null);
        if (email_admin != null) {
            startActivity(new Intent(this, Admin.class));
            finish();
        }

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgetPassword.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setVisibility(View.VISIBLE);
                loginBtn.setVisibility(View.GONE);
                email_var = userEmail.getText().toString();
                password_var = userPassword.getText().toString();

                if (email_var.isEmpty()) {
                    userEmail.setError("Required");
                } else if (password_var.isEmpty()) {
                    userPassword.setError("Required");
                } else if (email_var.equals("boyshostel1984@gmail.com") || email_var.equals("girlshostel1984@gmail.com")) {
                    validate_admin_Data(email_var, password_var);
                } else {
                    validate_user_data(email_var, password_var);
                }
            }
        });
        signup_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));

            }
        });
    }

    private void validate_user_data(String email_var, String password_var) {
        auth.signInWithEmailAndPassword(email_var, password_var)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            openMain();
                            finish();
                        } else {
                            pd.setVisibility(View.GONE);
                            loginBtn.setVisibility(View.VISIBLE);

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.setVisibility(View.GONE);
                        loginBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(Login.this, "Something went wrong , try again !!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void openMain() {
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }

    private void validate_admin_Data(String email, String password) {
        if (email.equals("boyshostel1984@gmail.com") && password.equals("Boys@1984")) {
            openDashboard("boyshostel1984@gmail.com", "Boys@1984");
        } else if (email.equals("girlshostel1984@gmail.com") && password.equals("Girls@1984")) {

            openDashboard("girlshostel1984@gmail.com", "Girls@1984");
        } else {
            Toast.makeText(this, "Incorrect email and password !!", Toast.LENGTH_LONG).show();
        }

    }

    private void openDashboard(String email_a, String password_a) {
        editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email_a);
        editor.putString(KEY_PASSWORD, password_a);
        editor.apply();
        Intent intent = new Intent(this, Admin.class);
        startActivity(intent);
        finish();
    }
}