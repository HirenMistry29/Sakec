package com.example.sakec3.Events;

import android.os.Build;
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
import com.example.sakec3.SE.EventHomeAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
    EventsAdapter EA;

    //database
    private DatabaseReference reference;


    public Student_Events() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_student__events, container, false);
       eventsrecyclerview = view.findViewById(R.id.eventsrecyclerview);

        //        to reverse the recycler view and show the latest post on top
        LinearLayoutManager LayoutMangager = new LinearLayoutManager(getActivity());
        LayoutMangager.setReverseLayout(true);
        LayoutMangager.setStackFromEnd(true);
        eventsrecyclerview.setLayoutManager(LayoutMangager);

//       eventsrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));


            FirebaseRecyclerOptions<Eventsgetset> options =
                    new FirebaseRecyclerOptions.Builder<Eventsgetset>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Events").child("SE"), Eventsgetset.class)
                            .build();

            EA = new EventsAdapter(options);
            eventsrecyclerview.setAdapter(EA);



//                        .build();



       return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EA.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        EA.stopListening();
    }
}