package com.example.puppypals_foryourpooch;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puppypals_foryourpooch.model.AddPupAdd;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private List<AddPupAdd> uploads;

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

        //Toast.makeText(context, "adad"+add.getImageUri(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, "adad"+add.getAdId(), Toast.LENGTH_SHORT).show();
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ImageView imageView;


        public ImageViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.dog_breed);
            imageView = itemView.findViewById(R.id.pupimage);



        }
    }


}
