/*IT19149318
 * Dharmasinghe P.D.G.N.T.D.
 * KDY_WD03*/
package com.example.puppypals_foryourpooch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BreedAdapter extends RecyclerView.Adapter<BreedAdapter.BreedAdapterVh> implements Filterable {

    //Declaring Variables.
    private static final String TAG = "RecyclerView";

    private ArrayList<BreedModel> breedModelList;
    private ArrayList<BreedModel> getbreedModelListFiltered;
    private BreedModel bm;
    private Context context;
    private SelectBreed selectBreed;
    private DatabaseReference dbRef;

    //Constructors
    public BreedAdapter(ArrayList<BreedModel> breedModelList, SelectBreed selectBreed) {
        this.breedModelList = breedModelList;
        this.getbreedModelListFiltered = breedModelList;
        this.selectBreed = selectBreed;

    }

    //Constructors
    public BreedAdapter(Context context, ArrayList<BreedModel> breedModelList, SelectBreed selectBreed) {
        this.context = context;
        this.breedModelList = breedModelList;
        this.getbreedModelListFiltered = breedModelList;
        this.selectBreed = selectBreed;
    }

    //Fetching layout.
    @NonNull
    @Override
    public BreedAdapter.BreedAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new BreedAdapterVh(LayoutInflater.from(context).inflate(R.layout.raw_breeds, null));
    }

    //Binding data to layout.
    @Override
    public void onBindViewHolder(@NonNull BreedAdapter.BreedAdapterVh holder, final int position) {

        holder.breedname.setText(breedModelList.get(position).getBreedName());

        //Setting image to the image view.
        Glide.with(context).load(breedModelList.get(position).getBreedImage()).into(holder.pic);

        holder.brdRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Alert dialog.
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you Sure You wanna Remove breed "+breedModelList.get(position).getBreedName()+" ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                deleteBreed(breedModelList.get(position).getBreedId());
                                Intent intent = new Intent(context.getApplicationContext(), Manage_breed_info.class);
                                Toast.makeText(context.getApplicationContext(), breedModelList.get(position).getBreedName()+" Deleted Successfully.", Toast.LENGTH_SHORT).show();
                                context.startActivity(intent);

                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });


                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

    }

    //Method for deleting a breed
    public void deleteBreed(String breedId) {
        dbRef = FirebaseDatabase.getInstance().getReference().child("Breed").child(breedId);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbRef.removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return breedModelList.size();
    }

    //Search result filter method.
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null | constraint.length() == 0) {
                    filterResults.count = getbreedModelListFiltered.size();
                    filterResults.values = getbreedModelListFiltered;
                } else {
                    String searchChr = constraint.toString().toLowerCase();
                    List<BreedModel> resultData = new ArrayList<>();
                    for (BreedModel breedModel : getbreedModelListFiltered) {
                        if (breedModel.getBreedName().toLowerCase().trim().contains(searchChr)) {
                            resultData.add(breedModel);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }
            //Return data to Recycler view list
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                breedModelList = (ArrayList<BreedModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    //Class for passing objects
    public class SelectBreed {

        public void selectedBreed(BreedModel breedModel) {
            context.startActivity(new Intent(context.getApplicationContext(), CusBreedInfoScroll.class).putExtra("Breed", breedModel));
        }

        public void selectForUpdateBreed(BreedModel breedModel) {
            context.startActivity(new Intent(context.getApplicationContext(), UpdateBreedInfo.class).putExtra("Breed", breedModel));
        }

    }

    //Adapter class for breeds.
    public class BreedAdapterVh extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView breedname;
        Button brdRemoveBtn, brdUpdateBtn;

        public BreedAdapterVh(@NonNull View itemView) {
            super(itemView);
            breedname = itemView.findViewById(R.id.breedname);
            pic = itemView.findViewById(R.id.brdRowImg);
            brdUpdateBtn = itemView.findViewById(R.id.updateBreedInfo);
            brdRemoveBtn = itemView.findViewById(R.id.brdRemove);

            breedname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectBreed sb = new SelectBreed();
                    sb.selectedBreed(breedModelList.get(getAdapterPosition()));
                }
            });

            pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectBreed sb = new SelectBreed();
                    sb.selectedBreed(breedModelList.get(getAdapterPosition()));
                }
            });

            brdUpdateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectBreed sb = new SelectBreed();
                    sb.selectForUpdateBreed(breedModelList.get(getAdapterPosition()));
                }
            });
        }


    }
}
