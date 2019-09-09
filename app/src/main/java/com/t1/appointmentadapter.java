package com.t1;

import android.content.Context;
import android.icu.text.Normalizer2;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class appointmentadapter extends RecyclerView.Adapter<appointmentadapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<Modelappointment> mlist;
    String doctor_uid,doctor_document;
    FirebaseFirestore db;
    DocumentReference dref1,dref2; //dref1 for patient dref2 for doctor
    FirebaseAuth mAuth;
    appointmentadapter(Context context, ArrayList<Modelappointment> list)
    {
        mcontext = context;
        mlist=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.appcard,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Modelappointment appointment = mlist.get(position);


        TextView name1 = holder.item_name;
        TextView description1 = holder.item_description;
        Button cancel_button = holder.item_button;

        name1.setText(appointment.getName());
        description1.setText(appointment.getDescription());

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id1 = appointment.getId();
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user1 = mAuth.getCurrentUser();
                String userid = user1.getUid();

                doctor_uid = appointment.getDoctoruid();
                doctor_document = appointment.getDoctordocument();


                //This Map is new update data for the DAtabase
                Map<String,Object> new_data = new HashMap<>();

                new_data.put("status","cancel");
                new_data.put("completed","true");

                db = FirebaseFirestore.getInstance();
                dref1 = db.collection("patients").document(userid)
                        .collection("appointment").document(id1);


                dref1.update(new_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Log.d("APpointment Cancel Status","Sucessful");
                        }

                        else
                        {
                            Log.d("APpointment Cancel Status","UNsucessful");
                        }


                    }
                });

                dref2 = db.collection("doctors").document(doctor_uid)
                        .collection("appointment").document(doctor_document);

                dref2.update(new_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Log.d("APpointment Cancel Status","Sucessful");
                        }

                        else
                        {
                            Log.d("APpointment Cancel Status","UNsucessful");
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

    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView  item_name,item_description;
        Button item_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            item_button = itemView.findViewById(R.id.cancel_button);
            item_name = itemView.findViewById(R.id.appname);
            item_description = itemView.findViewById(R.id.appdesc);
        }
    }
}
