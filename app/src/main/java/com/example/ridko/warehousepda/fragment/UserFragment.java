package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.application.App;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/6/22.
 */

public class UserFragment extends Fragment {
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.tv4)
    TextView tv4;
    @Bind(R.id.linearlayout)
    LinearLayout linearlayout;
    @Bind(R.id.button)
    Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        访问服务器更新用户信息

    }

    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_layout, container, false);
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
        if (App.USER_NAME!=null)
        tv1.setText(App.USER_NAME+"");
        if ((App.USER_ID!=null))
            tv2.setText(App.USER_ID+"");
        if(App.LOADTIME!=null)
            tv3.setText(App.LOADTIME+"");
        if (App.STATION!=null)
            tv4.setText(App.STATION+"");
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

    @OnClick({R.id.linearlayout, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearlayout:
                blinkDialog();
                break;
            case R.id.button:

                break;
        }
    }
    private void blinkDialog(){
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View blinkView = inflater.inflate(R.layout.dialog_change_prassword, null);
        Button no=(Button) blinkView.findViewById(R.id.dialog_no);
        Button yes=(Button) blinkView.findViewById(R.id.dialog_yes);
        final EditText edit1=(EditText)blinkView.findViewById(R.id.edit1);
        final EditText edit2=(EditText)blinkView.findViewById(R.id.edit2);
        final EditText edit3=(EditText)blinkView.findViewById(R.id.edit3);
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
                String oldPrassword=edit1.getText().toString();
                String newPrassword=edit2.getText().toString();
                String samePrassword=edit3.getText().toString();
                if (newPrassword!=null&&newPrassword.equals(samePrassword)){
                if (oldPrassword!=null){
//                    判断旧密码是否正确
                }else {
                    App.toastShow(getContext(),getResources().getString(R.string.text29), Toast.LENGTH_SHORT);
                }
                dialog.dismiss();
                }else {
                    App.toastShow(getContext(),getResources().getString(R.string.text28), Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
