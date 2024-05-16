package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myfirstapp.Utility.NetworkChangeListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    static boolean BACK_PRESSED_ONCE=false;

    NetworkChangeListener networkChangeListener=new NetworkChangeListener();

   public String name,age,gender,phone,bio,password,setup_done,profilepic;
   FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
   String uid;

   static Boolean SETUP_DONE=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startService(new Intent(getBaseContext(), BreakService.class));


        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        profilepic=intent.getStringExtra("profile");

            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("users");
            reference.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult().exists()){
                            SETUP_DONE=true;
                            DataSnapshot dataSnapshot=task.getResult();
                            name=String.valueOf(dataSnapshot.child("userName").getValue());
                            name=name.toUpperCase();
                            Toast.makeText(MainActivity.this, "verify your profile in profile page", Toast.LENGTH_SHORT).show();
                            age=String.valueOf(dataSnapshot.child("age").getValue());
                            gender=String.valueOf(dataSnapshot.child("gender").getValue());
                            bio=String.valueOf(dataSnapshot.child("bio").getValue());
                            phone=String.valueOf(dataSnapshot.child("phone_no").getValue());
                            profilepic=String.valueOf(dataSnapshot.child("profile_pic").getValue());
                            setup_done=String.valueOf(dataSnapshot.child("setup_done").getValue());
                            //Toast.makeText(MainActivity.this, setup_done, Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            });


       ToHome();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
       // bottomNavigationView.setSelectedItemId(R.id.profile_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNav);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener onNav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home_bottom:
                    ToHome();
                    //  getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new home_page).commit();
                    break;
                case R.id.profile_bottom:
                    if(SETUP_DONE){
                        ToProfile();
                    }else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new setup_profile()).commit();
                    }

                    //getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new profile_page()).commit();
                    break;
                case R.id.notification_bottom:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new notification_page()).commit();
                    break;
                case R.id.more_bottom:
                    ToMore();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new more_page()).commit();
                    break;
            }

                return true;
        }
    };

    private void ToHome() {
        home_page homePage=new home_page();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle data =new Bundle();
        data.putString("name",name);
        data.putString("age",age);
        data.putString("gender",gender);
        data.putString("bio",bio);
        data.putString("phone",phone);
        data.putString("profilepic",profilepic);
        data.putString("userID",uid);
        homePage.setArguments(data);
        fragmentTransaction.replace(R.id.frame_layout,homePage).commit();
    }
    private void ToMore() {
        more_page morePage=new more_page();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle data =new Bundle();
        data.putString("name",name);
        data.putString("phone",phone);
        data.putString("profilepic",profilepic);
        data.putString("userID",uid);
        morePage.setArguments(data);
        fragmentTransaction.replace(R.id.frame_layout,morePage).commit();
    }
    private void ToProfile() {
        profile_page profilePage=new profile_page();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle data =new Bundle();
        data.putString("name",name);
        data.putString("age",age);
        data.putString("gender",gender);
        data.putString("bio",bio);
        data.putString("phone",phone);
        data.putString("profilepic",profilepic);
        data.putString("userID",uid);

        profilePage.setArguments(data);
        fragmentTransaction.replace(R.id.frame_layout,profilePage).commit();
    }


    @Override
    protected void onStart() {

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();

        if(user==null)
        {
            Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        }else{
            uid=user.getUid();
        }
    }

    @Override
    protected void onPause() {
        stopService(new Intent(getBaseContext(), BreakService.class));
        super.onPause();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if(BACK_PRESSED_ONCE){
            super.onBackPressed();
        }else {
            BACK_PRESSED_ONCE=true;
            Toast.makeText(getApplicationContext(), "press back again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    BACK_PRESSED_ONCE=false;
                }
            },2000);
        }
    }
}