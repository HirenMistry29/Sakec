package com.example.sakec3.studentdetails;

import static java.util.Calendar.getInstance;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sakec3.R;
import com.example.sakec3.SE.StudentHome;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class studentdetails extends AppCompatActivity {
    private Button update;
    static public EditText firstname , middlename , lastname;
    private EditText sakecemail , phnno ;
    private EditText registrationno , smartcardno;
    private EditText div , rollno;

//    Spinner
    private Spinner branch , year;
    int flag=0;
    int stick=0;
    int hat=0;

    //String
    static String first_name , middle_name , last_name;
    static String sakec_email , mobile;
    static String registration_no , smart_card;
    static String yr , division , roll_no;
    static String Branch , Year;
//    static String branchid;

//    FireBase Firestore
    FirebaseFirestore dbroot;
    FirebaseDatabase std_details;
    DatabaseReference std_details_reference =  std_details.getInstance().getReference();

//    FireBase Auth
    FirebaseAuth auth;
    EditText password;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdetails);

//      ---finding elements in studentdetails.xml file---
        update = findViewById(R.id.btnContinue);
        firstname=findViewById(R.id.firstname);
        middlename=findViewById(R.id.middlename);
        lastname=findViewById(R.id.lastname);
        sakecemail=findViewById(R.id.sakecemail);
        phnno = findViewById(R.id.phnno);
        branch = findViewById(R.id.branch);
        registrationno = findViewById(R.id.registrationno);
        smartcardno=findViewById(R.id.smartcardno);
        year=findViewById(R.id.clas);
        div=findViewById(R.id.div);
        rollno=findViewById(R.id.rollno);
        password = findViewById(R.id.password);

        //        -----FireBase-----
        dbroot = FirebaseFirestore.getInstance();

//        --------FireBase Auth--------------------
        auth = FirebaseAuth.getInstance();




//       ---Branch Spinner(Drop Down list)---
        ArrayAdapter<CharSequence> branchlist = ArrayAdapter.createFromResource(
                this , R.array.branch , com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item );
            branch.setAdapter(branchlist);
            branch.setPrompt("Branch");
            branch.setBackgroundColor(Color.BLACK);
            branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                    if(parent.getItemAtPosition(i).equals("Select Branch")){
                        //do nothing

                    }
                    else{
                        flag = 1;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

//            ----Year Spinner(Drop Down List)---
        ArrayAdapter<CharSequence> yearlist = ArrayAdapter.createFromResource(
                this , R.array.year , com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item );
        year.setAdapter(yearlist);
        year.setPrompt("Year");
        year.setBackgroundColor(Color.BLACK);
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if(parent.getItemAtPosition(i).equals("Select year")){
                    //do nothing
                }
                else{
                    stick = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name = firstname.getText().toString();
                last_name = lastname.getText().toString();
                middle_name  = middlename.getText().toString();
                sakec_email = sakecemail.getText().toString();
                mobile = phnno.getText().toString();


//                branchid = branch.getText().toString();
                registration_no = registrationno.getText().toString();
                smart_card = smartcardno.getText().toString();
//                yr = year.getText().toString();
                division = div.getText().toString();
                roll_no = rollno.getText().toString();
                Branch = branch.getSelectedItem().toString();
                Year = year.getSelectedItem().toString();





                if(first_name.isEmpty() || last_name.isEmpty() || middle_name.isEmpty() || sakec_email.isEmpty() || mobile.isEmpty()
                         || flag==0 || stick==0 || division.isEmpty() || roll_no.isEmpty() || mobile.length()<10 ){

                    Toast.makeText(studentdetails.this, "Enter the details", Toast.LENGTH_SHORT).show();
                }else{
                    auth.createUserWithEmailAndPassword(sakecemail.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(studentdetails.this, "Account Created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = dbroot.collection("Users").document(user.getUid());

                            Map<String,Object> details = new HashMap<>();
                            details.put("FirstName",first_name.trim());
                            details.put("LastName",last_name.trim());
                            details.put("MiddleName",middle_name.trim());
                            details.put("SakecEmail",sakec_email.trim());
                            details.put("PhoneNo.",mobile.trim());
                            details.put("RegistrationNo.",registration_no.trim());
                            details.put("SmartCardNo.",smart_card.trim());
                            details.put("Divison",division.trim());
                            details.put("RollNo.",roll_no.trim());
                            details.put("Branch",Branch.trim());
                            details.put("Year",Year.trim());
                            details.put("Role","1");  // ROLE 1 for SE Students;

                            df.set(details);

//                            insertData();
//                            UploadData();
                            startActivity(new Intent(studentdetails.this, StudentHome.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(studentdetails.this, "Failed to create an account", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }

    public void insertData(){

    }

    private void UploadData(){
        std_details_reference = std_details_reference.child("STUDENTS");
        final String uniqueKey = std_details_reference.push().getKey();
        String Year = year.getSelectedItem().toString();
        String Branch = branch.getSelectedItem().toString();
        StdDetailsgetset details = new StdDetailsgetset(first_name,middle_name,last_name,sakec_email,mobile,registration_no,smart_card,division,roll_no,Year,Branch,uniqueKey);
        std_details_reference.child(uniqueKey).setValue(details).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(studentdetails.this, "Details Uploaded", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(studentdetails.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}