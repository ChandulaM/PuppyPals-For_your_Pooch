package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadUserImage extends AppCompatActivity {

    private static final int PICK_IMG_REQUEST = 2;

    Button btn_choose, btn_upload;
    ImageView profilePic;
    ProgressBar progressBar;
    Uri imgUri;
    StorageReference storageReference;
    DatabaseReference userRef;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_user_image);

        btn_choose = findViewById(R.id.upImg_btn_choose);
        btn_upload = findViewById(R.id.upImg_btn_upload);
        profilePic = findViewById(R.id.upImg_pic);
        progressBar = findViewById(R.id.upImg_progress);

        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("UserUploads");
        userRef = FirebaseDatabase.getInstance().getReference().child("User").child(fAuth.getUid());

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile() {
        if(imgUri != null){
            final StorageReference fileRef = storageReference.child(System.currentTimeMillis()
                    + "." + getExtension(imgUri));

            fileRef.putFile(imgUri)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 750);
                            Toast.makeText(UploadUserImage.this, "Image Uploaded!", Toast.LENGTH_SHORT).show();
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {    //to get the download url
                                @Override
                                public void onSuccess(Uri uri) {
                                    userRef.child("imgUrl").setValue(uri.toString());
                                    startActivity(new Intent(getApplicationContext(), UserProfile.class));
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadUserImage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            //calculate the progress percentage for the upload
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progressBar.setProgress((int)progress);
                        }
                    });
        }else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMG_REQUEST && resultCode == RESULT_OK && data != null
            && data.getData() != null){
            imgUri = data.getData();
            profilePic.setImageURI(imgUri);

        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
        }
    }
}