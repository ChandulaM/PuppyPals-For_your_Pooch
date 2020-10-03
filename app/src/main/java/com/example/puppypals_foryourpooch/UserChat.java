package com.example.puppypals_foryourpooch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puppypals_foryourpooch.model.Chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserChat extends AppCompatActivity {

    RecyclerView recyclerView;
    MessageAdapter msgAdapter;
    TextView header;
    ImageView pic;
    EditText type_message;
    Button send;
    DatabaseReference chatRef, friendChat, userRef;
    FirebaseAuth fAuth;
    List<Chat> chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        final String friendId = getIntent().getStringExtra("uId");
        recyclerView = findViewById(R.id.msg_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        header = findViewById(R.id.userchat_header);
        pic = findViewById(R.id.chat_prPic);
        type_message = findViewById(R.id.chat_msg_type);
        send = findViewById(R.id.btn_send);
        chats = new ArrayList<>();
        fAuth = FirebaseAuth.getInstance();

        Log.d("TAG", "r Uid: " + friendId);
        chatRef = FirebaseDatabase.getInstance().getReference()
                .child("Chats").child(fAuth.getUid()).child(friendId);

        userRef = FirebaseDatabase.getInstance().getReference().child("User").child(friendId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("imgUrl").getValue() != null){
                    header.setText(snapshot.child("username").getValue()
                    .toString());
                    Picasso.with(getApplicationContext())
                            .load(snapshot.child("imgUrl")
                            .getValue().toString())
                            .fit()
                            .centerCrop()
                            .into(pic);
                }else{
                    header.setText(snapshot.child("username").getValue()
                            .toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserChat.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(chats != null)
                    chats.clear();
                if (snapshot.hasChildren()){
                    for (DataSnapshot snap : snapshot.getChildren()){
                        Log.d("SNA", "onDataChange: " + snap.child("message").getValue().toString());
                        Chat chat = new Chat();
                        chat.setSenderId(snap.child("senderId").getValue().toString());
                        chat.setMessage(snap.child("message").getValue().toString());
                        chat.setTime(snap.child("time").getValue().toString());
                        chats.add(chat);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserChat.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String id = snapshot.child(chatRef.push().getKey()).toString();
                Log.d("TAG", "onChildAdded: " + snapshot.child(chatRef.push().getKey()));
                for(DataSnapshot snap : snapshot.child(id).getChildren()){
                    Chat chat = new Chat();
                    chat.setSenderId(snap.child("senderId").getValue().toString());
                    chat.setMessage(snap.child("message").getValue().toString());
                    chat.setTime(snap.child("time").getValue().toString());
                    chats.add(chat);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("a", "msg: "+ chats.size());
        msgAdapter = new MessageAdapter(getApplicationContext(), chats);
        recyclerView.setAdapter(msgAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String your_message = type_message.getText().toString();
                String chatId = chatRef.push().getKey();
                Chat userChat = new Chat(fAuth.getUid(), chatId,
                        your_message, getCurrentTime());

                chatRef.child(chatId).setValue(userChat).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        type_message.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserChat.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                chats.add(userChat);
                Log.d("a", "msg: "+ chats.size());
                msgAdapter = new MessageAdapter(getApplicationContext(), chats);
                recyclerView.setAdapter(msgAdapter);

                friendChat = FirebaseDatabase.getInstance().getReference().child("Chats")
                        .child(friendId).child(fAuth.getUid());
                friendChat.child(chatId).setValue(userChat);
            }
        });
    }

    private String getCurrentTime(){
        Long currentTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        Date date = new Date(currentTime);
        String time = simpleDateFormat.format(date);
        return time;
    }
}