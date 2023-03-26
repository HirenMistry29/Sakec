package com.example.sakec3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sakec3.signin.SignIn;
import com.example.sakec3.teacher.TeacherLogin;


public class MainActivity extends AppCompatActivity {
    private ImageView studentsignin;
    private ImageView teachersignin;
    private ImageView Sakeclogo;

//    Animation
    static public Animation TopAnim , BottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load Animations
        TopAnim = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        BottomAnim = AnimationUtils.loadAnimation(this , R.anim.bottom_animation);

//      ---Finding the element from xml file---
        teachersignin = findViewById(R.id.teachersignin);
        teachersignin.setAnimation(BottomAnim);
        Sakeclogo = findViewById(R.id.SakecLogo);

        studentsignin = findViewById(R.id.studentsignin);

//        ---accessig animation---
        Sakeclogo.setAnimation(TopAnim);
        studentsignin.setAnimation(BottomAnim);


//        ---process if we click Student Sign In button---
        studentsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , SignIn.class));
                finish();
            }
        });


//       ---process if we click Student Sign In button---
        teachersignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , TeacherLogin.class));

            }
        });



    }


}


//------Notes-------
//intent = going from one page to another without taking data from a1 to a2
//bundle = going from one page to another , also taking data frm a1 to a2 ,
//            data in a2 will get destroy once the app is closed
//shared prefernece = going from one page to another , also taking data frm a1 to a2 ,
//                    data in a2 will not get destroy once the close
// database = going from one page to another , also taking data frm a1 to a2 ,
//            data in a2 will not get destroy once the close