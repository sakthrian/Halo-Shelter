package com.example.myfirstapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.squareup.picasso.Picasso;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class home_page extends Fragment {

    public static boolean NEED_ROOM_DONE,NEED_ROOMMATE_DONE,POST_PROPERTY_DONE;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    private static final String TAG="DeleteProfileActivity";
    public String name,profilepic,uid=user.getUid();
    private TextView welcome;
    CircleImageView userImg;
    ImageView chat;
    LinearLayout my_team,post_property,see_matches;
    Button need_room,need_roommate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home_page, container, false);

        welcome=view.findViewById(R.id.welcome_name);
        chat=view.findViewById(R.id.chat_image_btn);
        my_team=view.findViewById(R.id.my_team);
        post_property=view.findViewById(R.id.post_property);
        need_room=view.findViewById(R.id.need_room_btn);
        need_roommate=view.findViewById(R.id.need_roommate_btn);
        see_matches=view.findViewById(R.id.see_matches);

        DatabaseReference need_room_reference= FirebaseDatabase.getInstance().getReference("RoomMates");
        DatabaseReference need_roommate_reference= FirebaseDatabase.getInstance().getReference("Rooms");
        DatabaseReference post_property_reference= FirebaseDatabase.getInstance().getReference("Properties");

        need_roommate_reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){

                        NEED_ROOMMATE_DONE=true;
                    }else {
                        NEED_ROOMMATE_DONE=false;
                    }
                }
            }
        });

        need_room_reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){

                        NEED_ROOM_DONE=true;
                    }else {
                        NEED_ROOM_DONE=false;
                    }
                }
            }
        });

        post_property_reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){

                        POST_PROPERTY_DONE=true;
                    }else {
                        POST_PROPERTY_DONE=false;
                    }
                }
            }
        });

            need_roommate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!NEED_ROOM_DONE&&!NEED_ROOMMATE_DONE){
                        Intent intent=new Intent(getActivity(),NeedRoommateActivity.class);
                        startActivity(intent);
                    }else {
                        showSeeMatchesDialog();
                    }

                }

            });

            need_room.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!NEED_ROOM_DONE&&!NEED_ROOMMATE_DONE){
                        Intent intent=new Intent(getActivity(),NeedRoomActivity.class);
                        startActivity(intent);
                    }else {
                        showSeeMatchesDialog();
                    }


                }
            });

            post_property.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(POST_PROPERTY_DONE){
                        showEditPropertyDialog();
                    }else {
                        Intent intent=new Intent(getActivity(),PostPropertyActivity.class);
                        startActivity(intent);
                    }
                }
            });

            my_team.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(),MyTeamActivity.class);
                    startActivity(intent);
                }
            });

            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(),ChatActivity.class);
                    startActivity(intent);
                }
            });

            see_matches.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(NEED_ROOMMATE_DONE||NEED_ROOM_DONE||POST_PROPERTY_DONE){
                        Intent intent=new Intent(getActivity(),SeeMatchesActivity.class);
                        startActivity(intent);
                    }else{
                       showPostRequirementDialog();
                    }

                }
            });



        return view;
    }

    private void showSeeMatchesDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.see_matches_bottom_sheet_layout);

        Button see_matches_bs = dialog.findViewById(R.id.see_matches_bs_button);
        Button edit_requirement_bs = dialog.findViewById(R.id.edit_requirement_bs_button);

        see_matches_bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

                Intent intent = new Intent(getActivity(), SeeMatchesActivity.class);
                startActivity(intent);

            }

        });

        edit_requirement_bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                if(NEED_ROOM_DONE){
                    Intent intent = new Intent(getActivity(), EditNeedRoomActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), EditNeedRoommateActivity.class);
                    startActivity(intent);
                }

            }

        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void showEditPropertyDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_property_bottom_sheet_layout);

        Button edit = dialog.findViewById(R.id.edit_property_button);
        Button delete = dialog.findViewById(R.id.delete_property_button);

        delete.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.delete_button_color));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

                Intent intent = new Intent(getActivity(), EditPropertyActivity.class);
                startActivity(intent);

            }

        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
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

                        dialog.dismiss();

                        home_page.POST_PROPERTY_DONE=false;
                        Toast.makeText(getActivity(), "deleted", Toast.LENGTH_SHORT).show();
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
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void showPostRequirementDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.post_requirement_bottom_sheet_layout);

        LinearLayout need_room_btn = dialog.findViewById(R.id.need_room_post_requirement_bs);
        LinearLayout need_roommate_btn = dialog.findViewById(R.id.need_roommate_post_requirement_bs);

        need_room_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

                Intent intent=new Intent(getActivity(),NeedRoomActivity.class);
                startActivity(intent);

            }
        });

        need_roommate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent=new Intent(getActivity(),NeedRoommateActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userImg=getActivity().findViewById(R.id.circleImageViewHome);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle data=getArguments();

        if (data!=null){
            name=data.getString("name");
            profilepic=data.getString("profilepic");
        }

        welcome.setText(name);

        Picasso.get().load(profilepic).into(userImg);

    }
}