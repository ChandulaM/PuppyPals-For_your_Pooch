package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.puppypals_foryourpooch.model.AddPupAdd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class My_add_list extends AppCompatActivity {

    private RecyclerView myView;
    private ImageHolderForUpdateDelete imageHolderForUpdateDelete;
    private ImageAdapter imageAdapter;
    private List<AddPupAdd> adv;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private Button back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_add_list);

        myView = findViewById(R.id.recyclerViewMyAds);
        back = findViewById(R.id.back);
        myView.setHasFixedSize(true);
        myView.setLayoutManager(new LinearLayoutManager(this));
        adv = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        final String userId  = auth.getUid().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("uploads");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    AddPupAdd advert = new AddPupAdd();

                    advert.setImageUri(dataSnapshot.child("imageUri").getValue().toString());
                    advert.setEmail(dataSnapshot.child("email").getValue().toString());
                    advert.setPrice(Float.parseFloat(dataSnapshot.child("price").getValue().toString()));
                    advert.setPhone(dataSnapshot.child("phone").getValue().toString());
                    advert.setAdId(dataSnapshot.getKey());


                    if(dataSnapshot.child("userId").getValue()
                    .toString().equals(userId)) {
                        adv.add(advert);
                    }
                }

                imageHolderForUpdateDelete = new ImageHolderForUpdateDelete(My_add_list.this,adv);
                myView.setAdapter(imageHolderForUpdateDelete);

                /*imageAdapter = new ImageAdapter(My_add_list.this,add);
                myView.setAdapter(imageAdapter);*/



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(My_add_list.this, "Database error", Toast.LENGTH_SHORT).show();


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), pup_add_page.class));
            }
        });

    }
}