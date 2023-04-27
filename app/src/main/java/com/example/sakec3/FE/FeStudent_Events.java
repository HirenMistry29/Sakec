package com.example.sakec3.FE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakec3.Events.EventsAdapter;
import com.example.sakec3.Events.Eventsgetset;
import com.example.sakec3.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class FeStudent_Events extends Fragment {
    private RecyclerView eventsrecyclerview;
    private ProgressBar progress;
    private ArrayList<Eventsgetset> list;
    private EventsAdapter adapter;
    FeEventsAdapter FEA;

    //database
    private DatabaseReference reference;


    public FeStudent_Events() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_festudent__events, container, false);
       eventsrecyclerview = view.findViewById(R.id.eventsrecyclerview);

        //        to reverse the recycler view and show the latest post on top
        LinearLayoutManager LayoutMangager = new LinearLayoutManager(getActivity());
        LayoutMangager.setReverseLayout(true);
        LayoutMangager.setStackFromEnd(true);
        eventsrecyclerview.setLayoutManager(LayoutMangager);

//       eventsrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));


            FirebaseRecyclerOptions<Eventsgetset> options =
                    new FirebaseRecyclerOptions.Builder<Eventsgetset>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Events").child("FE"), Eventsgetset.class)
                            .build();

            FEA = new FeEventsAdapter(options);
            eventsrecyclerview.setAdapter(FEA);

       return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FEA.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        FEA.stopListening();
    }
}