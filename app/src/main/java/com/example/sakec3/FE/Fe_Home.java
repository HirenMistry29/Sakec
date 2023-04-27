package com.example.sakec3.FE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.sakec3.Events.EventsAdapter;
import com.example.sakec3.Events.Eventsgetset;
import com.example.sakec3.R;
import com.example.sakec3.SE.EventHomeAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Fe_Home extends Fragment {
    public TextView person;
    FirebaseFirestore dbroot;

    //    Recycler View
    private RecyclerView eventsrecyclerview;
    private ArrayList<Eventsgetset> list;
    private EventsAdapter adapter;
    FEEventHomeAdapter FEA;

    //    Database
    private DatabaseReference db;

    public Fe_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fe__home, container, false);

        //ImageSlider
        ImageSlider imageSlider = view.findViewById(R.id.imageslider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.award, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.award1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.naac, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        dbroot = FirebaseFirestore.getInstance();

//      Recycler View
        eventsrecyclerview = view.findViewById(R.id.eventsrecyclerview);
        //        to reverse the recycler view and show the latest post on top
        LinearLayoutManager LayoutMangager = new LinearLayoutManager(getActivity());
        LayoutMangager.setReverseLayout(true);
        LayoutMangager.setStackFromEnd(true);
        eventsrecyclerview.setLayoutManager(LayoutMangager);
        eventsrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        FirebaseRecyclerOptions<Eventsgetset> options =
                new FirebaseRecyclerOptions.Builder<Eventsgetset>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Events").child("FE"), Eventsgetset.class)
                        .build();

        FEA = new FEEventHomeAdapter(options);
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