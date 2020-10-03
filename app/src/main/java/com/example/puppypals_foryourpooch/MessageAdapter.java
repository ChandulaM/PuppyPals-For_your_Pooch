package com.example.puppypals_foryourpooch;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puppypals_foryourpooch.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder>{

    private Context context;
    private List<Chat> chats;
    private FirebaseAuth fAuth;
    private DatabaseReference dbRef, chatRef;

    public MessageAdapter(Context context, List<Chat> chats) {
        this.context = context;
        this.chats = chats;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.msg_item, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageHolder holder, int position) {

        fAuth = FirebaseAuth.getInstance();
        final String currentId = fAuth.getUid();
        final Chat chat = chats.get(position);

        Log.d("TAG", "onBindViewHolder1: " + chat.getChatId());

        dbRef = FirebaseDatabase.getInstance().getReference().child("User")
                .child(chat.getSenderId());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    String username;
                    if(!currentId.equals(chat.getSenderId())) {
                        username = snapshot.child("username").getValue().toString();
                    }else{
                        username = "You";
                    }
                    holder.senderUsername.setText(username);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.message.setText(chat.getMessage());
        holder.time.setText(chat.getTime());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder{

        private TextView senderUsername, message, time;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            senderUsername = itemView.findViewById(R.id.sender_uname);
            message = itemView.findViewById(R.id.msg);
            time = itemView.findViewById(R.id.msg_time);
        }
    }

}
