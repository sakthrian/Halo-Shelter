package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.Model.PropertyDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class PostPropertyActivity extends AppCompatActivity {

    Spinner smoking_spinner,drinking_spinner,overnight_guest_spinner,food_habit_spinner;
    EditText contact_no_et,location_et,rent_et,duration_et;
    CheckBox family_cb,student_cb,batchelor_cb,couples_cb;
    TextView add_requirement_tv_btn;
    Switch show_contact;
    Button submit;
    ImageView propertyIMG;
    Uri imageURI;
    ProgressBar progressBar;

    String name,age,bio,gender,profilepic,family,couples,student,batchelor,location,rent,contact_no,duration;

    String smoking,drinking,overnight_guests,food_habits,phone,uid,propertypic;

    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property);

        smoking_spinner= findViewById(R.id.smoking_spinner);
        drinking_spinner=findViewById(R.id.drinking_spinner);
        overnight_guest_spinner=findViewById(R.id.overnight_guest_spinner);
        food_habit_spinner=findViewById(R.id.food_habits_spinner);

        family_cb=findViewById(R.id.family_checkbox);
        couples_cb=findViewById(R.id.couples_checkbox);
        batchelor_cb=findViewById(R.id.batchelor_checkbox);
        student_cb=findViewById(R.id.student_checkbox);

        contact_no_et=findViewById(R.id.contact_no_pp);

        location_et=findViewById(R.id.property_location);
        rent_et=findViewById(R.id.property_rent_amount);
        duration_et=findViewById(R.id.stay_duration);

        show_contact=findViewById(R.id.show_contact_switch_pp);

        submit=findViewById(R.id.post_property_submit_btn);


        propertyIMG=findViewById(R.id.property_image_upload);

        progressBar=findViewById(R.id.post_property_progressbar);

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
                        contact_no_et.setText(phone);
                        name=String.valueOf(dataSnapshot.child("userName").getValue());
                        age=String.valueOf(dataSnapshot.child("age").getValue());
                        bio=String.valueOf(dataSnapshot.child("bio").getValue());
                        gender=String.valueOf(dataSnapshot.child("gender").getValue());
                        profilepic=String.valueOf(dataSnapshot.child("profile_pic").getValue());


                    }
                }
            }
        });


        ArrayList<String> preference1_array=new ArrayList<>();
        preference1_array.add("Allowed");
        preference1_array.add("Not Allowed");
        preference1_array.add("Occasionally Allowed");

        ArrayList<String> preference2_array=new ArrayList<>();
        preference2_array.add("Veg");
        preference2_array.add("Non-Veg");

        smoking_spinner.setAdapter(new ArrayAdapter<>(PostPropertyActivity.this, android.R.layout.simple_spinner_dropdown_item,preference1_array));
        smoking_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                smoking=adapterView.getItemAtPosition(i).toString();
                // Toast.makeText(NeedRoomActivity.this, make_team, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        drinking_spinner.setAdapter(new ArrayAdapter<>(PostPropertyActivity.this, android.R.layout.simple_spinner_dropdown_item,preference1_array));
        drinking_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                drinking=adapterView.getItemAtPosition(i).toString();
                // Toast.makeText(NeedRoomActivity.this, make_team, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        overnight_guest_spinner.setAdapter(new ArrayAdapter<>(PostPropertyActivity.this, android.R.layout.simple_spinner_dropdown_item,preference1_array));
        overnight_guest_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                overnight_guests=adapterView.getItemAtPosition(i).toString();
                // Toast.makeText(NeedRoomActivity.this, make_team, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        food_habit_spinner.setAdapter(new ArrayAdapter<>(PostPropertyActivity.this, android.R.layout.simple_spinner_dropdown_item,preference2_array));
        food_habit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               food_habits=adapterView.getItemAtPosition(i).toString();
                // Toast.makeText(NeedRoomActivity.this, make_team, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                location=location_et.getText().toString();
                rent=rent_et.getText().toString();
                duration=duration_et.getText().toString();
                if(show_contact.isChecked()){
                    contact_no=contact_no_et.getText().toString();}
                else{
                    contact_no="**********";
                }
                if(family_cb.isChecked()){
                    family="yes";}
                else{
                    family="no";
                }
                if(student_cb.isChecked()){
                    student="yes";}
                else{
                    student="no";
                }
                if(batchelor_cb.isChecked()){
                    batchelor="yes";}
                else{
                    batchelor="no";
                }
                if(couples_cb.isChecked()){
                    couples="yes";}
                else{
                    couples="no";
                }

                if(TextUtils.isEmpty(location)){
                    Toast.makeText(PostPropertyActivity.this, "location is needed", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(rent)){
                    Toast.makeText(PostPropertyActivity.this, "enter rent", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(duration)){
                    Toast.makeText(PostPropertyActivity.this, "enter duration of stay", Toast.LENGTH_SHORT).show();
                }else{

                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Properties"). child(uid);

                    StorageReference storageReference= FirebaseStorage.getInstance().getReference("properties").child(uid);

                    if(imageURI!=null){
                        progressBar.setVisibility(View.VISIBLE);
                        storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if(task.isSuccessful()){
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            propertypic=uri.toString();
                                            PropertyDetails PropertyDetails=new PropertyDetails(uid,name,bio,age,gender,contact_no,profilepic,location,rent,propertypic,family,batchelor,student,couples,duration,smoking,drinking,overnight_guests,food_habits);
                                            reference.setValue(PropertyDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        home_page.POST_PROPERTY_DONE=true;
                                                        Intent intent=new Intent(PostPropertyActivity.this,SeeMatchesActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }else {

                                                        Toast.makeText(PostPropertyActivity.this, "error occurred", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }else{
                        Toast.makeText(PostPropertyActivity.this, "property image must be uploaded", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
        propertyIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if(data!=null){
                imageURI=data.getData();
                propertyIMG.setImageURI(imageURI);
            }
        }
    }
}