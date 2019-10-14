package com.t1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class app_list extends AppCompatActivity {
    FirebaseFirestore db;
    private CollectionReference mref,mref1;
    FirebaseAuth firebaseAuth;
    ArrayList<appmodel> applist;
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
        setContentView(R.layout.activity_applist);

        intent=getIntent();

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        String docid = firebaseAuth.getCurrentUser().getUid();
        mref1 = db.collection("doctors").document(docid).collection("appointment");

        recyclerView = findViewById(R.id.rv1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
        recyclerView.setLayoutManager(rvLiLayoutManager);
        applist=new ArrayList<>();

        mref1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document : task.getResult())
                    {
                        if(!document.get("status").equals("cancel")) {
                            applist.add(new appmodel(document.get("patientname").toString(),
                                    document.get("Timing").toString(),
                                    document.get("status").toString(),
                                    document.get("date").toString(),
                                    document.getId().toString()
                            ));
                        }


                    }
                    Log.d("Status","added to applist");
                    app_adapter aadapter = new app_adapter(app_list.this,applist);
                    recyclerView.setAdapter(aadapter);
                    aadapter.setOnItemClickListener(new app_adapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {

                            //We put uid of doctor in intent so that it is easier to extract doctor info
                            Intent intent1 = new Intent(app_list.this,appdetail.class);
                            intent1.putExtra("docid",applist.get(position).getId());
                            startActivity(intent1);


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
