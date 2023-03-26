package com.example.sakec3.studentchapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sakec3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class teachersignin extends AppCompatActivity {
private ImageView addimage;
private ImageView showimage;
private Button uploadimg;
private EditText imgtitle;
private String imgurl="";

//for admin to know something is going on...
private ProgressDialog pd;

//---Bitmapinformation used to control the display of a computer screen---
private Bitmap bitmap;

//--Initialization Request Code--
private final int REQ = 1;

//      Firebase Storage Method
    //Storage Reference
    FirebaseStorage imgstorage;
    StorageReference imgstorageReference = imgstorage.getInstance().getReference();

    //Database Reference
    FirebaseDatabase imgdata;
    DatabaseReference imgdata_reference = imgdata.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadnotice);
//        ---finding elements in the teacher signin.xml file ---
        addimage = findViewById(R.id.addimage);
        showimage = findViewById(R.id.showimage);
        uploadimg = findViewById(R.id.uploadimg);
        imgtitle = findViewById(R.id.imagetitle);


        //    initialising the Progress dialog
        pd = new ProgressDialog(this);

//       --- method to add the image from the gallery to the app---
        addimage.setOnClickListener((view) ->{openGallery();} );


//      --- Method to add the image to the firebase---
        imgtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgtitle.getText().toString().isEmpty()){
                    imgtitle.setError("EMPTY");
                    imgtitle.requestFocus();
                }else if(bitmap == null){
                    uploadData();
                }
            }
        });
        uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadimage();;
            }
        });
    }


//    Method to Upload the data
    private void uploadData() {
        imgdata_reference = imgdata_reference.child("Notice");
        final String uniqueKey = imgdata_reference.push().getKey();
        String title = imgtitle.getText().toString();

//         -----Date-----
        Calendar calenderdate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");

//        with the help pf getTime method , we find date as well as time
        String date = currentDate.format(calenderdate.getTime());

//        -----Time-----
        Calendar calendertime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm");
        String time = currentTime.format(calendertime.getTime());

//        admin created Notice class
//        includes constructors
//        parameter to pass -> 1. title   2.imageUrl  3.date  4,time  5.key
//
        Notice notice = new Notice(title,imgurl,date,time, uniqueKey);
        imgdata_reference.child(uniqueKey).setValue(notice).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(teachersignin.this, "Notice Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(teachersignin.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

//    MEthod to Upload the image
    private void uploadimage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG , 50 , baos);
        byte[] finalimg = baos.toByteArray();  //array created to store the image in java
        final StorageReference imgpath;
        imgpath = imgstorageReference.child("Notice").child(finalimg+"jpg");
        final UploadTask uploadtask = imgpath.putBytes(finalimg);
        uploadtask.addOnCompleteListener(teachersignin.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadtask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imgpath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgurl = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                }else{
                    pd.dismiss();
                    Toast.makeText(teachersignin.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



//    ---after successfully adding the image---
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK){
//            --Uniform Resource Identifier--
//            The purpose of URI is to create a standard set of rules by which we can perform operations like
//            access, update, replace, find attributes, etc.
            Uri img_resource = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), img_resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            showimage.setImageBitmap(bitmap);
        }
    }

    public void openGallery(){
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }

}

