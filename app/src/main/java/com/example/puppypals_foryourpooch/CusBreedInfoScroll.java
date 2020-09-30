/*IT19149318
 * Dharmasinghe P.D.G.N.T.D.
 * KDY_WD03*/
package com.example.puppypals_foryourpooch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.google.firebase.auth.FirebaseAuth;

//Class for Display Breed information
public class CusBreedInfoScroll extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    ImageView im;
    TextView brdName, h, w, ls, adap, intell, feed, hlth, link;
    Button buyPup, morInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_breed_info_scroll);
        final BreedModel model = (BreedModel) getIntent().getSerializableExtra("Breed");
        final BreedModel model2 = (BreedModel) getIntent().getSerializableExtra("breedGrid");

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

        if (model != null) {
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
                    if(!Uri.EMPTY.equals(uri)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    }else  Toast.makeText(getApplicationContext(), "URL not available!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            Glide.with(getApplicationContext()).load(model2.getBreedImage()).into(im);
            brdName.setText(model2.getBreedName());
            h.setText(model2.getHeight().toString().trim());
            w.setText(model2.getWeight().toString().trim());
            ls.setText(model2.getLifeSpan().toString().trim());
            adap.setText(model2.getAdaptability());
            intell.setText(model2.getIntelligence());
            feed.setText(model2.getFeedings());
            hlth.setText(model2.getHealth());


            morInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        Uri uri = Uri.parse(model2.getLink());
                    if(!Uri.EMPTY.equals(uri)) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }else  Toast.makeText(getApplicationContext(), "URL not available!", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pitm1:

                Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.pitm2:

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logging Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));
                return true;
        }
        return false;
    }
}