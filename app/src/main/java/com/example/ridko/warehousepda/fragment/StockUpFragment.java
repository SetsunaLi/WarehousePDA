package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.picture.CutToBitmap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/3/31.
 */

public class StockUpFragment extends Fragment {

    @Bind(R.id.editNO)
    EditText editNO;
    @Bind(R.id.tvSearch)
    TextView tvSearch;
    @Bind(R.id.relativelayout)
    RelativeLayout relativelayout;
    @Bind(R.id.imgSearch)
    ImageView imgSearch;
    @Bind(R.id.tvNosearch)
    TextView tvNosearch;

    public static StockUpFragment newInstance() {
        return new StockUpFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_up_layout, container, false);
        ButterKnife.bind(this, view);
//        initView();
        return view;
    }
    private AnimationDrawable frameanim;
    public void initView(){
        frameanim=(AnimationDrawable)imgSearch.getBackground();
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
    protected static final String TAG_CONTENT_FRAGMENT = "ContentFragment";
    private StockUPTwo fragment;
    @OnClick({R.id.tvSearch,R.id.imgSearch})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tvSearch:
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                fragment=new StockUPTwo();
                transaction.add(R.id.content_frame,fragment,TAG_CONTENT_FRAGMENT).addToBackStack(null);
                transaction.show(fragment);
                transaction.commit();
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
    /** 图片转化Bitmap **/
    public Bitmap cutToBitmap(View view, int resId) {
        Resources res = getResources();
        int imageViewWidth = view.getWidth();
        int imageViewHeight = view.getHeight();
        return CutToBitmap.decodeResourceBySampleRate(res, resId,
                imageViewWidth, imageViewHeight);
    }
}
