package com.example.sakec3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.sakec3.signin.SignIn;
import com.example.sakec3.teacher.TeacherLogin;


public class MainActivity extends AppCompatActivity {
    private ImageView studentsignin;
    private ImageView teachersignin;
    private ImageView Sakeclogo;
    private TextView reg,login;

//    Noification
    private static final String CHANNEL_ID = "EVENTS";
    private static final int Events_ID = 100;

//    Animation
    static public Animation TopAnim , BottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.sakeclogo1,null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

//        NOTIFICATION

//        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        Notification NewEvent = new Notification.Builder(this)
//                .setLargeIcon(largeIcon)
//                .setSmallIcon(R.drawable.sakeclogo1)
//                .setContentText("New Event")
//                .setSubText("Sakec is organizing a new event")
//                .setChannelId(CHANNEL_ID)
//                .build();
//        nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"EVENT",NotificationManager.IMPORTANCE_HIGH));
//        nm.notify(Events_ID,NewEvent);

        //load Animations
        TopAnim = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        BottomAnim = AnimationUtils.loadAnimation(this , R.anim.bottom_animation);

//      ---Finding the element from xml file---
        teachersignin = findViewById(R.id.teachersignin);
        teachersignin.setAnimation(BottomAnim);
        Sakeclogo = findViewById(R.id.SakecLogo);
//        reg = findViewById(R.id.register);
//        login = findViewById(R.id.Login);

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

//        reg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, Registration.class));
//            }
//        });
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