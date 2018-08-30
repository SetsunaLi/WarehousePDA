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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.application.App;
import com.example.ridko.warehousepda.picture.CutToBitmap;

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
        text1.setText(getResources().getString(R.string.text40));
        ib1.setImageBitmap(cutToBitmap(ib1,R.drawable.buy1));
        ib2.setImageBitmap(cutToBitmap(ib2,R.drawable.stock2));
//        ib1.setImageDrawable(getResources().getDrawable(R.drawable.buy1));
//        ib2.setImageDrawable(getResources().getDrawable(R.drawable.stock2));
        ib1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ((ImageButton) view).setImageDrawable(getResources().getDrawable(R.drawable.buy3));
//                    ib1.setImageBitmap(cutToBitmap(ib1,R.drawable.buy3));
                }else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    int[] locatioin=new int[2];
                    view.getLocationOnScreen(locatioin);
                    if ((motionEvent.getRawX() >= locatioin[0] && motionEvent.getRawX()<=locatioin[0]+view.getWidth())
                            &&(motionEvent.getRawY()>= locatioin[1] &&motionEvent.getRawY()<= locatioin[1]+view.getHeight())){
//                        ib1.setImageDrawable(getResources().getDrawable(R.drawable.buy3));
                    }else {
                        if (App.ISBUY){
//                            ib1.setImageDrawable(getResources().getDrawable(R.drawable.buy1));
                            ib1.setImageBitmap(cutToBitmap(ib1,R.drawable.buy1));
                        }else {
//                            ib1.setImageDrawable(getResources().getDrawable(R.drawable.buy2));
                            ib1.setImageBitmap(cutToBitmap(ib1,R.drawable.buy2));
                        }
                    }
                }
                return false;
            }
        });
        ib2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    ((ImageButton) view).setImageDrawable(getResources().getDrawable(R.drawable.stock3));
//                    ib2.setImageBitmap(cutToBitmap(ib2,R.drawable.stock3));
                }else if (motionEvent.getAction()==MotionEvent.ACTION_MOVE) {
                    int[] locatioin = new int[2];
                    view.getLocationOnScreen(locatioin);
                    if ((motionEvent.getRawX() >= locatioin[0] && motionEvent.getRawX() <= locatioin[0] + view.getWidth())
                            && (motionEvent.getRawY() >= locatioin[1] && motionEvent.getRawY() <= locatioin[1] + view.getHeight())) {
//                        ib2.setImageDrawable(getResources().getDrawable(R.drawable.stock3));
                    } else {
                        if (App.ISBUY) {
//                            ib2.setImageDrawable(getResources().getDrawable(R.drawable.stock2));
                            ib2.setImageBitmap(cutToBitmap(ib2,R.drawable.stock2));
                        } else {
//                            ib2.setImageDrawable(getResources().getDrawable(R.drawable.stock1));
                            ib2.setImageBitmap(cutToBitmap(ib2,R.drawable.stock1));
                        }
                    }
                }
                return false;
            }
        });
//        initView();
    }
    private AnimationDrawable frameanim;
    public void initView(){
        frameanim=(AnimationDrawable)imgSearch.getBackground();
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
    private String strNO = "";
    private Fragment fragment;
    protected static final String TAG_CONTENT_FRAGMENT = "ContentFragment";
    @OnClick({R.id.ib1, R.id.ib2,R.id.tvSearch,R.id.imgSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib1:
//                ((ImageButton)view).setImageDrawable(getResources().getDrawable(R.drawable.buy1));
//                ib2.setImageDrawable(getResources().getDrawable(R.drawable.stock2));
                ib1.setImageBitmap(cutToBitmap(ib1,R.drawable.buy1));
                ib2.setImageBitmap(cutToBitmap(ib2,R.drawable.stock2));
                App.ISBUY=true;
                text1.setText(getResources().getString(R.string.text40));
                break;
            case R.id.ib2:
//                ((ImageButton)view).setImageDrawable(getResources().getDrawable(R.drawable.stock1));
//                ib1.setImageDrawable(getResources().getDrawable(R.drawable.buy2));
                ib1.setImageBitmap(cutToBitmap(ib1,R.drawable.buy2));
                ib2.setImageBitmap(cutToBitmap(ib2,R.drawable.stock1));
                App.ISBUY=false;
                text1.setText(getResources().getString(R.string.text41));
                break;
            case R.id.tvSearch:
                strNO = edit1.getText().toString() + "";
                if (strNO!=null) {
                    if (fragment==null)
                        fragment=new SpecialFragment2();
                    FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.content_frame,fragment,TAG_CONTENT_FRAGMENT).addToBackStack(null);
                    transaction.show(fragment);
                    transaction.commit();
                } else {
                    App.toastShow(getContext(), getResources().getString(R.string.stockRemoval_hint), Toast.LENGTH_SHORT);
                }
               /*try {
                RegionService regionService = ClientService.getBean(RegionService.class);
                List<Region> regions = regionService.getRegion();
                Log.i("client",regions.toString());
               }catch (Exception e){
                   e.fillInStackTrace();
                   Log.i("client",e.getMessage());
               }*/
                break;
            case R.id.imgSearch:
                frameanim=(AnimationDrawable)getContext().getResources().getDrawable(R.drawable.search_anim_drawable);
                imgSearch.setImageDrawable(frameanim);
                frameanim.start();
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

     /** 重置图片大小 **/
    public Bitmap resetBitmap(View view, Bitmap oldBitmap) {
        int viewWidth = view.getMeasuredWidth();
        int bitHeight = view.getMeasuredHeight();
        // 第一种方法
        return Bitmap.createScaledBitmap(oldBitmap, viewWidth, bitHeight, true);
        // 第二种方法
        // Matrix matrix = new Matrix();
        // matrix.postScale(viewWidth, bitHeight);
        // Bitmap newbm = Bitmap.createBitmap(oldBitmap, 0, 0,
        // oldBitmap.getWidth(),oldBitmap.getHeight(), matrix, true);
        // return newbm;
    }
}
