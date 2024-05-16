package com.example.myfirstapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfirstapp.MessageActivity;
import com.example.myfirstapp.R;
import com.example.myfirstapp.Model.RequestDetails;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private Context context;
    private List<RequestDetails> dataList;

    public UserAdapter(Context context, List<RequestDetails> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getProfile()).into(holder.chatUserImage);
        holder.name_chat.setText(dataList.get(position).getName());


        holder.recCardChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, MessageActivity.class);
                intent.putExtra("profile",dataList.get(holder.getAdapterPosition()).getProfile());
                intent.putExtra("name",dataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("uid",dataList.get(holder.getAdapterPosition()).getUid());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class UserViewHolder extends RecyclerView.ViewHolder{

    CircleImageView chatUserImage;
    TextView name_chat;
    RelativeLayout recCardChat;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        chatUserImage=itemView.findViewById(R.id.chat_profile_image);
        recCardChat=itemView.findViewById(R.id.user_item);
        name_chat=itemView.findViewById(R.id.chat_username);

    }
}
