package com.example.puppypals_foryourpooch;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;



public class AdminProfile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ImageButton  navViewBrd, navManageBrd, navAddBrd;
    private Button changeUP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        navViewBrd = findViewById(R.id.adminViewBreed);
        navManageBrd = findViewById(R.id.manageBreed);
        navAddBrd = findViewById(R.id.addNewBreed);

        navViewBrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AdminProfile.this,CusSelectBreed.class);
                startActivity(i);


            }
        });

        navManageBrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AdminProfile.this,Manage_breed_info.class);
                startActivity(i);
            }
        });

        navAddBrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AdminProfile.this,AddBreedInfo.class);
                startActivity(i);
            }
        });


    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pitm1:
                Toast.makeText(this,"You are in Admin Profile", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.pitm2:
                Toast.makeText(this,"Logging Out!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}