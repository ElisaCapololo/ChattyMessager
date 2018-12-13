package com.elisacapololo.chattymessager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignedInActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseAuth auth;
    EditText editTextMessage;
    String username;
    String userphoto;
    RecyclerView recyclerViewMessageList;
    FirebaseRecyclerAdapter<Message, MessageViewHolder> firebaseAdaptar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        editTextMessage = findViewById(R.id.edit_message);
        username = auth.getCurrentUser().getDisplayName();

        recyclerViewMessageList = findViewById(R.id.recyclerViewListMessage);
        recyclerViewMessageList.setLayoutManager(new LinearLayoutManager(this));

        initLoadData();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_send);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Message message = new Message(editTextMessage.getText().toString(),username,userphoto, null);
              databaseReference.child("messages").push().setValue(message);
              editTextMessage.setText("");
            }
        });
    }

    public void initLoadData() {
        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(databaseReference.child("messages"), Message.class)
                        .build();

        firebaseAdaptar = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message model) {
              holder.userName.setText(model.getName());
              holder.userMessage.setText(model.getText());
            }

            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new MessageViewHolder(inflater.inflate(R.layout.item_message, viewGroup, false));
            }
        };
        recyclerViewMessageList.setAdapter(firebaseAdaptar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAdaptar.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAdaptar.startListening();
    }
}
