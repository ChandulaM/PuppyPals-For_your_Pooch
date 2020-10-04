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
import android.widget.TextView;
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
    private DatabaseReference databaseReference,databaseReference1;
    private Button add , myadd;
    private TextView count;
    private long countVal ;


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
        myadd = findViewById(R.id.myadd);
        count = findViewById(R.id.count);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //setAdsCount(snapshot.getChildrenCount());

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String key = dataSnapshot.getKey();
                    AddPupAdd user = new AddPupAdd();
                    user.setBreed(dataSnapshot.child("breed").getValue().toString());
                    user.setImageUri(dataSnapshot.child("imageUri").getValue().toString());
                    user.setUserId(dataSnapshot.child("userId").getValue().toString());
                    user.setAdId(key);

                    list.add(user);

                }
                pup_add_page count1 = new pup_add_page();

                count1.setAdsCount(snapshot.getChildrenCount());


                count.setText(String.valueOf(count1.getAdsCount()));
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

        myadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(pup_add_page.this,My_add_list.class);
                startActivity(intent);

            }
        });




    }
    public void setAdsCount(long count){

        countVal = count;

    }
    public long getAdsCount(){


        return countVal;
    }

    /*
    public long count() {

        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("uploads");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    setAdsCount(snapshot.getChildrenCount());
                }
                else {

                    Toast.makeText(pup_add_page.this, "no adds", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(pup_add_page.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return 5;
    }*/

}