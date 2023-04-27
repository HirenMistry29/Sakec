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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sakec3.MainActivity;
import com.example.sakec3.R;
import com.example.sakec3.SE.StudentHome;
import com.example.sakec3.StudentProfile;
import com.example.sakec3.studentprofiledisplay;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class TE_Dashboard extends AppCompatActivity {
    static public DrawerLayout drawer;
    static public NavigationView navigate;
    static public Toolbar toolbar;
    public ImageView editprofile;

    //    Profile
    public TextView person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_te_dashboard);
        drawer = findViewById(R.id.navigationdrawer);
        navigate = findViewById(R.id.navigate);
        toolbar = findViewById(R.id.toolbar);
        editprofile = findViewById(R.id.editprofile);



//        -----Edit Profile -----
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TE_Dashboard.this , studentprofiledisplay.class ));
            }
        });
        //        -----Toolbar Setup-----
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this , drawer , toolbar , R.string.OpenDrawer , R.string.CloseDrawer);

        drawer.addDrawerListener(toggle);

        toggle.syncState();

        LoadFragment(new TE_Home(), 0);
        navigate.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id== R.id.home){
                    LoadFragment(new TE_Home(),1);
                }
                else if(id == R.id.logout){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(TE_Dashboard.this, MainActivity.class));
                    finish();
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private  void LoadFragment(Fragment fragment , int flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft =    fm.beginTransaction();
        if(flag==0){
            ft.add(R.id.container , fragment);
        }else if(flag == 1){
            ft.replace(R.id.container , fragment);
        }
        ft.commit();
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

}