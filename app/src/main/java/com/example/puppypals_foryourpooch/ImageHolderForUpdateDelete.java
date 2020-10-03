package com.example.puppypals_foryourpooch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puppypals_foryourpooch.model.AddPupAdd;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageHolderForUpdateDelete extends RecyclerView.Adapter<ImageHolderForUpdateDelete.ImageViewHolder> {

    private Context context;
    private List<AddPupAdd> pupList;
    private DatabaseReference databaseReference;



    public ImageHolderForUpdateDelete(Context context, List<AddPupAdd> pupList) {
        this.context = context;
        this.pupList = pupList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.updateanddelete,parent,false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        final AddPupAdd add = pupList.get(position);

        final String id = add.getAdId();

        Picasso.with(context)
                .load(add.getImageUri())
                .fit()
                .centerCrop()
                .into(holder.imageView);




        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), UpdateAndDeletepup.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", add.getAdId());
                context.startActivity(intent);

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference = FirebaseDatabase.getInstance().getReference()
                        .child("uploads").child(add.getAdId());
                databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context.getApplicationContext(), My_add_list.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return pupList.size();
    }
    public static class ImageViewHolder extends RecyclerView.ViewHolder{

        public Button update , delete;
        public ImageView imageView;


        public ImageViewHolder(View itemView) {
            super(itemView);

            delete = itemView.findViewById(R.id.delete);
            update = itemView.findViewById(R.id.update);
            imageView = itemView.findViewById(R.id.pupimage);

        }
    }
}
