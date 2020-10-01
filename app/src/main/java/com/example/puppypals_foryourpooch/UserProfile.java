package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {
    Button btn_update, btn_upload;
    ImageView profilePic;
    TextView userName, dogName, dogAge, dogBreed;
    FirebaseAuth fAuth;
    DatabaseReference userRef, dogRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getBotNav();

        btn_update = findViewById(R.id.uProf_btn_update);
        btn_upload = findViewById(R.id.uProf_btn_upload);
        userName = findViewById(R.id.uProf_header);
        dogName = findViewById(R.id.uProf_dname);
        dogBreed = findViewById(R.id.uProf_breed);
        dogAge = findViewById(R.id.uProf_age);
        profilePic = findViewById(R.id.uProf_profPic);

        fAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("User").child(fAuth.getUid()); //gets current user

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    userName.setText(dataSnapshot.child("username").getValue().toString());
                    if(dataSnapshot.hasChild("imgUrl")) {
                        Picasso.with(getApplicationContext())
                                .load(dataSnapshot.child("imgUrl").getValue().toString())
                                .fit()
                                .centerCrop()
                                .into(profilePic);
                    }
                    String dogId = dataSnapshot.child("dog").getValue().toString();

                    dogRef = FirebaseDatabase.getInstance().getReference().child("Dog").child(dogId);   //gets the current user's dog
                    dogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                //setting the information retrieved to the textViews
                                dogName.setText(dataSnapshot.child("name").getValue().toString());
                                dogBreed.setText(dataSnapshot.child("breed").getValue().toString());
                                dogAge.setText(dataSnapshot.child("age").getValue().toString());
                            }else
                                Toast.makeText(UserProfile.this, "No dog registered for current user", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserProfile.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UpdateProfile.class));
            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    private void getBotNav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_nav);
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bot_nav_search:
                        startActivity(new Intent(getApplicationContext(), PuppyPalSearch.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bot_nav_home:
                        return true;
                    case R.id.bot_nav_info:
                        startActivity(new Intent(getApplicationContext(), CusSelectBreed.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void uploadPic(View view) {
        startActivity(new Intent(getApplicationContext(), UploadUserImage.class));
    }
}