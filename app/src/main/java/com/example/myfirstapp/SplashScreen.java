package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    TextView haloshelter_text,no_internet;
    ProgressBar progressBar;

    FirebaseUser user;
    String uid;

    String name,profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        no_internet=findViewById(R.id.no_network_tv);
        progressBar=findViewById(R.id.progress_splash);

        try {
          user  = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
            reference.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult().exists()){
                            DataSnapshot dataSnapshot=task.getResult();
                            name=String.valueOf(dataSnapshot.child("userName").getValue());
                            name=name.toUpperCase();
                            profilepic=String.valueOf(dataSnapshot.child("profile_pic").getValue());
                      /*  Toast.makeText(MainActivity.this, "verify your profile in profile page", Toast.LENGTH_SHORT).show();
                        age=String.valueOf(dataSnapshot.child("age").getValue());
                        gender=String.valueOf(dataSnapshot.child("gender").getValue());
                        bio=String.valueOf(dataSnapshot.child("bio").getValue());
                        phone=String.valueOf(dataSnapshot.child("phone_no").getValue());

                        setup_done=String.valueOf(dataSnapshot.child("setup_done").getValue());
                        //Toast.makeText(MainActivity.this, setup_done, Toast.LENGTH_SHORT).show();*/

                        }
                    }
                }
            });

        }catch (Exception e){
           // Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        lottieAnimationView=findViewById(R.id.lottie_building_animation);
        lottieAnimationView.animate().setStartDelay(4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(isConnected()){
                    Intent intent=new Intent(SplashScreen.this,RegisterActivity.class);
                    intent.putExtra("name",name);
                    intent.putExtra("profile",profilepic);
                    startActivity(intent);
                    finish();

                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    no_internet.setVisibility(View.VISIBLE);
                }

            }
        },5000);

    }

    boolean isConnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null){
            if(networkInfo.isConnected())
                return true;
            else
                return false;

        }else
            return false;
    }
}