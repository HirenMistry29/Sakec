package com.example.sakec3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

//    FireBaseAuth
    FirebaseAuth Fauth;
//    FireBase Firestore
    FirebaseFirestore Fstore;

//        EditTExt
    private EditText email ,pass;

//    Button
    ProgressBar PB;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        update = findViewById(R.id.btnContinue);
        email = findViewById(R.id.sakecemail);
        pass = findViewById(R.id.password);
        PB = findViewById(R.id.pb);

//        Creating Firebase Instance
        Fauth = FirebaseAuth.getInstance();
        Fstore = FirebaseFirestore.getInstance();


//
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() && pass.getText().toString().isEmpty()){
                    Toast.makeText(Registration.this, "Enter the details", Toast.LENGTH_SHORT).show();
                }
                else{
                    Fauth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    PB.setVisibility(view.INVISIBLE);

                                    FirebaseUser user = Fauth.getCurrentUser();
                                    Toast.makeText(Registration.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    DocumentReference DF = Fstore.collection("Users").document(user.getUid());
                                    Map<String, Object> userDetails = new HashMap<>();
                                    userDetails.put("Email",email.getText().toString());
                                    userDetails.put("Password",pass.getText().toString());
                                    userDetails.put("Role","1");
                                    DF.set(userDetails);
                                }
                            });
                }

            }
        });
    }
}

/*
 FirebaseUser user = Fauth.getCurrentUser();
                           Toast.makeText(Registration.this, "Account Created", Toast.LENGTH_SHORT).show();
                           DocumentReference DF = Fstore.collection("Users").document(user.getUid());
                           Map<String, Object> userDetails = new HashMap<>();
                           userDetails.put("Email",email.getText().toString());
                           userDetails.put("Password",pass.getText().toString());
                           userDetails.put("Role","1");
                           DF.set(userDetails);
 */