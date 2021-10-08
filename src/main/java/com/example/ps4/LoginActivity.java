package com.example.ps4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText prenom, nom, email, password;
    Button btnLogin;
    TextView register;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = findViewById(R.id.gotoRegister);
        prenom = findViewById(R.id.inputPrenom);
        password = findViewById(R.id.inputPassword);
        nom = findViewById(R.id.inputNom);
        email = findViewById(R.id.inputEmail);
        btnLogin = findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext() , CitiesActivity.class)) ;
            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_ = email.getText().toString().trim();
                String password_ = password.getText().toString().trim();
                if(TextUtils.isEmpty(email_)){
                    email.setError("Email is Required");
                }
                if(TextUtils.isEmpty(password_)){
                    password.setError("Password is Required");
                }
                if(password.length() < 6 ){
                    password.setError("Short password") ;
                }
                fAuth.signInWithEmailAndPassword(email_,password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this , "User Logged in" , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext() , CitiesActivity.class)) ;
                        }
                        else{
                            Toast.makeText(LoginActivity.this , "Error ! " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }) ;
            }
        }) ;
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext() , RegisterActivity.class));
            }
            });
    }
}