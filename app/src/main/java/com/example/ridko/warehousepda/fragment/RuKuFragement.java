package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.adapter.ListDataAdapter;
import com.example.ridko.warehousepda.entity.ListEntity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/7/24.
 */

public class RuKuFragement extends Fragment {
    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.edit1)
    EditText edit1;
    @Bind(R.id.spinner1)
    Spinner spinner1;
    @Bind(R.id.spinner2)
    Spinner spinner2;
    @Bind(R.id.spinner3)
    Spinner spinner3;
    @Bind(R.id.edit2)
    EditText edit2;
    @Bind(R.id.edit3)
    EditText edit3;
    @Bind(R.id.edit4)
    EditText edit4;
    @Bind(R.id.edit5)
    EditText edit5;
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
        View view = inflater.inflate(R.layout.ru_ku_layout, container, false);
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

    @OnClick({R.id.button_ok,})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.button_ok:
                blinkDialog();
                break;
        }
    }
    private void blinkDialog() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View blinkView = inflater.inflate(R.layout.dialog_bind, null);
        Button no = (Button) blinkView.findViewById(R.id.dialog_no);
        Button yes = (Button) blinkView.findViewById(R.id.dialog_yes);
        EditText editNo = (EditText) blinkView.findViewById(R.id.editNO);
        ImageView img = (ImageView) blinkView.findViewById(R.id.imgSearch);
        dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();
        dialog.getWindow().setContentView(blinkView);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
                KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_BACK);
                getActivity().onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                询标开始
            }
        });
    }
}
