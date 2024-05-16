package com.example.myfirstapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfirstapp.DetailRoomMateActivity;
import com.example.myfirstapp.R;
import com.example.myfirstapp.Model.RoomMateDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MyRoommateAdapter extends RecyclerView.Adapter<MyRoommateViewHolder> {

    private Context context;
    private List<RoomMateDetails> dataList;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    public MyRoommateAdapter(Context context, List<RoomMateDetails> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyRoommateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_room_item,parent,false);

        return new MyRoommateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRoommateViewHolder holder, int position) {
        String uid=dataList.get(position).getUid();

        Glide.with(context).load(dataList.get(position).getProfile()).into(holder.recRoomImage);
        holder.name.setText(dataList.get(position).getName());
        holder.rent.setText(dataList.get(position).getRent());
        holder.location.setText(dataList.get(position).getLocation());
        // holder.gender.setText(dataList.get(position).getGender());
        holder.looking_for.setText(dataList.get(position).getLooking_for());

        holder.recCardRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, DetailRoomMateActivity.class);
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
                intent.putExtra("team",dataList.get(holder.getAdapterPosition()).getTeams());
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

class MyRoommateViewHolder extends RecyclerView.ViewHolder{

    ImageView recRoomImage;
    TextView name,rent,location,gender,looking_for;
    CardView recCardRoom;

    public MyRoommateViewHolder(@NonNull View itemView) {
        super(itemView);

        recRoomImage=itemView.findViewById(R.id.recRommImage);
        recCardRoom=itemView.findViewById(R.id.recCardRoom);
        location=itemView.findViewById(R.id.rec_room_location);
        looking_for=itemView.findViewById(R.id.rec_room_genderRequired);
        name=itemView.findViewById(R.id.rec_room_name);
        rent=itemView.findViewById(R.id.rec_room_rent);

    }
}

