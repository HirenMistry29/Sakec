package com.example.sakec3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserLogin extends AppCompatActivity {
    private EditText email,password;

    ProgressBar pb;
    Button createAccount , login;
    String e_mail , pass_word;

//    -----FireBase-----
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        createAccount = findViewById(R.id.createAccount);
        login = findViewById(R.id.Login);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        pb = findViewById(R.id.progressBar);

//        Firebase
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserLogin.this , UserRegister.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);

                e_mail = email.getText().toString();
                pass_word = password.getText().toString();
                fAuth.signInWithEmailAndPassword(e_mail,pass_word).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pb.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            checkUserAccessLevel(task.getResult().getUser().getUid());
//                            startActivity(new Intent(UserLogin.this, StudentProfile.class));
                        }
                        else{
                            Toast.makeText(UserLogin.this, "Failed to login", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }

    public void checkUserAccessLevel(String uid){
        DocumentReference DF = fstore.collection("Users").document(uid);
//        extract the uid data
        DF.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());

                if(documentSnapshot.getString("Year").equals("SE")){
                    Toast.makeText(UserLogin.this, "Logged in As SE", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserLogin.this,StudentProfile.class));
                    finish();
                }
                else if(documentSnapshot.getString("Year").equals("FE")){
                    Toast.makeText(UserLogin.this, "Loggedin as FE", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserLogin.this, FeDashboard.class));
                    finish();
                }
                else if(documentSnapshot.getString("Year").equals("TE")){
                    Toast.makeText(UserLogin.this, "Logged in as TE", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserLogin.this, TE_Dashboard.class));
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}