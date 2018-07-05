package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/3/31.
 */

public class SpecialStorageFragment extends Fragment {

    @Bind(R.id.ib1)
    ImageButton ib1;
    @Bind(R.id.ib2)
    ImageButton ib2;
    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.edit1)
    EditText edit1;
    @Bind(R.id.tvSearch)
    TextView tvSearch;
    @Bind(R.id.relativelayout)
    RelativeLayout relativelayout;
    @Bind(R.id.imgSearch)
    ImageView imgSearch;
    @Bind(R.id.tvNosearch)
    TextView tvNosearch;

    public static SpecialStorageFragment newInstance() {
        return new SpecialStorageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.special_fragment, container, false);
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
        ib1.setImageDrawable(getResources().getDrawable(R.drawable.buy1));
        ib2.setImageDrawable(getResources().getDrawable(R.drawable.stock2));
        ib1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ((ImageButton) view).setImageDrawable(getResources().getDrawable(R.drawable.buy3));
                }
                return false;
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
                break;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ib1, R.id.ib2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib1:
                break;
            case R.id.ib2:
                break;
        }
    }
}
