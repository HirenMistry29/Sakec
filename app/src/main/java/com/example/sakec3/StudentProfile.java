package com.example.sakec3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sakec3.Events.Student_Events;
import com.example.sakec3.SE.StudentHome;
import com.example.sakec3.attendance.Attendance;
import com.example.sakec3.signin.SignIn2;
import com.example.sakec3.studentchapter.StudentChapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentProfile extends AppCompatActivity {
    static public DrawerLayout drawer;
    static public NavigationView navigate;
    static public Toolbar toolbar;
    public ImageView editprofile;


//    Profile
      public TextView person;

//      FireBase Firestore
    FirebaseFirestore dbroot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        drawer = findViewById(R.id.navigationdrawer);
        navigate = findViewById(R.id.navigate);
        toolbar = findViewById(R.id.toolbar);
//        person = findViewById(R.id.studentname);
        editprofile = findViewById(R.id.editprofile);




//        FirebaseAuth.getInstance();
        dbroot = FirebaseFirestore.getInstance();


//        -----Edit Profile -----
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentProfile.this , studentprofiledisplay.class ));
            }
        });


//        -----Toolbar Setup-----
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this , drawer , toolbar , R.string.OpenDrawer , R.string.CloseDrawer);

        drawer.addDrawerListener(toggle);

        toggle.syncState();

        LoadFragment(new StudentHome(), 0);

        navigate.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.home){
//                    fetchData();
                    LoadFragment(new StudentHome(),1);
                } else if(id == R.id.Library){

                } else if ( id == R.id.attendance){
                    LoadFragment(new Attendance(), 1);
                } else if (id == R.id.quiz){

                } else if (id == R.id.studentchapter) {
                    LoadFragment(new StudentChapter(),1);
                }
                else if(id == R.id.Events){
                    LoadFragment(new Student_Events(),1);

                }

                else if (id == R.id.logout){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(StudentProfile.this , MainActivity.class));
                    finish();
                }
                else{

                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        //if drawer is open and user presses back , it will close the drawer
        //if the drawer is close , it will work as a back key for app
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    private  void LoadFragment(Fragment fragment , int flag){
//        fragment = new Attendance();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft =    fm.beginTransaction();
        if(flag==0){
            ft.add(R.id.container , fragment);
        }else if(flag == 1){
            ft.replace(R.id.container , fragment);
        }
        ft.commit();
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
                    Toast.makeText(StudentProfile.this, "Failed to Fetch", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentProfile.this, "Failed to Fetch", Toast.LENGTH_SHORT).show();
            }
        });
    }



}

