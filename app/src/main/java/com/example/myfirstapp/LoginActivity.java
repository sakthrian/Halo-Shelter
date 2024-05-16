package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button login_btn;
    CheckBox checkbox;
    EditText email_et,password_et;
    ProgressBar progressbar;
    FirebaseAuth mAuth;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_btn=findViewById(R.id.login_button);
        checkbox=findViewById(R.id.login_checkbox);
        email_et=findViewById(R.id.login_email_et);
        password_et=findViewById(R.id.login_password_et);
        mAuth=FirebaseAuth.getInstance();
        progressbar=findViewById(R.id.login_progressbar);



        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }else {
                    password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=email_et.getText().toString();
                pass=password_et.getText().toString();


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "enter email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this, "enter password", Toast.LENGTH_SHORT).show();
                }
                else if(pass.length()<9){
                    Toast.makeText(LoginActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                }else{
                    if(!email.matches(emailPattern)){
                        email_et.setError("enter proper email address");
                    }else{
                        progressbar.setVisibility(View.VISIBLE);
                        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {

                                    ToMain();
                                    Toast.makeText(LoginActivity.this, "logged in", Toast.LENGTH_SHORT).show();

                                }else{
                                    progressbar.setVisibility(View.GONE);
                                    String error=task.getException().getMessage();
                                    Toast.makeText(LoginActivity.this, "error : "+error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });


    }

    private void ToMain() {

        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            ToMain();
        }
    }

}