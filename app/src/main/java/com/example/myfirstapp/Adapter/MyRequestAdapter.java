package com.example.myfirstapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfirstapp.DetailTeamMateActivity;
import com.example.myfirstapp.R;
import com.example.myfirstapp.Model.RequestDetails;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyRequestAdapter extends RecyclerView.Adapter<MyRequestViewHolder> {

    private Context context;
    private List<RequestDetails> dataList;

    public MyRequestAdapter(Context context, List<RequestDetails> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_request_item,parent,false);

        return new MyRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getProfile()).into(holder.recRequestImage);
        holder.name.setText(dataList.get(position).getName());


        holder.recCardRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, DetailTeamMateActivity.class);
                intent.putExtra("profile",dataList.get(holder.getAdapterPosition()).getProfile());
                intent.putExtra("name",dataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("rent",dataList.get(holder.getAdapterPosition()).getRent());
                intent.putExtra("location",dataList.get(holder.getAdapterPosition()).getLocation());
                intent.putExtra("gender",dataList.get(holder.getAdapterPosition()).getGender());
                intent.putExtra("looking_for",dataList.get(holder.getAdapterPosition()).getLooking_for());
                intent.putExtra("occupancy",dataList.get(holder.getAdapterPosition()).getOccupancy());
                intent.putExtra("description",dataList.get(holder.getAdapterPosition()).getBio());
                intent.putExtra("contact",dataList.get(holder.getAdapterPosition()).getContact());
                intent.putExtra("age",dataList.get(holder.getAdapterPosition()).getAge());
                //intent.putExtra("roomImage",dataList.get(holder.getAdapterPosition()).getRoom_image());
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

class MyRequestViewHolder extends RecyclerView.ViewHolder{

    CircleImageView recRequestImage;
    TextView name;
    CardView recCardRequest;

    public MyRequestViewHolder(@NonNull View itemView) {
        super(itemView);

        recRequestImage=itemView.findViewById(R.id.recrequestImage);
        recCardRequest=itemView.findViewById(R.id.recCardRequest);
        name=itemView.findViewById(R.id.rec_request_name);

    }
}
