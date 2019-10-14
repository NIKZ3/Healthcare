package com.t1;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class app_adapter extends RecyclerView.Adapter<app_adapter.ViewHolder>{

    private Context mcontext;
    private ArrayList<appmodel> mlist;
    private OnItemClickListener mlistener;
    Intent intent;
    String status1,pat_doc,patid;
    FirebaseFirestore db;
    private CollectionReference mref, mref1;
    FirebaseAuth firebaseAuth;


    public interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlistener = listener;
    }



    app_adapter(Context context, ArrayList<appmodel> list) {
        mcontext = context;
        mlist = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);

        View view = layoutInflater.inflate(R.layout.doc_appcard,parent,false);

        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final appmodel appitem=mlist.get(position);

        TextView pname = holder.pname;
        TextView pdate = holder.pdate;
        TextView timing = holder.timing;
        TextView status = holder.status;
        Button complete=holder.comp_treat;


        pdate.setText("DATE:"+appitem.getdate());
        pname.setText("Patient Name:"+appitem.getpname());
        timing.setText("Reporting Timing:"+appitem.gettiming());
        status.setText("Status:"+appitem.getstatus());

        status1=appitem.getstatus();
        if(status1.equals("confirmed"))
        {
            complete.setVisibility(View.VISIBLE);
            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final View view1=view;
                    db = FirebaseFirestore.getInstance();
                    firebaseAuth = FirebaseAuth.getInstance();
                    String user = firebaseAuth.getCurrentUser().getUid();
                    mref = db.collection("doctors").document(user).collection("appointment");
                    String documentid=appitem.getId();
                    mref.document(documentid).update("completed","true").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(view1.getContext(),"Treatment Completed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    mref.document(documentid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful())
                            {

                                DocumentSnapshot document  = task.getResult();
                                if(document.exists())
                                {
                                    pat_doc=document.get("docref").toString();
                                    patid=document.get("patientuid").toString();

                                    mref1=db.collection("patients").document(patid).collection("appointment");
                                    mref1.document(pat_doc).update("completed","true").addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Log.d("treatment completed","Success");
                                                //Toast.makeText(appdetail.this,"Time allotted Successfully",Toast.LENGTH_LONG).show();
                                            }


                                            else
                                            {
                                                Log.d("patient appointment", "fail");
                                            }
                                        }
                                    });
                                }

                            }
                        }
                    });

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView pname,timing,status,pdate;
        Button comp_treat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pdate = itemView.findViewById(R.id.patdate);
            pname = itemView.findViewById(R.id.patname);
            timing = itemView.findViewById(R.id.pattiming);
            status = itemView.findViewById(R.id.patstatus);
            comp_treat=itemView.findViewById(R.id.button7);
            comp_treat.setVisibility(View.INVISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(mlistener!=null)
                    {

                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mlistener.onItemClick(position);
                        }
                    }


                }
            });

        }
    }
}