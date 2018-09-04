package com.example.ridko.warehousepda.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.entity.DemoEntity1;
import com.example.ridko.warehousepda.listener.MyItemOnTouchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/9/4.
 */

public class OutDemoFragment1 extends Fragment implements MyItemOnTouchListener{
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.layout1)
    LinearLayout layout1;
    @Bind(R.id.button_blink)
    Button buttonBlink;
    @Bind(R.id.button_ok)
    Button buttonOk;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private List<DemoEntity1> mlist = new ArrayList<>();
    private MyAdapter myAdapter;
    private int selectID = -1;

    //可以不要
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());
        initData();
        myAdapter = new MyAdapter(getContext(), R.layout.list_item_3_demo, mlist);
        listview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myAdapter.selectItem(i);
                myAdapter.notifyDataSetChanged();
                selectID = i;
                buttonOk.setText(getResources().getString(R.string.button13));

            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            DemoEntity1 demoEntity1 = new DemoEntity1();
            demoEntity1.setDyelotNO("JQ997" + i * 3);
            demoEntity1.setApplyCount(7 + i * 3);
            demoEntity1.setPracticalCount(7 + i * 3);
            demoEntity1.setReadCount(7 + i * 3);
            demoEntity1.setBuNO(04 + i * 3);
            demoEntity1.setState("正常");
            mlist.add(i, demoEntity1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.button_blink, R.id.button_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_blink:
                break;
            case R.id.button_ok:
                break;
        }
    }
    protected static final String TAG_CONTENT_FRAGMENT = "ContentFragment";
    private Fragment fragment;
    @Override
    public void onItemClick(int position) {
        fragment=new OutDemoFragment2();
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_frame,fragment,TAG_CONTENT_FRAGMENT).addToBackStack(null);
        transaction.show(fragment);
        transaction.commit();
    }

    class MyAdapter extends ArrayAdapter<DemoEntity1> {
        private List<DemoEntity1> list;
        private LayoutInflater mInflater;
        //        在适配器做做item 触摸监听监听
        private MyItemOnTouchListener myItemOnTouchListener;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DemoEntity1> objects) {
            super(context, resource, objects);
            this.list = objects;
            this.mInflater = LayoutInflater.from(context);
        }

        private int id = -255;

        public void selectItem(int id) {
            this.id = id;
        }
        public void setMyItemOnTouchListener(MyItemOnTouchListener myItemOnTouchListener) {
                    this.myItemOnTouchListener = myItemOnTouchListener;
        }
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_3_demo, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.item1 = (TextView) convertView.findViewById(R.id.item1);
                viewHolder.item2 = (TextView) convertView.findViewById(R.id.item2);
                viewHolder.item3 = (TextView) convertView.findViewById(R.id.item3);
                viewHolder.item4 = (TextView) convertView.findViewById(R.id.item4);
                viewHolder.item5 = (TextView) convertView.findViewById(R.id.item5);
                viewHolder.item6 = (TextView) convertView.findViewById(R.id.item6);
                viewHolder.item7 = (TextView) convertView.findViewById(R.id.item7);
                viewHolder.button=(Button)convertView.findViewById(R.id.button1);
                viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.head);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.item1.setText(position + 1 + "");
            viewHolder.item2.setText(mlist.get(position).getDyelotNO() + "");
            viewHolder.item3.setText(mlist.get(position).getApplyCount() + "");
            viewHolder.item4.setText(mlist.get(position).getPracticalCount() + "");
            viewHolder.item5.setText(mlist.get(position).getReadCount() + "");
            viewHolder.item6.setText(mlist.get(position).getBuNO() + "");
            viewHolder.item7.setText(mlist.get(position).getState() + "");
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myItemOnTouchListener!=null)
                        myItemOnTouchListener.onItemClick(position);
                }
            });
            if (position == id) {
                viewHolder.layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorDialogTitleBG));
            } else {
                viewHolder.layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorZERO));
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
            TextView item7;
            Button button;
            LinearLayout layout;
        }
    }
}
