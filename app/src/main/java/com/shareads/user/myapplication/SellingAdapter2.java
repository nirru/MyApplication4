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
 * Created by user on 11/14/2016.
 */

public class SellingAdapter2 extends RecyclerView.Adapter<SellingAdapter2.MyViewHolder2> {
        private ArrayList<book> ItemLst;
        private Context context;

        public SellingAdapter2(Context context, ArrayList<book> ItemLst) {
            this.ItemLst = ItemLst;
            this.context=context;
        }


public class MyViewHolder2 extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView image;

    public MyViewHolder2(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.txt);
        image=(ImageView)view.findViewById(R.id.image);
    }
}



    @Override
    public SellingAdapter2.MyViewHolder2 onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topselling_row, parent, false);

        return new SellingAdapter2.MyViewHolder2(itemView);
    }

    @Override
    public void onBindViewHolder(SellingAdapter2.MyViewHolder2 holder, int i) {
        holder.title.setText(ItemLst.get(i).getBook_name());
        holder.image.setImageResource(ItemLst.get(i).getBook_image());
        holder.image.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                              //  PopupMenu pop=new PopupMenu(SellingAdapter2.this,image);
                                            }
                                        }
        );
    }



    @Override
    public int getItemCount() {
        return ItemLst.size();
    }

}
