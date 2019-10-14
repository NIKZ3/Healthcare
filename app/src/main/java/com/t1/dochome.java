package com.t1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.bumptech.glide.Glide;
//import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class dochome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CollectionReference mref;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    Intent intent;
    View header;
    TextView username;

    TextView doc_name,doc_type,doc_rating,doc_xp;
    TextView doc_fees;
    ImageView doc_pic;
    String name,type,rating,xp,image_url,doctoruid,latitude,longitude;
    Long fees;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    public void getdetails()
    {
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        mref =  db.collection("doctors");
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user1=firebaseAuth.getCurrentUser();
        doctoruid=user1.getUid();


        doc_name=(TextView)findViewById(R.id.docname);
        doc_type=(TextView)findViewById(R.id.doctype);
        doc_xp=(TextView)findViewById(R.id.docxp);
        doc_fees=(TextView)findViewById(R.id.docfees);
        doc_rating=(TextView)findViewById(R.id.docrating);
        doc_pic=(ImageView)findViewById(R.id.img);



        mref.document(doctoruid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {

                    DocumentSnapshot document  = task.getResult();
                    if(document.exists())
                    {
                        doc_name.setText(document.get("name").toString());
                        doc_fees.setText("Rs "+document.get("consultationfees").toString());
                        doc_xp.setText(document.get("xp").toString()+" years");
                        //doc_type.setText(document.get("Type").toString());
                        if(document.get("Type").toString().equals("gyno"))
                        {
                            doc_type.setText("Gynaecologist");
                        }
                        else if(document.get("Type").toString().equals("child"))
                        {
                            doc_type.setText("Pediatrician");
                        }
                        else if(document.get("Type").toString().equals("dental"))
                        {
                            doc_type.setText("Dentist");
                        }
                        else if(document.get("Type").toString().equals("skin"))
                        {
                            doc_type.setText("Dermatologist");
                        }
                        doc_rating.setText(document.get("rating").toString()+"/5");
                        image_url=document.get("imageurl").toString();
                        StorageReference storageReference  = storage.getReference().child(image_url).child("images/doc_pic");
                        Glide.with(getApplicationContext()).load(image_url).into(doc_pic);

                    }


                }
                else
                {
                    Log.i("Error","Document not present");
                }
            }
        });


    }

    public void check_appointment(View view)
    {
        Intent intent1 = new Intent(dochome.this,app_list.class);
        startActivity(intent1);
    }

    public void check_reviews(View view)
    {
        Intent intent2=new Intent(dochome.this,review_list.class);
        startActivity(intent2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dochome);


        getdetails();

        FirebaseUser user1 = firebaseAuth.getCurrentUser();
        String id1 = user1.getUid();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);
        username = (TextView) header.findViewById(R.id.username1);
        username.setText(user1.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dochome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_gallery) {
            Intent intent3=new Intent(dochome.this,app_list.class);
            startActivity(intent3);

        } else if (id == R.id.nav_slideshow) {
            Intent intent4=new Intent(dochome.this,review_list.class);
            startActivity(intent4);


        }else if (id == R.id.nav_share) {
            firebaseAuth.getInstance().signOut();
            Intent intent=new Intent(dochome.this,MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}