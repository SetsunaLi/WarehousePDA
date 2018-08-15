package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.activity.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/7/14.
 */

public class IntoID extends Fragment {
    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.text3)
    TextView text3;
    /*    @Bind(R.id.text4)
        TextView text4;
        @Bind(R.id.text5)
        TextView text5;
        @Bind(R.id.text6)
        TextView text6;*/
    @Bind(R.id.edit1)
    EditText edit1;
    @Bind(R.id.button_ok)
    Button buttonOk;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.into_id_layout, container, false);
        ButterKnife.bind(this, view);
        initView();

        return view;
    }

    public void initView() {
//text1~text6赋值
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

    private MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
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

    @OnClick({R.id.button_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_ok:
                strNO = edit1.getText().toString() + "";
//                把缸号录入入库单
//                返回上一层
//                activity.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK, KeyEvent.ACTION_DOWN));
                getFragmentManager().popBackStack();
                break;
        }
    }

}
