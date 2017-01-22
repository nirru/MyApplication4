package com.shareads.user.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.preferences.BookPreferences;

import java.util.List;

/**
 * Created by user on 11/22/2016.
 */

public class profileadapter extends ArrayAdapter {
    private final Context context;
    List<String> mprofile;
    BookPreferences preferences;


    public profileadapter(Context context, int resource,List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mprofile = objects;
        preferences = new BookPreferences(context);
    }

    @Override
    public int getCount() {
        return mprofile.size();
    }

    @Override
    public String getItem(int position) {
        return mprofile.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        profileadapter.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.profile, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            holder.mprofile.setText(mprofile.get(position));
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (profileadapter.ViewHolder) convertView.getTag();
        }


        //get first letter of each String item
        String firstLetter = String.valueOf(getItem(position).charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(getItem(position));
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        holder.imageView.setImageDrawable(drawable);

        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView mprofile;

        public ViewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.userImage);
            mprofile = (TextView) v.findViewById(R.id.userName);
        }
    }
}

