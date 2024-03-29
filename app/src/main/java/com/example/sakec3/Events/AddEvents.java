package com.example.sakec3.Events;

import static android.app.Activity.RESULT_OK;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.example.sakec3.R;
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


public class AddEvents extends Fragment {
//    **-----VARIABLES : content -----**
    //title and description
    private EditText title;
    private EditText description;

    //Contact Details and Registration link
    private EditText name1,phn1,name2,phn2;
    private EditText Regstration_link;

//    Spinner
    Spinner select_year;
    String selectyear;

    //    Noification
    private static final String CHANNEL_ID = "EVENTS";
    private static final int Events_ID = 100;
    NotificationManager nm;
    Notification NewEvent;

//    Global Variables
    String eventTitle,eventDescription,eventName1,eventName2,eventPhn1,eventPhn2,eventRegLink;


//    **-----VARIABLES : ADD Image from gallery ------**
    //STEP 1 : opening Gallery
    private ImageView addimage;
    private final int REQ=1;
    //STEP 2 : storing the image in form of bitmap...
    private Bitmap bitmap;
    //show image in frame card view
    private ImageView showimage;
    //Button
    private Button upload;
//    imguri
    private String imgurl="";

//    progress Dialogue
    private ProgressDialog pd;

    //FireBase RealTime Database
    //Storage Reference
    FirebaseStorage imgstorage;
    StorageReference imgstorageReference = imgstorage.getInstance().getReference();

    //Database Reference
    FirebaseDatabase imgdata;
    DatabaseReference imgdata_reference = imgdata.getInstance().getReference();




    EditText content;

    public AddEvents() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_events, container, false);
        content = v.findViewById(R.id.description);
        content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (view.getId()==R.id.description)
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

        //Notifications:
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.sakeclogo1,null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        nm = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        NewEvent = new Notification.Builder(getContext())
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.sakeclogo1)
                .setContentText("New Event")
                .setSubText("Sakec is organizing a new event")
                .setChannelId(CHANNEL_ID)
                .build();
        nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"EVENT",NotificationManager.IMPORTANCE_HIGH));
        nm.notify(Events_ID,NewEvent);

//        **-----Add Image from Gallery-----**
        addimage = v.findViewById(R.id.addimage);
        showimage = v.findViewById(R.id.showimage);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
//        -----------------End OF ADD Image--------------------

//        **-----Content of Event-----**
        title = v.findViewById(R.id.title);
        description = v.findViewById(R.id.description);
        name1=v.findViewById(R.id.name1);
        phn1=v.findViewById(R.id.phn1);
        name2=v.findViewById(R.id.name2);
        phn2=v.findViewById(R.id.phn2);
        Regstration_link=v.findViewById(R.id.reg);
        upload=v.findViewById(R.id.uploadevent);
        select_year = v.findViewById(R.id.select_year);
        pd = new ProgressDialog(getActivity());


//        spinner
        ArrayAdapter<CharSequence> yearlist = ArrayAdapter.createFromResource(
                getActivity(), R.array.year , com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item);
        select_year.setAdapter(yearlist);
        select_year.setPrompt("Year");
        select_year.setBackgroundColor(Color.WHITE);

        select_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectyear = select_year.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().isEmpty()){
                    title.setError("Empty");
                    title.requestFocus();
                }
                else if(bitmap==null){
                    selectyear = select_year.getSelectedItem().toString();
                    if(selectyear.equals("FE")){
                    uploadDataFE(); }
                    else if(selectyear.equals("SE")){
                        uploadDataSE();
                    }
                    else if(selectyear.equals("TE")){
                        uploadDataTE();
                    }
                    else if(selectyear.equals("BE")){
                        uploadDataBE();
                    }
                }else{
                    selectyear = select_year.getSelectedItem().toString();
                    if(selectyear.equals("FE")){
                    uploadimageFE(); }
                    else if(selectyear.equals("SE")){
                        uploadimageSE();
                    }
                    else if(selectyear.equals("TE")){
                        uploadimageTE();
                    }
                    else if(selectyear.equals("BE")){
                        uploadimageBE();
                    }
                }

            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ && resultCode == RESULT_OK){
            Uri img_resource = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),img_resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            showimage.setImageBitmap(bitmap);
        }

    }

    public void openGallery(){
        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage,REQ);
    }
    private void uploadimageFE() {
            pd.setMessage("Uploading...");
            pd.show();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] finalimg = baos.toByteArray();  //array created to store the image in java
            final StorageReference imgpath;
            imgpath = imgstorageReference.child("Events").child("FE").child(finalimg + "jpg");
            final UploadTask uploadtask = imgpath.putBytes(finalimg);
            getActivity();
            uploadtask.addOnCompleteListener(getActivity(), new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        uploadtask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imgpath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imgurl = String.valueOf(uri);
                                        uploadDataFE();
                                    }
                                });
                            }
                        });
                    } else {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }//---------uploadimage FE function ends

    private void uploadDataFE() {
            imgdata_reference = imgdata_reference.child("Events").child("FE");
            final String uniqueKey = imgdata_reference.push().getKey();
            eventTitle = title.getText().toString();
            eventDescription = description.getText().toString();
            selectyear = select_year.getSelectedItem().toString();
            eventName1 = name1.getText().toString();
            eventName2 = name2.getText().toString();
            eventPhn1 = phn1.getText().toString();
            eventPhn2 = phn2.getText().toString();
            eventRegLink = Regstration_link.getText().toString();

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

            Eventsgetset eventsgetset = new Eventsgetset(eventTitle,eventDescription,selectyear ,eventName1,eventName2 ,eventPhn1,eventPhn2,eventRegLink,imgurl,date,time, uniqueKey);
            imgdata_reference.child(uniqueKey).setValue(eventsgetset).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Event Uploaded", Toast.LENGTH_SHORT).show();
                    nm.notify(Events_ID,NewEvent);
                    title.setText("");
                    description.setText("");
                    name1.setText("");
                    name2.setText("");
                    phn1.setText("");
                    phn2.setText("");
                    Regstration_link.setText("");


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void uploadimageSE() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();  //array created to store the image in java
        final StorageReference imgpath;
        imgpath = imgstorageReference.child("Events").child("SE").child(finalimg + "jpg");
        final UploadTask uploadtask = imgpath.putBytes(finalimg);
        getActivity();
        uploadtask.addOnCompleteListener(getActivity(), new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadtask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imgpath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgurl = String.valueOf(uri);
                                    uploadDataSE();
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }//---------uploadimage SE function ends

    private void uploadDataSE() {
        imgdata_reference = imgdata_reference.child("Events").child("SE");
        final String uniqueKey = imgdata_reference.push().getKey();
        eventTitle = title.getText().toString();
        eventDescription = description.getText().toString();
        selectyear = select_year.getSelectedItem().toString();
        eventName1 = name1.getText().toString();
        eventName2 = name2.getText().toString();
        eventPhn1 = phn1.getText().toString();
        eventPhn2 = phn2.getText().toString();
        eventRegLink = Regstration_link.getText().toString();

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

        Eventsgetset eventsgetset = new Eventsgetset(eventTitle,eventDescription,selectyear ,eventName1,eventName2 ,eventPhn1,eventPhn2,eventRegLink,imgurl,date,time, uniqueKey);
        imgdata_reference.child(uniqueKey).setValue(eventsgetset).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Event Uploaded", Toast.LENGTH_SHORT).show();
                nm.notify(Events_ID,NewEvent);
                title.setText("");
                description.setText("");
                name1.setText("");
                name2.setText("");
                phn1.setText("");
                phn2.setText("");
                Regstration_link.setText("");


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadimageTE() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();  //array created to store the image in java
        final StorageReference imgpath;
        imgpath = imgstorageReference.child("Events").child("TE").child(finalimg + "jpg");
        final UploadTask uploadtask = imgpath.putBytes(finalimg);
        getActivity();
        uploadtask.addOnCompleteListener(getActivity(), new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadtask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imgpath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgurl = String.valueOf(uri);
                                    uploadDataTE();
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }//---------uploadimage SE function ends

    private void uploadDataTE() {
        imgdata_reference = imgdata_reference.child("Events").child("TE");
        final String uniqueKey = imgdata_reference.push().getKey();
        eventTitle = title.getText().toString();
        eventDescription = description.getText().toString();
        selectyear = select_year.getSelectedItem().toString();
        eventName1 = name1.getText().toString();
        eventName2 = name2.getText().toString();
        eventPhn1 = phn1.getText().toString();
        eventPhn2 = phn2.getText().toString();
        eventRegLink = Regstration_link.getText().toString();

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

        Eventsgetset eventsgetset = new Eventsgetset(eventTitle,eventDescription,selectyear ,eventName1,eventName2 ,eventPhn1,eventPhn2,eventRegLink,imgurl,date,time, uniqueKey);
        imgdata_reference.child(uniqueKey).setValue(eventsgetset).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Event Uploaded", Toast.LENGTH_SHORT).show();
                nm.notify(Events_ID,NewEvent);
                title.setText("");
                description.setText("");
                name1.setText("");
                name2.setText("");
                phn1.setText("");
                phn2.setText("");
                Regstration_link.setText("");


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadimageBE() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();  //array created to store the image in java
        final StorageReference imgpath;
        imgpath = imgstorageReference.child("Events").child("BE").child(finalimg + "jpg");
        final UploadTask uploadtask = imgpath.putBytes(finalimg);
        getActivity();
        uploadtask.addOnCompleteListener(getActivity(), new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadtask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imgpath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgurl = String.valueOf(uri);
                                    uploadDataBE();
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }//---------uploadimage SE function ends

    private void uploadDataBE() {
        imgdata_reference = imgdata_reference.child("Events").child("BE");
        final String uniqueKey = imgdata_reference.push().getKey();
        eventTitle = title.getText().toString();
        eventDescription = description.getText().toString();
        selectyear = select_year.getSelectedItem().toString();
        eventName1 = name1.getText().toString();
        eventName2 = name2.getText().toString();
        eventPhn1 = phn1.getText().toString();
        eventPhn2 = phn2.getText().toString();
        eventRegLink = Regstration_link.getText().toString();

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

        Eventsgetset eventsgetset = new Eventsgetset(eventTitle,eventDescription,selectyear ,eventName1,eventName2 ,eventPhn1,eventPhn2,eventRegLink,imgurl,date,time, uniqueKey);
        imgdata_reference.child(uniqueKey).setValue(eventsgetset).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Event Uploaded", Toast.LENGTH_SHORT).show();
                nm.notify(Events_ID,NewEvent);
                title.setText("");
                description.setText("");
                name1.setText("");
                name2.setText("");
                phn1.setText("");
                phn2.setText("");
                Regstration_link.setText("");


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}