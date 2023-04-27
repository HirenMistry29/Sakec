package com.example.sakec3.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.sakec3.R;
import com.example.sakec3.StudentProfile;
import com.example.sakec3.studentdetails.studentdetails;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignIn extends SignIn2 {
    private AppCompatButton log;
//    public static abstract final int default_web_client_id = 1900007;

    //google
    private ImageView Google;
    GoogleSignInClient client;

    //firebase
    FirebaseAuth auth;
    FirebaseAuth AuthUI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // finding element in UI
        log = findViewById(R.id.log);
        Google = findViewById(R.id.google);

         //  firebase
        auth = FirebaseAuth.getInstance();

        // login button function
//        log.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(SignIn.this , studentdetails.class));
//            }
//        });

        //google Login
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, options);
        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    SignIn();
            }
        });
    }


    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), studentdetails.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }


    //Update UI

    public void updateUI(GoogleSignInAccount account) {
    //If app login is Successful
        if (account != null) {

         startActivity(new Intent(SignIn.this , StudentProfile.class));
            Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "You are not signed in", Toast.LENGTH_LONG).show();
        }
    }

    private void SignIn(){
        Intent i = client.getSignInIntent();
        startActivityForResult(i, 1234);
    }


}

