package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.adapter.ListDataAdapter;
import com.example.ridko.warehousepda.entity.ListEntity;
import com.example.ridko.warehousepda.view.HVListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/6/13.
 */

public class StockUPThree extends Fragment {
    @Bind(R.id.head)
    LinearLayout head;
    @Bind(R.id.list)
    HVListView list;
    @Bind(R.id.button_blink)
    Button buttonBlink;
    @Bind(R.id.button_ok)
    Button buttonOk;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ArrayList<ListEntity> mlist = new ArrayList<>();
    private ListDataAdapter mAdapter;

    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_up_layout3, container, false);
        ButterKnife.bind(this, view);
        list.mListHead = (LinearLayout) getActivity().findViewById(R.id.head);
        mAdapter = new ListDataAdapter(mlist, getContext());
        list.setAdapter(mAdapter);
        mAdapter.setData(mlist);
        mAdapter.notifyDataSetChanged();
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
    UnBindAndBindFragment f3 = UnBindAndBindFragment.newInstance();
    @OnClick({R.id.button_blink, R.id.button_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_blink:
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.content_frame,f3);
                transaction.show(f3);
                transaction.commit();
                break;
            case R.id.button_ok:
                KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_BACK);
                getActivity().onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
                break;
        }
    }
}
