package com.example.puppypals_foryourpooch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BreedRows extends AppCompatActivity {


    ImageView pic;
    TextView breedname;
    Button brdRemoveBtn, brdUpdateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raw_breeds);

        breedname = findViewById(R.id.breedname);
        pic = findViewById(R.id.brdRowImg);
        brdUpdateBtn = findViewById(R.id.updateBreedInfo);
        brdRemoveBtn = findViewById(R.id.brdRemove);


        brdUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BreedRows.this, AddBreedInfo.class);
                startActivity(i);
            }
        });

        brdRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}