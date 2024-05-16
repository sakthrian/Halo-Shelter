package com.example.myfirstapp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.Adapter.MyRequestAdapter;
import com.example.myfirstapp.Model.RequestDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class notification_page extends Fragment {

    static boolean HAVE_REQUEST=false;

    ImageView noti_bell;
    TextView no_noti,request;

    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    List<String> requestList;
    RecyclerView recyclerView_request;
    List<RequestDetails> dataList;
    DatabaseReference databaseReferenceroom,databaseReferenceroommate;

    DatabaseReference reqRef;
    ValueEventListener eventListener;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_page, container, false);

        recyclerView_request=view.findViewById(R.id.request_recycler_view);
        no_noti=view.findViewById(R.id.no_noti_found_tv);
        request=view.findViewById(R.id.notification_request_tv);
        noti_bell=view.findViewById(R.id.imageView_notification);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView_request.setLayoutManager(gridLayoutManager);


        dataList=new ArrayList<>();
        requestList=new ArrayList<>();

        MyRequestAdapter adapter=new MyRequestAdapter(getActivity(),dataList);
        recyclerView_request.setAdapter(adapter);
        try{

            reqRef=FirebaseDatabase.getInstance().getReference().child("Requests");
            reqRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int flag=-1;
                    requestList.clear();
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        for (DataSnapshot requsetSnapshot : userSnapshot.getChildren()) {
                            uid = requsetSnapshot.getKey();
                            if (uid.equals(user.getUid())) {
                                HAVE_REQUEST=true;
                                flag=0;
                                requestList.add(userSnapshot.getKey());
                                //displayArrayListInToast(requestList);
                            }else{
                                if(flag<0){
                                    HAVE_REQUEST=false;
                                }

                            }
                        }
                    }
                    if(HAVE_REQUEST){
                        if(getActivity()!=null){
                            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                            builder.setCancelable(false);
                            builder.setView(R.layout.progress_layout);
                            AlertDialog dialog=builder.create();
                            dialog.show();
                            no_noti.setVisibility(View.GONE);
                            noti_bell.setVisibility(View.GONE);
                            request.setVisibility(View.VISIBLE);
                            databaseReferenceroom= FirebaseDatabase.getInstance().getReference("Rooms");
                            databaseReferenceroommate= FirebaseDatabase.getInstance().getReference("RoomMates");
                            dataList.clear();
                            eventListener=databaseReferenceroom.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                                        RequestDetails requestDetails=itemSnapshot.getValue(RequestDetails.class);
                                        String uid= requestDetails.getUid();
                                        if(requestList.contains(uid)){
                                            dataList.add(requestDetails);
                                        }

                                    }
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                    dialog.dismiss();
                                }
                            });
                            eventListener=databaseReferenceroommate.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                                        RequestDetails requestDetails=itemSnapshot.getValue(RequestDetails.class);
                                        String uid= requestDetails.getUid();
                                        if(requestList.contains(uid)){
                                            dataList.add(requestDetails);
                                        }

                                    }
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                    dialog.dismiss();
                                }
                            });

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private void displayArrayListInToast(List<String> requestList) {

        StringBuilder stringBuilder = new StringBuilder();

        // Iterate through the elements of the ArrayList
        for (String item : requestList) {
            stringBuilder.append(item).append("\n");
        }

        // Create and show the Toast message
        Toast.makeText(getActivity(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}