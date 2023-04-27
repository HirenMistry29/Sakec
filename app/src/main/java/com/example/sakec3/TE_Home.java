package com.example.sakec3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class TE_Home extends Fragment {

    public TextView person;
    FirebaseFirestore dbroot;

    //    Recycler View
    private RecyclerView eventsrecyclerview;
    private ArrayList<Eventsgetset> list;
//    private EventsAdapter adapter;
//    EventHomeAdapter EA;

    //    Database
    private DatabaseReference db;



    public TE_Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_t_e__home, container, false);
        //ImageSlider
        ImageSlider imageSlider = view.findViewById(R.id.imageslider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.award, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.award1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.naac, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        dbroot = FirebaseFirestore.getInstance();



        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
//        EA.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
//        EA.stopListening();
    }
}