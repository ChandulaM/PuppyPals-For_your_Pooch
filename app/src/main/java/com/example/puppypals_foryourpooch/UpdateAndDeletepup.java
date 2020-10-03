package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class UpdateAndDeletepup extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button back , update , chooseImage;
    private TextView breed,email,phone,price;
    private ImageView imageView;
    private Uri imageUri;
    private StorageReference storageReference;
    private StorageTask storageTask;
    private DatabaseReference addRef;
    private String oldUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_and_deletepup);

        String adId = getIntent().getStringExtra("id");

        back = findViewById(R.id.back_button);
        breed = findViewById(R.id.breedname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        price = findViewById(R.id.price);
        imageView = findViewById(R.id.petimage);
        update = findViewById(R.id.update);

        addRef = FirebaseDatabase.getInstance().getReference().child("uploads").child(adId);

        addRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren()){

                    breed.setText(Objects.requireNonNull(snapshot.child("breed").getValue()).toString());
                    email.setText(Objects.requireNonNull(snapshot.child("email").getValue()).toString());
                    phone.setText(Objects.requireNonNull(snapshot.child("phone").getValue()).toString());
                    price.setText(Objects.requireNonNull(snapshot.child("price").getValue()).toString());
                    Picasso.with(getApplicationContext())
                            .load(Objects.requireNonNull(snapshot.child("imageUri").getValue()).toString())
                            .fit()
                            .centerCrop()
                            .into(imageView);
                    imageView.getBackground().setAlpha(0);

                    oldUri =  snapshot.child("imageUri").getValue().toString();

                }
                else
                {
                    Toast.makeText(UpdateAndDeletepup.this, "nothing to show", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UpdateAndDeletepup.this, "database error", Toast.LENGTH_SHORT).show();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateAndDeletepup.this,pup_add_page.class);
                startActivity(intent);
            }
        });

            update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                update();

            }
        });


    }
    private void update(){

        if(imageUri != null){
            final StorageReference fileReference = storageReference.child( System.currentTimeMillis() + "." + getFileUri(imageUri));
            fileReference.delete();

            final StorageReference newFileReference = storageReference.child( System.currentTimeMillis() + "." + getFileUri(imageUri));
            newFileReference.putFile(imageUri);

            addRef.child("imageUri").setValue(imageUri);
        }

                                    String newBreed = breed.getText().toString().trim();
                                    String newEmail = email.getText().toString().trim();
                                    float newPrice = Float.valueOf(price.getText().toString().trim());
                                    String newPhone = phone.getText().toString().trim();

        if(newPrice < 0){
            price.setError("invalid amount");
            return;
        }
        if(TextUtils.isEmpty(newEmail)) {
            email.setError("Please enter an email");
            return;
        }
        if(TextUtils.isEmpty(newPhone)){
            phone.setError("Please enter a contact number");
            return;
        }
        if(price == null) {
            email.setError("Please enter an price for your dog");
            return;
        }
        String userMail = email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(userMail.matches(emailPattern)==false){

            email.setError("Please enter an valid email");

        }
        else {

            addRef.child("email").setValue(newEmail);
            addRef.child("phone").setValue(newPhone);
            addRef.child("price").setValue(newPrice);
            addRef.child("breed").setValue(newBreed);


            Toast.makeText(UpdateAndDeletepup.this, "Ad Updated successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), My_add_list.class));
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null){

            imageUri = data.getData();


            Picasso.with(this).load(imageUri).into(imageView);
            imageView.getBackground().setAlpha(0);


        }

    }
    private String getFileUri(Uri uri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void openFileChooser(){

        Intent  intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

}