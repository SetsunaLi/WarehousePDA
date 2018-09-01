package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.adapter.ListDataAdapter;
import com.example.ridko.warehousepda.application.App;
import com.example.ridko.warehousepda.entity.DemoEntity1;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/8/28.
 */

public class OutDemoManagmentFragment extends Fragment {

    @Bind(R.id.editNO)
    EditText editNO;
    @Bind(R.id.relativelayout)
    RelativeLayout relativelayout;
    @Bind(R.id.imgSearch)
    ImageView imgSearch;
    @Bind(R.id.tvNosearch)
    TextView tvNosearch;
    @Bind(R.id.layout1)
    LinearLayout layout1;
    @Bind(R.id.button_ok)
    Button buttonOk;
    @Bind(R.id.listview)
    ListView listview;

    public static OutDemoManagmentFragment newInstance() {
        return new OutDemoManagmentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_layout1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private List<DemoEntity1> mlist=new ArrayList<>();
    private MyAdapter myAdapter;
    private int selectID=-1;
    //这里写界面
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());
        initView();
        initData();
        initUtil();
        myAdapter=new MyAdapter(getContext(),R.layout.list_item_3_demo,mlist);
        listview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myAdapter.selectItem(i);
                myAdapter.notifyDataSetChanged();
                selectID=i;
                buttonOk.setText(getResources().getString(R.string.button13));

            }
        });
        editNO.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
//                点击监听
                buttonOk.setText(getResources().getString(R.string.text_search));
                selectID=-1;
            }
        });
        editNO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//实时内容
            }
        });
    }

    private AnimationDrawable frameanim;

    public void initView() {
        layout1.setVisibility(View.GONE);
    }
    private void initData(){
        for (int i=0;i<10;i++){
            DemoEntity1 demoEntity1=new DemoEntity1();
            demoEntity1.setApplyNO("JQ997"+i*3);
            demoEntity1.setBuNO(04+i*3);
            demoEntity1.setApplyCount(7+i*3);
            demoEntity1.setPracticalCount(7+i*3);
            demoEntity1.setState("正常");
            mlist.add(i,demoEntity1);
        }
    }

    /**
     * 输入法管理器
     */
    private InputMethodManager mInputMethodManager;
    /**
     * 初始化必须工具
     */
    private void initUtil() {
        //初始化输入法
        mInputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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

    private String strNO = "";
    private boolean flag = false;
    protected static final String TAG_CONTENT_FRAGMENT = "ContentFragment";
    private Runnable load=new Runnable() {
        @Override
        public void run() {
            buttonOk.setFocusable(true);
            imgSearch.setVisibility(View.GONE);
            tvNosearch.setVisibility(View.GONE);
            layout1.setVisibility(View.VISIBLE);

        }
    };
    private void loadNO(){
        imgSearch.setVisibility(View.VISIBLE);
        tvNosearch.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.GONE);
//                播放一次
//                android:oneshot="true"\\false为循环播放
        frameanim = (AnimationDrawable) getContext().getResources().getDrawable(R.drawable.search_anim_drawable);
        imgSearch.setImageDrawable(frameanim);
        frameanim.start();
        strNO = editNO.getText().toString() + "";
        if (strNO.length() != 0) {
            buttonOk.setFocusable(false);
            Handler handler=new Handler();
            handler.postDelayed(load,1500);
        } else {
            App.toastShow(getContext(), getResources().getString(R.string.stockRemoval_hint), Toast.LENGTH_SHORT);
        }
    }
    private Fragment fragment;
    @OnClick({ R.id.button_ok, R.id.imgSearch,R.id.editNO})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_ok:
               /* if (buttonOk.getText().equals("搜索")){
                    loadNO();
                    editNO.setFocusable(false);
                    if (mInputMethodManager.isActive()) {
                        mInputMethodManager.hideSoftInputFromWindow(editNO.getWindowToken(), 0);// 隐藏输入法
                    }
                }else {*/
                    fragment=new OutDemoFragment2();
                    FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.content_frame,fragment,TAG_CONTENT_FRAGMENT).addToBackStack(null);
                    transaction.show(fragment);
                    transaction.commit();
//                }
                break;
            case R.id.imgSearch:
                loadNO();
                break;
            case R.id.editNO:
                editNO.setFocusable(true);//设置输入框可聚集
                editNO.setFocusableInTouchMode(true);//设置触摸聚焦
                editNO.requestFocus();//请求焦点
                editNO.findFocus();//获取焦点
                mInputMethodManager.showSoftInput(editNO, InputMethodManager.SHOW_FORCED);// 显示输入法
                break;
        }
    }

    /*private void blinkDialog() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View blinkView = inflater.inflate(R.layout.dialog_stock_removal, null);
        Button no = (Button) blinkView.findViewById(R.id.dialog_no);
        Button yes = (Button) blinkView.findViewById(R.id.dialog_yes);
        TextView text = (TextView) blinkView.findViewById(R.id.dialog_text);
        text.setText(R.string.dialog_blink);
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
                dialog.dismiss();
            }
        });
    }*/

    class MyAdapter extends ArrayAdapter<DemoEntity1> {
        private List<DemoEntity1> list;
        private LayoutInflater mInflater;
        public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DemoEntity1> objects) {
            super(context, resource, objects);
            this.list=objects;
            this.mInflater=LayoutInflater.from(context);
        }
        private int id=-255;
        public void selectItem(int id){
            this.id=id;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder=null;
            if (convertView == null) {
                convertView=mInflater.inflate(R.layout.list_item_3_demo,parent,false);
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
            viewHolder.item2.setText(mlist.get(position).getApplyNO()+"");
            viewHolder.item3.setText(mlist.get(position).getBuNO()+"");
            viewHolder.item4.setText(mlist.get(position).getApplyCount()+"");
            viewHolder.item5.setText(mlist.get(position).getPracticalCount()+"");
            viewHolder.item6.setText(mlist.get(position).getState()+"");
            if (position==id){
                viewHolder.layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorDialogTitleBG));
            }else {
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
