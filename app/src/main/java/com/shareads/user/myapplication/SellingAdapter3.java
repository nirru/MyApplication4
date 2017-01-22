package com.shareads.user.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 11/15/2016.
 */

public class SellingAdapter3 extends RecyclerView.Adapter<SellingAdapter3.MyViewHolder2> {
    private ArrayList<book> ItemLst;
    private Context context;

    public SellingAdapter3(Context context, ArrayList<book> ItemLst) {
        this.ItemLst = ItemLst;
        this.context=context;
    }


    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public MyViewHolder2(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text);
            image=(ImageView)view.findViewById(R.id.img);
        }
    }



    @Override
    public SellingAdapter3.MyViewHolder2 onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topselling_row2, parent, false);

        return new SellingAdapter3.MyViewHolder2(itemView);
    }

    @Override
    public void onBindViewHolder(SellingAdapter3.MyViewHolder2 holder, int i) {
        holder.title.setText(ItemLst.get(i).getBook_name());
        holder.image.setImageResource(ItemLst.get(i).getBook_image());
    }



    @Override
    public int getItemCount() {
        return ItemLst.size();
    }

}