package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.Model.RoomMateDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NeedRoomActivity extends AppCompatActivity {

    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    private Spinner occupency_spinner,gender_requirement_spinner,making_team_spinner ;
    EditText contact_no_et,location_et,rent_et;
    TextView add_room_tv_btn;
    Switch show_contact;
    Button submit;

    String location,rent,contact_no,uid,name,age,gender,bio,profilepic;

    private String occupency_selected,gender_required,make_team,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_room);

        gender_requirement_spinner= findViewById(R.id.gender_requirement_spinner);
        occupency_spinner=findViewById(R.id.occupancy_spinner);
        making_team_spinner=findViewById(R.id.making_team_spinner);

        contact_no_et=findViewById(R.id.contact_no);
        location_et=findViewById(R.id.room_location);
        rent_et=findViewById(R.id.room_rent_amount);

        show_contact=findViewById(R.id.show_contact_switch);

        submit=findViewById(R.id.need_room_submit_btn);

        add_room_tv_btn=findViewById(R.id.add_room_tv);

        if(user!=null){
         uid=user.getUid();}

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");

        reference.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot=task.getResult();

                        phone=String.valueOf(dataSnapshot.child("phone_no").getValue());
                        name=String.valueOf(dataSnapshot.child("userName").getValue());
                        age=String.valueOf(dataSnapshot.child("age").getValue());
                        bio=String.valueOf(dataSnapshot.child("bio").getValue());
                        gender=String.valueOf(dataSnapshot.child("gender").getValue());
                        profilepic=String.valueOf(dataSnapshot.child("profile_pic").getValue());
                        contact_no_et.setText(phone);

                    }
                }
            }
        });

        ArrayList<String> make_team_array=new ArrayList<>();
        make_team_array.add("Yes");
        make_team_array.add("No");

        ArrayList<String> occupency_array=new ArrayList<>();
        occupency_array.add("Single");
        occupency_array.add("Shared");
        occupency_array.add("Any");

        ArrayList<String> gender_requeired_array=new ArrayList<>();
        gender_requeired_array.add("Male");
        gender_requeired_array.add("Female");
        gender_requeired_array.add("Any");



        making_team_spinner.setAdapter(new ArrayAdapter<>(NeedRoomActivity.this, android.R.layout.simple_spinner_dropdown_item,make_team_array));
        making_team_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               make_team=adapterView.getItemAtPosition(i).toString();
                // Toast.makeText(NeedRoomActivity.this, make_team, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gender_requirement_spinner.setAdapter(new ArrayAdapter<>(NeedRoomActivity.this, android.R.layout.simple_spinner_dropdown_item,gender_requeired_array));
        gender_requirement_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender_required=adapterView.getItemAtPosition(i).toString();
               // Toast.makeText(NeedRoomActivity.this, gender_required, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        occupency_spinner.setAdapter(new ArrayAdapter<>(NeedRoomActivity.this, android.R.layout.simple_spinner_dropdown_item,occupency_array));
        occupency_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                occupency_selected=adapterView.getItemAtPosition(i).toString();
               // Toast.makeText(NeedRoomActivity.this, occupency_selected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        add_room_tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NeedRoomActivity.this,NeedRoommateActivity.class);
                startActivity(intent);
                finish();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                location=location_et.getText().toString();
                rent=rent_et.getText().toString();
                if(show_contact.isChecked()){
                    contact_no=contact_no_et.getText().toString();}
                else{
                    contact_no="**********";
                }

                if(TextUtils.isEmpty(location)){
                    Toast.makeText(NeedRoomActivity.this, "location is needed", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(rent)){
                    Toast.makeText(NeedRoomActivity.this, "enter rent", Toast.LENGTH_SHORT).show();
                } else{
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("RoomMates"). child(uid);

                    RoomMateDetails RoomMateDetails=new RoomMateDetails(uid,name,bio,age,gender,contact_no,profilepic,location,rent,occupency_selected,gender_required,make_team);
                    reference.setValue(RoomMateDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                home_page.NEED_ROOM_DONE=true;
                                Intent intent=new Intent(NeedRoomActivity.this,SeeMatchesActivity.class);
                                startActivity(intent);
                                finish();

                            }else {

                                Toast.makeText(NeedRoomActivity.this, "error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}