/*IT19149318
* Dharmasinghe P.D.G.N.T.D.
* KDY_WD03*/
package com.example.puppypals_foryourpooch;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

//Class for Adding Breed data
public class AddBreedInfo extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    //Variable decelerations
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText breednm, brdHi, brdWe, brdLspn, adapt, intel, feeds, health, links;
    private Button rest, save;
    private ImageButton addImg, navViewBrd, navManageBrd, navAddBrd;
    private ImageView breedImg;
    private ProgressBar pBar;

    private Uri brdImgUri;

    private StorageReference ProductImagesRef;
    private DatabaseReference mDatabaseRef;
    private ProgressDialog loadingBar;
    private BreedModel bm;


    private String breedName, adaptability, intelligence, feedings, hlth, link, saveCurrentDate, saveCurrentTime, productRandomKey, downloadImageUrl;
    private Integer height, weight, lifeSpan;

    //Clearing variables
    private void clearControlls() {
        breednm.setText("");
        brdHi.setText("");
        brdWe.setText("");
        brdLspn.setText("");
        adapt.setText("");
        intel.setText("");
        feeds.setText("");
        health.setText("");
        links.setText("");
        breedImg.invalidate();
        breedImg.setImageBitmap(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_breed_info);

        //Finding views
        breednm = findViewById(R.id.breedName);
        brdHi = findViewById(R.id.hno);
        brdWe = findViewById(R.id.wno);
        brdLspn = findViewById(R.id.lsno);
        adapt = findViewById(R.id.adapget);
        intel = findViewById(R.id.intelget);
        feeds = findViewById(R.id.feedget);
        health = findViewById(R.id.healget);
        links = findViewById(R.id.infoLink);

        rest = findViewById(R.id.resetBreed);
        save = findViewById(R.id.saveBreed);

        addImg = findViewById(R.id.addImgBtn);
        navViewBrd = findViewById(R.id.adminViewBreed);
        navManageBrd = findViewById(R.id.manageBreed);
        navAddBrd = findViewById(R.id.addNewBreed);

        breedImg = findViewById(R.id.dogImgView);

        //Database variable declaration.
        ProductImagesRef = FirebaseStorage.getInstance().getReference("Breed Images");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Breed");

        bm = new BreedModel();

        //Loading bar object
        loadingBar = new ProgressDialog(this);

        //Clicking save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

        //Clicking reset button.
        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearControlls();
            }
        });

        //Clicking add image, image button.
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        //Top navigation image button for view and search breeds.
        navViewBrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddBreedInfo.this, CusSelectBreed.class);
                startActivity(i);


            }
        });

        //Top navigation image button for manage breeds.
        navManageBrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddBreedInfo.this, Manage_breed_info.class);
                startActivity(i);
            }
        });

        //Top navigation image button for add new breeds.
        navAddBrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddBreedInfo.this, AddBreedInfo.class);
                startActivity(i);
            }
        });


    }

    //Local storage files opening method
    private void openFileChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(i.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    //Fetching image data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            brdImgUri = data.getData();
            breedImg.setImageURI(brdImgUri);
        }

    }

    //Getting image file extensions
    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mm = MimeTypeMap.getSingleton();
        return mm.getExtensionFromMimeType(cr.getType(uri));
    }


    //Validating input data
    public void validateData() {

        breedName = breednm.getText().toString();
        adaptability = adapt.getText().toString();
        intelligence = intel.getText().toString();
        feedings = feeds.getText().toString();
        hlth = health.getText().toString();
        link = links.getText().toString();

        if (TextUtils.isEmpty(breedName)) {
            Toast.makeText(this, "Breed Name is Mandatory!", Toast.LENGTH_SHORT).show();
            breednm.setError("Enter Breed Name");
        } else if (TextUtils.isEmpty(brdHi.getText().toString())) {
            Toast.makeText(this, "Breed height is Mandatory!", Toast.LENGTH_SHORT).show();
            brdHi.setError("Enter Breed Height");
        } else if (TextUtils.isEmpty(brdWe.getText().toString())) {
            Toast.makeText(this, "Breed Weight is Mandatory!", Toast.LENGTH_SHORT).show();
            brdWe.setError("Enter Breed Weight");
        } else if (TextUtils.isEmpty(brdLspn.getText().toString())) {
            Toast.makeText(this, "Breed Life Span is Mandatory!", Toast.LENGTH_SHORT).show();
            brdLspn.setError("Enter Breed Life Span");
        } else if (TextUtils.isEmpty(adaptability)) {
            Toast.makeText(this, "Breed Adaptability is Mandatory!", Toast.LENGTH_SHORT).show();
            adapt.setError("Enter Adaptability Info");
        } else if (TextUtils.isEmpty(intelligence)) {
            Toast.makeText(this, "Breed Intelligence is Mandatory!", Toast.LENGTH_SHORT).show();
            intel.setError("Enter Intelligence Info");
        } else if (TextUtils.isEmpty(feedings)) {
            Toast.makeText(this, "Breed Feedings is Mandatory!", Toast.LENGTH_SHORT).show();
            feeds.setError("Enter Feeding Info");
        } else if (TextUtils.isEmpty(hlth)) {
            Toast.makeText(this, "Breed Health is Mandatory!", Toast.LENGTH_SHORT).show();
            health.setError("Enter Health Info");
//        } else if (TextUtils.isEmpty(link)) {
//            Toast.makeText(this, "Info link is Mandatory!", Toast.LENGTH_SHORT).show();
//            links.setError("Enter the Link ");
        } else if (brdImgUri == null) {
            Toast.makeText(this, "Breed image is Mandatory!", Toast.LENGTH_SHORT).show();

        } else {
            StoreProductInformation();
        }


    }

    //Store, upload image to the database.
    private void StoreProductInformation() {

        loadingBar.setTitle("Adding New Breed Info");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new Breed Info.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = ProductImagesRef.child(brdImgUri.getLastPathSegment());

        final UploadTask uploadTask = filePath.putFile(brdImgUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddBreedInfo.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(AddBreedInfo.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();

                           // Toast.makeText(AddBreedInfo.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    //Save all the data to the database.
    private void SaveProductInfoToDatabase() {

        try {
            bm.setBreedId(productRandomKey);
            bm.setBreedName(breedName.trim());
            bm.setHeight(Integer.parseInt(brdHi.getText().toString().trim()));
            bm.setWeight(Integer.parseInt(brdWe.getText().toString().trim()));
            bm.setLifeSpan(Integer.parseInt(brdLspn.getText().toString().trim()));
            bm.setAdaptability(adaptability.trim());
            bm.setIntelligence(intelligence.trim());
            bm.setFeedings(feedings.trim());
            bm.setHealth(hlth.trim());
            bm.setLink(link.trim());
            bm.setBreedImage(downloadImageUrl.trim());

            mDatabaseRef.child(productRandomKey).setValue(bm);
            Toast.makeText(getApplicationContext(), R.string.toast_breed_add, Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
            clearControlls();
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid height/weight/lifeSpan values!", Toast.LENGTH_SHORT).show();
        }


    }

    //For profile popup menu.
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pitm1:
                Intent i = new Intent(this, AdminProfile.class);
                startActivity(i);
                Toast.makeText(this, "Admin Profile.", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.pitm2:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logging Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));
                return true;
        }
        return false;
    }
}