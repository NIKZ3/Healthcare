package com.t1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Distribution;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class doclist extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<docmodel> doc_list;
    FirebaseFirestore db;
    private CollectionReference mref;
    Intent intent;

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
        setContentView(R.layout.activity_doclist);

        intent = getIntent();

        //Setting Up Recycler View and the adapter
        db = FirebaseFirestore.getInstance();
        mref =  db.collection("doctors");
        Query query = mref.whereEqualTo("Type",intent.getStringExtra("Type"));

        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
        recyclerView.setLayoutManager(rvLiLayoutManager);
        doc_list = new ArrayList<>();

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document : task.getResult())
                    {
                        doc_list.add(new docmodel(document.get("imageurl").toString(),
                                document.get("xp").toString(),
                                document.get("name").toString(),document.get("rating").toString(),
                                (Long)document.get("consultationfees"),
                                document.get("startTime").toString(),document.get("endTime").toString(),document.get("uid").toString(),
                                document.get("Latitude").toString(),document.get("Longitude").toString(),
                                document.get("address").toString()
                        ));

                    }

                    Log.d("Status","success");

                    docadapter doc_adapter = new docadapter(doclist.this,doc_list);
                    recyclerView.setAdapter(doc_adapter);
                    doc_adapter.setOnItemClickListener(new docadapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {

                            //We put uid of doctor in intent so that it is easier to extract doctor info
                            Intent intent = new Intent(doclist.this,docdetail.class);
                            intent.putExtra("uid",doc_list.get(position).getUid());
                            intent.putExtra("latitude",doc_list.get(position).getLatitude());
                            intent.putExtra("longitude",doc_list.get(position).getLongitude());
                            intent.putExtra("address",doc_list.get(position).getAddress());
                            startActivity(intent);


                        }
                    });
                }
                else
                {

                    Log.d("Status","failed");
                }
            }
        });


    }
}
