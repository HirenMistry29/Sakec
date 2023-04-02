package com.example.sakec3.Events;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sakec3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class Student_Events extends Fragment {
    private RecyclerView eventsrecyclerview;
    private ProgressBar progress;
    private ArrayList<Eventsgetset> list;
    private EventsAdapter adapter;

    //database
    private DatabaseReference reference;


    public Student_Events() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_student__events, container, false);
       eventsrecyclerview = view.findViewById(R.id.eventsrecyclerview);
       progress=view.findViewById(R.id.progress);

       reference= FirebaseDatabase.getInstance().getReference().child("Events");

       eventsrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
       eventsrecyclerview.setHasFixedSize(true);

       getEvent();


       return view;
    }

    private void getEvent() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list=new ArrayList<>();
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    Eventsgetset data = snapshot.getValue(Eventsgetset.class);
                    list.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}