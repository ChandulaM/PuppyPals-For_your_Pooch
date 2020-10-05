package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class AllChats extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView allChatError;
    ProgressBar progressBar;
    AllChatAdapter chatAdapter;
    DatabaseReference ref, userRef;
    FirebaseAuth fAuth;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_chats);

        getBotNav();

        allChatError = findViewById(R.id.chat_error_msg);
        progressBar = findViewById(R.id.allChats_prBar);
        users = new ArrayList<>();
        recyclerView = findViewById(R.id.chatsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref = FirebaseDatabase.getInstance().getReference().child("Chats");
        fAuth = FirebaseAuth.getInstance();

        ref.child(fAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (users != null)
                    users.clear();
                if (snapshot.hasChildren()) {
                    for (DataSnapshot snap : snapshot.getChildren()) { //loops through the current user's friends
                        String userId = snap.getKey();
                        User user = new User();
                        user.setUserId(userId);
                        users.add(user);
                    }
                }
                progressBar.setVisibility(View.GONE);
                if(!users.isEmpty()) {
                    chatAdapter = new AllChatAdapter(getApplicationContext(), users);
                    recyclerView.setAdapter(chatAdapter);
                }else{
                    allChatError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getBotNav() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_nav);

        bottomNavigationView.setSelectedItemId(R.id.bot_nav_chat);

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
                    case R.id.bot_nav_info:
                        startActivity(new Intent(getApplicationContext(), CusSelectBreed.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bot_nav_ad:
                        startActivity(new Intent(getApplicationContext(), pup_add_page.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}