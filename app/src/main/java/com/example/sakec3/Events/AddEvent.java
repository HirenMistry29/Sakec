package com.example.sakec3.Events;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.sakec3.R;

public class AddEvent extends AppCompatActivity {
    EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        content = findViewById(R.id.content);
        content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (view.getId()==R.id.content)
                {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP: view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
             }
        });
    }
}