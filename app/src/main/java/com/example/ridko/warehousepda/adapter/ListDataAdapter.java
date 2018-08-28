package com.example.ridko.warehousepda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.entity.ListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mumu on 2018/6/13.
 */

public class ListDataAdapter extends BaseAdapter {
    private ArrayList<ListEntity> mlist;
    private Context mContext;
    private LayoutInflater mInflater;
    public ListDataAdapter(ArrayList<ListEntity> list, Context context){
        this.mlist=list;
        this.mContext=context;
        this.mInflater=LayoutInflater.from(context);
    }
    public void setData(ArrayList<ListEntity> sg) {
        if (sg!=null) {
            this.mlist = (ArrayList<ListEntity>) sg.clone();
            notifyDataSetChanged();
        }
    }
    private int id=-255;
    public void selectItem(int id){
        this.id=id;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        /*Object result =null;
        if(position<getCount()){
            result = mlist.get(position);
        }
        return result;*/
        return mlist.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            convertView=mInflater.inflate(R.layout.no_item_layout,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.item1=(TextView)convertView.findViewById(R.id.item1);
            viewHolder.item2=(TextView)convertView.findViewById(R.id.item2);
            viewHolder.item3=(TextView)convertView.findViewById(R.id.item3);
            viewHolder.item4=(TextView)convertView.findViewById(R.id.item4);
            viewHolder.item5=(TextView)convertView.findViewById(R.id.item5);
            viewHolder.item6=(TextView)convertView.findViewById(R.id.item6);
            viewHolder.layout=(LinearLayout)convertView.findViewById(R.id.head);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.item1.setText(position+"");
        viewHolder.item2.setText(mlist.get(position).getBuNo()+"");
        viewHolder.item3.setText(mlist.get(position).getBuName()+"");
        viewHolder.item4.setText(mlist.get(position).getRanchangNo()+"");
        viewHolder.item5.setText(mlist.get(position).getCount()+"");
        viewHolder.item6.setText(mlist.get(position).getWeight()+"");
        if (position==id){
            viewHolder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorDialogTitleBG));
        }else {
            viewHolder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorZERO));
        }
        return convertView;
    }
     class ViewHolder {
        TextView item1;
        TextView item2;
        TextView item3;
        TextView item4;
        TextView item5;
        TextView item6;
         LinearLayout layout;
    }
}
