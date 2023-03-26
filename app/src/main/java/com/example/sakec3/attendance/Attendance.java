package com.example.sakec3.attendance;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sakec3.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class Attendance extends Fragment {
//    we are using androidx fragment

//           -----STEPS-----
//    we need to form the record for the bar chart
//    store the record in arraylist<BarEntry (type of array)>
//    Each BarEntry has 2 values => X & Y
//    Then we create a Bar DataSet(color , size) which contains data of X & Y
//    then we pass the record data to Bar DataSet
//    then we create Bar Data , and apply this data to graph using *bar.setData(BarData)* method

//---String---
    String math_at, os_at, dbms_at, mp_at, aoa_at;

//    ---INT---
    int math_atd , os_atd, dbms_atd , mp_atd, aoa_atd;

//    ---textiew---
    TextView math, os , dbms , mp,aoa;

//    -----Firebase Firestore-----
    FirebaseFirestore dbroot;


    public Attendance() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        what is Inflater
        View view =  inflater.inflate(R.layout.fragment_attendance, container, false);

//       you cannot find the elements directly in fragment
//        you need to first create a View method and then return it
        BarChart bar = view.findViewById(R.id.BarChart);
        Button button = view.findViewById(R.id.button);
        math = view.findViewById(R.id.math);
        os = view.findViewById(R.id.OS);
        dbms = view.findViewById(R.id.DBMS);
        mp = view.findViewById(R.id.MP);
        aoa = view.findViewById(R.id.AOA);

//        -----Firebase Firestore-----
        dbroot = FirebaseFirestore.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //        -----attendance input-----

                fetchdata();


//        -----All The Instructions-----
//       S1 ===   Create Record
                ArrayList<BarEntry> record = new ArrayList<>();
                record.add(new BarEntry(1,math_atd));
                record.add(new BarEntry(2,os_atd));
                record.add(new BarEntry(3,dbms_atd));
                record.add(new BarEntry(4,mp_atd));
                record.add(new BarEntry(5,aoa_atd));



//        Create Bar Dataset
                BarDataSet dataset = new BarDataSet(record , "report");
                dataset.setValueTextColor(Color.WHITE);
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                dataset.setValueTextSize(20f);

//        Create Bar Data
                BarData bardata = new BarData(dataset);
                bar.setData(bardata);
                bar.setFitBars(true);
                bar.getDescription().setText("Attendance(in %)") ;
                bar.getDescription().setTextSize(14f);
                bar.getDescription().setTextColor(Color.WHITE);
                bar.animateY(2000);
            }
        });


        return view;
    }

    public void fetchdata(){
        DocumentReference Name = dbroot.collection("attendance").document("0XTWLnKX3jWyWnEDVbCn");
        Name.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                   math.setText("Math : "+documentSnapshot.getString("Math")+"%");
                   math_at = documentSnapshot.getString("Math").toString();
                   math_atd = Integer.parseInt(math_at);

                    os.setText("OS : "+documentSnapshot.getString("OperatingSystem")+"%");
                    os_at = documentSnapshot.getString("OperatingSystem").toString();
                    os_atd = Integer.parseInt(os_at);

                    dbms.setText("DBMS : "+documentSnapshot.getString("DBMS")+"%");
                    dbms_at = documentSnapshot.getString("DBMS").toString();
                    dbms_atd = Integer.parseInt(dbms_at);

                    mp.setText("MP : "+documentSnapshot.getString("MicroProcessor")+"%");
                    mp_at = documentSnapshot.getString("MicroProcessor").toString();
                    mp_atd = Integer.parseInt(mp_at);

                    aoa.setText("MP : "+documentSnapshot.getString("AnalysisOfAlgorithms")+"%");
                    aoa_at = documentSnapshot.getString("AnalysisOfAlgorithms").toString();
                    aoa_atd = Integer.parseInt(aoa_at);

                }
                else{
                    Toast.makeText(getActivity(), "Failed to Fetch", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed to Fetch", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

//To implement barchar , added maven repository in the settings.gradle->repositories.
//added dependency in the build.gradle(module)