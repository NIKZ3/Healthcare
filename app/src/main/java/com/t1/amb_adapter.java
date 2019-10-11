package com.t1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.t1.R;
import com.t1.ambmodel;
import com.t1.docadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class amb_adapter extends RecyclerView.Adapter<amb_adapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<ambmodel> mlist;
    private amb_adapter.OnItemClickListener mlistener;
    private String dial;


    public interface OnItemClickListener{
        void onItemClick(int position);

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        final int REQUEST_CALL=1;
        if(requestCode == REQUEST_CALL)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mcontext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            {
                mcontext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
            else
            {
               // Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }




    }


    public void setOnItemClickListener(amb_adapter.OnItemClickListener listener)
    {
        mlistener = listener;
    }

    amb_adapter(Context context, ArrayList<ambmodel> list)
    {
        mcontext = context;
        mlist = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);

        View view = layoutInflater.inflate(R.layout.activity_emergency,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull amb_adapter.ViewHolder holder, int position) {

        final int REQUEST_CALL=1;
        final ambmodel docitem = mlist.get(position);

        TextView name1 = holder.name;
        ImageView ambcall1 = holder.ambcall;
        ImageView book2 = holder.book1;
        final Context context = docitem.getcontext();
        mcontext = context;
       //final Activity a1 = (Activity) context;
        final String number=docitem.gettel().toString();




        name1.setText(docitem.getHospital_name());
        ambcall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dial = "tel:"+ number;
                if(ContextCompat.checkSelfPermission(mcontext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions((Activity) mcontext,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
                }
                else
                {
                    dial = "tel:"+ number;
                    mcontext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }
            }
        });

        book2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
               final  CollectionReference mref =  db.collection("doctors");
               




                        mref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        final String doc_log=document.get("Longitude").toString();
                                        final String doc_lat=document.get("Latitude").toString();
                                        String clinic=document.get("clinicname").toString();




                                        Map<String,Object> data_user = new HashMap<>();
                                        data_user.put("Patient Longitude",11);
                                        data_user.put("Patient Latitude",11);

                                        data_user.put("Doctor Longitude",doc_log);
                                        data_user.put("Doctor Latitude",doc_lat);
                                        data_user.put("Clinic name",clinic);
                                        data_user.put("booked","true");

                                        db.collection("ambulance")
                                                .add(data_user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {



                                                        Log.i("message","success");

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.i("message","failure");
                                                    }
                                                });


                                    }
                                }
                            }
                        });



                    }
        });




    }

    @Override
    public int getItemCount() {
        return mlist.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView ambcall;
        ImageView book1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.hname);
            ambcall = itemView.findViewById(R.id.phone_call);
            book1 = itemView.findViewById(R.id.imageView);

        }

    }
}


