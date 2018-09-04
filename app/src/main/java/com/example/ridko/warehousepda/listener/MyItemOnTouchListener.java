package com.example.ridko.warehousepda.listener;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mumu on 2018/8/2.
 */
    //自定义 子布局OnTouch接口
public interface MyItemOnTouchListener {

//    void onItemTouchClick(View view, int postion,MotionEvent motionEvent);
    void onItemClick(int position);
/*
    void onItemLongClick(RecyclerView.ViewHolder holder);

    boolean startDragging(RecyclerView.ViewHolder holder, View view,MotionEvent e);*/

}
