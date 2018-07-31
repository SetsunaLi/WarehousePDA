package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.adapter.ListDataAdapter;
import com.example.ridko.warehousepda.application.App;
import com.example.ridko.warehousepda.entity.ListEntity;
import com.example.ridko.warehousepda.entity.ListEntity2;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/3/31.
 */

public class UnBindAndBindFragment extends Fragment {

    @Bind(R.id.inventory)
    ImageButton inventory;
    @Bind(R.id.button_unbind)
    ImageButton buttonUnbind;
    @Bind(R.id.button_bind)
    ImageButton buttonBind;
    @Bind(R.id.text_title)
    TextView textTitle;
    @Bind(R.id.text_count)
    TextView textCount;
    @Bind(R.id.listview)
    ListView listview;

    public static UnBindAndBindFragment newInstance() {
        return new UnBindAndBindFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unbind_and_bind, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private ArrayAdapter myAdapter;
    private ArrayList<ListEntity2> mylist;
    //这里写界面
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());
        buttonBind.setImageDrawable(getResources().getDrawable(R.drawable.bind3));
        buttonUnbind.setImageDrawable(getResources().getDrawable(R.drawable.unbind1));
        buttonUnbind.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((ImageButton) view).setImageDrawable(getResources().getDrawable(R.drawable.unbind2));
                }/*else if(event.getAction()==MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(getResources().getDrawable(R.drawable.unbind3));
                    buttonBind.setImageDrawable(getResources().getDrawable(R.drawable.bind1));
                }*/
                return false;
            }
        });
        buttonBind.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((ImageButton) view).setImageDrawable(getResources().getDrawable(R.drawable.bind2));
                }/*else if (event.getAction()==MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(getResources().getDrawable(R.drawable.bind3));
                    buttonUnbind.setImageDrawable(getResources().getDrawable(R.drawable.unbind1));
                }*/
                return false;
            }
        });
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View viewHeader = inflater.inflate(R.layout.list_table_1, null);
//        View ll = viewHeader.findViewById(R.id.linearlayout);
        //初始化RadioGroup
        LinearLayout group = (LinearLayout) viewHeader.findViewById(R.id.linearlayout);
        //在listView的头上添加View必须用listView类型的layoutParams给欲添加的view设置一下，便于布局管理器给它测量位置
        AbsListView.LayoutParams lp
                = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        group.setLayoutParams(lp);
        //将RadioGroup布局添加到listView顶部
        listview.addHeaderView(group);
        mylist=new ArrayList<>();
        myAdapter=new ArrayAdapter(getContext(),R.layout.list_item_1,mylist){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv1=(TextView)convertView.findViewById(R.id.text1);
                TextView tv2=(TextView)convertView.findViewById(R.id.text2);
                TextView tv3=(TextView)convertView.findViewById(R.id.text3);
                tv1.setText(mylist.get(position).getStr1()+"");
                tv2.setText(mylist.get(position).getStr2()+"");
                tv3.setText(mylist.get(position).getStr3()+"");
                return super.getView(position, convertView, parent);
            }
        };
        listview.setAdapter(myAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listview.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorDialogTitleBG));
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    //当客户点击MENU按钮的时候，调用该方法
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.removeItem(R.id.btn_about);
    }

    //当客户点击菜单当中的某一个选项时，会调用该方法
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                blinkDialog();
                break;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private String carrierNO;

    private void blinkDialog() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View blinkView = inflater.inflate(R.layout.dialog_bind, null);
        Button no = (Button) blinkView.findViewById(R.id.dialog_no);
        Button yes = (Button) blinkView.findViewById(R.id.dialog_yes);
        final EditText editNo = (EditText) blinkView.findViewById(R.id.editNO);
        ImageView img = (ImageView) blinkView.findViewById(R.id.imgSearch);

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
                carrierNO = editNo.getText().toString() + "";
                dialog.dismiss();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                询标开始
            }
        });
    }

    @OnClick({R.id.button_unbind, R.id.button_bind, R.id.inventory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_unbind:
                ((ImageButton) view).setImageDrawable(getResources().getDrawable(R.drawable.unbind3));
                buttonBind.setImageDrawable(getResources().getDrawable(R.drawable.bind1));
                App.BIND = false;
                break;
            case R.id.button_bind:
                ((ImageButton) view).setImageDrawable(getResources().getDrawable(R.drawable.bind3));
                buttonUnbind.setImageDrawable(getResources().getDrawable(R.drawable.unbind1));
                App.BIND = true;
                break;
            case R.id.inventory:
                break;
        }
    }
}
