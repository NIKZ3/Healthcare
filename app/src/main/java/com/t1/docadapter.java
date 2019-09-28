package com.t1;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class docadapter extends RecyclerView.Adapter<docadapter.ViewHolder> {


    private Context mcontext;
    private ArrayList<docmodel> mlist;
    private OnItemClickListener mlistener;


    public interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlistener = listener;
    }


    docadapter(Context context, ArrayList<docmodel> list)
    {
        mcontext = context;
        mlist = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);

        View view = layoutInflater.inflate(R.layout.doccard,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        docmodel docitem = mlist.get(position);

        ImageView imageView1 = holder.imageView;
        TextView name1 = holder.name;
        TextView patientxp1 = holder.patientxp;
        TextView review1 = holder.review;
        TextView starttiming1 = holder.starttiming;
        TextView endtiming1 = holder.endtiming;
        TextView consultationfees1 = holder.consultationfees;



            name1.setText(docitem.getName());
            review1.setText("RATING:" + docitem.getReveiw());
            patientxp1.setText("Experience: " + docitem.getPatientxp());

            String cfees = "FEES: "+"Rs."+String.valueOf(docitem.getConsultationfees());
            consultationfees1.setText(cfees);
            String start="Start Time: "+ String.valueOf(docitem.getstartTiming());
            starttiming1.setText(start);
        String end="Closing Time: "+ String.valueOf(docitem.getendTiming());
            endtiming1.setText(end);

            Glide.with(mcontext).load(docitem.getImage()).into(imageView1);





    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,patientxp,review,starttiming,endtiming,consultationfees;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.docimage);
            name = itemView.findViewById(R.id.docname);
            patientxp = itemView.findViewById(R.id.docpatientxp);
            review = itemView.findViewById(R.id.docreview);
            starttiming = itemView.findViewById(R.id.docstarttiming);
            endtiming = itemView.findViewById(R.id.docendtiming);
            consultationfees = itemView.findViewById(R.id.docconsultationfees);

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
