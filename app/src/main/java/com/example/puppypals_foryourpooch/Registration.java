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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.puppypals_foryourpooch.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity{
    EditText email, username, password, conPassword;
    Button regDog;
    User user;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.reg_email);
        username = findViewById(R.id.reg_uname);
        password = findViewById(R.id.reg_pass);
        conPassword = findViewById(R.id.reg_confPass);
        regDog = findViewById(R.id.btn_regDog);

        fAuth = FirebaseAuth.getInstance();

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        /*if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }*/

        regDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference = FirebaseDatabase.getInstance().getReference().child("User");

                if(password.getText().toString().length() < 6){
                    password.setError("Password must have at least 7 characters");
                    return;
                }

                if(TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("Please enter an email");
                    return;
                }
                else if(TextUtils.isEmpty(username.getText().toString())) {
                    username.setError("Please enter a username");
                    return;
                }
                else if(TextUtils.isEmpty(password.getText().toString())) {
                    username.setError("Please enter a password");
                    return;
                }
                else if(TextUtils.isEmpty(conPassword.getText().toString())) {
                    username.setError("Please confirm your password");
                    return;
                }
                else if(!password.getText().toString().equalsIgnoreCase(conPassword.getText().toString())) {
                    conPassword.setError("Passwords don't match");
                    return;
                }
                else {
                    user = new User();

                    String userEmail = email.getText().toString().trim();
                    String userName = username.getText().toString().trim();
                    String userPassword = password.getText().toString().trim();

                    gpsTracker = new GpsTracker(Registration.this);
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();

                    user.setEmail(userEmail);
                    user.setUsername(userName);
                    user.setPassword(userPassword);
                    user.setLatitude(latitude);
                    user.setLongitude(longitude);

                    fAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                reference.child(fAuth.getUid()).setValue(user);
                                startActivity(new Intent(getApplicationContext(), DogRegistration.class));
                            }
                            else
                                Toast.makeText(Registration.this, "Error creating account", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}