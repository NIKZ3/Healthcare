package com.t1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class review_list extends AppCompatActivity {

    FirebaseFirestore db;
    private CollectionReference mref,mref1;
    FirebaseAuth firebaseAuth;
    ArrayList<revmodel> revlist;
    Intent intent;
    RecyclerView recyclerView;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        intent=getIntent();

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        String docid = firebaseAuth.getCurrentUser().getUid();
        mref1 = db.collection("doctors").document(docid).collection("reveiws");

        recyclerView = findViewById(R.id.rv2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
        recyclerView.setLayoutManager(rvLiLayoutManager);
        revlist=new ArrayList<>();

        mref1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document : task.getResult())
                    {
                        revlist.add(new revmodel(document.get("score").toString(),
                                document.get("reveiw_content").toString()
                        ));


                    }
                    Log.d("Status","added to revlist");
                    rev_adapter radapter = new rev_adapter(review_list.this,revlist);
                    recyclerView.setAdapter(radapter);

                }
                else
                {

                    Log.d("Status","failed");
                }
            }

        });
    }

}

