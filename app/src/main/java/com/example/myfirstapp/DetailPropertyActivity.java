package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailPropertyActivity extends AppCompatActivity {
    TextView name,rent,location,description,contact,smoking,drinking,overnight_guests,food_habit,duration;
    LinearLayout family,couples,batchelor,student;
    ImageView roomimage;
    CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_property);

        name=findViewById(R.id.detail_property_name);
        rent=findViewById(R.id.detail_property_rent);
        location=findViewById(R.id.detail_property_location);
        smoking=findViewById(R.id.detail_property_smoking);
        drinking=findViewById(R.id.detail_property_drinking);
        overnight_guests=findViewById(R.id.detail_property_overnight_guests);
        roomimage=findViewById(R.id.detail_property_img);
        profileImage=findViewById(R.id.detail_property_profile_img);
        contact=findViewById(R.id.detail_property_contact);
        food_habit=findViewById(R.id.detail_property_food_habit);
        family=findViewById(R.id.detail_property_family);
        couples=findViewById(R.id.detail_property_couples);
        batchelor=findViewById(R.id.detail_property_batchelor);
        student=findViewById(R.id.detail_property_student);
        description=findViewById(R.id.detail_property_description);
        duration=findViewById(R.id.detail_property_stay_duration);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            name.setText(bundle.getString("name"));
            rent.setText(bundle.getString("rent"));
            location.setText(bundle.getString("location"));
            smoking.setText(bundle.getString("smoking"));
            drinking.setText(bundle.getString("drinking"));
            overnight_guests.setText(bundle.getString("overnight_guests"));
            description.setText(bundle.getString("description"));
            contact.setText(bundle.getString("contact"));
            food_habit.setText(bundle.getString("food_habits"));
            duration.setText(bundle.getString("duration"));

            String profile,roomImage,familyst,couplesst,batchelorst,studentst;
            profile=bundle.getString("profile");
            roomImage=bundle.getString("roomImage");
            familyst=bundle.getString("family");
            couplesst=bundle.getString("couples");
            batchelorst=bundle.getString("batchelor");
            studentst=bundle.getString("student");
            if(familyst.equals("yes")){
                family.setVisibility(View.VISIBLE);
            }
            if(couplesst.equals("yes")){
                couples.setVisibility(View.VISIBLE);
            }
            if(batchelorst.equals("yes")){
                batchelor.setVisibility(View.VISIBLE);
            }
            if(studentst.equals("yes")){
                student.setVisibility(View.VISIBLE);
            }
            Picasso.get().load(profile).into(profileImage);
            Picasso.get().load(roomImage).into(roomimage);
        }
    }
}