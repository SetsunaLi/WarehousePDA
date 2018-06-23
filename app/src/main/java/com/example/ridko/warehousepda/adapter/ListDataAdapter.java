package com.example.ridko.warehousepda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        Object result =null;
        if(position<getCount()){
            result = mlist.get(position);
        }
        return result;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view =null;
        ViewHolder viewHolder=null;
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }else{
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
        }
        return view;
    }
    static class ViewHolder {


    }
}
