package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class EditPropertyActivity extends AppCompatActivity {
    private static final String TAG="EditPropertyActivity";

    Spinner smoking_spinner,drinking_spinner,overnight_guest_spinner,food_habit_spinner;
    EditText contact_no_et,location_et,rent_et,duration_et;
    CheckBox family_cb,student_cb,batchelor_cb,couples_cb;
    TextView add_requirement_tv_btn;
    Switch show_contact;
    Button update,delete;
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
        setContentView(R.layout.activity_edit_property);
        smoking_spinner= findViewById(R.id.smoking_spinner_ep);
        drinking_spinner=findViewById(R.id.drinking_spinner_ep);
        overnight_guest_spinner=findViewById(R.id.overnight_guest_spinner_ep);
        food_habit_spinner=findViewById(R.id.food_habits_spinner_ep);

        family_cb=findViewById(R.id.family_checkbox_ep);
        couples_cb=findViewById(R.id.couples_checkbox_ep);
        batchelor_cb=findViewById(R.id.batchelor_checkbox_ep);
        student_cb=findViewById(R.id.student_checkbox_ep);

        contact_no_et=findViewById(R.id.contact_no_ep);

        location_et=findViewById(R.id.property_location_ep);
        rent_et=findViewById(R.id.property_rent_amount_ep);
        duration_et=findViewById(R.id.stay_duration_ep);

        show_contact=findViewById(R.id.show_contact_switch_ep);

        update=findViewById(R.id.post_property_update_btn);
        delete=findViewById(R.id.post_property_delete_btn);
        delete.setBackgroundTintList(ContextCompat.getColorStateList(EditPropertyActivity.this,R.color.delete_button_color));


        propertyIMG=findViewById(R.id.property_image_upload_ep);

        progressBar=findViewById(R.id.post_property_progressbar_ep);

        if(user!=null){
            uid=user.getUid();}

        DatabaseReference referenceuser= FirebaseDatabase.getInstance().getReference("users");

        referenceuser.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot=task.getResult();

                        phone=String.valueOf(dataSnapshot.child("phone_no").getValue());
                        contact_no_et.setText(phone);

                    }
                }
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Properties");

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
                        propertypic = String.valueOf(dataSnapshot.child("property_image").getValue());
                        family = String.valueOf(dataSnapshot.child("family").getValue());
                        couples = String.valueOf(dataSnapshot.child("couples").getValue());
                        batchelor = String.valueOf(dataSnapshot.child("batchelor").getValue());
                        student = String.valueOf(dataSnapshot.child("student").getValue());
                        rent = String.valueOf(dataSnapshot.child("rent").getValue());
                        duration = String.valueOf(dataSnapshot.child("duration").getValue());
                        smoking=String.valueOf(dataSnapshot.child("smoking").getValue());
                        drinking=String.valueOf(dataSnapshot.child("drinking").getValue());
                        overnight_guests=String.valueOf(dataSnapshot.child("overnight_guests").getValue());
                        food_habits=String.valueOf(dataSnapshot.child("food_habit").getValue());


                        //  Toast.makeText(EditNeedRoomActivity.this, gender+name+age+uid, Toast.LENGTH_SHORT).show();


                        location_et.setText(location);
                        rent_et.setText(rent);
                        duration_et.setText(duration);


                        if (smoking.matches("Allowed"))
                            smoking_spinner.setSelection(0);
                        else if (smoking.matches("Not Allowed"))
                            smoking_spinner.setSelection(1);
                        else
                            smoking_spinner.setSelection(2);


                        if (drinking.matches("Allowed"))
                            drinking_spinner.setSelection(0);
                        else if (drinking.matches("Not Allowed"))
                            drinking_spinner.setSelection(1);
                        else
                            drinking_spinner.setSelection(2);


                        if (overnight_guests.matches("Allowed"))
                            overnight_guest_spinner.setSelection(0);
                        else if (overnight_guests.matches("Not Allowed"))
                            overnight_guest_spinner.setSelection(1);
                        else
                            overnight_guest_spinner.setSelection(2);



                        if(food_habits.equals("Veg"))
                            food_habit_spinner.setSelection(0);
                        else
                            food_habit_spinner.setSelection(1);


                        if(contact_no.equals("**********"))
                            show_contact.setChecked(false);


                        if(family.equals("yes")){
                            family_cb.setChecked(true);
                        }else {
                            family_cb.setChecked(false);
                        }

                        if(student.equals("yes")){
                            student_cb.setChecked(true);
                        }else {
                            student_cb.setChecked(false);
                        }

                        if(batchelor.equals("yes")){
                            batchelor_cb.setChecked(true);
                        }else {
                            batchelor_cb.setChecked(false);
                        }

                        if(couples.equals("yes")){
                            couples_cb.setChecked(true);
                        }else {
                            couples_cb.setChecked(false);
                        }

                        Picasso.get().load(propertypic).into(propertyIMG);


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

        smoking_spinner.setAdapter(new ArrayAdapter<>(EditPropertyActivity.this, android.R.layout.simple_spinner_dropdown_item,preference1_array));
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

        drinking_spinner.setAdapter(new ArrayAdapter<>(EditPropertyActivity.this, android.R.layout.simple_spinner_dropdown_item,preference1_array));
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

        overnight_guest_spinner.setAdapter(new ArrayAdapter<>(EditPropertyActivity.this, android.R.layout.simple_spinner_dropdown_item,preference1_array));
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
        food_habit_spinner.setAdapter(new ArrayAdapter<>(EditPropertyActivity.this, android.R.layout.simple_spinner_dropdown_item,preference2_array));
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
        update.setOnClickListener(new View.OnClickListener() {
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



                HashMap user=new HashMap();
                user.put("rent",rent);
                user.put("location",location);
                user.put("family",family);
                user.put("couples",couples);
                user.put("contact",contact_no);
                user.put("batchelor",batchelor);
                user.put("student",student);
                user.put("duration",duration);
                user.put("smoking",smoking);
                user.put("drinking",drinking);
                user.put("overnight_guests",overnight_guests);
                user.put("food_habit",food_habits);

                reference.child(uid).updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Intent intent=new Intent(EditPropertyActivity.this,SeeMatchesActivity.class);
                            //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            Toast.makeText(EditPropertyActivity.this, "updated", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(EditPropertyActivity.this, "failed to update ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder=new AlertDialog.Builder(EditPropertyActivity.this);
                builder.setTitle("Delete your Property details ?");
               // builder.setMessage("Do you really want to delete your profile and related data? This action is irreversible!");
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();


                        StorageReference storageReferenceproperty=firebaseStorage.getReference("properties").child(uid);
                        storageReferenceproperty.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG,"OnSuccess:Photo Deleted");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,e.getMessage());
                                // Toast.makeText(DeleteProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        DatabaseReference databaseReferenceproperty= FirebaseDatabase.getInstance().getReference("Properties");
                        databaseReferenceproperty.child(uid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG,"OnSuccess:user data deleted");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,e.getMessage());
                                //Toast.makeText(DeleteProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        finish();

                        home_page.POST_PROPERTY_DONE=false;
                        Toast.makeText(EditPropertyActivity.this, "deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog=builder.create();

                alertDialog.show();
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