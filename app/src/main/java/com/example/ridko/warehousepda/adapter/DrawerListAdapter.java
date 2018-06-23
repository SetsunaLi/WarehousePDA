package com.example.ridko.warehousepda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.been.DrawerListContent;

import java.util.List;

/**
 * Created by mumu on 2018/3/29.
 */

public class DrawerListAdapter extends ArrayAdapter {
    Context context;
    List<DrawerListContent.DrawerItem> mDate=null;
    public DrawerListAdapter(Context context, int resource, List<DrawerListContent.DrawerItem> object) {
        super(context, resource,object);
        this.context=context;
        this.mDate=object;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_drawer_list, parent, false);
        }

        DrawerListContent.DrawerItem item = mDate.get(position);
        //Set the label
        TextView label1 = (TextView) convertView.findViewById(R.id.drawerItemName);
        label1.setText(item.content);
        ImageView icon = (ImageView) convertView.findViewById(R.id.drawerIcon);
        //Set icon
        icon.setImageResource(item.icon);
        return convertView;
    }
}
