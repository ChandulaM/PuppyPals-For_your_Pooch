/*IT19149318
 * Dharmasinghe P.D.G.N.T.D.
 * KDY_WD03*/
package com.example.puppypals_foryourpooch;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//Class for display, search dog breeds
public class CusSelectBreed extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private static final String TAG = "breed";

    private Toolbar toolbar;
    private GridView gridView;

    private DatabaseReference dbRef;
    private StorageReference stRef = FirebaseStorage.getInstance().getReference("Breed Images");
    ;

    public ArrayList<BreedModel> breedModelList = new ArrayList<>();
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_select_breed);

        getBotNav();

        gridView = findViewById(R.id.breedGrid);
        toolbar = findViewById(R.id.toolbar);

        dbRef = FirebaseDatabase.getInstance().getReference();

        clearAll();
        getDataFromDB();

    }

    //Search bar implementation.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchbar_for_customers, menu);
        MenuItem menuItem = menu.findItem(R.id.serchbar_for_customers);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    //Adapter class.
    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<BreedModel> brdList;
        private List<BreedModel> brdListFiltered;
        private Context context;

        public CustomAdapter(List<BreedModel> brdList, Context context) {
            this.brdList = brdList;
            this.brdListFiltered = new ArrayList<>(brdList);
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
        public View getView(final int position, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.row_cus_breed, null);

            ImageView imageViewbrd = view1.findViewById(R.id.cbrdIm);
            TextView textViewbrd = view1.findViewById(R.id.txtCusbreed);
            Log.d(TAG, "getView: " + brdListFiltered.size());
            Glide.with(context).load(brdListFiltered.get(position).getBreedImage()).into(imageViewbrd);
            Log.d(TAG, "breed list position : " + position);
            Log.d(TAG, "breed list position : " + brdListFiltered.get(position));
            textViewbrd.setText(brdListFiltered.get(position).getBreedName());

            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CusSelectBreed.this, CusBreedInfoScroll.class).putExtra("breedGrid", brdListFiltered.get(position)));
                }
            });
            return view1;
        }
        //Search result filter method.
        @Override
        public Filter getFilter() {
            return filter;
        }
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<BreedModel> filterResults = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.addAll(brdList);
                } else {
                    String searchChr = constraint.toString().toLowerCase().trim();
                    for (BreedModel breedModel : brdList) {
                        if (breedModel.getBreedName().toLowerCase().trim().contains(searchChr)) {
                            filterResults.add(breedModel);
                        }
                    }
                }
                FilterResults resultData = new FilterResults();
                resultData.values = filterResults;
                return resultData;
            }
            //Return data to grid list
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                brdListFiltered.clear();
                brdListFiltered.addAll((List) results.values);
                notifyDataSetChanged();
                for (int i = 0; i < brdListFiltered.size(); i++) {
                    Log.d(TAG, "results: " + brdListFiltered.get(i));
                }
            }
        };
    }


    private void getDataFromDB() {
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


    private void clearAll() {
        if (breedModelList != null) {
            breedModelList.clear();

            if (customAdapter != null) {
                customAdapter.notifyDataSetChanged();
            }
        }
        breedModelList = new ArrayList<>();
    }

    //Selecting an item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.serchbar_for_customers) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                Toast.makeText(this, "Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.pitm2:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logging Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));
                return true;
        }
        return false;
    }
    private void getBotNav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_nav);

        bottomNavigationView.setSelectedItemId(R.id.bot_nav_info);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bot_nav_search:
                        startActivity(new Intent(getApplicationContext(), PuppyPalSearch.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bot_nav_home:
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bot_nav_info:
                        return true;
                    case R.id.bot_nav_chat:
                        startActivity(new Intent(getApplicationContext(), AllChats.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bot_nav_ad:
                        startActivity(new Intent(getApplicationContext(), pup_add_page.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

}