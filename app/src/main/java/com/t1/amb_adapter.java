package com.t1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.t1.R;
import com.t1.ambmodel;
import com.t1.docadapter;

import java.util.ArrayList;

public class amb_adapter extends RecyclerView.Adapter<amb_adapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<ambmodel> mlist;
    private amb_adapter.OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);

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

        ambmodel docitem = mlist.get(position);

        TextView name1 = holder.name;




        name1.setText(docitem.getHospital_name());



    }

    @Override
    public int getItemCount() {
        return mlist.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.hname);

        }

    }
}


