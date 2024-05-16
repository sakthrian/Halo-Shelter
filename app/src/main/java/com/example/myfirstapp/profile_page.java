package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.databinding.FragmentProfilePageBinding;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile_page extends Fragment {


    private TextView name_tv,bio_tv,age_tv,gender_tv,phone_tv;
    ProgressBar load;
    private String name,bio,age,gender,phone,profilepic,uid;
    CircleImageView userImg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_profile_page, container, false);
        Button Edit_profile=v.findViewById(R.id.create_profile);
        name_tv=v.findViewById(R.id.name_view);
        bio_tv=v.findViewById(R.id.bio_view);
        age_tv=v.findViewById(R.id.age_view);
        gender_tv=v.findViewById(R.id.gender_view);
        phone_tv=v.findViewById(R.id.ph_no_view);
        load=v.findViewById(R.id.image_load);


        //userImg=v.findViewById(R.id.circleImageView);



        Edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),EditProfileActivity.class);
                startActivity(intent);

            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userImg=getActivity().findViewById(R.id.circleImageViewProfile);
    }

    @Override
    public void onStart() {
        super.onStart();


        Bundle data=getArguments();

        if (data!=null){
            name=data.getString("name");
            age=data.getString("age");
            bio=data.getString("bio");
            gender=data.getString("gender");
            phone=data.getString("phone");
            profilepic=data.getString("profilepic");
            uid=data.getString("userID");
            //Toast.makeText(getActivity(), profilepic, Toast.LENGTH_SHORT).show();
        }

        Picasso.get().load(profilepic).into(userImg);
        name_tv.setText(name);
        bio_tv.setText(bio);
        gender_tv.setText(gender);
        age_tv.setText(age);
        phone_tv.setText(phone);
    }



}