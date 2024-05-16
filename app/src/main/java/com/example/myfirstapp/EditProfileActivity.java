package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    EditText name_et,ph_no_et,bio_et,age_et;
    RadioGroup gender_rg;
    RadioButton male_rb,female_rb,selected_rb;
    Button edit_done_btn;
    CircleImageView profileImg;
    ProgressBar load;
    Uri imageURI;
    StorageTask uploadTask;

    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference;

    String name,age,bio,phone,profilepic,gender,uid=user.getUid();

    static boolean dp_change=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name_et=findViewById(R.id.nameEP);
        ph_no_et=findViewById(R.id.phone_noEP);
        bio_et=findViewById(R.id.bioEP);
        age_et=findViewById(R.id.ageEP);
        gender_rg=findViewById(R.id.radioGroupEP);
        edit_done_btn=findViewById(R.id.done_buttonEP);
        profileImg=findViewById(R.id.circleImageViewEP);
        male_rb=findViewById(R.id.maleEP);
        female_rb=findViewById(R.id.femaleEP);
        load=findViewById(R.id.progressbarEP);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        StorageReference storageReference= FirebaseStorage.getInstance().getReference("upload").child(uid);

      /*  if(profilepic=="https://firebasestorage.googleapis.com/v0/b/myfirstapp-a2210.appspot.com/o/blank-profile-picture-973460_1280.png?alt=media&token=29cc6ab5-d8da-4c7c-9fea-6c934d9c7f99"){
            StorageReference storageReference= FirebaseStorage.getInstance().getReference("upload").child(uid);
            if(imageURI!=null){
                storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    profilepic=uri.toString();
                                }
                            });
                        }
                    }
                });
            }
        }*/



        reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot=task.getResult();
                        name=String.valueOf(dataSnapshot.child("userName").getValue());
                        age=String.valueOf(dataSnapshot.child("age").getValue());
                        gender=String.valueOf(dataSnapshot.child("gender").getValue());
                        bio=String.valueOf(dataSnapshot.child("bio").getValue());
                        phone=String.valueOf(dataSnapshot.child("phone_no").getValue());
                        profilepic=String.valueOf(dataSnapshot.child("profile_pic").getValue());

                        //Toast.makeText(MainActivity.this, setup_done, Toast.LENGTH_SHORT).show();
                      // Toast.makeText(EditProfileActivity.this, gender+name+age+uid, Toast.LENGTH_SHORT).show();


                       name_et.setText(name);
                       ph_no_et.setText(phone);
                       bio_et.setText(bio);
                       age_et.setText(age);
                       if(gender.equals("female")){
                           //Toast.makeText(EditProfileActivity.this, "true", Toast.LENGTH_SHORT).show();
                           female_rb.setChecked(true);
                       }
                        Picasso.get().load(profilepic).into(profileImg);
                       load.setVisibility(View.GONE);

                    }
                }
            }
        });

        edit_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(EditProfileActivity.this, profilepic, Toast.LENGTH_LONG).show();

                name=name_et.getText().toString();
                name=name.toUpperCase();
                phone=ph_no_et.getText().toString();
                bio=bio_et.getText().toString();
                age=age_et.getText().toString();

                selected_rb=findViewById(gender_rg.getCheckedRadioButtonId());

                gender=selected_rb.getText().toString();

                HashMap user=new HashMap();
                user.put("age",age);
                user.put("bio",bio);
                user.put("gender",gender);
                user.put("phone_no",phone);
                user.put("userName",name);

                reference.child(uid).updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Intent intent=new Intent(EditProfileActivity.this,MainActivity.class);
                            intent.putExtra("name",name);
                            intent.putExtra("profile",profilepic);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(EditProfileActivity.this, "failed to update your profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
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
                profileImg.setImageURI(imageURI);
                dp_change=true;
            }
        }
    }

}