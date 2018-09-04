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
    @Bind(R.id.imgSearch)
    ImageView imgSearch;
    @Bind(R.id.tvNosearch)
    TextView tvNosearch;
    @Bind(R.id.button_ok)
    Button buttonOk;

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


    //这里写界面
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());
        initUtil();

        editNO.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
//                点击监听
                buttonOk.setText(getResources().getString(R.string.text_search));
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
    protected static final String TAG_CONTENT_FRAGMENT = "ContentFragment";
    private Fragment fragment;
    private Runnable load=new Runnable() {
        @Override
        public void run() {
            buttonOk.setFocusable(true);
            fragment=new OutDemoFragment1();
            FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.content_frame,fragment,TAG_CONTENT_FRAGMENT).addToBackStack(null);
            transaction.show(fragment);
            transaction.commit();
        }
    };
    private void loadNO(){
//                播放一次
//                android:oneshot="true"\\false为循环播放
        frameanim = (AnimationDrawable) getContext().getResources().getDrawable(R.drawable.search_anim_drawable);
        imgSearch.setImageDrawable(frameanim);
        frameanim.start();

        if (mInputMethodManager.isActive()) {
            mInputMethodManager.hideSoftInputFromWindow(editNO.getWindowToken(), 0);// 隐藏输入法
        }
        buttonOk.setFocusable(false);
        editNO.setFocusable(false);
        Handler handler=new Handler();
        handler.postDelayed(load,1500);
    }

    @OnClick({ R.id.button_ok, R.id.imgSearch,R.id.editNO})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_ok:
                strNO = editNO.getText().toString() + "";
                if (strNO!= "0000") {
                    loadNO();
                } else {
                    App.toastShow(getContext(), getResources().getString(R.string.stockRemoval_hint), Toast.LENGTH_SHORT);
                }
                break;
            case R.id.imgSearch:
                strNO = editNO.getText().toString() + "";
                if (strNO!= "0000") {
                    loadNO();
                } else {
                    App.toastShow(getContext(), getResources().getString(R.string.stockRemoval_hint), Toast.LENGTH_SHORT);
                }
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


}
