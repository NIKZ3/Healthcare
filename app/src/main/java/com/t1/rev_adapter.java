package com.t1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class rev_adapter extends RecyclerView.Adapter<rev_adapter.ViewHolder>{

    private Context mcontext;
    private ArrayList<revmodel> mlist;
    private rev_adapter.OnItemClickListener mlistener;
    Intent intent;

    public interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(rev_adapter.OnItemClickListener listener)
    {
        mlistener = listener;
    }



    rev_adapter(Context context, ArrayList<revmodel> list) {
        mcontext = context;
        mlist = list;
    }

    @NonNull
    @Override
    public rev_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);

        View view = layoutInflater.inflate(R.layout.rev_appcard,parent,false);

        rev_adapter.ViewHolder viewHolder=new rev_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull rev_adapter.ViewHolder holder, int position) {
        revmodel appitem=mlist.get(position);

        TextView review = holder.review;
        TextView rating = holder.rating;


        review.setText("Review: "+appitem.getreview());
        rating.setText("Rating"+appitem.getrating());


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView review,rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            review = itemView.findViewById(R.id.review);
            rating = itemView.findViewById(R.id.rating);



        }
    }
}
