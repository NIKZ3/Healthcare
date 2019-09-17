package com.t1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class d_activity extends AppCompatActivity {


    public void dlogin(View view)
    {

        Intent intent = new Intent(d_activity.this,doclogin.class);
        startActivity(intent);



    }

    public void dregister(View view)
    {
        Intent intent = new Intent(d_activity.this,doc_register.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_activity);
    }
}
