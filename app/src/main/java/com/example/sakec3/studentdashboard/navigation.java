package com.example.sakec3.studentdashboard;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sakec3.R;
import com.example.sakec3.studentdetails.studentdetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class navigation extends studentdetails {
    TextView person;
//    private TextView edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
        person = findViewById(R.id.personname);
        fetchData();


    }

    public void fetchData(){
        DocumentReference Name = FirebaseFirestore.getInstance().collection("students").document("Name");
        Name.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    person.setText(documentSnapshot.getString("FirstName"+" "+documentSnapshot.getString("LastName")));
                }
                else{
                    Toast.makeText(navigation.this, "Failed to Fetch", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(navigation.this, "Failed to Fetch", Toast.LENGTH_SHORT).show();
            }
        });
    }
}