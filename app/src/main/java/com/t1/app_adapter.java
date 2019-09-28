package com.t1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class app_adapter extends RecyclerView.Adapter<app_adapter.ViewHolder>{

    private Context mcontext;
    private ArrayList<appmodel> mlist;
    private OnItemClickListener mlistener;

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
        appmodel appitem=mlist.get(position);

        TextView pname = holder.pname;
        TextView timing = holder.timing;
        TextView status = holder.status;


        pname.setText("Patient Name"+appitem.getpname());
        timing.setText("Reporting Timing"+appitem.gettiming());
        status.setText("Status"+appitem.getstatus());

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView pname,timing,status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pname = itemView.findViewById(R.id.patname);
            timing = itemView.findViewById(R.id.pattiming);
            status = itemView.findViewById(R.id.patstatus);

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