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

import com.example.puppypals_foryourpooch.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity{
    EditText email, username, password, conPassword;
    Button regDog;
    User user;
    DatabaseReference reference;
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

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        regDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference = FirebaseDatabase.getInstance().getReference().child("User");

                if(TextUtils.isEmpty(email.getText().toString()))
                    email.setError("Please enter an email");
                else if(TextUtils.isEmpty(username.getText().toString()))
                    username.setError("Please enter a username");
                else if(TextUtils.isEmpty(password.getText().toString()))
                    username.setError("Please enter a password");
                else if(TextUtils.isEmpty(conPassword.getText().toString()))
                    username.setError("Please confirm your password");
                else if(!password.getText().toString().equalsIgnoreCase(conPassword.getText().toString()))
                    conPassword.setError("Passwords don't match");
                else {
                    user = new User();

                    gpsTracker = new GpsTracker(Registration.this);
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();

                    user.setEmail(email.getText().toString().trim());
                    user.setUsername(username.getText().toString().trim());
                    user.setPassword(password.getText().toString().trim());
                    user.setLatitude(latitude);
                    user.setLongitude(longitude);

                    reference.push().setValue(user);
                    startActivity(new Intent(getApplicationContext(), DogRegistration.class));
                }


            }
        });
    }

}