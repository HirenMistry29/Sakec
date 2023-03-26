package com.example.sakec3.attendance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sakec3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class admin_attendance extends Fragment {
//----FireBase Firestore----
FirebaseFirestore dbroot;
EditText math;
    EditText os;
    EditText dbms;
    EditText mp;
    EditText aoa;

//    String
    String math_at , os_at , dbms_at , mp_at , aoa_at;

//    Int
    int math_atd , os_atd;

    public admin_attendance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.admin_attendance, container, false);
        math = view.findViewById(R.id.math);
        os = view.findViewById(R.id.os);
        dbms = view.findViewById(R.id.dbms);
        mp = view.findViewById(R.id.mp);
        aoa = view.findViewById(R.id.aoa);
        Button update = view.findViewById(R.id.update);

//        -----FireBase FireStore-----

        dbroot = FirebaseFirestore.getInstance();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                math_at = math.getText().toString();
//                math_atd = Integer.parseInt(math_at);

                os_at = os.getText().toString();
//                os_atd = Integer.parseInt(os_at);

                dbms_at = dbms.getText().toString();
                mp_at = mp.getText().toString();
                aoa_at = aoa.getText().toString();
                insertData();
            }
        });

        return view;
    }

    public void insertData(){
//        creating a map to store multiple data in one object
        Map<String , String> details = new HashMap<>();
        details.put("Math", math_at.trim());
        details.put("OperatingSystem",os_at.trim());
        details.put("DBMS",dbms_at.trim());
        details.put("MicroProcessor",mp_at.trim());
        details.put("AnalysisOfAlgorithms",aoa_at.trim());

//        details.put(last_name.trim());
        dbroot.collection("attendance").add(details)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        math.setText(" ");
                        os.setText(" ");
                        dbms.setText(" ");
                        mp.setText(" ");
                        aoa.setText(" ");
//                        lastname.setText(" ");
                        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}