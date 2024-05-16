package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class EditNeedRoomActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private Spinner occupency_spinner, gender_requirement_spinner, making_team_spinner;
    EditText contact_no_et_er, location_et_er, rent_et_er;
    Switch show_contact;
    Button update;

    String location, rent, contact_no, uid, name, age, gender, bio, profilepic;

    private String occupency_selected, gender_required, make_team, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_need_room);

        gender_requirement_spinner = findViewById(R.id.gender_requirement_spinner_editroom);
        occupency_spinner = findViewById(R.id.occupancy_spinner_eitroom);
        making_team_spinner = findViewById(R.id.making_team_spinner_editroom);

        contact_no_et_er = findViewById(R.id.contact_no_editroom);
        location_et_er = findViewById(R.id.room_location_editroom);
        rent_et_er = findViewById(R.id.room_rent_amount_editroom);

        show_contact = findViewById(R.id.show_contact_switch_editroom);

        update = findViewById(R.id.need_room_update_btn);

        if (user != null) {
            uid = user.getUid();
            //Toast.makeText(EditNeedRoomActivity.this, uid, Toast.LENGTH_SHORT).show();}


            DatabaseReference referenceuser = FirebaseDatabase.getInstance().getReference("users");
            // StorageReference storageReference= FirebaseStorage.getInstance().getReference("upload").child(uid);

            referenceuser.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();

                            phone = String.valueOf(dataSnapshot.child("phone_no").getValue());
                            contact_no_et_er.setText(phone);

                        }
                    }
                }
            });

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RoomMates");

            reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            name = String.valueOf(dataSnapshot.child("name").getValue());
                            age = String.valueOf(dataSnapshot.child("age").getValue());
                            gender = String.valueOf(dataSnapshot.child("gender").getValue());
                            bio = String.valueOf(dataSnapshot.child("bio").getValue());
                            contact_no = String.valueOf(dataSnapshot.child("contact").getValue());
                            profilepic = String.valueOf(dataSnapshot.child("profile").getValue());
                            location = String.valueOf(dataSnapshot.child("location").getValue());
                            gender_required = String.valueOf(dataSnapshot.child("looking_for").getValue());
                            occupency_selected = String.valueOf(dataSnapshot.child("occupancy").getValue());
                            rent = String.valueOf(dataSnapshot.child("rent").getValue());
                            make_team = String.valueOf(dataSnapshot.child("teams").getValue());
                            // profilepic=String.valueOf(dataSnapshot.child("profile_pic").getValue());


                            //  Toast.makeText(EditNeedRoomActivity.this, gender+name+age+uid, Toast.LENGTH_SHORT).show();


                            location_et_er.setText(location);
                            rent_et_er.setText(rent);


                            if (occupency_selected.matches("Single"))
                                occupency_spinner.setSelection(0);
                            else if (occupency_selected.matches("Shared"))
                                occupency_spinner.setSelection(1);
                            else
                                occupency_spinner.setSelection(2);


                            if (gender_required.equals("Male"))
                                gender_requirement_spinner.setSelection(0);
                            else if (gender_required.equals("Female"))
                                gender_requirement_spinner.setSelection(1);
                            else
                                gender_requirement_spinner.setSelection(2);

                            if(make_team.equals("Yes"))
                                making_team_spinner.setSelection(0);
                            else
                                making_team_spinner.setSelection(1);

                            if(contact_no.equals("**********"))
                                show_contact.setChecked(false);


                        }
                    }
                }
            });


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(EditProfileActivity.this, profilepic, Toast.LENGTH_LONG).show();

                    location=location_et_er.getText().toString();
                    rent=rent_et_er.getText().toString();
                    if(show_contact.isChecked()){
                        contact_no=contact_no_et_er.getText().toString();}
                    else{
                        contact_no="**********";
                    }

                    HashMap user=new HashMap();
                    user.put("rent",rent);
                    user.put("location",location);
                    user.put("occupancy",occupency_selected);
                    user.put("looking_for",gender_required);
                    user.put("contact",contact_no);
                    user.put("teams",make_team);

                    reference.child(uid).updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                Intent intent=new Intent(EditNeedRoomActivity.this,SeeMatchesActivity.class);
                             //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                                Toast.makeText(EditNeedRoomActivity.this, "updated", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(EditNeedRoomActivity.this, "failed to update ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });


            ArrayList<String> make_team_array = new ArrayList<>();
            make_team_array.add("Yes");
            make_team_array.add("No");

            ArrayList<String> occupency_array = new ArrayList<>();
            occupency_array.add("Single");
            occupency_array.add("Shared");
            occupency_array.add("Any");

            ArrayList<String> gender_requeired_array = new ArrayList<>();
            gender_requeired_array.add("Male");
            gender_requeired_array.add("Female");
            gender_requeired_array.add("Any");


            making_team_spinner.setAdapter(new ArrayAdapter<>(EditNeedRoomActivity.this, android.R.layout.simple_spinner_dropdown_item, make_team_array));
            making_team_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    make_team = adapterView.getItemAtPosition(i).toString();
                    // Toast.makeText(NeedRoomActivity.this, make_team, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            gender_requirement_spinner.setAdapter(new ArrayAdapter<>(EditNeedRoomActivity.this, android.R.layout.simple_spinner_dropdown_item, gender_requeired_array));
            gender_requirement_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    gender_required = adapterView.getItemAtPosition(i).toString();
                    // Toast.makeText(NeedRoomActivity.this, gender_required, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            occupency_spinner.setAdapter(new ArrayAdapter<>(EditNeedRoomActivity.this, android.R.layout.simple_spinner_dropdown_item, occupency_array));
            occupency_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    occupency_selected = adapterView.getItemAtPosition(i).toString();
                    // Toast.makeText(NeedRoomActivity.this, occupency_selected, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        }
    }
}