package com.t1;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.t1.App.CHANNEL_1_ID;

public class patientinitial extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NotificationManagerCompat notificationManager;
    private FirebaseAuth mAuth;
    TextView username ;
    View header;
    FirebaseFirestore db;
    CollectionReference mref;
    FirebaseAuth firebaseAuth;
    private DrawerLayout drawer;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void sendnotification(String status,String docname)
    {


        if(status.equals("confirmed")) {
            notificationManager = NotificationManagerCompat.from(patientinitial.this);
            Notification notification = new NotificationCompat.Builder(patientinitial.this, CHANNEL_1_ID).setSmallIcon(R.drawable.ic_menu_camera)
                    .setContentTitle("Appointment").setContentText( docname + "Confirmed").setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();
            notificationManager.notify(1, notification);
        }

        else if(status.equals("denied")) {
            notificationManager = NotificationManagerCompat.from(patientinitial.this);
            Notification notification = new NotificationCompat.Builder(patientinitial.this, CHANNEL_1_ID).setSmallIcon(R.drawable.ic_menu_camera)
                    .setContentTitle("Appointment").setContentText(docname + "declined").setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();
            notificationManager.notify(1, notification);
        }

        else if(status.equals("cancel"))
        {
            notificationManager = NotificationManagerCompat.from(patientinitial.this);
            Notification notification = new NotificationCompat.Builder(patientinitial.this, CHANNEL_1_ID).setSmallIcon(R.drawable.ic_menu_camera)
                    .setContentTitle("Appointment").setContentText(docname + "Cancelled").setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();
            notificationManager.notify(1, notification);
        }

    }


//Sets up data change listeneres to all appointments not yet confirmed
    public void appointmentlisteners()
    {

        FirebaseUser user2 = mAuth.getCurrentUser();
        String uid = user2.getUid();
        Log.d("Status",uid);
        db = FirebaseFirestore.getInstance();
        mref = db.collection("patients").document(uid).collection("appointment");

        Query query = mref.whereEqualTo("completed","false");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    Log.d("Status","Successful");



                    for(QueryDocumentSnapshot document: task.getResult())
                    {

                        Log.d("Status","Doc retreived");

                        //Adding listener
                        String id = document.get("docref").toString();
                        DocumentReference docref = mref.document(id);

                        docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.w("Status", "Listen failed.", e);
                                    return;
                                }

                                String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
                                        ? "Local" : "Server";

                                if (snapshot != null && snapshot.exists()) {
                                    Log.d("Status", source + " data: " + snapshot.getData());
                                    //Notification function
                                    //String docname = snapshot.get("docname").toString();
                                    String status = snapshot.get("status").toString();
                                    String docname = snapshot.get("docname").toString();
                                    sendnotification(status,docname);

                                } else {
                                    Log.d("status", source + " data: null");
                                }
                            }
                        });



                    }
                }
                else
                {

                    Log.d("Status","UNSuccessful");

                }

            }
        });



    }

//On click buttons for all categories of doctors
    public void womenhealth(View view)
    {

        //On button press goes to activity doc list where entire card view is visible
        Intent intent = new Intent(patientinitial.this,doclist.class);

        //type tells us which button was pressed 
        intent.putExtra("Type","gyno");
        startActivity(intent);
    }

    public void child(View view)
    {
        Intent intent = new Intent(patientinitial.this,doclist.class);

        intent.putExtra("Type","child");
        startActivity(intent);

    }

    public void skin(View view)
    {
        Intent intent = new Intent(patientinitial.this,doclist.class);

        intent.putExtra("Type","skin");
        startActivity(intent);

    }

    public void dental(View view)
    {
        Intent intent = new Intent(patientinitial.this,doclist.class);

        intent.putExtra("Type","dental");
        startActivity(intent);

    }

  //-----------




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientinitial);
        mAuth = FirebaseAuth.getInstance();

        Toast.makeText(patientinitial.this,"Logged In",Toast.LENGTH_LONG).show();


        //Sets up appointments

        this.appointmentlisteners();



        FirebaseUser user1 = mAuth.getCurrentUser();
        String id1 = user1.getUid();




       // Log.d("UID","yooooo");
      //  Log.d("UID",id1);




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);
        username = (TextView) header.findViewById(R.id.username);
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
        getMenuInflater().inflate(R.menu.patientinitial, menu);
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

            Intent intent = new Intent(patientinitial.this,myappointment.class);
            startActivity(intent);

        }
        /* else if (id == R.id.nav_gallery) {



        }*/

     /*   } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
