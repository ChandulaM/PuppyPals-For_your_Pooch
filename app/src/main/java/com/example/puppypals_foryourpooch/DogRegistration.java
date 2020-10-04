package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.puppypals_foryourpooch.model.Dog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DogRegistration extends AppCompatActivity {
    Button btn_signUp;
    Spinner spinner;
    EditText dogName, dogAge;
    DatabaseReference userRef, dogRef, breedRef;
    FirebaseAuth fAuth;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerData;
    Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_registration);

        dog = new Dog();
        fAuth = FirebaseAuth.getInstance();
        breedRef = FirebaseDatabase.getInstance().getReference().child("Breed");
        dogName = findViewById(R.id.dogreg_dname);
        dogAge = findViewById(R.id.dogreg_age);
        btn_signUp = findViewById(R.id.btn_dogreg_signup);
        spinner = findViewById(R.id.dogreg_breed);

        spinnerData = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                spinnerData);
        spinner.setAdapter(adapter);
        retrieveBreeds();

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = dogName.getText().toString().trim();
                Integer age = Integer.parseInt(dogAge.getText().toString().trim());

                dogRef = FirebaseDatabase.getInstance().getReference().child("Dog");
                userRef = FirebaseDatabase.getInstance().getReference().child("User");

                if(name.isEmpty()){
                    dogName.setError("Name cannot be empty");
                    return;
                }
                else if(age == null){
                    dogAge.setError("Age cannot be empty");
                    return;
                }
                else{
                    dog.setName(name);
                    dog.setAge(age);
                    dog.setBreed(spinner.getSelectedItem().toString());
                    String dogId = dogRef.push().getKey();
                    dogRef.child(dogId).setValue(dog);
                    userRef.child(fAuth.getUid()).child("dog").setValue(dogId);
                    startActivity(new Intent(getApplicationContext(), UserProfile.class));
                }
            }
        });
    }

    public void retrieveBreeds(){

        listener = breedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item : dataSnapshot.getChildren()){

                    spinnerData.add(item.child("breedName").getValue().toString());

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DogRegistration.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        dog.setBreed(adapterView.getItemAtPosition(i).toString());
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}