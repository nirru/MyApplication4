package tabs_fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shareads.user.myapplication.R;

import java.util.ArrayList;

/**
 * Created by user on 11/12/2016.
 */

public class CustomList extends ArrayList<String> {

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;

    public CustomList(Activity context, String[] web, Integer[] imageId) {
       // super(context, R.id.list_row, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_row, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;

    }
}

