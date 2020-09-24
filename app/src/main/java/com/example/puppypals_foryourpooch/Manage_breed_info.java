package com.example.puppypals_foryourpooch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Manage_breed_info extends AppCompatActivity /*implements BreedAdapter.SelectBreed*/{
    private Toolbar toolbar;
    private RecyclerView recyView;

    private DatabaseReference dbRef ;
    private StorageReference stRef = FirebaseStorage.getInstance().getReference("Breed Images");;

    private ArrayList<BreedModel> breedModelList ;
    private BreedAdapter adapter;
    private Context context;
    //String[] breeds = {"Beagle","German Shepherd","Pomeranian","Bulldog","Labrador Retriever","Rottweiler","Dobermann"};
    BreedModel breedModel;
    BreedAdapter.SelectBreed selectBreed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_breed_info2);

        recyView = findViewById(R.id.brdRecyclerview);
        toolbar = findViewById(R.id.toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyView.setLayoutManager(layoutManager);
        recyView.setHasFixedSize(true);

        dbRef = FirebaseDatabase.getInstance().getReference();

        breedModelList = new ArrayList<>();
        clearAll();
        getDataFromDB();
        breedModel = new BreedModel();

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");

        recyView.setLayoutManager(new LinearLayoutManager(this));
        recyView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));



    }

    private void getDataFromDB(){
        Query query = dbRef.child("Breed");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BreedModel breedModel = new BreedModel();
                    breedModel.setBreedId(dataSnapshot.child("breedId").getValue().toString());
                     breedModel.setBreedImage(dataSnapshot.child("breedImage").getValue().toString());
                     breedModel.setBreedName(dataSnapshot.child("breedName").getValue().toString());
                    breedModel.setHeight(Integer.parseInt(dataSnapshot.child("height").getValue().toString()));
                    breedModel.setWeight(Integer.parseInt(dataSnapshot.child("weight").getValue().toString()));
                    breedModel.setLifeSpan(Integer.parseInt(dataSnapshot.child("lifeSpan").getValue().toString()));
                    breedModel.setAdaptability(dataSnapshot.child("adaptability").getValue().toString());
                    breedModel.setIntelligence(dataSnapshot.child("intelligence").getValue().toString());
                    breedModel.setFeedings(dataSnapshot.child("feedings").getValue().toString());
                    breedModel.setHealth(dataSnapshot.child("health").getValue().toString());
                    breedModel.setLink(dataSnapshot.child("link").getValue().toString());

                     breedModelList.add(breedModel);
                }
                adapter = new BreedAdapter(getApplicationContext(), breedModelList, selectBreed);
                recyView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void clearAll(){
        if(breedModelList != null){
            breedModelList.clear();

            if(adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
        breedModelList = new ArrayList<>();
    }



    /*@Override
    public void selectedBreed(BreedModel breedModel) {
        startActivity(new Intent(Manage_breed_info.this, CusBreedInfoScroll.class).putExtra("Breed", breedModel));
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem =menu.findItem(R.id.Search_View);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.Search_View) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}