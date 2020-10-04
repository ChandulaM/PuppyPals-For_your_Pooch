package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.puppypals_foryourpooch.model.AddPupAdd;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pup_register extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button chooseImage;
    private Button upload;
    private Button back;
    private ImageView imageView;
    private EditText owner,breed,email,phone,price;


    private Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private StorageTask storageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pup_register);

        chooseImage = findViewById(R.id.addbutton);
        upload = findViewById(R.id.update);
        imageView = findViewById(R.id.petimage);
        breed = findViewById(R.id.breedname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        price = findViewById(R.id.price);
        back = findViewById(R.id.back_button);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("uploads");
        firebaseAuth = FirebaseAuth.getInstance();

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (storageTask != null && storageTask.isInProgress()) {
                    Toast.makeText(pup_register.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagesActivity();
            }
        });

    }
    private void openFileChooser(){

        Intent  intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null){

            imageUri = data.getData();

            //Log.i("onActivityResult", String.valueOf(data)+resultCode+requestCode);

            Picasso.with(this).load(imageUri).into(imageView);
            imageView.getBackground().setAlpha(0);

        }

    }

    private String getFileUri(Uri uri){

        //Log.i("getFileUri", String.valueOf(uri));
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile(){

        if(imageUri != null){

            final StorageReference fileReference = storageReference.child( System.currentTimeMillis() + "." + getFileUri(imageUri));
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    if(Float.valueOf(price.getText().toString()) < 0){
                                        price.setError("invalid amount");
                                        return;
                                    }
                                    if(TextUtils.isEmpty(email.getText().toString())) {
                                        email.setError("Please enter an email");
                                        return;
                                    }
                                    if(TextUtils.isEmpty(phone.getText().toString())){
                                        phone.setError("Please enter a contact number");
                                        return;
                                    }
                                    if(Float.valueOf(price.getText().toString()) == null) {
                                        price.setError("Please enter an price for your dog");
                                        return;
                                    }
                                    String userMail = email.getText().toString();
                                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                                    if(userMail.matches(emailPattern)==false){

                                        email.setError("Please enter an valid email");

                                    }
                                    else {


                                        AddPupAdd add = new AddPupAdd();
                                        add.setAdId(databaseReference.push().getKey());
                                        add.setUserId(firebaseAuth.getUid().toString());
                                        add.setBreed(breed.getText().toString());
                                        add.setPhone(phone.getText().toString());
                                        add.setEmail(email.getText().toString());
                                        add.setPrice(Float.valueOf(price.getText().toString()));
                                        add.setImageUri(uri.toString());

                                        databaseReference.child(databaseReference.push().getKey()).setValue(add);

                                        Toast.makeText(pup_register.this, "Ad posted successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), pup_add_page.class));
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(pup_register.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(pup_register.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                            double progress = (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());

                        }
                    });
        }
        else{

            Toast.makeText(this,"no file selected",Toast.LENGTH_SHORT).show();
        }

    }
    private void openImagesActivity(){

        Intent intent = new Intent(this,pup_add_page.class);
        startActivity(intent);

    }

}