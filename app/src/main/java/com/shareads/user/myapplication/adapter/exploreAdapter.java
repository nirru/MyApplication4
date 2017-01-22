package com.shareads.user.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.book;

import java.util.ArrayList;

/**
 * Created by user on 12/1/2016.
 */

public class exploreAdapter extends RecyclerView.Adapter<exploreAdapter.MyViewHolder2> {

        private ArrayList<book> ItemLst;
        private Context context;

        public exploreAdapter(Context context, ArrayList<book> ItemLst) {
            this.ItemLst = ItemLst;
            this.context=context;
        }


        public class MyViewHolder2 extends RecyclerView.ViewHolder {
            public ImageView image;

            public MyViewHolder2(View view) {
                super(view);
                image=(ImageView)view.findViewById(R.id.image);
            }
        }



        @Override
        public exploreAdapter.MyViewHolder2 onCreateViewHolder(ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.explore ,parent, false);

            return new exploreAdapter.MyViewHolder2(itemView);
        }

        @Override
        public void onBindViewHolder(exploreAdapter.MyViewHolder2 holder, int i) {
            holder.image.setImageResource(ItemLst.get(i).getbook_explore());
        }



        @Override
        public int getItemCount() {
            return ItemLst.size();
        }

    }

