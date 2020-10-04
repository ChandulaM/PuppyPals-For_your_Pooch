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

public class AllChatAdapter extends RecyclerView.Adapter<AllChatAdapter.ChatHolder>{

    private Context context;
    private List<User> users;
    private FirebaseAuth fAuth;
    private DatabaseReference userRef;

    public AllChatAdapter(Context context, List<User> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.your_puppypals, parent, false);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatHolder holder, int position) {

        fAuth = FirebaseAuth.getInstance();
        final User user = users.get(position);
        userRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUserId());

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.userProf.setText(snapshot.child("username").getValue().toString());
                if(snapshot.child("imgUrl").getValue() != null){
                    Picasso.with(context)
                            .load(snapshot.child("imgUrl").getValue().toString())
                            .fit()
                            .centerCrop()
                            .into(holder.imageView);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d(TAG, "onBindViewHolder: " + user.getUserId());
        holder.btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), UserChat.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("uId", user.getUserId());
                Log.d(TAG, "onClick: " + user.getUserId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder{

        private TextView userProf;
        private ImageView imageView;
        private Button btn_chat;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            userProf =itemView.findViewById(R.id.friend_name);
            imageView = itemView.findViewById(R.id.friend_pic);
            btn_chat = itemView.findViewById(R.id.btn_friend_chat);
        }
    }

}
