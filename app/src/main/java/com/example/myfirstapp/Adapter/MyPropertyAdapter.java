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
import com.example.myfirstapp.DetailPropertyActivity;
import com.example.myfirstapp.Model.PropertyDetails;
import com.example.myfirstapp.R;

import java.util.List;

public class MyPropertyAdapter extends RecyclerView.Adapter<MyPropertyViewHolder> {

    private Context context;
    private List<PropertyDetails> dataList;

    public MyPropertyAdapter(Context context, List<PropertyDetails> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyPropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_room_item,parent,false);

        return new MyPropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPropertyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getProfile()).into(holder.recRoomImage);
        holder.name.setText(dataList.get(position).getName());
        holder.rent.setText(dataList.get(position).getRent());
        holder.location.setText(dataList.get(position).getLocation());
        // holder.gender.setText(dataList.get(position).getGender());

        holder.recCardRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, DetailPropertyActivity.class);
                intent.putExtra("profile",dataList.get(holder.getAdapterPosition()).getProfile());
                intent.putExtra("name",dataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("rent",dataList.get(holder.getAdapterPosition()).getRent());
                intent.putExtra("location",dataList.get(holder.getAdapterPosition()).getLocation());
                intent.putExtra("gender",dataList.get(holder.getAdapterPosition()).getGender());
                intent.putExtra("family",dataList.get(holder.getAdapterPosition()).getFamily());
                intent.putExtra("couples",dataList.get(holder.getAdapterPosition()).getCouples());
                intent.putExtra("description",dataList.get(holder.getAdapterPosition()).getBio());
                intent.putExtra("contact",dataList.get(holder.getAdapterPosition()).getContact());
                intent.putExtra("age",dataList.get(holder.getAdapterPosition()).getAge());
                intent.putExtra("batchelor",dataList.get(holder.getAdapterPosition()).getBatchelor());
                intent.putExtra("student",dataList.get(holder.getAdapterPosition()).getStudent());
                intent.putExtra("duration",dataList.get(holder.getAdapterPosition()).getDuration());
                intent.putExtra("smoking",dataList.get(holder.getAdapterPosition()).getSmoking());
                intent.putExtra("drinking",dataList.get(holder.getAdapterPosition()).getDrinking());
                intent.putExtra("overnight_guests",dataList.get(holder.getAdapterPosition()).getOvernight_guests());
                intent.putExtra("food_habits",dataList.get(holder.getAdapterPosition()).getFood_habit());
                intent.putExtra("roomImage",dataList.get(holder.getAdapterPosition()).getProperty_image());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class MyPropertyViewHolder extends RecyclerView.ViewHolder{

    ImageView recRoomImage;
    TextView name,rent,location,gender,looking_for;
    CardView recCardRoom;

    public MyPropertyViewHolder(@NonNull View itemView) {
        super(itemView);

        recRoomImage=itemView.findViewById(R.id.recRommImage);
        recCardRoom=itemView.findViewById(R.id.recCardRoom);
        location=itemView.findViewById(R.id.rec_room_location);
        looking_for=itemView.findViewById(R.id.rec_room_genderRequired);
        name=itemView.findViewById(R.id.rec_room_name);
        rent=itemView.findViewById(R.id.rec_room_rent);

    }
}
