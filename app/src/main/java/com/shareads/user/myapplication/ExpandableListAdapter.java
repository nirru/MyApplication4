package com.shareads.user.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 12/3/2016.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<nav_item> mNavItems;
    private HashMap<nav_item, List<nav_SubItem>> NavSubItems;

    public ExpandableListAdapter(Context context, ArrayList<nav_item> navItems,HashMap<nav_item, List<nav_SubItem>> navSubItems) {
        mContext = context;
        mNavItems = navItems;
         NavSubItems= navSubItems;
    }

    @Override
    public int getGroupCount() {
        return mNavItems.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return NavSubItems.get(mNavItems.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mNavItems.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return NavSubItems.get(mNavItems.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
      // nav_item MenuName = (nav_item) getGroup(i);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.drawer_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.title);
        ImageView image=(ImageView)convertView.findViewById(R.id.icon) ;

       item.setText( mNavItems.get(i).mTitle );
       image.setImageResource(mNavItems.get(i).mIcon);
//        //image.setImageResource(R.id.icon);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
       // final nav_SubItem SubMenuText = (nav_SubItem) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawersubitem, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.subtitle);
        ImageView image = (ImageView) convertView.findViewById(R.id.subicon);

        item.setText( mNavItems.get(childPosition).mTitle );
        image.setImageResource(mNavItems.get(childPosition).mIcon);

        return convertView;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}




