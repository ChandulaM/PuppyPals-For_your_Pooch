package com.example.puppypals_foryourpooch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AdminProfile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ImageButton navViewBrd, navManageBrd, navAddBrd;
    private Button save, show, update;
    private EditText uName, pswd;
    private Admin admin;
    private DatabaseReference dbRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        navViewBrd = findViewById(R.id.adminViewBreed);
        navManageBrd = findViewById(R.id.manageBreed);
        navAddBrd = findViewById(R.id.addNewBreed);

        save = findViewById(R.id.adminSaveBtn);
        show = findViewById(R.id.adminShowbtn);
        update = findViewById(R.id.adminProBtb);

        uName = findViewById(R.id.adminName);
        pswd = findViewById(R.id.adminPswd);

        admin = new Admin();
        loadingBar = new ProgressDialog(this);



        navViewBrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminProfile.this, CusSelectBreed.class);
                startActivity(i);


            }
        });

        navManageBrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminProfile.this, Manage_breed_info.class);
                startActivity(i);
            }
        });

        navAddBrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminProfile.this, AddBreedInfo.class);
                startActivity(i);
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Admin");
                try{
                    if(TextUtils.isEmpty(uName.getText().toString())){
                        Toast.makeText(getApplicationContext(),"User name Mandatory!", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty((pswd.getText().toString()))){
                        Toast.makeText(getApplicationContext(),"Password Mandatory!", Toast.LENGTH_SHORT).show();
                    }else {
                        admin.setUname(uName.getText().toString().trim());
                        admin.setPswd(pswd.getText().toString().trim());

                        dbRef.child("admin1").setValue(admin);

                        Toast.makeText(getApplicationContext(),"Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        clearControl();
                    }

                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loadingBar.setTitle("Fetching Data...");
                loadingBar.setMessage("Dear Admin, please wait while we are fetching your credentials.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Admin").child("admin1");
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            uName.setText(snapshot.child("uname").getValue().toString());
                            pswd.setText(snapshot.child("pswd").getValue().toString());
                            loadingBar.dismiss();
                        }else Toast.makeText(getApplicationContext(),"No Data to Display!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Admin");
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("admin1")){
                            try{
                                if(TextUtils.isEmpty(uName.getText().toString())){
                                    Toast.makeText(getApplicationContext(),"User name Mandatory!", Toast.LENGTH_SHORT).show();
                                }else if(TextUtils.isEmpty((pswd.getText().toString()))){
                                    Toast.makeText(getApplicationContext(),"Password Mandatory!", Toast.LENGTH_SHORT).show();
                                }else {
                                    admin.setUname(uName.getText().toString().trim());
                                    admin.setPswd(pswd.getText().toString().trim());

                                    dbRef = FirebaseDatabase.getInstance().getReference().child("Admin").child("admin1");
                                    dbRef.setValue(admin);

                                    Toast.makeText(getApplicationContext(),"Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                    clearControl();
                                }

                            }catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext(),"Invalid Data!", Toast.LENGTH_SHORT).show();
                            }
                        }else Toast.makeText(getApplicationContext(),"No Data to Update!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }



    private void clearControl(){
        uName.setText("");
        pswd.setText("");
    }

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
                Toast.makeText(this, "You are in Admin Profile", Toast.LENGTH_SHORT).show();
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