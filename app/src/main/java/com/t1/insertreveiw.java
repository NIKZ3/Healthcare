package com.t1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.Document;

import java.util.HashMap;
import java.util.Map;

public class insertreveiw extends AppCompatActivity {

    String doctoruid;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText r_content;
    FirebaseFirestore db;
    int reveiw_score;


    //I need to get initial rating score of doctor add the given score and tdoctor rating divide by 2 and
    //update the new rating that way we keep average intact

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    public void setrating()
    {
        String score1 = String.valueOf(reveiw_score);
        Log.d("Outside 6",score1);

        db.collection("doctors").document(doctoruid).update("rating",score1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Log.d("Score Insert","SUccesss");
                            Toast.makeText(insertreveiw.this,"Review given Successfully",Toast.LENGTH_LONG).show();
                        }


                        else
                            {
                            Log.d("Score Insert", "SUccesss");
                        }
                    }
                });
    }


    public void submitreveiw(View view)
    {
        int radioid = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioid);
        String reveiw_string = radioButton.getText().toString();
        reveiw_score = Integer.parseInt(reveiw_string);
        String reveiw_content = r_content.getText().toString();

        Map<String,String> reveiw_data = new HashMap<>();

        reveiw_data.put("score",reveiw_string);
        reveiw_data.put("reveiw_content",reveiw_content);

        db.collection("doctors").document(doctoruid).collection("reveiws").add(reveiw_data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful())
                        {
                            Log.d("STATUS REVEIW","Successful");
                        }

                        else
                        {
                            Log.d("STATUS REVEIW","UNsuccessful");
                        }

                    }
                });

        db.collection("doctors").document(doctoruid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    int rating = Integer.parseInt(document.get("rating").toString());
                    if(rating==0)
                    {
                        setrating();
                        Log.d("IN 6 rating","6");

                    }

                    else
                    {
                        reveiw_score = (reveiw_score + rating)/2;
                        Log.d("Outside 6",document.get("rating").toString());

                        setrating();
                    }

                }


            }
        });




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertreveiw);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        doctoruid = intent.getStringExtra("doctoruid");

        radioGroup = findViewById(R.id.reveiwgroup);
        r_content =  findViewById(R.id.reveiwcontent);
    }
}
