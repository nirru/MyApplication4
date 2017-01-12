package com.shareads.user.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.book;

import java.util.ArrayList;

/**
 * Created by user on 12/1/2016.
 */

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.MyViewHolder2> {
    private ArrayList<book> ItemLst;
    private Context context;

    public orderAdapter(Context context, ArrayList<book> ItemLst) {
        this.ItemLst = ItemLst;
        this.context=context;
    }


    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public TextView track,date;
        public Switch sw;

        public MyViewHolder2(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text);
            image=(ImageView)view.findViewById(R.id.img);
            track=(TextView)view.findViewById(R.id.track);
            date=(TextView)view.findViewById(R.id.deliverydate);
        }
    }



    @Override
    public orderAdapter.MyViewHolder2 onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order, parent, false);

        return new orderAdapter.MyViewHolder2(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder2 holder, int i) {
        holder.title.setText(ItemLst.get(i).getBook_name());
        holder.image.setImageResource(ItemLst.get(i).getBook_image());
    }



    @Override
    public int getItemCount() {
        return ItemLst.size();
    }
}

