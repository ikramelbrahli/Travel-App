package com.example.ps4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = null;
    EditText prenom, nom, email, password;
    Button btnLogin;
    TextView login;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore ;
    String userID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        prenom = findViewById(R.id.inputPrenom);
        password = findViewById(R.id.inputPassword);
        login = findViewById(R.id.gotoRegister);
        nom = findViewById(R.id.inputNom);
        email = findViewById(R.id.inputEmail);
        btnLogin = findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext() , CitiesActivity.class)) ;
            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_ = email.getText().toString().trim();
                String password_ = password.getText().toString().trim();
                String nom_= nom.getText().toString().trim();
                String prenom_= prenom.getText().toString().trim();
                if(TextUtils.isEmpty(email_)){
                    email.setError("Email is Required");
                }
                if(TextUtils.isEmpty(password_)){
                    password.setError("Password is Required");
                }
                if(password.length() < 6 ){
                    password.setError("Short password") ;
                }
                fAuth.createUserWithEmailAndPassword(email_,password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this , "User Created" , Toast.LENGTH_SHORT).show();
                            userID =fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("User").document(userID) ;
                            Map<String , Object> user = new HashMap<>();
                            user.put("nom",nom_);
                            user.put("prenom",prenom_);
                            user.put("email",email_);
                            user.put("password",password_);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG , "User profile created for" + userID) ;
                                }
                            });
                            startActivity(new Intent(getApplicationContext() , CitiesActivity.class)) ;
                        }
                        else{
                            Toast.makeText(RegisterActivity.this , "Error ! " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }) ;
            }
        }) ;
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext() , LoginActivity.class));
            }
        });
    }
}