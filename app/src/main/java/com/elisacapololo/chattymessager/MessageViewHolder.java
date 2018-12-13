package com.elisacapololo.chattymessager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageViewHolder extends RecyclerView.ViewHolder {

      TextView userName;
      TextView userMessage;
      ImageView perfil;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.textName);
        userMessage = itemView.findViewById(R.id.textMessage);
        perfil = itemView.findViewById(R.id.profile_image);
    }
}
