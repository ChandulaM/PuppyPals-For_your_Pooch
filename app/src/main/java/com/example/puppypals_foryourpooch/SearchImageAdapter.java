package com.example.puppypals_foryourpooch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puppypals_foryourpooch.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SearchImageAdapter extends RecyclerView.Adapter<SearchImageAdapter.SearchImageViewHolder> {
    private static final String TAG = "This User";
    private Context mContext;
    private List<User> users;
    private FirebaseAuth fAuth;
    private DatabaseReference userRef;

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
    public void onBindViewHolder(@NonNull final SearchImageViewHolder holder, int position) {
        fAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("User").child(fAuth.getUid()); //current user
        final User userCurrent = users.get(position);
        holder.username.setText(userCurrent.getUsername());

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){

                    double lat = Double.valueOf(dataSnapshot.child("latitude").getValue().toString());
                    double lon = Double.valueOf(dataSnapshot.child("longitude").getValue().toString());
                    double lat1 = Double.valueOf(userCurrent.getLatitude());
                    double lon1 = Double.valueOf(userCurrent.getLongitude());

                    DistanceCalculator calculator = new DistanceCalculator();
                    double distanceBetween = calculator.distance(lat, lat1, lon, lon1 );

                    holder.distance.setText(String.format("%.2f",distanceBetween) + " km away");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //load the profile picture
        Picasso.with(mContext)
                .load(userCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.profile_pic);

        //to view a specific user's profile
        holder.view_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext.getApplicationContext(), ViewProfile.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userId", userCurrent.getUserId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class SearchImageViewHolder extends RecyclerView.ViewHolder {
        public TextView username, distance;
        public ImageView profile_pic;
        public Button view_profile_btn, chat_btn;

        public SearchImageViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.search_uname);
            distance = itemView.findViewById(R.id.search_distance);
            profile_pic = itemView.findViewById(R.id.search_pic);
            view_profile_btn = itemView.findViewById(R.id.search_btn_view);
            chat_btn = itemView.findViewById(R.id.search_btn_chat);
        }
    }

}
