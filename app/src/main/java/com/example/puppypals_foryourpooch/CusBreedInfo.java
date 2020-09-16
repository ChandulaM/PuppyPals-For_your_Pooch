package com.example.puppypals_foryourpooch;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CusBreedInfo extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_breed_info);
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
                Toast.makeText(this,"Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.pitm2:
                Toast.makeText(this,"Item 2 clicked", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}