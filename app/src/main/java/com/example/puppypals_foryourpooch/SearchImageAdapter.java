package com.example.puppypals_foryourpooch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puppypals_foryourpooch.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchImageAdapter extends RecyclerView.Adapter<SearchImageAdapter.SearchImageViewHolder> {

    private Context mContext;
    private List<User> users;

    public SearchImageAdapter(Context context, List<User> users){
        mContext = context;
        this.users = users;
    }

    @NonNull
    @Override
    public SearchImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.map_search_items, parent, false);
        return new SearchImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchImageViewHolder holder, int position) {
        User userCurrent = users.get(position);
        holder.username.setText(userCurrent.getUsername());
        holder.distance.setText(Double.toString(userCurrent.getLatitude()));
        Picasso.with(mContext)
                .load(userCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.profile_pic);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class SearchImageViewHolder extends RecyclerView.ViewHolder {
        public TextView username, distance;
        public ImageView profile_pic;

        public SearchImageViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.search_uname);
            distance = itemView.findViewById(R.id.search_distance);
            profile_pic = itemView.findViewById(R.id.search_pic);
        }
    }

}
