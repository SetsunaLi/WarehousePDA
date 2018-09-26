package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.application.App;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/6/22.
 */

public class SystemFragment extends Fragment {

    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.edit1)
    EditText edit1;
    @Bind(R.id.edit2)
    EditText edit2;
    @Bind(R.id.tv4)
    TextView tv4;
    @Bind(R.id.switch1)
    Switch switch1;
    @Bind(R.id.button)
    Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        访问服务器跟新系统信息

    }

    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.system_layout, container, false);
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
        if (App.SYSTEM_VERSION != null)
            tv1.setText(App.SYSTEM_VERSION + "");
        if (App.IP != null)
            edit1.setText(App.IP + "");
        if (App.PORT != null)
            edit2.setText(App.PORT + "");
        if (App.DEVICE_NO != null)
            tv4.setText(App.DEVICE_NO + "");
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                App.SYSTEM_PUSH = b;
                if (b) {
//                    打开推送
                } else {
//                    关闭推送
                }
            }
        });
        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                App.IP=edit1.getText().toString()+"";
            }
        });
        edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                App.PORT=edit2.getText().toString()+"";
            }
        });
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

    @OnClick({R.id.button,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
//                重新访问服务器更新信息
//                 fuction

                if (App.SYSTEM_VERSION != null)
                    tv1.setText(App.SYSTEM_VERSION + "");
                if (App.IP != null)
                    edit1.setText(App.IP + "");
                if (App.PORT != null)
                    edit2.setText(App.PORT + "");
                if (App.DEVICE_NO != null)
                    tv4.setText(App.DEVICE_NO + "");
                break;
        }
    }
}
