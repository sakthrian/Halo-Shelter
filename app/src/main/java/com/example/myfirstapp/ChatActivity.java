package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportFragmentManager().beginTransaction().replace(R.id.chat_frame_layout, new ChatsFragment()).commit();
        BottomNavigationView bottomNavigationView=findViewById(R.id.chat_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNav);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.chat_bottom:
                    getSupportFragmentManager().beginTransaction().replace(R.id.chat_frame_layout, new ChatsFragment()).commit();
                    break;
                case R.id.friends_bottom:
                    getSupportFragmentManager().beginTransaction().replace(R.id.chat_frame_layout, new UsersFragment()).commit();
                    break;
            }

            return true;
        }
    };
}