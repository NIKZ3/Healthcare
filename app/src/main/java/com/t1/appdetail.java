package com.t1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class appdetail extends AppCompatActivity {

    FirebaseFirestore db;
    private CollectionReference mref, mref1;
    FirebaseAuth firebaseAuth;
    Intent intent;
    String time,date1;
    public String documentid,pat_doc,patid;
    public EditText timing,date2;
    public Button confirm_time,confirm_app;

    public void confirm_appointment(View view) {
        timing.setVisibility(View.VISIBLE);
        confirm_time.setVisibility(View.VISIBLE);
        mref1=db.collection("patients").document(patid).collection("appointment");
        mref.document(documentid).update("status","confirmed","completed","false").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful())
                 {
                     Toast.makeText(appdetail.this,"Appointement confirmed",Toast.LENGTH_LONG).show();
                 }
            }
        });

        mref1.document(pat_doc).update("status","confirmed","completed","false").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Log.d("patient appointement","Success");
                    //Toast.makeText(appdetail.this,"Time allotted Successfully",Toast.LENGTH_LONG).show();
                }


                else
                {
                    Log.d("patient appointment", "fail");
                }
            }
        });

    }

    public void decline_appointment(View view) {
        mref1=db.collection("patients").document(patid).collection("appointment");
        mref.document(documentid).update("status","denied","completed","false").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(appdetail.this,"Appointement denied",Toast.LENGTH_LONG).show();
                }
            }
        });

        mref1.document(pat_doc).update("status","denied","completed","false").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Log.d("patient appointement","Success");
                    //Toast.makeText(appdetail.this,"Time allotted Successfully",Toast.LENGTH_LONG).show();
                }


                else
                {
                    Log.d("patient appointment", "fail");
                }
            }
        });
    }


    public void confirm_time(View view)
    {


        time=timing.getText().toString();
        date1 = date2.getText().toString();

        mref1=db.collection("patients").document(patid).collection("appointment");

        Map<String, Object> data = new HashMap<>();
        data.put("Timing", time);
        data.put("date", date1);



        mref.document(documentid).update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Log.d("Time insert","Success");
                    Toast.makeText(appdetail.this,"Time allotted Successfully",Toast.LENGTH_LONG).show();
                }


                else
                {
                    Log.d("Time Insert", "fail");
                }
            }
        });

        mref1.document(pat_doc).update("Timing",time).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Log.d("Time insert","Success");
                    //Toast.makeText(appdetail.this,"Time allotted Successfully",Toast.LENGTH_LONG).show();
                }


                else
                {
                    Log.d("Time Insert", "fail");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appdetail2);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        final TextView pname = findViewById(R.id.textView5);


        String user = firebaseAuth.getCurrentUser().getUid();
        mref = db.collection("doctors").document(user).collection("appointment");
        intent=getIntent();
        documentid=intent.getStringExtra("docid");

        mref.document(documentid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {

                    DocumentSnapshot document  = task.getResult();
                    if(document.exists())
                    {
                        pname.setText(document.get("patientname").toString());
                        pat_doc=document.get("docref").toString();
                        patid=document.get("patientuid").toString();


                    }

                }
            }
        });

        timing=(EditText) findViewById(R.id.editText);
        date2=(EditText) findViewById(R.id.date);
        confirm_time=(Button)findViewById(R.id.button6);
        confirm_app=(Button)findViewById(R.id.button2);
        timing.setVisibility(View.INVISIBLE);
        confirm_time.setVisibility(View.INVISIBLE);


        // Log.i("patient id",patid);
    }

}