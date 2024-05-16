package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SeeMatchesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_matches);

        getSupportFragmentManager().beginTransaction().replace(R.id.see_match_frame_layout, new RoomsFragment()).commit();
        BottomNavigationView bottomNavigationView=findViewById(R.id.see_matches_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNav);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.rooms_menu:
                    getSupportFragmentManager().beginTransaction().replace(R.id.see_match_frame_layout, new RoomsFragment()).commit();
                    break;
                case R.id.roommate_menu:
                    getSupportFragmentManager().beginTransaction().replace(R.id.see_match_frame_layout, new RoommateFragment()).commit();
                    break;
                case R.id.property_menu:
                    getSupportFragmentManager().beginTransaction().replace(R.id.see_match_frame_layout, new PropertyFragment()).commit();
                    break;
            }

            return true;
        }
    };
}