package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class more_page extends Fragment {

    LinearLayout my_team,about_us,contact_us,logout,delete_acc;
    Button remove_listing,edit_requirement,edit_property;
    CircleImageView circleImageView;
    ImageView edit_button;
    TextView name_tv;
    FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    String name,profilepic;

    private static final String TAG="DeleteProfileActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_more_page, container, false);
        logout=view.findViewById(R.id.logout);
        my_team=view.findViewById(R.id.my_team_more);
        about_us=view.findViewById(R.id.about_us);
        contact_us=view.findViewById(R.id.contact_us);
        delete_acc=view.findViewById(R.id.delete_acc);
        edit_requirement=view.findViewById(R.id.edit_requirement_button);
        remove_listing=view.findViewById(R.id.remove_listing_button);
        name_tv=view.findViewById(R.id.name_more);
        edit_button=view.findViewById(R.id.profile_edit);
        edit_property=view.findViewById(R.id.property_edit_button);

        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser.equals("")){
            Toast.makeText(getActivity(), "something went wrong!!", Toast.LENGTH_SHORT).show();

        }else {

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showLogoutDialog();

                }
            });

            about_us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAboutUsDialog();
                }
            });

            contact_us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendMail();
                }
            });

            remove_listing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(home_page.NEED_ROOMMATE_DONE||home_page.NEED_ROOM_DONE){
                        showRemoveListingDialog();
                    }else{
                        //Toast.makeText(getActivity(), "post your requirements", Toast.LENGTH_SHORT).show();
                        showPostRequirementDialog();
                    }

                }
            });

            delete_acc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(getActivity(),DeleteProfileActivity.class);
                    startActivity(intent);


                }
            });

            my_team.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(),MyTeamActivity.class);
                    startActivity(intent);
                }
            });

            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(),EditProfileActivity.class);
                    startActivity(intent);
                }
            });

            edit_property.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(home_page.POST_PROPERTY_DONE){
                        Intent intent=new Intent(getActivity(),EditPropertyActivity.class);
                        startActivity(intent);
                    }else{
                        //Toast.makeText(getActivity(), "post your requirements", Toast.LENGTH_SHORT).show();
                        showPostPropertyDialog();
                    }

                }
            });

            edit_requirement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(home_page.NEED_ROOMMATE_DONE){
                        Intent intent=new Intent(getActivity(),EditNeedRoommateActivity.class);
                        startActivity(intent);
                    }else if(home_page.NEED_ROOM_DONE){
                        Intent intent=new Intent(getActivity(),EditNeedRoomActivity.class);
                        startActivity(intent);
                    }else{
                       // Toast.makeText(getActivity(), "post your requirements", Toast.LENGTH_SHORT).show();
                        showPostRequirementDialog();
                    }


                }
            });
        }


        return view;
    }

    private void showRemoveListingDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.remove_listing_bottom_sheet_layout);

        RadioButton fulfill,not_fulfill;
        RadioGroup removeListing;

        removeListing=dialog.findViewById(R.id.radioGroupremovelisting);

        fulfill=dialog.findViewById(R.id.fulfilled);
        not_fulfill=dialog.findViewById(R.id.not_fulfilled);



        Button confirm = dialog.findViewById(R.id.removelisting_confirm_button);
        Button cancel= dialog.findViewById(R.id.removelisting_cancel_button);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

                StorageReference storageReferenceroom=firebaseStorage.getReference("rooms").child(firebaseUser.getUid());
                storageReferenceroom.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

                DatabaseReference databaseReferenceroom= FirebaseDatabase.getInstance().getReference("Rooms");
                databaseReferenceroom.child(firebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

                DatabaseReference databaseReferenceroommate= FirebaseDatabase.getInstance().getReference("RoomMates");
                databaseReferenceroommate.child(firebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

                home_page.NEED_ROOM_DONE=false;
                home_page.NEED_ROOMMATE_DONE=false;

                RadioButton selected_rb=dialog.findViewById(removeListing.getCheckedRadioButtonId());

                String select=selected_rb.getText().toString();
                //Toast.makeText(getActivity(), select, Toast.LENGTH_SHORT).show();

                if(select.contains("No"))
                {
                    Toast.makeText(getActivity(), "Sorry for the inconvenience,\nYour Requirement has been deleted ", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getActivity(), "Thanks for using HaloShelter,\nYour Requirement has been deleted ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void showAboutUsDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.about_us_bottom_sheet_layout);


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void showPostPropertyDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.post_property_bottom_sheet_layout);

        Button post_property = dialog.findViewById(R.id.post_property_bs_button);

        post_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                Intent intent = new Intent(getActivity(),PostPropertyActivity.class);
                startActivity(intent);
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void showLogoutDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout_bottom_sheet_layout);

        Button yes_btn = dialog.findViewById(R.id.logout_yes_button);
        Button no_btn = dialog.findViewById(R.id.logout_no_button);

        yes_btn.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.delete_button_color));

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.SETUP_DONE=false;

                dialog.dismiss();

                mAuth.signOut();
                Intent intent = new Intent(getActivity(),RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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

    private void sendMail() {
        String recipientList = "sakthrian.tpy@gmail.com,hindhujaya@gmail.com";

        try {
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + recipientList));
            intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
            startActivity(intent);
        } catch(Exception e) {
            Toast.makeText(getActivity(), "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        circleImageView=getActivity().findViewById(R.id.circleImageViewMore);
    }

    @Override
    public void onStart() {
        super.onStart();


        Bundle data=getArguments();

        if (data!=null){
            name=data.getString("name");
            profilepic=data.getString("profilepic");

        }

        Picasso.get().load(profilepic).into(circleImageView);
        name_tv.setText(name);

    }

}