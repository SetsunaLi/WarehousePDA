package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
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
 * Created by mumu on 2018/3/31.
 */

public class StockRemovalFragment extends Fragment {

    @Bind(R.id.editNO)
    EditText editNO;
    @Bind(R.id.imgSearch)
    ImageView imgSearch;
    @Bind(R.id.tvNosearch)
    TextView tvNosearch;
    @Bind(R.id.tvSearch)
    TextView tvSearch;
    @Bind(R.id.edit1)
    EditText edit1;
    @Bind(R.id.layout1)
    LinearLayout layout1;
    @Bind(R.id.edit2)
    EditText edit2;
    @Bind(R.id.edit3)
    EditText edit3;
    @Bind(R.id.button_blink)
    Button buttonBlink;
    @Bind(R.id.button_ok)
    Button buttonOk;

    public static StockRemovalFragment newInstance() {
        return new StockRemovalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_removal_layout, container, false);
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
        initView();
    }
    private AnimationDrawable frameanim;
    public void initView() {
        layout1.setVisibility(View.GONE);
//        buttonBlink.setVisibility(View.GONE);
//        buttonOk.setVisibility(View.GONE);
//        frameanim=(AnimationDrawable)imgSearch.getBackground();
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
    private boolean flag=false;
    protected static final String TAG_CONTENT_FRAGMENT = "ContentFragment";
    @OnClick({R.id.tvSearch,R.id.button_blink, R.id.button_ok,R.id.imgSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSearch:
                strNO = editNO.getText().toString() + "";
                if (strNO.length() >= 10) {

                } else {
                    App.toastShow(getContext(), getResources().getString(R.string.stockRemoval_hint), Toast.LENGTH_SHORT);
                }
                break;
            case R.id.button_blink:
                if (!false){

                    blinkDialog();
                }
                break;
            case R.id.button_ok:
                if (!false){

                }
                break;
            case R.id.imgSearch:
//                播放一次
//                android:oneshot="true"\\false为循环播放
                frameanim=(AnimationDrawable)getContext().getResources().getDrawable(R.drawable.search_anim_drawable);
                imgSearch.setImageDrawable(frameanim);
                frameanim.start();
                break;
        }
    }
    private void blinkDialog(){
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View blinkView = inflater.inflate(R.layout.dialog_stock_removal, null);
        Button no=(Button) blinkView.findViewById(R.id.dialog_no);
        Button yes=(Button) blinkView.findViewById(R.id.dialog_yes);
        TextView text=(TextView)blinkView.findViewById(R.id.dialog_text);
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
    }
}
