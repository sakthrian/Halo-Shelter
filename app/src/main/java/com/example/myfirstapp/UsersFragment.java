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
import android.widget.TextView;

import com.example.myfirstapp.Adapter.UserAdapter;
import com.example.myfirstapp.Model.RequestDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    static boolean FRIENDS_FOUND=false;

    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    TextView noFriends;

    RecyclerView recyclerView;
    List<RequestDetails> dataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_users, container, false);
        noFriends=view.findViewById(R.id.textView_no_friend_found);

        recyclerView=view.findViewById(R.id.chats_user_recyclerView);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

       /* AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog=builder.create();
        dialog.show();*/

        dataList=new ArrayList<>();
        UserAdapter adapter=new UserAdapter(getActivity(),dataList);
        recyclerView.setAdapter(adapter);

        databaseReference= FirebaseDatabase.getInstance().getReference("Friends").child(user.getUid());
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        FRIENDS_FOUND=true;
                    }else{
                        FRIENDS_FOUND=false;
                    }
                }
            }
        });

        //dialog.show();

        eventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    RequestDetails requestDetails=itemSnapshot.getValue(RequestDetails.class);
                    dataList.add(requestDetails);
                    FRIENDS_FOUND=true;
                }
                if(FRIENDS_FOUND){
                    noFriends.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                //dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                //dialog.dismiss();
            }
        });
        return view;
    }
}