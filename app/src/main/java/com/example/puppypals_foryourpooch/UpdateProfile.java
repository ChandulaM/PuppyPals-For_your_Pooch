package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.puppypals_foryourpooch.model.Dog;
import com.example.puppypals_foryourpooch.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {

    Button btn_update;
    TextView email, password, dogName, dogAge;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    DatabaseReference userRef, dogRef;
    User user;
    Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        btn_update = findViewById(R.id.upProf_btn_update);
        email = findViewById(R.id.upProf_email);
        password = findViewById(R.id.upProf_oldPwd);
        dogName = findViewById(R.id.upProf_dname);
        dogAge = findViewById(R.id.uProf_age);
        progressBar = findViewById(R.id.upProf_prBar);

        fAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("User").child(fAuth.getUid());

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    email.setText(dataSnapshot.child("email").getValue().toString());
                    password.setText(dataSnapshot.child("password").getValue().toString());
                    String dogId = dataSnapshot.child("dog").getValue().toString();

                    dogRef = FirebaseDatabase.getInstance().getReference().child("Dog").child(dogId);
                    dogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                dogName.setText(dataSnapshot.child("name").getValue().toString());
                                dogAge.setText(dataSnapshot.child("age").getValue().toString());
                            }else{
                                startActivity(new Intent(getApplicationContext(), DogRegistration.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    startActivity(new Intent(getApplicationContext(), Registration.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEmail = email.getText().toString().trim();
                String newPass = password.getText().toString().trim();
                String newDogName = dogName.getText().toString().trim();
                Integer newDogAge = Integer.parseInt(dogAge.getText().toString().trim());



                if(newPass.length() < 6){
                    password.setError("Password must have at least 7 characters");
                    return;
                }
                if(TextUtils.isEmpty(newEmail)) {
                    email.setError("Please enter an email");
                    return;
                }
                if(TextUtils.isEmpty(newPass)){
                    email.setError("Please enter a password");
                    return;
                }
                if(TextUtils.isEmpty(newDogName)) {
                    email.setError("Please enter a name for your dog");
                    return;
                }
                if(newDogAge == null) {
                    email.setError("Please enter an age for your dog");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                userRef.child("email").setValue(newEmail);
                userRef.child("password").setValue(newPass);

                //dogRef = userRef.child("dog").get



            }
        });



    }
}