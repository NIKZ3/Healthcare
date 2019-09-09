package com.t1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;

public class p_activity extends AppCompatActivity {


    public void pregister(View view)
    {

        Intent intent = new Intent(p_activity.this,pregister_activity.class);
        startActivity(intent);

    }

    public void plogin(View view)
    {
        Intent intent = new Intent(p_activity.this,plogin.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_activity);
    }
}
