package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.adapter.BRecyclerAdapter;
import com.example.ridko.warehousepda.adapter.BasePullUpRecyclerAdapter;
import com.example.ridko.warehousepda.entity.Managment;
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
        list=new ArrayList<>();
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
    public void initList(){
        Managment m1=new Managment();
        m1.setIvNO(R.drawable.user);
        m1.setTvStr(getResources().getString(R.string.text15));
        Managment m2=new Managment();
        m2.setIvNO(R.drawable.system);
        m2.setTvStr(getResources().getString(R.string.text16));
        Managment m3=new Managment();
        m3.setIvNO(R.drawable.device);
        m3.setTvStr(getResources().getString(R.string.text17));
        list.add(m1);
        list.add(m2);
        list.add(m3);
    }
    public void initView(){
       /* if (list!=null){
            adapter=new ArrayAdapter(getActivity(),R.layout.list_item_2,list);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }*/
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(linearLayoutManager);
        //        如果RecyclerView不能实现onTouch效果，换成ListView
        mgAdapter=new ManagementAdapter(rvList,list,R.layout.list_item_2);
        mgAdapter.setState(BasePullUpRecyclerAdapter.STATE_INVISIBLE);
        mgAdapter.notifyDataSetChanged();
        mgAdapter.setOnItemClickListener(this);
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
        switch (position){
            case 0:
                view.setBackground(getResources().getDrawable(R.drawable.bg11));
//                Objects o=view.findViewById(R.id.iv);
                ImageView iv1=(ImageView)(view.findViewById(R.id.iv));
                iv1.setImageDrawable(getResources().getDrawable(R.drawable.user_selected));
                UserFragment  f1=new UserFragment();
                FragmentTransaction transaction1=getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.add(R.id.content_frame,f1);
                transaction1.show(f1);
                transaction1.commit();
                break;
            case 1:
                view.setBackground(getResources().getDrawable(R.drawable.bg10));
                ImageView iv2=(ImageView)(view.findViewById(R.id.iv));
                iv2.setImageDrawable(getResources().getDrawable(R.drawable.system_selected));
                SystemFragment f2=new SystemFragment();
                FragmentTransaction transaction2=getActivity().getSupportFragmentManager().beginTransaction();
                transaction2.add(R.id.content_frame,f2);
                transaction2.show(f2);
                transaction2.commit();
                break;
            case 2:
                view.setBackground(getResources().getDrawable(R.drawable.bg9));
                ImageView iv3=(ImageView)(view.findViewById(R.id.iv));
                iv3.setImageDrawable(getResources().getDrawable(R.drawable.device_selected));
                DeviceFragment f3=new DeviceFragment();
                FragmentTransaction transaction3=getActivity().getSupportFragmentManager().beginTransaction();
                transaction3.add(R.id.content_frame,f3);
                transaction3.show(f3);
                transaction3.commit();
                break;
        }
        mgAdapter.notifyDataSetChanged();
    }

    class ManagementAdapter extends BasePullUpRecyclerAdapter<Managment> {

        public ManagementAdapter(RecyclerView v, Collection<Managment> datas, int itemLayoutId) {
            super(v, datas, itemLayoutId);
        }

        @Override
        public void convert(RecyclerHolder holder, Managment item, int position) {
            if (item!=null){
                LinearLayout ll=holder.getView(R.id.linearlayout);
                ll.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return false;
                    }
                });
                holder.setBackground(R.id.linearlayout,getResources().getDrawable(R.drawable.bg_layout2));
                if (item.getIvNO()!=0) {
                    holder.setImageResource(R.id.iv, item.getIvNO());
                }
                if (item.getTvStr()!=null) {
                    holder.setText(R.id.tv, item.getTvStr() + "");
                    holder.setTextColor(R.id.tv,getResources().getColor(R.color.colorHomeButton2));
                }
            }
        }

//    public interface
    }
}
