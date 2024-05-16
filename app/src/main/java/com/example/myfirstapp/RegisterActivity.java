package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    Button register_btn;
    TextView login_tv;
    CheckBox checkbox;
    EditText email_et,password_et,conform_password_et;
    ProgressBar progressbar;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String email,pass,conform_pass;
    static final String TAG="RegisterActivity";
    String name,profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_btn=findViewById(R.id.register_button);
        login_tv=findViewById(R.id.register_login_tv);
        checkbox=findViewById(R.id.register_checkbox);
        email_et=findViewById(R.id.register_email_et);
        password_et=findViewById(R.id.register_password_et);
        conform_password_et=findViewById(R.id.register_conform_password_et);
        progressbar=(ProgressBar) findViewById(R.id.register_progressbar);

        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        profilepic=intent.getStringExtra("profile");


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    conform_password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    conform_password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=email_et.getText().toString();
                pass=password_et.getText().toString();
                conform_pass=conform_password_et.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "enter email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(RegisterActivity.this, "enter password", Toast.LENGTH_SHORT).show();
                }
                else if(pass.length()<9){
                    Toast.makeText(RegisterActivity.this, "password must be minimum 8 characters", Toast.LENGTH_SHORT).show();

                }else{
                    if(!email.matches(emailPattern)){
                        Toast.makeText(RegisterActivity.this, "invalid email", Toast.LENGTH_SHORT).show();
                        email_et.setError("enter proper email address");
                        email_et.requestFocus();
                    }else if(pass.equals(conform_pass)){
                        progressbar.setVisibility(view.VISIBLE);
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "registered sucessfully", Toast.LENGTH_SHORT).show();

                                   // Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                    //startActivity(intent);
                                    ToCreateProfile();
                                }else{
                                    try {
                                        throw task.getException();
                                    }catch (FirebaseAuthWeakPasswordException e){
                                        password_et.setError("your password is weak");
                                        password_et.requestFocus();
                                    }catch (FirebaseAuthInvalidCredentialsException e){
                                        email_et.setError("email is invalid or already registered");
                                        email_et.requestFocus();
                                    }catch (FirebaseAuthUserCollisionException e){
                                        email_et.setError("email is invalid or already registered");
                                        email_et.requestFocus();
                                    } catch (Exception e) {
                                        Log.e(TAG,e.getMessage());
                                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });



                    }else{
                        progressbar.setVisibility(view.INVISIBLE);
                        Toast.makeText(RegisterActivity.this, "password didnt matches", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressbar.setVisibility(view.VISIBLE);
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ToCreateProfile() {
        Intent intent = new Intent(RegisterActivity.this,CreateProfileActivity.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void ToMain() {
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("profile",profilepic);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            ToMain();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        progressbar.setVisibility(View.GONE);
    }
}