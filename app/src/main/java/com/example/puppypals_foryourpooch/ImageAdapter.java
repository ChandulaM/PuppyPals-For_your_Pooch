package com.example.puppypals_foryourpooch;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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

import com.example.puppypals_foryourpooch.model.AddPupAdd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private static final String TAG = "Img Url";
    private Context context;
    private List<AddPupAdd> uploads;

    private DatabaseReference reference;

    public ImageAdapter(Context context,List<AddPupAdd> add){

        this.context = context;
        this.uploads = add;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.advertiesmt_laout,parent,false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        final AddPupAdd add = uploads.get(position);

        holder.textView.setText(add.getBreed());

        Picasso.with(context)
                .load(uploads.get(position).getImageUri())
                .fit()
                .centerCrop()
                .into(holder.imageView);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(context.getApplicationContext(),add.getAdId(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context.getApplicationContext(),view_advertiesment.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", add.getAdId());
                context.startActivity(intent);

            }
        });

    }
    @Override
    public int getItemCount() {

        return uploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ImageView imageView;


        public ImageViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.dog_breed);
            imageView = itemView.findViewById(R.id.dog);



        }
    }


}
