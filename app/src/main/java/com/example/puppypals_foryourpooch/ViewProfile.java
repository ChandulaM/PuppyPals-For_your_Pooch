package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ViewProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        getBotNav();
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