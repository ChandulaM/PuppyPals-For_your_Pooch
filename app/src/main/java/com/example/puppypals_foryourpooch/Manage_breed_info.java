package com.example.puppypals_foryourpooch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Manage_breed_info extends AppCompatActivity implements BreedAdapter.SelectBreed{
    Toolbar toolbar;
    RecyclerView recyView;

    List<BreedModel> breedModelList = new ArrayList<>();
    String[] breeds = {"Beagle","German Shepherd","Pomeranian","Bulldog","Labrador Retriever","Rottweiler","Dobermann"};

    BreedAdapter breedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_breed_info2);

        recyView = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");

        recyView.setLayoutManager(new LinearLayoutManager(this));
        recyView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        for(String s:breeds){
            BreedModel breedModel = new BreedModel(s);
            breedModelList.add(breedModel);
        }

        breedAdapter = new BreedAdapter(breedModelList, this);
        recyView.setAdapter(breedAdapter);


    }


    @Override
    public void selectedBreed(BreedModel breedModel) {
        startActivity(new Intent(Manage_breed_info.this, ManageBreedInfo.class).putExtra("data", breedModel));
    }

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
                breedAdapter.getFilter().filter(newText);
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