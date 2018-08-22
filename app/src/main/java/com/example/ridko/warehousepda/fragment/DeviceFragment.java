package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.application.App;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/6/22.
 */

public class DeviceFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.seekbar1)
    AppCompatSeekBar seekbar1;
    @Bind(R.id.sys_spinner1)
    Spinner spinner1;
    @Bind(R.id.sys_spinner2)
    Spinner spinner2;
    @Bind(R.id.sys_spinner3)
    Spinner spinner3;
    @Bind(R.id.sys_spinner4)
    Spinner spinner4;
    @Bind(R.id.sys_spinner5)
    Spinner spinner5;
    @Bind(R.id.sys_spinner6)
    Spinner spinner6;
    @Bind(R.id.text2)
    TextView text2;
    @Bind(R.id.seekbar2)
    AppCompatSeekBar seekbar2;
    @Bind(R.id.text3)
    TextView text3;
    @Bind(R.id.seekbar3)
    AppCompatSeekBar seekbar3;
    @Bind(R.id.button)
    Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        获取手持机参数
    }

    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_layout, container, false);
        ButterKnife.bind(this, view);
        initView();
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
    }

    //private MyAdapter myAdapter;
    public void initView() {
//        未展开的的样式
        spinner1.setAdapter(new MyAdapter(getContext(), R.layout.adapter_mytopactionbar_spinner, getResources().getStringArray(R.array.arithmetic_array)));
        spinner2.setAdapter(new MyAdapter(getContext(), R.layout.adapter_mytopactionbar_spinner, getResources().getStringArray(R.array.Q_array)));
        spinner3.setAdapter(new MyAdapter(getContext(), R.layout.adapter_mytopactionbar_spinner, getResources().getStringArray(R.array.Q_array)));
        spinner4.setAdapter(new MyAdapter(getContext(), R.layout.adapter_mytopactionbar_spinner, getResources().getStringArray(R.array.Q_array)));
        spinner5.setAdapter(new MyAdapter(getContext(), R.layout.adapter_mytopactionbar_spinner, getResources().getStringArray(R.array.storage_array)));
        spinner6.setAdapter(new MyAdapter(getContext(), R.layout.adapter_mytopactionbar_spinner, getResources().getStringArray(R.array.area_array)));
        spinner1.setSelection(1);
        spinner2.setSelection(4);
        spinner3.setSelection(0);
        spinner4.setSelection(15);
        spinner5.setSelection(0);
        spinner6.setSelection(3);
        spinner1.setOnItemSelectedListener(new MySpinnerListener());
        spinner2.setOnItemSelectedListener(new MySpinnerListener());
        spinner3.setOnItemSelectedListener(new MySpinnerListener());
        spinner4.setOnItemSelectedListener(new MySpinnerListener());
        spinner5.setOnItemSelectedListener(new MySpinnerListener());
        spinner6.setOnItemSelectedListener(new MySpinnerListener());
        seekbar1.setOnSeekBarChangeListener(this);
        seekbar1.setProgress(25);
        seekbar2.setOnSeekBarChangeListener(this);
        seekbar2.setProgress(65535);
        seekbar3.setOnSeekBarChangeListener(this);
        seekbar3.setProgress(300);
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
    //改变时候调用
//设置手持机参数2
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekbar1:
                if (fromUser) {
                    text1.setText(progress + 5 + "dBM");
                    App.PRWOER = progress + 5;
                }
                break;
            case R.id.seekbar2:
                if (fromUser) {
                    text2.setText(progress + "ms");
                    App.WORK_TIME = progress;
                }
                break;
            case R.id.seekbar3:
                if (fromUser) {
                    text3.setText(progress + "ms");
                    App.FREE_TIME = progress;
                }
        }
    }

    //拖动变化时候调用
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    //停止拖动时候调用
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @OnClick(R.id.button)
    public void onViewClicked(View view) {
//        button恢复默认设置
    }

    class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(@NonNull Context context, @LayoutRes int resource, String[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.adapter_mytopactionbar_spinner_item, parent, false);
            }
//            展开的item样式
            TextView spinnerText = (TextView) convertView.findViewById(R.id.spinner_textView);
            spinnerText.setText(getItem(position) + "");
            return convertView;
        }
    }
    class MySpinnerListener implements AdapterView.OnItemSelectedListener {
//被选中改变默认值的时候触发，在界面构建和退出时候会全部触发一次
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            int i=position;
            switch (adapterView.getId()) {
                case R.id.sys_spinner1:
//                算法
                    App.ARITHMETIC = position;
                    break;
                case R.id.sys_spinner2:
//                起始值
                    App.START_Q = position;
                    break;
                case R.id.sys_spinner3:
//                最小Q
                    App.MIN_Q = position;
                    break;
                case R.id.sys_spinner4:
//                最大Q
                    App.MAX_Q = position;
                    break;
                case R.id.sys_spinner5:
//                存储
                    App.STORAGE = position;
                    break;
                case R.id.sys_spinner6:
//                区域
                    App.AREA = position;
                    break;
            }
        }
//不被选中的时候出发
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
