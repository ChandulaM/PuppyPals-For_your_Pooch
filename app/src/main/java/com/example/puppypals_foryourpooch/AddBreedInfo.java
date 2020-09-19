package com.example.puppypals_foryourpooch;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AddBreedInfo extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener  {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText brdName, brdH, brdW, brdLs, adap, intell, feed, hlth, link;
    private Button rest, save;
    private ImageButton addImg, navViewBrd, navManageBrd, navAddBrd;
    private ImageView breedImg;
    private ProgressBar pBar;

    private Uri brdImgUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_breed_info);

        brdName = findViewById(R.id.breedName);
        brdH = findViewById(R.id.hno);
        brdW = findViewById(R.id.wno);
        brdLs = findViewById(R.id.lsno);
        adap = findViewById(R.id.adapget);
        intell = findViewById(R.id.intelget);
        feed = findViewById(R.id.feedget);
        hlth = findViewById(R.id.healget);
        link = findViewById(R.id.infoLink);

        rest = findViewById(R.id.resetBreed);
        save = findViewById(R.id.saveBreed);

        addImg = findViewById(R.id.addImgBtn);
        navViewBrd = findViewById(R.id.adminViewBreed);
        navManageBrd = findViewById(R.id.manageBreed);
        navAddBrd = findViewById(R.id.addNewBreed);

        breedImg = findViewById(R.id.dogImgView);
        pBar = findViewById(R.id.proBar);

        mStorageRef = FirebaseStorage.getInstance().getReference("Breed Images");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Breed");

       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                uploadDataAndImg();
           }
       });

       rest.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });

       addImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                openFileChooser();
           }
       });

       navViewBrd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });

       navManageBrd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });

       navAddBrd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });


    }

    private void openFileChooser(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(i.ACTION_GET_CONTENT);
        startActivityForResult(i,PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            brdImgUri = data.getData();
            breedImg.setImageURI(brdImgUri);
        }

    }
    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mm = MimeTypeMap.getSingleton();
        return mm.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadDataAndImg(){
        if(brdImgUri != null){
            StorageReference sr = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(brdImgUri));

            sr.putFile(brdImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pBar.setProgress(0);
                        }
                    }, 500);
                    Toast.makeText(AddBreedInfo.this, "Data Uploaded Successfully.", Toast.LENGTH_LONG).show();
                    BreedModel breedModel = new BreedModel(brdName.getText().toString().trim(),
                            Integer.parseInt(brdH.getText().toString().trim()),Integer.parseInt(brdW.getText().toString().trim()),
                            Integer.parseInt(brdLs.getText().toString().trim()),adap.getText().toString().trim(),
                            intell.getText().toString().trim(),feed.getText().toString().trim(),hlth.getText().toString().trim(),link.getText().toString().trim(),
                            taskSnapshot.getStorage().getDownloadUrl().toString().trim());
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(breedModel);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddBreedInfo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pBar.setProgress((int) progress);
                }
            });

        }else {
            Toast.makeText(this, "No File Selected!!!", Toast.LENGTH_SHORT).show();
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
        switch (item.getItemId()){
            case R.id.pitm1:
                Toast.makeText(this,"Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.pitm2:
                Toast.makeText(this,"Item 2 clicked", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}