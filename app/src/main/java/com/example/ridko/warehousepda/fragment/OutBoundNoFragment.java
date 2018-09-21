package com.example.ridko.warehousepda.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.application.App;
import com.example.ridko.warehousepda.client.OutboundApplyDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/9/18.
 */

public class OutBoundNoFragment extends Fragment {
    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.text2)
    TextView text2;
    @Bind(R.id.text3)
    TextView text3;
    @Bind(R.id.edit1)
    EditText edit1;
    @Bind(R.id.edit2)
    EditText edit2;
    @Bind(R.id.edit3)
    EditText edit3;
    @Bind(R.id.edit4)
    EditText edit4;
    @Bind(R.id.edit5)
    EditText edit5;
    @Bind(R.id.edit6)
    EditText edit6;
    @Bind(R.id.edit7)
    EditText edit7;
    @Bind(R.id.edit8)
    EditText edit8;
    @Bind(R.id.list1)
    ListView list1;
    @Bind(R.id.button_ok)
    Button buttonOk;

    private MyAdapter myAdapter;
    private List<OutboundApplyDetail> outboundApplyDetailList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_layout3, container, false);
        ButterKnife.bind(this, view);
        //        将RadioGroup布局添加到listView顶部
        list1.addHeaderView(inflater.inflate(R.layout.list_item_5_demo, list1, false));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());
        initData();
        initView();
        if (outboundApplyDetailList != null)
            myAdapter = new MyAdapter(getContext(), R.layout.list_item_5_demo,outboundApplyDetailList);
        else
            myAdapter = new MyAdapter(getContext(), R.layout.list_item_5_demo, new ArrayList<OutboundApplyDetail>());
        list1.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myAdapter.selectItem(i-1);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (App.detilList != null)
            App.detilList.clear();
        if (App.outboundApplyDetailList != null)
            App.outboundApplyDetailList.clear();
    }
    private void initData(){
        this.outboundApplyDetailList= App.outboundApplyDetailList;
    }
    public void initView() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        text2.setText(simpleDateFormat.format(date) + "");
        edit6.setText(simpleDateFormat.format(date) + "");
        edit8.setText(simpleDateFormat.format(date) + "");
        if (outboundApplyDetailList != null && outboundApplyDetailList.size() != 0) {
            text1.setText(outboundApplyDetailList.get(0).getApplyNo() + "");
            edit1.setText(outboundApplyDetailList.get(0).getApplyNo() + "");
        }
    }
    protected static final String TAG_CONTENT_FRAGMENT = "ContentFragment";
    @OnClick({R.id.button_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_ok:
                Fragment fragment=HomeFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragment, TAG_CONTENT_FRAGMENT);
                transaction.show(fragment);
                transaction.commit();
//                getFragmentManager().popBackStack();
                break;
        }
    }
    class MyAdapter extends ArrayAdapter<OutboundApplyDetail> {
        private List<OutboundApplyDetail> list;
        private LayoutInflater mInflater;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<OutboundApplyDetail> objects) {
            super(context, resource, objects);
            this.list = objects;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public synchronized void add(@Nullable OutboundApplyDetail object) {
            super.add(object);
        }

        private int id = -255;

        public void selectItem(int id) {
            if (this.id == id)
                id = -255;
            else
                this.id = id;
        }

        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_5_demo, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.item1 = (TextView) convertView.findViewById(R.id.item1);
                viewHolder.item2 = (TextView) convertView.findViewById(R.id.item2);
                viewHolder.item3 = (TextView) convertView.findViewById(R.id.item3);
                viewHolder.item4 = (TextView) convertView.findViewById(R.id.item4);
                viewHolder.item5 = (TextView) convertView.findViewById(R.id.item5);
                viewHolder.item6 = (TextView) convertView.findViewById(R.id.item6);
                viewHolder.item7 = (TextView) convertView.findViewById(R.id.item7);
                viewHolder.head = (LinearLayout) convertView.findViewById(R.id.head);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.item1.setText(position + 1 + "");
            viewHolder.item2.setText(list.get(position).getVatDyeNo() + "");
            viewHolder.item3.setText(list.get(position).getClothNo() + "");
            viewHolder.item4.setText(list.get(position).getClothName() + "");
            viewHolder.item5.setText(list.get(position).getColorNo() + "");
            viewHolder.item6.setText(list.get(position).getColorName() + "");
            viewHolder.item7.setText(list.get(position).getNum() + "");
            if (position == id) {
                viewHolder.head.setBackgroundColor(getContext().getResources().getColor(R.color.colorDialogTitleBG));
            } else {
                viewHolder.head.setBackgroundColor(getContext().getResources().getColor(R.color.colorZERO));
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
            LinearLayout head;
        }
    }
}
