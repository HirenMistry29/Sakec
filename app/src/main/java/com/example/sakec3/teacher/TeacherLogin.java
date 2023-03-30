package com.example.sakec3.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sakec3.R;

public class TeacherLogin extends AppCompatActivity {
    private EditText email , employeeid , password;
    private Button loginbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        employeeid = findViewById(R.id.employeeid);
        loginbutton = findViewById(R.id.login);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailid = email.getText().toString();
                String pass = password.getText().toString();
                String empno = employeeid.getText().toString();
                startActivity(new Intent(TeacherLogin.this , teacherdashboard.class));
//                if(empno.equals("1234") && pass.equals("1234") && emailid.equals("faculty") ){
//                    startActivity(new Intent(TeacherLogin.this , teacherdashboard.class));
//                    finish();
//                }
//                else{
//                    Toast.makeText(TeacherLogin.this, "error", Toast.LENGTH_SHORT).show();
//                }

            }
        });

    }
}