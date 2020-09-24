package com.example.puppypals_foryourpooch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class CusBreedInfoScroll extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    ImageView im;
    TextView brdName, h, w, ls, adap, intell, feed, hlth, link;
    Button buyPup, morInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_breed_info_scroll);
        final BreedModel model = (BreedModel) getIntent().getSerializableExtra("Breed");

        im = findViewById(R.id.cusBreedimg);
        brdName = findViewById(R.id.cusBreednm);
        h = findViewById(R.id.txtHeight);
        w = findViewById(R.id.txtWeight);
        ls = findViewById(R.id.txtLspan);
        adap = findViewById(R.id.adaptxt);
        intell = findViewById(R.id.intelltxt);
        feed = findViewById(R.id.fed);
        hlth = findViewById(R.id.med);
        link = findViewById(R.id.moreinfo);

        buyPup = findViewById(R.id.bypup);
        morInfo = findViewById(R.id.moreinfo);


        Glide.with(getApplicationContext()).load(model.getBreedImage()).into(im);
        brdName.setText(model.getBreedName());
        h.setText(model.getHeight().toString().trim());
        w.setText(model.getWeight().toString().trim());
        ls.setText(model.getLifeSpan().toString().trim());
        adap.setText(model.getAdaptability());
        intell.setText(model.getIntelligence());
        feed.setText(model.getFeedings());
        hlth.setText(model.getHealth());

        morInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(model.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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
                Toast.makeText(this,"Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.pitm2:
                Toast.makeText(this,"Item 2 clicked", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}