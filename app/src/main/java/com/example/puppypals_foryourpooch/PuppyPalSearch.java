package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.puppypals_foryourpooch.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PuppyPalSearch extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchImageAdapter searchAdapter;

    DatabaseReference userRef;
    FirebaseAuth fAuth;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pupply_pal_search);

        //setting the bottom navigation for the activity
        getBotNav();

        recyclerView = findViewById(R.id.search_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        users = new ArrayList<>();

        userRef = FirebaseDatabase.getInstance().getReference().child("User");
        fAuth = FirebaseAuth.getInstance();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    if(!fAuth.getUid().toString().equals(snap.getKey().toString())){
                        User user = new User(snap.child("email").getValue().toString(),
                                snap.child("username").getValue().toString(),
                                snap.child("password").getValue().toString(),
                                snap.child("imgUrl").getValue().toString(),
                                Double.valueOf(snap.child("latitude").getValue().toString()),
                                Double.valueOf(snap.child("longitude").getValue().toString()));
                        users.add(user);
                    }
                }
                searchAdapter = new SearchImageAdapter(getApplicationContext(), users);
                recyclerView.setAdapter(searchAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PuppyPalSearch.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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