package com.example.puppypals_foryourpooch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.puppypals_foryourpooch.model.Dog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DogRegistration extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    Button btn_signUp;
    Spinner spinner;
    EditText dogName, dogAge;
    DatabaseReference userRef, dogRef;
    FirebaseAuth fAuth;
    Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_registration);

        dog = new Dog();
        fAuth = FirebaseAuth.getInstance();
        dogName = findViewById(R.id.dogreg_dname);
        dogAge = findViewById(R.id.dogreg_age);
        btn_signUp = findViewById(R.id.btn_dogreg_signup);
        spinner = findViewById(R.id.dogreg_breed);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.breeds_trial,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        dog.setBreed(adapterView.getItemAtPosition(i).toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}