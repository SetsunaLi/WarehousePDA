package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.adapter.BRecyclerAdapter;
import com.example.ridko.warehousepda.adapter.BasePullUpRecyclerAdapter;
import com.example.ridko.warehousepda.entity.Managment;
import com.example.ridko.warehousepda.listener.MyItemOnTouchListener;
import com.example.ridko.warehousepda.second.development.RecyclerHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mumu on 2018/3/31.
 */

public class DeviceManagmentFragment extends Fragment implements BasePullUpRecyclerAdapter.OnItemClickListener {

    @Bind(R.id.rv_list)
    RecyclerView rvList;

    public static DeviceManagmentFragment newInstance() {
        return new DeviceManagmentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_management_layout, container, false);
        ButterKnife.bind(this, view);
        list = new ArrayList<>();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private List<Managment> list;
    private ManagementAdapter mgAdapter;

    //    private ArrayAdapter adapter;
    //这里写界面
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());
        initList();
        initView();
    }

    public void initList() {
        Managment m1 = new Managment();
        m1.setIvNO(R.drawable.user);
        m1.setTvStr(getResources().getString(R.string.text15));
        Managment m2 = new Managment();
        m2.setIvNO(R.drawable.system);
        m2.setTvStr(getResources().getString(R.string.text16));
        Managment m3 = new Managment();
        m3.setIvNO(R.drawable.device);
        m3.setTvStr(getResources().getString(R.string.text17));
        list.add(m1);
        list.add(m2);
        list.add(m3);
    }
    public void initView() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(linearLayoutManager);
        //        如果RecyclerView不能实现onTouch效果，换成ListView
        mgAdapter = new ManagementAdapter(rvList, list, R.layout.list_item_2);
        mgAdapter.setState(BasePullUpRecyclerAdapter.STATE_INVISIBLE);
        mgAdapter.notifyDataSetChanged();
        mgAdapter.setOnItemClickListener(this);
        rvList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action=motionEvent.getActionMasked();
                View child = ((RecyclerView) view).findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child!=null)
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_UP:
                        TextView flagText1 = (TextView) child.findViewById(R.id.text1);
                        if (flagText1!=null) {
                            int position1 = Integer.parseInt(flagText1.getText().toString());
                            switch (position1) {
                                case 0:
                                    ((LinearLayout) child.findViewById(R.id.linearlayout)).setBackground(getResources().getDrawable(R.drawable.bg_layout2));
                                    ((ImageView) child.findViewById(R.id.iv)).setImageDrawable(getResources().getDrawable(R.drawable.user));
                                    ((TextView) child.findViewById(R.id.tv)).setTextColor(getResources().getColor(R.color.colorHomeButton2));
                                    break;
                                case 1:
                                    ((LinearLayout) child.findViewById(R.id.linearlayout)).setBackground(getResources().getDrawable(R.drawable.bg_layout2));
                                    ((ImageView) child.findViewById(R.id.iv)).setImageDrawable(getResources().getDrawable(R.drawable.system));
                                    ((TextView) child.findViewById(R.id.tv)).setTextColor(getResources().getColor(R.color.colorHomeButton2));
                                    break;
                                case 2:
                                    ((LinearLayout) child.findViewById(R.id.linearlayout)).setBackground(getResources().getDrawable(R.drawable.bg_layout2));
                                    ((ImageView) child.findViewById(R.id.iv)).setImageDrawable(getResources().getDrawable(R.drawable.device));
                                    ((TextView) child.findViewById(R.id.tv)).setTextColor(getResources().getColor(R.color.colorHomeButton2));
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        TextView flagText2 = (TextView) child.findViewById(R.id.text1);
                        if (flagText2!=null) {
                            int position2 = Integer.parseInt(flagText2.getText().toString());
                            switch (position2) {
                                case 0:
                                    LinearLayout ll1 = ((LinearLayout) child.findViewById(R.id.linearlayout));
                                    ll1.setBackground(getResources().getDrawable(R.drawable.bg11));
                                    ((ImageView) child.findViewById(R.id.iv)).setImageDrawable(getResources().getDrawable(R.drawable.user_selected));
                                    ((TextView) child.findViewById(R.id.tv)).setTextColor(getResources().getColor(R.color.colorHomeButton1));
                                    break;
                                case 1:
                                    ((LinearLayout) child.findViewById(R.id.linearlayout)).setBackground(getResources().getDrawable(R.drawable.bg10));
                                    ((ImageView) child.findViewById(R.id.iv)).setImageDrawable(getResources().getDrawable(R.drawable.system_selected));
                                    ((TextView) child.findViewById(R.id.tv)).setTextColor(getResources().getColor(R.color.colorHomeButton1));
                                    break;
                                case 2:
                                    ((LinearLayout) child.findViewById(R.id.linearlayout)).setBackground(getResources().getDrawable(R.drawable.bg9));
                                    ((ImageView) child.findViewById(R.id.iv)).setImageDrawable(getResources().getDrawable(R.drawable.device_selected));
                                    ((TextView) child.findViewById(R.id.tv)).setTextColor(getResources().getColor(R.color.colorHomeButton1));
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
        rvList.setAdapter(mgAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        switch (position) {
            case 0:
                UserFragment f1 = new UserFragment();
                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.add(R.id.content_frame, f1);
                transaction1.show(f1);
                transaction1.commit();
                break;
            case 1:
                SystemFragment f2 = new SystemFragment();
                FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction2.add(R.id.content_frame, f2);
                transaction2.show(f2);
                transaction2.commit();
                break;
            case 2:
                DeviceFragment f3 = new DeviceFragment();
                FragmentTransaction transaction3 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction3.add(R.id.content_frame, f3);
                transaction3.show(f3);
                transaction3.commit();
                break;
        }
        mgAdapter.notifyDataSetChanged();
    }

/*    @Override
    public void onItemClick(RecyclerView.ViewHolder holder) {
        int i=0;

    }

    @Override
    public void onItemLongClick(RecyclerView.ViewHolder holder) {
        int i=0;

    }

    @Override
    public boolean startDragging(RecyclerView.ViewHolder holder,View view, MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()){
            case MotionEvent.ACTION_CANCEL:
                mgAdapter.notifyDataSetChanged();
                switch (holder.getAdapterPosition()){
                    case 0:
                        LinearLayout ll1=(LinearLayout)(view.findViewById(R.id.linearlayout));
                        ll1.setBackground(getResources().getDrawable(R.drawable.bg_layout2));
                        ImageView iv1=(ImageView)(view.findViewById(R.id.iv));
                        iv1.setImageDrawable(getResources().getDrawable(R.drawable.user));
                        TextView tv1=(TextView)(view.findViewById(R.id.tv));
                        tv1.setTextColor(getResources().getColor(R.color.colorHomeButton2));
                        break;
                    case 1:
                        LinearLayout ll2=(LinearLayout)(view.findViewById(R.id.linearlayout));
                        ll2.setBackground(getResources().getDrawable(R.drawable.bg_layout2));
                        ImageView iv2=(ImageView)(view.findViewById(R.id.iv));
                        iv2.setImageDrawable(getResources().getDrawable(R.drawable.system));
                        TextView tv2=(TextView)(view.findViewById(R.id.tv));
                        tv2.setTextColor(getResources().getColor(R.color.colorHomeButton2));
                        break;
                    case 2:
                        LinearLayout ll3=(LinearLayout)(view.findViewById(R.id.linearlayout));
                        ll3.setBackground(getResources().getDrawable(R.drawable.bg_layout2));
                        ImageView iv3=(ImageView)(view.findViewById(R.id.iv));
                        iv3.setImageDrawable(getResources().getDrawable(R.drawable.device));
                        TextView tv3=(TextView)(view.findViewById(R.id.tv));
                        tv3.setTextColor(getResources().getColor(R.color.colorHomeButton2));
                        break;
                    default:
                        break;
                }
            case MotionEvent.ACTION_DOWN:
                switch (holder.getAdapterPosition()){
                    case 0:
                        view.setBackground(getResources().getDrawable(R.drawable.bg11));
                        ImageView iv1=(ImageView)(view.findViewById(R.id.iv));
                        iv1.setImageDrawable(getResources().getDrawable(R.drawable.user_selected));
                        TextView tv1=(TextView)(view.findViewById(R.id.tv));
                        tv1.setTextColor(getResources().getColor(R.color.colorHomeButton1));
                        break;
                    case 1:
                        view.setBackground(getResources().getDrawable(R.drawable.bg10));
                        ImageView iv2=(ImageView)(view.findViewById(R.id.iv));
                        iv2.setImageDrawable(getResources().getDrawable(R.drawable.system_selected));
                        TextView tv2=(TextView)(view.findViewById(R.id.tv));
                        tv2.setTextColor(getResources().getColor(R.color.colorHomeButton1));
                        break;
                    case 2:
                        view.setBackground(getResources().getDrawable(R.drawable.bg9));
                        ImageView iv3=(ImageView)(view.findViewById(R.id.iv));
                        iv3.setImageDrawable(getResources().getDrawable(R.drawable.device_selected));
                        TextView tv3=(TextView)(view.findViewById(R.id.tv));
                        tv3.setTextColor(getResources().getColor(R.color.colorHomeButton1));
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
        return false;
    }*/
//
   /* @Override
    public void onItemTouchClick(View view, int postion,MotionEvent motionEvent) {

    }*/

    class ManagementAdapter extends BasePullUpRecyclerAdapter<Managment> {
        //        在适配器做做item 触摸监听监听
        private MyItemOnTouchListener myItemOnTouchListener;

        //        private GestureDetectorCompat mGestureDetector;
        public ManagementAdapter(final RecyclerView rv, Collection<Managment> datas, int itemLayoutId
        ) {
            super(rv, datas, itemLayoutId);
//            this.myItemOnTouchListener=myItemOnTouchListener;
//            铺抓触摸动作
          /*  GestureDetector.OnGestureListener gestureListener=new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    android.util.Log.d("wesley", "RecyclerItemTouchHandler.onSingleTapUp");
                    View child = rv.findChildViewUnder(e.getX(), e.getY());
                    if (child != null) {
                        myItemOnTouchListener.onItemClick(rv.getChildViewHolder(child));
                    }
                    return super.onSingleTapUp(e);
                }
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = rv.findChildViewUnder(e.getX(), e.getY());
                    if (child != null) {
                        myItemOnTouchListener.onItemLongClick(rv.getChildViewHolder(child));
                    }
                    android.util.Log.d("wesley", "RecyclerItemTouchHandler.onLongPress");
                }

                @Override
                public boolean onDown(MotionEvent e) {
                    View child = rv.findChildViewUnder(e.getX(), e.getY());
                    boolean handled = child != null && myItemOnTouchListener.startDragging(rv.getChildViewHolder(child),child, e);
                    if (handled) {
                        mGestureDetector.setIsLongpressEnabled(false);
                        android.util.Log.d("wesley", "RecyclerItemTouchHandler.onDown");
                    }
                    return handled || super.onDown(e);
                }
            };
            mGestureDetector=new GestureDetectorCompat(rv.getContext().getApplicationContext(),gestureListener);*/
        }

        @Override
        public void convert(RecyclerHolder holder, Managment item, final int position) {
            if (item != null) {
              /*  TextView ll=holder.getView(R.id.tv);
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (myItemOnTouchListener!=null)
                            myItemOnTouchListener.onItemTouchClick(view,position,null);
                    }
                LinearLayout ll=holder.getView(R.id.linearlayout);
                ll.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        在适配器触发子监听
                        if (myItemOnTouchListener!=null)
                            myItemOnTouchListener.onItemTouchClick(view,position,motionEvent);
                        return false;
                    }
                });
                ll.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return false;
                    }
                });*/
                holder.setBackground(R.id.linearlayout, getResources().getDrawable(R.drawable.bg_layout2));
                if (item.getIvNO() != 0) {
                    holder.setImageResource(R.id.iv, item.getIvNO());
                }
                if (item.getTvStr() != null) {
                    holder.setText(R.id.tv, item.getTvStr() + "");
                    holder.setTextColor(R.id.tv, getResources().getColor(R.color.colorHomeButton2));
                }
                holder.setText(R.id.text1, position + "");
            }
        }

        public void setMyItemOnTouchListener(MyItemOnTouchListener myItemOnTouchListener) {
            this.myItemOnTouchListener = myItemOnTouchListener;
        }

     /*   @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return mGestureDetector.onTouchEvent(e);
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }*/
    }

}
