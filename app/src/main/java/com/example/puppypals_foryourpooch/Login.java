package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.puppypals_foryourpooch.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity  {

    Button btn_signUp, btn_login;
    EditText username, password;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signUp = findViewById(R.id.signup_btn);
        btn_login = findViewById(R.id.login_btn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.login_pBar);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    username.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("Password is required");
                    return;
                }
                username.setError(null);
                password.setError(null);
                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), UserProfile.class));
                        }
                        else{
                            Toast.makeText(Login.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });
    }
}