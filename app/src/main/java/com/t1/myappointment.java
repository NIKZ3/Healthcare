package com.t1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class myappointment extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Modelappointment> appointment;
    FirebaseAuth mAuth;
    CollectionReference cref,cref1;
    String url;
    FirebaseFirestore db;


    //Finishes activity on back key pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setData()
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user1 = mAuth.getCurrentUser();
        String id = user1.getUid();

        db = FirebaseFirestore.getInstance();

        cref = db.collection("patients").document(id).collection("appointment");
        cref1 = db.collection("doctors");
        appointment = new ArrayList<>();

        cref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document:task.getResult())
                    {
                        //Document goes into adapter only if it is incomplete and not cancelled
                        if(document.get("completed").equals("false") && !document.get("status").equals("cancel")) {
                            Modelappointment m1 = new Modelappointment(document.get("docname").toString(),
                                    document.get("Timing").toString(), document.getId().toString(),
                                    document.get("doctoruid").toString(), document.get("doctordocument").toString(),
                                    document.get("status").toString());
                            appointment.add(m1);
                        }
                    }

                    appointmentadapter ap = new appointmentadapter(myappointment.this,appointment);
                    recyclerView.setAdapter(ap);


                }
            }
        });





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myappointment);

        recyclerView = findViewById(R.id.rv1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rv1LiLayoutManager = layoutManager;

        recyclerView.setLayoutManager(rv1LiLayoutManager);

        //appointment = new ArrayList<>();



        setData();




    }
}
