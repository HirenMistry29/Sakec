package com.example.sakec3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRegister extends AppCompatActivity {
    EditText fullName, email, password, phone;
    Button registerBtn, goToLogin;
    Spinner year;
    int stick =0;

    String full_name , e_mail , pass_word , phone_no , Year;

    //    ----FireBase----
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        //        -----FireBase-----
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

        fullName = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password  = findViewById(R.id.registerPassword);
        phone = findViewById(R.id.registerPhone);
        registerBtn = findViewById(R.id.registerBtn);
        goToLogin = findViewById(R.id.goToLogin);
        year = findViewById(R.id.select_year);

//
        //            ----Year Spinner(Drop Down List)---
        ArrayAdapter<CharSequence> yearlist = ArrayAdapter.createFromResource(
                this , R.array.year , com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item );
        year.setAdapter(yearlist);
        year.setPrompt("Year");
        year.setBackgroundColor(Color.BLUE);
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if(parent.getItemAtPosition(i).equals("Select year")){
                    //do nothing
                }
                else{
                    stick = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                full_name = fullName.getText().toString();
                e_mail = email.getText().toString();
                pass_word = password.getText().toString();
                phone_no = phone.getText().toString();
                Year = year.getSelectedItem().toString();

                if(full_name.isEmpty()) {
                    fullName.setError("Error");
                }
                else if(e_mail.isEmpty()){
                    email.setError("Error");
                }
                else if(pass_word.isEmpty()){
                    password.setError("Error");
                }
                else if(phone_no.isEmpty() && phone_no.length()<10){
                    phone.setError("Error");
                }
                else{
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        UserModel model = new UserModel(full_name,e_mail,pass_word,phone_no, Year);
                                        String id = task.getResult().getUser().getUid();

                                        database.getReference().child("Users").child(id).setValue(model);

                                        FirebaseUser user = fAuth.getCurrentUser();
                                        DocumentReference DF = fStore.collection("Users").document(user.getUid());

                                        Map<String, Object> userInfo = new HashMap<>();
                                        userInfo.put("FullName", full_name);
                                        userInfo.put("UserEmail", e_mail);
                                        userInfo.put("PhoneNo.", phone_no);
                                        userInfo.put("Year",Year);
    //
    //                                  value 1 is for students of SE
                                        userInfo.put("isUser", "1");
                                        DF.set(userInfo);
                                        startActivity(new Intent(UserRegister.this,UserLogin.class));
                                    }
                                    else{
                                        Toast.makeText(UserRegister.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserRegister.this,UserLogin.class));
            }
        });


    }
}


//                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                                @Override
//                                public void onSuccess(AuthResult authResult) {
//                                    Toast.makeText(UserRegister.this, "Account Created", Toast.LENGTH_SHORT).show();
//                                    FirebaseUser user = fAuth.getCurrentUser();
//                                    DocumentReference DF = fStore.collection("Users").document(user.getUid());
//
//                                    Map<String, Object> userInfo = new HashMap<>();
//                                    userInfo.put("FullName", fullName.getText().toString());
//                                    userInfo.put("UserEmail", email.getText().toString());
//                                    userInfo.put("PhoneNo.", phone.getText().toString());
////
////                                  value 1 is for students of SE
//                                    userInfo.put("isUser", "1");
//                                    DF.set(userInfo);
//
//                                    startActivity(new Intent(getApplicationContext(), UserLogin.class));
//                                    finish();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(UserRegister.this, "Failed To Create User", Toast.LENGTH_SHORT).show();
//                                }
//                            });