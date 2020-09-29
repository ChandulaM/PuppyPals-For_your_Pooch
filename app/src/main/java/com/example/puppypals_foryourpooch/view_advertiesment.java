package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puppypals_foryourpooch.model.AddPupAdd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class view_advertiesment extends AppCompatActivity {

    private Button back;
    private TextView breed,email,phone,price;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_advertiesment);

        String adId = getIntent().getStringExtra("id");

        back = findViewById(R.id.back);
        breed = findViewById(R.id.breedname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        price = findViewById(R.id.price);
        imageView = findViewById(R.id.petimage);

        FirebaseAuth auth;
        DatabaseReference addRef;

        auth = FirebaseAuth.getInstance();
        addRef = FirebaseDatabase.getInstance().getReference().child("uploads").child(adId);

        addRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren()){

                    breed.setText(snapshot.child("breed").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                    phone.setText(snapshot.child("phone").getValue().toString());
                    price.setText(snapshot.child("price").getValue().toString());
                    Picasso.with(getApplicationContext())
                            .load(snapshot.child("imageUri").getValue().toString())
                            .fit()
                            .centerCrop()
                            .into(imageView);

                }
                else
                {
                    Toast.makeText(view_advertiesment.this, "nothing to show", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(view_advertiesment.this, "database error", Toast.LENGTH_SHORT).show();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view_advertiesment.this,pup_add_page.class);
                startActivity(intent);
            }
        });


    }
}