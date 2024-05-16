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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myfirstapp.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateProfileActivity extends AppCompatActivity {


    EditText name_et,ph_no_et,bio_et,age_et;
    RadioGroup gender_rg;
    RadioButton selected_rb;
    Button done_btn;
    CircleImageView profileImg;
    ProgressBar loading;
    FirebaseAuth auth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database;
    FirebaseStorage storage;
    DocumentReference documentReference;
    Uri imageURI;
    String imageuri,uid,email,password="123456789";
    static String setup_done="0";

    String name,ph_no,bio,age,gender;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        name_et=findViewById(R.id.name);
        ph_no_et=findViewById(R.id.phone_no);
        bio_et=findViewById(R.id.bio);
        age_et=findViewById(R.id.age);
        gender_rg=findViewById(R.id.radioGroup);
        done_btn=findViewById(R.id.done_button);
        profileImg=findViewById(R.id.circleImageView);
        loading=findViewById(R.id.loading_progress_bar_cp);

        if(user!=null){
            uid=user.getUid();
            email=user.getEmail();
           // Toast.makeText(this, uid+email, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "no user", Toast.LENGTH_SHORT).show();
        }



        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=name_et.getText().toString();
                name=name.toUpperCase();
                ph_no=ph_no_et.getText().toString();
                bio=bio_et.getText().toString();
               age=age_et.getText().toString();

               selected_rb=findViewById(gender_rg.getCheckedRadioButtonId());

               gender=selected_rb.getText().toString();

               if(TextUtils.isEmpty(name)){
                   Toast.makeText(CreateProfileActivity.this, "enter name", Toast.LENGTH_SHORT).show();
               } else if(TextUtils.isEmpty(ph_no)){
                    Toast.makeText(CreateProfileActivity.this, "enter phone number", Toast.LENGTH_SHORT).show();
               } else if(TextUtils.isEmpty(bio)){
                    Toast.makeText(CreateProfileActivity.this, "enter bio", Toast.LENGTH_SHORT).show();
               } else if(TextUtils.isEmpty(age)){
                    Toast.makeText(CreateProfileActivity.this, "enter age", Toast.LENGTH_SHORT).show();
               } else if(TextUtils.isEmpty(gender)){
                    Toast.makeText(CreateProfileActivity.this, "select gender", Toast.LENGTH_SHORT).show();
               }else{
                   loading.setVisibility(View.VISIBLE);
                   DatabaseReference reference=FirebaseDatabase.getInstance().getReference("users"). child(uid);
                //   documentReference= FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().getUid());
                   StorageReference storageReference=FirebaseStorage.getInstance().getReference("upload").child(uid);

                   if(imageURI!=null){
                       storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                               if(task.isSuccessful()){
                                   storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                       @Override
                                       public void onSuccess(Uri uri) {
                                           setup_done="1";
                                           imageuri=uri.toString();
                                           Users users=new Users(uid,name,email,password,imageuri,bio,age,gender,ph_no,setup_done);
                                          // documentReference.set(users);
                                           reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   if(task.isSuccessful()){
                                                       Intent intent=new Intent(CreateProfileActivity.this,MainActivity.class);
                                                       intent.putExtra("name",name);
                                                       intent.putExtra("age",age);
                                                       intent.putExtra("bio",bio);
                                                       intent.putExtra("phone",ph_no);
                                                       intent.putExtra("gender",gender);
                                                       intent.putExtra("profile",imageuri);
                                                       startActivity(intent);
                                                       MainActivity.SETUP_DONE=true;
                                                       finish();
                                                   }else {
                                                       loading.setVisibility(View.INVISIBLE);
                                                       Toast.makeText(CreateProfileActivity.this, "error in creating profile", Toast.LENGTH_SHORT).show();
                                                   }
                                               }
                                           });
                                       }
                                   });
                               }
                           }
                       });
                   }else {
                       setup_done="1";
                       imageuri="https://firebasestorage.googleapis.com/v0/b/myfirstapp-a2210.appspot.com/o/blank-profile-picture-973460_1280.png?alt=media&token=29cc6ab5-d8da-4c7c-9fea-6c934d9c7f99";
                       Users users=new Users(uid,name,email,password,imageuri,bio,age,gender,ph_no,setup_done);
                       reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){
                                   Intent intent=new Intent(CreateProfileActivity.this,MainActivity.class);
                                   intent.putExtra("name",name);
                                   intent.putExtra("age",age);
                                   intent.putExtra("bio",bio);
                                   intent.putExtra("phone",ph_no);
                                   intent.putExtra("gender",gender);
                                   startActivity(intent);
                                   MainActivity.SETUP_DONE=true;
                                   finish();
                               }else {
                                   loading.setVisibility(View.INVISIBLE);
                                   Toast.makeText(CreateProfileActivity.this, "error in creating profile", Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
                   }



               }
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
            }
        }
    }
}