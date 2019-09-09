package com.t1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class pregister_activity extends AppCompatActivity {

   private CollectionReference mref;
    private FirebaseAuth mAuth;

    TextView emailid,name,password;
    String user_name,email,pass;
    FirebaseFirestore db = FirebaseFirestore.getInstance();




    public void pregister(View view)
    {

        mAuth = FirebaseAuth.getInstance();

        emailid = (TextView) findViewById(R.id.email);
        name = (TextView) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
       mref=db.collection("patients");


        email = emailid.getText().toString();
        pass  = password.getText().toString();
         user_name= name.getText().toString();



        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            FirebaseUser user1 = mAuth.getCurrentUser();
                            String user_uid = user1.getUid();

                            Map<String,Object> data_user = new HashMap<>();
                            data_user.put("Name",user_name);
                            data_user.put("Medical Conditions","NONE");
                            data_user.put("UID",user_uid);

                            mref.document(user_uid).set(data_user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {


                                            Log.d("Current","Insertion completed");

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Log.d("Current","Insertion failed");
                                        }
                                    });

                            Intent intent = new Intent(pregister_activity.this,patientinitial.class);

                            startActivity(intent);
                            finish();


                           // Log.d("Register","success");

                            //Log.d("Register",user1.getUid());
                        }

                        else
                        {
                            Toast.makeText(pregister_activity.this,"NOOO",Toast.LENGTH_LONG).show();
                            Log.d("Register","USer creation Failed");
                        }
                    }
                });

        Toast.makeText(pregister_activity.this,"YOOO",Toast.LENGTH_LONG);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregister_activity);

       /*

        mAuth.createUserWithEmailAndPassword("n1@gmail.com","NIKHIL")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            String uid = mAuth.getUid();
                            Toast.makeText(pregister_activity.this,"YOOO",Toast.LENGTH_LONG);
                            Log.d("Register","USer creation success");
                        }

                        else
                        {
                            Toast.makeText(pregister_activity.this,"NOOO",Toast.LENGTH_LONG);
                            Log.d("Register","USer creation Failed");
                        }
                    }
                });*/

    }
}
