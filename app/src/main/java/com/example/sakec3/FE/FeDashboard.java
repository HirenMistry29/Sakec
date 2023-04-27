package com.example.sakec3.FE;

import static com.example.sakec3.StudentProfile.drawer;
import static com.example.sakec3.StudentProfile.navigate;
import static com.example.sakec3.StudentProfile.toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.sakec3.Events.upload_events;
import com.example.sakec3.MainActivity;
import com.example.sakec3.R;
import com.example.sakec3.SE.StudentHome;
import com.example.sakec3.attendance.admin_attendance;
import com.example.sakec3.studentchapter.teachersignin;
import com.example.sakec3.teacher.teacherdashboard;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class FeDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fe_dashboard);

        drawer = findViewById(R.id.navigationdrawer);
        navigate = findViewById(R.id.navigate);
        toolbar = findViewById(R.id.toolbar);


//        -----Toolbar Setup-----
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this , drawer , toolbar , R.string.OpenDrawer , R.string.CloseDrawer);

        drawer.addDrawerListener(toggle);

        toggle.syncState();

        LoadFragment(new FeStudent_Events(), 0);

        navigate.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.home){
                    LoadFragment(new Fe_Home(),1);
                } else if(id == R.id.Library){

                } else if ( id == R.id.attendance){
                    LoadFragment(new admin_attendance(), 1);
                } else if (id == R.id.quiz){

                } else if (id == R.id.studentchapter) {
//                    LoadFragment(new StudentChapter(),1);
//                    startActivity(new Intent(FeDashboard.this , teachersignin.class));
                }
                else if (id== R.id.Events){
                    LoadFragment(new FeStudent_Events(),1);
                }
                else if (id == R.id.logout){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(FeDashboard.this , MainActivity.class));

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

    private void LoadFragment(Fragment fragment , int flag){
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
}