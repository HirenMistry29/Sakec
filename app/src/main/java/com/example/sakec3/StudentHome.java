package com.example.sakec3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentHome extends Fragment {
    public TextView person;
    FirebaseFirestore dbroot;



    public StudentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_student_home, container, false);

         person = view.findViewById(R.id.studentname);
         dbroot = FirebaseFirestore.getInstance();
//        fetchData();


        return view;
    }

    public void fetchData(){
        DocumentReference Name = dbroot.collection("students").document("Name");
        Name.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    person.setText(documentSnapshot.getString("FirstName"+" "+documentSnapshot.getString("LastName")));
                }
                else{
//                    Toast.makeText(StudentProfile.this , "Failed to fetcht the data", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(StudentHome.this, "Failed to Fetch", Toast.LENGTH_SHORT).show();
            }
        });
    }
}