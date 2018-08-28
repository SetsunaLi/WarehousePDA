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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.adapter.BasePullUpRecyclerAdapter;
import com.example.ridko.warehousepda.adapter.ListDataAdapter;
import com.example.ridko.warehousepda.entity.ListEntity;
import com.example.ridko.warehousepda.second.development.RecyclerHolder;
import com.example.ridko.warehousepda.view.HVListView;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/7/6.
 */

public class SpecialFragment2 extends Fragment {
    @Bind(R.id.list1)
    ListView list;
/*    @Bind(R.id.recyleview1)
    RecyclerView recyclerView;*/
    @Bind(R.id.button_blink)
    Button buttonBlink;
    @Bind(R.id.button_ok)
    Button buttonOk;
/*    @Bind(R.id.head)
    LinearLayout head;*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private ArrayList<ListEntity> mlist=new ArrayList<>();
    private ListDataAdapter mAdapter;
//    private ListAdapter adapter;
    private int selectID=0;
    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.special_layout2, container, false);
        ButterKnife.bind(this, view);
        initData();

  /*      View viewHeader = inflater.inflate(R.layout.no_title_item_layout, null);
        //初始化RadioGroup
        LinearLayout group = (LinearLayout) viewHeader.findViewById(R.id.head);
        //在listView的头上添加View必须用listView类型的layoutParams给欲添加的view设置一下，便于布局管理器给它测量位置
        AbsListView.LayoutParams lp
                = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        group.setLayoutParams(lp);
//        将RadioGroup布局添加到listView顶部
        list.addHeaderView(group);*/
//        list.mListHead=group;
        mAdapter=new ListDataAdapter(mlist,getActivity());
        list.setAdapter(mAdapter);
        mAdapter.setData(mlist);
//        list.setEmptyView("暂无数据显示");
        mAdapter.notifyDataSetChanged();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mAdapter.selectItem(position);
                mAdapter.notifyDataSetChanged();
                selectID=position;
            }
        });
  /*     LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new ListAdapter(recyclerView,mlist,R.layout.no_title_item_layout);
        adapter.setState(BasePullUpRecyclerAdapter.STATE_INVISIBLE);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);*/
        return view;
    }

    private void initData(){
        for(int i=0;i<10;i++){
            ListEntity listEntity=new ListEntity();
            listEntity.setBuNo("123"+i);
            listEntity.setBuName("亿锋布匹");
            listEntity.setRanchangNo("YF00-"+i);
            listEntity.setCount(100+i);
            listEntity.setWeight(18.88f);
            mlist.add(i,listEntity);
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    //这里写界面
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());

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
    protected static final String TAG_CONTENT_FRAGMENT = "ContentFragment";
    private Fragment fragment;
    @OnClick({R.id.button_blink, R.id.button_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_blink:
//                录入mlist中selectID的缸号
                fragment=new IntoID();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.content_frame,fragment,TAG_CONTENT_FRAGMENT).addToBackStack(null);
                transaction.show(fragment);
                transaction.commit();
                break;
            case R.id.button_ok:
//                获取mlist中selectID的详细数据
                fragment=new SpecialFragment3();
                FragmentTransaction transaction2=getActivity().getSupportFragmentManager().beginTransaction();
                transaction2.add(R.id.content_frame,fragment,TAG_CONTENT_FRAGMENT).addToBackStack(null);
                transaction2.show(fragment);
                transaction2.commit();
                break;
        }
    }
/*    class ListAdapter extends BasePullUpRecyclerAdapter<ListEntity>  {

        public ListAdapter(RecyclerView v, Collection<ListEntity> datas, int itemLayoutId) {
            super(v, datas, itemLayoutId);
        }

        @Override
        public void convert(RecyclerHolder holder, ListEntity item, int position) {
            holder.setText(R.id.item1,position+"");
            holder.setText(R.id.item2,item.getBuNo()+"");
            holder.setText(R.id.item3,item.getBuName()+"");
            holder.setText(R.id.item4,item.getRanchangNo()+"");
            holder.setText(R.id.item5,item.getCount()+"");
            holder.setText(R.id.item6,item.getWeight()+"");
        }
    }*/

}
