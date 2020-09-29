package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

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
    private ImageAdapter imageAdapter;
    private List<AddPupAdd> add;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private Button back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_add_list);

        myView = findViewById(R.id.recyclerView);
        myView.setHasFixedSize(true);
        myView.setLayoutManager(new LinearLayoutManager(this));
        add = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("uploads");

        final String userId = auth.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                AddPupAdd advert = new AddPupAdd();

                String key = snapshot.getKey();

                advert.setImageUri(snapshot.child("imageUri").getValue().toString());
                advert.setEmail(snapshot.child("email").getValue().toString());
                advert.setPrice(Float.valueOf(snapshot.child("price").getValue().toString()));
                advert.setPhone(snapshot.child("phone").getValue().toString());

                String adUid = snapshot.child("userId").getValue().toString();

                if( userId == adUid ) {

                    add.add(advert);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}