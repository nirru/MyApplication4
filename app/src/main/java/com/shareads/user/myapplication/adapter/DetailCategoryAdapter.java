package com.shareads.user.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.model.BookCategoryResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 12/10/2016.
 */

public class DetailCategoryAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private Context mContext;
    public List<T> dataSet;
    private static MyClickListener myClickListener;

    public DetailCategoryAdapter(Context mContext,List<T> productLists) {
        this.mContext = mContext;
        this.dataSet = productLists;
    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void animateTo(List<T> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }


    private void applyAndAnimateRemovals(List<T> newModels) {
        for (int i = dataSet.size() - 1; i >= 0; i--) {
            final T model = dataSet.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }


    private void applyAndAnimateAdditions(List<T> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final T model = newModels.get(i);
            if (!dataSet.contains(model)) {
                addItem(i, model);
            }
        }
    }


    private void applyAndAnimateMovedItems(List<T> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final T model = newModels.get(toPosition);
            final int fromPosition = dataSet.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void addItem(T item) {
        if (!dataSet.contains(item)) {
            dataSet.add(item);
            notifyItemInserted(dataSet.size() - 1);
        }
    }

    public void addItem(int position, T model) {
        dataSet.add(position, model);
        notifyItemInserted(position);
    }

    public void removeItem(T item) {
        int indexOfItem = dataSet.indexOf(item);
        if (indexOfItem != -1) {
            this.dataSet.remove(indexOfItem);
            notifyItemRemoved(indexOfItem);
        }
    }

    public T removeItem(int position) {
        final T model = dataSet.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void clearItem(){
        if (dataSet != null)
            dataSet.clear();
    }

    public void moveItem(int fromPosition, int toPosition) {
        final T model = dataSet.remove(fromPosition);
        dataSet.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public T getItem(int index) {
        if (dataSet != null && dataSet.get(index) != null) {
            return dataSet.get(index);
        } else {
            throw new IllegalArgumentException("Item with index " + index + " doesn't exist, dataSet is " + dataSet);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position)!=null? VIEW_ITEM: VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == VIEW_ITEM){
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.cart_row, parent, false);
            vh = new EventViewHolder(itemView);
        }
        else if(viewType == VIEW_PROG){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_item, parent, false);
            vh = new ProgressViewHolder(v);
        }else {
            throw new IllegalStateException("Invalid type, this type ot items " + viewType + " can't be handled");
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof EventViewHolder){
            T dataItem = dataSet.get(position);
            try {
                ((DetailCategoryAdapter.EventViewHolder) holder).title.setText(((BookCategoryResult)dataItem).getTitle());
                ((DetailCategoryAdapter.EventViewHolder) holder).category.setText(((BookCategoryResult)dataItem).getAuthor());
                if(((BookCategoryResult)dataItem).getDiscountprice().trim().equals("0") || ((BookCategoryResult)dataItem).getDiscount().equals("Donate")){
                    ((DetailCategoryAdapter.EventViewHolder) holder).relative_Discount.setVisibility(View.GONE);
                    ((DetailCategoryAdapter.EventViewHolder) holder).price.setText(((BookCategoryResult)dataItem).getPrice());
                }else{
                    ((DetailCategoryAdapter.EventViewHolder) holder).relative_Discount.setVisibility(View.VISIBLE);
                    ((DetailCategoryAdapter.EventViewHolder) holder).price.setText(((BookCategoryResult)dataItem).getDiscountprice());
                    ((DetailCategoryAdapter.EventViewHolder) holder).discount_text.setText("Rs" + ((BookCategoryResult)dataItem).getPrice() + "(" + ((BookCategoryResult)dataItem).getDiscount() + ")");
                }
                if(((BookCategoryResult)dataItem).getFrontimage()!=null && !((BookCategoryResult)dataItem).getFrontimage().equals("")) {
                    Picasso.with(mContext)
                            .load(((BookCategoryResult)dataItem).getFrontimage())
                            .resize(90,90)
                            .centerInside()
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.no_image_available)
                            .into(((DetailCategoryAdapter.EventViewHolder) holder).image);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }else{
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        if (dataSet!=null)
            return dataSet.size();
        else
            return 0;
    }


    private void setFadeAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RESTART, 0.5f, Animation.RESTART, 0.5f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView title;
        public ImageView image;
        public TextView price;
        public TextView category;
        public ImageView img_btn;
        public ImageView minus_button;
        public ImageView plus_button;
        public TextView valueTextView;
        public TextView discount_text;
        public RelativeLayout relative_Discount;
        public EventViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text);
            image = (ImageView) itemView.findViewById(R.id.img);
            price = (TextView) itemView.findViewById(R.id.price);
            category = (TextView) itemView.findViewById(R.id.category);
            img_btn = (ImageView)itemView.findViewById(R.id.cdelete) ;
            minus_button = (ImageView) itemView.findViewById(R.id.minusButton);
            plus_button = (ImageView) itemView.findViewById(R.id.plusButton);
            valueTextView = (TextView)itemView.findViewById(R.id.valueTextView);
            discount_text = (TextView)itemView.findViewById(R.id.discount) ;
            relative_Discount = (RelativeLayout)itemView.findViewById(R.id.relative_discount);
            minus_button.setVisibility(View.GONE);
            plus_button.setVisibility(View.GONE);
            valueTextView.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
            img_btn.setOnClickListener(this);
            img_btn.setVisibility(View.INVISIBLE);
//            minus_button.setOnClickListener(this);
//            plus_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try{
                if(null != myClickListener){
                    myClickListener.onItemClick(getLayoutPosition(), view);
                }else{
                    Toast.makeText(view.getContext(),"Click Event Null", Toast.LENGTH_SHORT).show();
                }
            }catch(NullPointerException e){
                Toast.makeText(view.getContext(),"Click Event Null Ex", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar)v.findViewById(R.id.progress_bar);
        }
    }


    /**
     * y Custom Item Listener
     */

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }


}

