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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class plogin extends AppCompatActivity {

    private FirebaseAuth mAuth;


    TextView emailid,password;
    String email,pass;

    public void p_login(View view)
    {

        mAuth = FirebaseAuth.getInstance();

        emailid = (TextView) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        email = emailid.getText().toString();
        pass  = password.getText().toString();

        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            Log.d("LOGIN", "signInWithEmail:success");
                            Intent intent = new Intent(plogin.this,patientinitial.class);
                            startActivity(intent);
                            finish();
                        }

                        else
                        {
                            Log.d("LOGIN", "Failed");
                            Toast.makeText(plogin.this,"Login Denied",Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plogin);
    }
}
