package com.example.ridko.warehousepda.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
import com.example.ridko.warehousepda.entity.DemoEntity2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/3/31.
 */

public class OutDemoFragment2 extends Fragment {


    @Bind(R.id.list1)
    ListView list1;
    @Bind(R.id.button_blink)
    Button buttonBlink;
    @Bind(R.id.button_ok)
    Button buttonOk;

    private List<DemoEntity2> mylist;
    private DemoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_layout2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private int selectID = -1;

    //可以不要
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());
        initData();
        adapter = new DemoAdapter(getContext(), R.layout.list_item_4_demo, mylist);
        list1.setAdapter(adapter);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.selectItem(i);
                adapter.notifyDataSetChanged();
                selectID = i;
            }
        });
    }

    private void initData() {
        mylist = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            DemoEntity2 demo = new DemoEntity2();
            demo.setClothingNO(new Random().nextInt(99));
            demo.setBuNO(new Random().nextInt(50));
            demo.setWeight(new Random().nextInt(20) + i * 0.3f);
            demo.setDyelotNO(((char) (new Random().nextInt(25) + 65)+"") + i + "JQ" + new Random().nextInt(9)+ new Random().nextInt(9));
            demo.setEpcNO("暂时没有EPC号");
            mylist.add(i, demo);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void blinkDialog() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View blinkView = inflater.inflate(R.layout.dialog_stock_removal, null);
        Button no = (Button) blinkView.findViewById(R.id.dialog_no);
        Button yes = (Button) blinkView.findViewById(R.id.dialog_yes);
        TextView text = (TextView) blinkView.findViewById(R.id.dialog_text);
        text.setText(R.string.text61);
        dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();
        dialog.getWindow().setContentView(blinkView);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                返回上一层
                getFragmentManager().popBackStack();
                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.button_blink, R.id.button_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_blink:
//                扫描开始
                break;
            case R.id.button_ok:
//                出库
                blinkDialog();
                break;
        }
    }

    class DemoAdapter extends ArrayAdapter<DemoEntity2> {
        private List<DemoEntity2> list;
        private LayoutInflater mInflater;

        public DemoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DemoEntity2> objects) {
            super(context, resource, objects);
            this.list = objects;
            this.mInflater = LayoutInflater.from(context);
        }

        private int id = -255;

        public void selectItem(int id) {
            this.id = id;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_4_demo, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.item1 = (TextView) convertView.findViewById(R.id.item1);
                viewHolder.item2 = (TextView) convertView.findViewById(R.id.item2);
                viewHolder.item3 = (TextView) convertView.findViewById(R.id.item3);
                viewHolder.item4 = (TextView) convertView.findViewById(R.id.item4);
                viewHolder.item5 = (TextView) convertView.findViewById(R.id.item5);
                viewHolder.item6 = (TextView) convertView.findViewById(R.id.item6);
                viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.head);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.item1.setText(position+1 + "");
            viewHolder.item2.setText(list.get(position).getClothingNO() + "");
            viewHolder.item3.setText(list.get(position).getBuNO() + "");
            viewHolder.item4.setText(list.get(position).getWeight() + "");
            viewHolder.item5.setText(list.get(position).getDyelotNO() + "");
            viewHolder.item6.setText(list.get(position).getEpcNO() + "");
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
            LinearLayout layout;
        }
    }
}
