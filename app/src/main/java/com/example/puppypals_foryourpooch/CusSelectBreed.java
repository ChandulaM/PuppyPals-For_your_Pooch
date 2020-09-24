package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class CusSelectBreed extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private Toolbar toolbar;
    private GridView gridView;

    private DatabaseReference dbRef ;
    private StorageReference stRef = FirebaseStorage.getInstance().getReference("Breed Images");;

    private ArrayList<BreedModel> breedModelList = new ArrayList<>();
    BreedModel breedModel;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_select_breed);

        gridView = findViewById(R.id.breedGrid);
        toolbar = findViewById(R.id.toolbar);

        dbRef = FirebaseDatabase.getInstance().getReference();

        clearAll();
        getDataFromDB();

//
//        this.setSupportActionBar(toolbar);
//        this.getSupportActionBar().setTitle("");





    }

    public class CustomAdapter extends BaseAdapter{

        private List<BreedModel> brdList;
        private List<BreedModel> brdListFiltered;
        private Context context;

        public CustomAdapter(List<BreedModel> brdList, Context context) {
            this.brdList = brdList;
            this.brdListFiltered = brdList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return brdListFiltered.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.row_cus_breed, null);

            ImageView imageView = view1.findViewById(R.id.cbrdIm);
            TextView textView = view1.findViewById(R.id.txtCusbreed);
            Glide.with(getApplicationContext()).load(brdList.get(position).getBreedImage()).into(imageView);
            textView.setText(brdList.get(position).getBreedName());
            return view1;
        }
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
                customAdapter = new CustomAdapter(breedModelList, CusSelectBreed.this);
                gridView.setAdapter(customAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void clearAll(){
        if(breedModelList != null){
            breedModelList.clear();

            if(customAdapter != null){
                customAdapter.notifyDataSetChanged();
            }
        }
        breedModelList = new ArrayList<>();
    }



    /*@Override
    public void selectedBreed(BreedModel breedModel) {
        startActivity(new Intent(Manage_breed_info.this, CusBreedInfoScroll.class).putExtra("Breed", breedModel));
    }*/

  //  @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search, menu);
//        MenuItem menuItem =menu.findItem(R.id.Search_View);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                customAdapter.getFilter().filter(newText);
//                return true;
//            }
//        });
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.Search_View) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


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