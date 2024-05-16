package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailTeamMateActivity extends AppCompatActivity {

    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference requestRef,friendRef;

    TextView name,rent,location,gender,occupancy,looking_for,description,contact;
    CircleImageView profileImage;
    Button make_team_btn,decline_req_btn;
    String uid,CurrentState="nothing_happen";

    String names,rents,locations,occupancys,genders,looking_fors,descriptions,contacts,profile,team;
    String myname,myrent,mylocation,myoccupancy,mygender,mylooking_for,mydescription,mycontact,myprofile,myteam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_team_mate);

        name=findViewById(R.id.detail_teammate_name);
        rent=findViewById(R.id.detail_teammate_rent);
        location=findViewById(R.id.detail_teammate_location);
        gender=findViewById(R.id.detail_teammate_gender);
        occupancy=findViewById(R.id.detail_teammate_occupancy);
        looking_for=findViewById(R.id.detail_teammate_lookingfor);
        description=findViewById(R.id.detail_teammate_description);
        profileImage=findViewById(R.id.detail_teammate_profile_img);
        contact=findViewById(R.id.detail_teammate_contact);
        make_team_btn=findViewById(R.id.detail_teammate_make_team_btn);
        decline_req_btn=findViewById(R.id.detail_teammate_decline_team_btn);

        requestRef= FirebaseDatabase.getInstance().getReference().child("Requests");
        friendRef= FirebaseDatabase.getInstance().getReference().child("Friends");

        DatabaseReference referenceroomate = FirebaseDatabase.getInstance().getReference("RoomMates");
        DatabaseReference referenceroom = FirebaseDatabase.getInstance().getReference("Rooms");

        referenceroomate.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        myname = String.valueOf(dataSnapshot.child("name").getValue());
                        //age = String.valueOf(dataSnapshot.child("age").getValue());
                        mygender = String.valueOf(dataSnapshot.child("gender").getValue());
                        mydescription = String.valueOf(dataSnapshot.child("bio").getValue());
                        mycontact = String.valueOf(dataSnapshot.child("contact").getValue());
                        myprofile = String.valueOf(dataSnapshot.child("profile").getValue());
                        mylocation = String.valueOf(dataSnapshot.child("location").getValue());
                        mylooking_for = String.valueOf(dataSnapshot.child("looking_for").getValue());
                        myoccupancy = String.valueOf(dataSnapshot.child("occupancy").getValue());
                        myrent = String.valueOf(dataSnapshot.child("rent").getValue());
                        myteam = String.valueOf(dataSnapshot.child("teams").getValue());
                        // profilepic=String.valueOf(dataSnapshot.child("profile_pic").getValue());


                        //  Toast.makeText(EditNeedRoomActivity.this, gender+name+age+uid, Toast.LENGTH_SHORT).show();


                    }
                }
            }
        });
        referenceroom.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        myname = String.valueOf(dataSnapshot.child("name").getValue());
                        //age = String.valueOf(dataSnapshot.child("age").getValue());
                        mygender = String.valueOf(dataSnapshot.child("gender").getValue());
                        mydescription = String.valueOf(dataSnapshot.child("bio").getValue());
                        mycontact = String.valueOf(dataSnapshot.child("contact").getValue());
                        myprofile = String.valueOf(dataSnapshot.child("profile").getValue());
                        mylocation = String.valueOf(dataSnapshot.child("location").getValue());
                        mylooking_for = String.valueOf(dataSnapshot.child("looking_for").getValue());
                        myoccupancy = String.valueOf(dataSnapshot.child("occupancy").getValue());
                        myrent = String.valueOf(dataSnapshot.child("rent").getValue());
                        myteam = String.valueOf(dataSnapshot.child("teams").getValue());
                        // profilepic=String.valueOf(dataSnapshot.child("profile_pic").getValue());


                        //  Toast.makeText(EditNeedRoomActivity.this, gender+name+age+uid, Toast.LENGTH_SHORT).show();


                    }
                }
            }
        });

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            names=bundle.getString("name");
            rents=bundle.getString("rent");
            locations=bundle.getString("location");
            genders=bundle.getString("gender");
            occupancys=bundle.getString("occupancy");
            looking_fors=bundle.getString("looking_for");
            descriptions =bundle.getString("description");
            contacts=bundle.getString("contact");
            uid=bundle.getString("uid");

            name.setText(names);
            rent.setText(rents);
            location.setText(locations);
            gender.setText(genders);
            occupancy.setText(occupancys);
            looking_for.setText(looking_fors);
            description.setText(descriptions);
            contact.setText(contacts);
            // occupancy.setText(occupancys);

            profile=bundle.getString("profile");
            team=bundle.getString("team");

            Picasso.get().load(profile).into(profileImage);
        }

        CheckUserExistance();

        decline_req_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unfriend();
            }
        });

        make_team_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAction();
            }
        });
    }
    private void Unfriend() {
        if(CurrentState.equals("friend")){
            friendRef.child(user.getUid()).child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        friendRef.child(uid).child(user.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(DetailTeamMateActivity.this, "teammate removed", Toast.LENGTH_SHORT).show();
                                    CurrentState="nothing_happen";
                                    make_team_btn.setText("make team");
                                    decline_req_btn.setVisibility(View.GONE);
                                    MyTeamActivity.TEAM_FOUND=false;
                                }
                            }
                        });
                    }
                }
            });
        }
        if(CurrentState.equals("he_sent_pending")){
            requestRef.child(user.getUid()).child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        requestRef.child(uid).child(user.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(DetailTeamMateActivity.this, "team request declined", Toast.LENGTH_SHORT).show();
                                    CurrentState="nothing_happen";
                                    make_team_btn.setText("make team");
                                    decline_req_btn.setVisibility(View.GONE);
                                    notification_page.HAVE_REQUEST=false;
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private void CheckUserExistance() {
        friendRef.child(user.getUid()).child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    CurrentState="friend";
                    make_team_btn.setText("chat");
                    decline_req_btn.setText("remove team");
                    decline_req_btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        friendRef.child(uid).child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    CurrentState="friend";
                    make_team_btn.setText("chat");
                    decline_req_btn.setText("remove team");
                    decline_req_btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        requestRef.child(user.getUid()).child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("status").getValue().toString().equals("pending")){
                        CurrentState="I_sent_pending";
                        make_team_btn.setText("cancel team request");
                        decline_req_btn.setVisibility(View.GONE);
                    }
                    /*if(snapshot.child("status").getValue().toString().equals("decline")){
                        CurrentState="I_sent_decline";
                        make_team_btn.setText("cancel team request");
                        decline_req_btn.setVisibility(View.GONE);
                    }*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        requestRef.child(uid).child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("status").getValue().toString().equals("pending")){
                        CurrentState="he_sent_pending";
                        make_team_btn.setText("accept team request");
                        decline_req_btn.setText("decline team request");
                        decline_req_btn.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(CurrentState.equals("nothing_happen")){
            CurrentState="nothing_happen";
            make_team_btn.setText("make team");
            decline_req_btn.setVisibility(View.GONE);
        }
    }

    private void PerformAction() {
        if(CurrentState.equals("nothing_happen")){
            HashMap hashMap = new HashMap();
            hashMap.put("status","pending");
            requestRef.child(user.getUid()).child(uid).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(DetailTeamMateActivity.this, "Team request has sent", Toast.LENGTH_SHORT).show();
                        decline_req_btn.setVisibility(View.GONE);
                        CurrentState="I_sent_pending";
                        make_team_btn.setText("cancel team request");
                    }else{
                        Toast.makeText(DetailTeamMateActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if(CurrentState.equals("I_sent_pending")||CurrentState.equals("I_sent_decline")){
            requestRef.child(user.getUid()).child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(DetailTeamMateActivity.this, "Team request cancelled", Toast.LENGTH_SHORT).show();
                        CurrentState="nothing_happen";
                        make_team_btn.setText("make team");
                        decline_req_btn.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(DetailTeamMateActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if(CurrentState.equals("he_sent_pending")){
            requestRef.child(uid).child(user.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        HashMap hashMap = new HashMap();
                        hashMap.put("status","friend");
                        hashMap.put("profile",profile);
                        hashMap.put("occupancy",occupancys);
                        hashMap.put("gender",genders);
                        hashMap.put("looking_for",looking_fors);
                        hashMap.put("rent",rents);
                        hashMap.put("location",locations);
                        hashMap.put("uid",uid);
                        hashMap.put("bio",descriptions);
                        hashMap.put("name",names);
                        hashMap.put("contact",contacts);

                        HashMap myhashMap = new HashMap();
                        myhashMap.put("status","friend");
                        myhashMap.put("profile",myprofile);
                        myhashMap.put("occupancy",myoccupancy);
                        myhashMap.put("gender",mygender);
                        myhashMap.put("looking_for",mylooking_for);
                        myhashMap.put("rent",myrent);
                        myhashMap.put("location",mylocation);
                        myhashMap.put("uid",user.getUid().toString());
                        myhashMap.put("bio",mydescription);
                        myhashMap.put("name",myname);
                        myhashMap.put("contact",mycontact);

                        friendRef.child(user.getUid()).child(uid).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    friendRef.child(uid).child(user.getUid()).updateChildren(myhashMap).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            Toast.makeText(DetailTeamMateActivity.this, "new teammate added", Toast.LENGTH_SHORT).show();
                                            CurrentState="friend";
                                            make_team_btn.setText("chat");
                                            decline_req_btn.setText("remove team");
                                            decline_req_btn.setVisibility(View.VISIBLE);
                                            notification_page.HAVE_REQUEST=false;
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }
        if(CurrentState.equals("friend")){
            Intent intent=new Intent(this,MessageActivity.class);
            intent.putExtra("profile",profile);
            intent.putExtra("name",names);
            intent.putExtra("uid",uid);
            startActivity(intent);
        }
    }
}