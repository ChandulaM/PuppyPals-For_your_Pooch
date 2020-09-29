package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class pup_add_page extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<AddPupAdd> list;
    private DatabaseReference databaseReference;
    private Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pup_add_page);

        recyclerView = findViewById(R.id.cycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("uploads");
        add = findViewById(R.id.newadd);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String key = dataSnapshot.getKey();
                    AddPupAdd user = new AddPupAdd();
                    user.setBreed(dataSnapshot.child("breed").getValue().toString());
                    user.setImageUri(dataSnapshot.child("imageUri").getValue().toString());
                    user.setUserId(dataSnapshot.child("userId").getValue().toString());
                    user.setAdId(key);
                    String t = dataSnapshot.child("imageUri").getValue().toString();
                    // Log.i("info",t);


                    list.add(user);

                }
                imageAdapter = new ImageAdapter(pup_add_page.this,list);
                recyclerView.setAdapter(imageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(pup_add_page.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pup_add_page.this,pup_register.class);
                startActivity(intent);
            }
        });




    }


}