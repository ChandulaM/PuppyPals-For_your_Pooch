package com.example.puppypals_foryourpooch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class BreedAdapter extends RecyclerView.Adapter<BreedAdapter.BreedAdapterVh> implements Filterable {

    private List<BreedModel> breedModelList;
    private List<BreedModel> getbreedModelListFiltered;
    private Context context;
    private SelectBreed selectBreed;

    public BreedAdapter(List<BreedModel> breedModelList, SelectBreed selectBreed) {
        this.breedModelList = breedModelList;
        this.getbreedModelListFiltered = breedModelList;
        this.selectBreed = selectBreed;
    }

    @NonNull
    @Override
    public BreedAdapter.BreedAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        return new BreedAdapterVh(LayoutInflater.from(context).inflate(R.layout.raw_breeds, null));
    }

    @Override
    public void onBindViewHolder(@NonNull BreedAdapter.BreedAdapterVh holder, int position) {
        BreedModel breedModel = breedModelList.get(position);
        String breedName = breedModel.getBreedName();
        String prefix = breedModel.getBreedName().substring(0,1);

        holder.breedname.setText(breedName);

    }

    @Override
    public int getItemCount() {
        return breedModelList.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null | constraint.length() == 0){
                    filterResults.count = getbreedModelListFiltered.size();
                    filterResults.values = getbreedModelListFiltered;
                }else {
                    String searchChr = constraint.toString().toLowerCase();
                    List<BreedModel> resultData = new ArrayList<>();

                    for(BreedModel breedModel: getbreedModelListFiltered){
                        if(breedModel.getBreedName().toLowerCase().contains(searchChr)){
                            resultData.add(breedModel);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                breedModelList = (List<BreedModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface SelectBreed{
        void selectedBreed(BreedModel breedModel);
    }

    public class BreedAdapterVh extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView breedname;
        ImageView imgVicon;
        public BreedAdapterVh(@NonNull View itemView) {
            super(itemView);
            breedname = itemView.findViewById(R.id.breedname);
//            imgVicon = itemView.findViewById(R.id.arrow);
//            pic = itemView.findViewById();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectBreed.selectedBreed(breedModelList.get(getAdapterPosition()));
                }
            });
        }
    }
}
