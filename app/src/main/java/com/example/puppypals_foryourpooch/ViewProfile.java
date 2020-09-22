package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ViewProfile extends AppCompatActivity {

    private static final String TAG = "USER";
    Button btn_chat, btn_back;
    ImageView profilePic;
    TextView userName, dogName, dogAge, dogBreed;
    FirebaseAuth fAuth;
    DatabaseReference userRef, dogRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        getBotNav();

        String userId = getIntent().getStringExtra("userId");
        Log.d(TAG, "User ID: " + userId);

        btn_chat = findViewById(R.id.viewProf_btn_chat);
        btn_back = findViewById(R.id.viewProf_btn_back);
        profilePic = findViewById(R.id.viewProf_prPic);
        userName = findViewById(R.id.viewProf_head);
        dogName = findViewById(R.id.viewProf_dname);
        dogAge = findViewById(R.id.viewProf_age);
        dogBreed = findViewById(R.id.viewProf_breed);

        fAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("User").child(userId);

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

                    dogRef = FirebaseDatabase.getInstance().getReference().child("Dog").child(dogId);   //gets the viewed user's dog
                    dogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                //setting the information retrieved to the textViews
                                dogName.setText(dataSnapshot.child("name").getValue().toString());
                                dogBreed.setText(dataSnapshot.child("breed").getValue().toString());
                                dogAge.setText(dataSnapshot.child("age").getValue().toString());
                            }else
                                Toast.makeText(ViewProfile.this, "No dog registered for current user", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ViewProfile.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewProfile.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PuppyPalSearch.class));
            }
        });
    }

    private void getBotNav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_nav);

        bottomNavigationView.setSelectedItemId(R.id.bot_nav_search);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bot_nav_search:
                        startActivity(new Intent(getApplicationContext(), PuppyPalSearch.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bot_nav_home:
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}