package com.example.ridko.warehousepda.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.activity.MainActivity;
import com.example.ridko.warehousepda.application.App;
import com.example.ridko.warehousepda.client.OkHttpClientManager;
import com.example.ridko.warehousepda.client.Outbound;
import com.example.ridko.warehousepda.client.OutboundApplyDetail;
import com.example.ridko.warehousepda.client.OutboundDetail;
import com.example.ridko.warehousepda.common.ResponseHandlerInterfaces;
import com.example.ridko.warehousepda.inventory.InventoryListItem;
import com.example.ridko.warehousepda.listener.MyItemOnTouchListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/9/4.
 */

public class OutDemoFragment1 extends Fragment implements MyItemOnTouchListener, ResponseHandlerInterfaces.ResponseTagHandler,
        ResponseHandlerInterfaces.TriggerEventHandler {
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.layout1)
    LinearLayout layout1;
    @Bind(R.id.button_blink)
    Button buttonBlink;
    @Bind(R.id.button_ok)
    Button buttonOk;

    private String memoryBankID = "none";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            if (data.getBoolean("UpLoading")) {
                fragment = new OutBoundNoFragment();
                getActivity().getSupportFragmentManager().popBackStackImmediate(OutBoundNoFragment.class.getName(),FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragment, TAG_CONTENT_FRAGMENT);
                transaction.show(fragment);
                transaction.commit();
            } else {
                App.toastShow(getContext(), getResources().getString(R.string.outBoundUploadFail), Toast.LENGTH_SHORT);
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    private MyAdapter myAdapter;
    private HashSet<String> epcSet;
    //    key=布号+缸号  value=List<OutboundDetail>  其中OutboundDetail为大界面读到的epc信息
    private Map<String, List<OutboundDetail>> clotheNoVateDyeList;
    protected static final String TAG_CONTENT_FRAGMENT = "ContentFragment";
    private Fragment fragment;

    //可以不要
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());
        initData();
        if (App.outboundApplyDetailList != null)
            myAdapter = new MyAdapter(getContext(), R.layout.list_item_3_demo, App.outboundApplyDetailList);
        myAdapter.setMyItemOnTouchListener(this);
        listview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myAdapter.selectItem(i);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        if (epcSet == null)
            epcSet = new HashSet<>();
//        epcSet.clear();
        if (clotheNoVateDyeList == null)
            clotheNoVateDyeList = new HashMap<>();
//        clotheNoVateDyeList.clear();

    }

    private void cleanData() {
        if (epcSet != null)
            epcSet.clear();
        if (clotheNoVateDyeList != null)
            clotheNoVateDyeList.clear();
        for (int i = 0; i < App.outboundApplyDetailList.size(); i++) {
            if (App.outboundApplyDetailList.get(i).getFlag() == 2)
                App.outboundApplyDetailList.remove(App.outboundApplyDetailList.get(i));
            else {
                App.outboundApplyDetailList.get(i).clearReadNum();
                App.outboundApplyDetailList.get(i).setFlag(0);
            }
        }
        myAdapter.notifyDataSetChanged();
    }

    /*方法在寻读状态设置为在读写器断开连接是停止*/
    public void resetInventoryDetail() {
        if (getActivity() != null) {
            if (buttonBlink != null)
                buttonBlink.setText(getString(R.string.start_title));
        }
    }

    public String getMemoryBankID() {
        return memoryBankID;
    }

    //    去空
    public String delSpacing(String str) {
        str = str.trim();
        return str;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (App.isReturn) {
                App.isReturn = false;
                if (App.detilList != null && App.detilList.size() != 0)
                    for (OutboundApplyDetail detail : App.outboundApplyDetailList) {
                        if (detail.getVatDyeNo().equals(App.detilList.get(0).getVateDye())) {
                            detail.clearReadNum();
                            if (detail.getFlag() != 2)
                                detail.setFlag(0);
                            if (clotheNoVateDyeList.containsKey(App.detilList.get(0).getClothNo() + App.detilList.get(0).getVateDye()))
                                clotheNoVateDyeList.get(App.detilList.get(0).getClothNo() + App.detilList.get(0).getVateDye()).clear();
                            else
                                clotheNoVateDyeList.put(App.detilList.get(0).getClothNo() + App.detilList.get(0).getVateDye(), new ArrayList<OutboundDetail>());
                            for (OutboundDetail od : App.detilList)
                                if (od.getFlag() == 1) {
                                    detail.addReadNum();
                                    if (detail.getNum()==detail.getReadNum())
                                        detail.setFlag(1);
                                    else if (detail.getNum()<detail.getReadNum())
                                        detail.setFlag(3);
                                    clotheNoVateDyeList.get(App.detilList.get(0).getClothNo() + App.detilList.get(0).getVateDye()).add(od);
                                    if (!epcSet.contains(od.getEpc()))
                                        epcSet.add(od.getEpc());
                                } else {
                                    if (epcSet.contains(od.getEpc()))
                                        epcSet.remove(od.getEpc());
                                }
                        }
                    }
                myAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (App.detilList != null)
            App.detilList.clear();
    }

    @OnClick({R.id.button_blink, R.id.button_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_blink:
                if (!App.mIsInventoryRunning)
                    cleanData();
//                扫描开始
                ((MainActivity) getActivity()).inventoryStartOrStop(buttonBlink);
                break;
            case R.id.button_ok:
                blinkDialog();
                break;
        }
    }

    private void blinkDialog() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View blinkView = inflater.inflate(R.layout.dialog_outbound, null);
        Button no = (Button) blinkView.findViewById(R.id.dialog_no);
        Button yes = (Button) blinkView.findViewById(R.id.dialog_yes);
        TextView text = (TextView) blinkView.findViewById(R.id.dialog_text);
        final EditText editText = (EditText) blinkView.findViewById(R.id.edit1);
        editText.setText("模拟出库");
        text.setText(R.string.text61);
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
                /*fragment = new OutBoundNoFragment();
                getActivity().getSupportFragmentManager().popBackStackImmediate(OutBoundNoFragment.class.getName(),FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragment, TAG_CONTENT_FRAGMENT);
                transaction.show(fragment);
                transaction.commit();*/
                List<OutboundDetail> detilList=new ArrayList<OutboundDetail>();
                for (List<OutboundDetail> od : clotheNoVateDyeList.values()) {
                    if (od.get(0).getFlag() != 2) {
                        detilList.addAll(od);
                    }
                }
                App.REMARKS=editText.getText().toString() + "";
                final String jsonString = JSON.toJSONString(new Outbound(App.outboundApplyDetailList.get(0).getApplyNo(), App.REMARKS, detilList));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Response response = null;
                        try {
                            response = OkHttpClientManager.postJsonAsyn(OkHttpClientManager.outBoundURL, jsonString);
                            if (response.isSuccessful()) {
                                Message msg = handler.obtainMessage();
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("UpLoading", true);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            } else {
                                Message msg = handler.obtainMessage();
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("UpLoading", false);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(final int position) {
        if (App.mIsInventoryRunning)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((MainActivity) getActivity()).inventoryStartOrStop(buttonBlink);
                }
            });
        OkHttpClientManager.getAsyn(OkHttpClientManager.applyDetailURL + App.outboundApplyDetailList.get(position).getVatDyeNo(), new OkHttpClientManager.ResultCallback<List<OutboundDetail>>() {
            @Override
            public void onError(Request request, Exception e) {
//                申请详情失败
                Log.i("报错", "检查错误");
            }

            @Override
            public void onResponse(List<OutboundDetail> responseList) {
                if (responseList != null) {
                    for (OutboundDetail detail : responseList) {
                       /* detail.setEpc(delSpacing(detail.getEpc()));
                        detail.setClothNo(delSpacing(detail.getClothNo()));
                        detail.setTicketNo(delSpacing(detail.getTicketNo()));
                        detail.setVateDye(delSpacing(detail.getVateDye()));*/
                        if (clotheNoVateDyeList.containsKey(App.outboundApplyDetailList.get(position).getClothNo() + App.outboundApplyDetailList.get(position).getVatDyeNo()))
                            for (OutboundDetail detail2 : clotheNoVateDyeList.get(App.outboundApplyDetailList.get(position).getClothNo() + App.outboundApplyDetailList.get(position).getVatDyeNo())) {
                                if (detail2.getEpc().equals(detail.getEpc()))
                                    detail.setFlag(1);
                            }
                    }
                    App.detilList = responseList;

                    fragment = new OutDemoFragment2();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag(TAG_CONTENT_FRAGMENT);
                    if (f instanceof OutDemoFragment1) {
                        transaction.hide(f);
                    }
                    transaction.add(R.id.content_frame, fragment, TAG_CONTENT_FRAGMENT).addToBackStack(null);
                    transaction.show(fragment);
                    transaction.commit();


                }
            }
        });

    }

    //读数据返回
    @Override
    public void handleTagResponse(InventoryListItem inventoryListItem, boolean isAddedToList) {
        if (isAddedToList) {
            if (!epcSet.contains(inventoryListItem.getText())) {
                epcSet.add(inventoryListItem.getText());
                OkHttpClientManager.getAsyn(OkHttpClientManager.epcClothURL + inventoryListItem.getText(),
                        new OkHttpClientManager.ResultCallback<OutboundDetail>() {
                            @Override
                            public void onError(Request request, Exception e) {
//                申请epc返回为空或者错误
                            }

                            @Override
                            public void onResponse(OutboundDetail response) {
                                if (response != null) {
                                 /*   response.setEpc(delSpacing(response.getEpc()));
                                    response.setClothNo(delSpacing(response.getClothNo()));
                                    response.setTicketNo(delSpacing(response.getTicketNo()));
                                    response.setVateDye(delSpacing(response.getVateDye()));*/
                                    boolean isInList = false;
                                    //判断在第一个列表是否存在
                                    // 若存在即存放到对应item里的outBoundDetailList里面
                                    //若不存在即在列表新建item并标致状态为串读
                                    for (OutboundApplyDetail applyDetail : App.outboundApplyDetailList) {
                                        if (response.getClothNo().equals(applyDetail.getClothNo())
                                                && response.getVateDye().equals(applyDetail.getVatDyeNo())) {
                                            if (!isInList)
                                                isInList = true;
                                            applyDetail.addReadNum();
                                            if (applyDetail.getNum() == applyDetail.getReadNum())
                                                applyDetail.setFlag(1);
                                            if (applyDetail.getNum() < applyDetail.getReadNum()&&applyDetail.getFlag()!=2)
                                                applyDetail.setFlag(3);
                                            if (clotheNoVateDyeList.containsKey(applyDetail.getClothNo() + applyDetail.getVatDyeNo())) {
                                                clotheNoVateDyeList.get(applyDetail.getClothNo() + applyDetail.getVatDyeNo()).add(response);
                                            } else {
                                                ArrayList<OutboundDetail> list = new ArrayList<OutboundDetail>();
                                                list.add(response);
                                                clotheNoVateDyeList.put(applyDetail.getClothNo() + applyDetail.getVatDyeNo(), list);
                                            }
                                        }
                                        if (isInList)
                                            break;
                                    }
                                    if (!isInList) {
                                        OutboundApplyDetail applyDetail1 = new OutboundApplyDetail("", response.getClothNo(), "", "", "", response.getVateDye(), 0);
                                        applyDetail1.addReadNum();
                                        applyDetail1.setFlag(2);
                                        App.outboundApplyDetailList.add(applyDetail1);
                                        ArrayList<OutboundDetail> list = new ArrayList<OutboundDetail>();
                                        list.add(response);
                                        clotheNoVateDyeList.put(response.getClothNo() + response.getVateDye(), list);
                                    }
                                    myAdapter.notifyDataSetChanged();
                                }
                            }
                        });
            }

        }
    }

    //手持机扫描键监听
    @Override
    public void triggerPressEventRecieved() {
        if (!App.mIsInventoryRunning)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((MainActivity) getActivity()).inventoryStartOrStop(buttonBlink);
                }
            });
    }

    @Override
    public void triggerReleaseEventRecieved() {
        if (App.mIsInventoryRunning)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((MainActivity) getActivity()).inventoryStartOrStop(buttonBlink);
                }
            });
    }

    class MyAdapter extends ArrayAdapter<OutboundApplyDetail> {
        private List<OutboundApplyDetail> list;
        private LayoutInflater mInflater;
        //        在适配器做做item 触摸监听监听
        private MyItemOnTouchListener myItemOnTouchListener;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<OutboundApplyDetail> objects) {
            super(context, resource, objects);
            this.list = objects;
            this.mInflater = LayoutInflater.from(context);
        }

        private int id = -255;

        public void selectItem(int id) {
            if (this.id == id)
                this.id = -255;
            else
                this.id = id;
        }

        public void setMyItemOnTouchListener(MyItemOnTouchListener myItemOnTouchListener) {
            this.myItemOnTouchListener = myItemOnTouchListener;
        }

        @Override
        public synchronized void add(@Nullable OutboundApplyDetail object) {
            super.add(object);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_3_demo, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.item1 = (TextView) convertView.findViewById(R.id.item1);
                viewHolder.item2 = (TextView) convertView.findViewById(R.id.item2);
                viewHolder.item3 = (TextView) convertView.findViewById(R.id.item3);
                viewHolder.item4 = (TextView) convertView.findViewById(R.id.item4);
                viewHolder.item5 = (TextView) convertView.findViewById(R.id.item5);
                viewHolder.item6 = (TextView) convertView.findViewById(R.id.item6);
                viewHolder.item7 = (TextView) convertView.findViewById(R.id.item7);
                viewHolder.item8 = (TextView) convertView.findViewById(R.id.item8);
                viewHolder.button = (Button) convertView.findViewById(R.id.button1);
                viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.head);
                viewHolder.headNo = (LinearLayout) convertView.findViewById(R.id.headNo);
                viewHolder.text1 = (TextView) convertView.findViewById(R.id.text1);
                viewHolder.check = (CheckBox) convertView.findViewById(R.id.checkbox1);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (list.get(position).getOutBoundNo()!=null){
                if(list.get(position).getOutBoundNo().isHead()){
                    viewHolder.headNo.setVisibility(View.VISIBLE);
                    viewHolder.text1.setText(list.get(position).getOutBoundNo().getApplyNo());
                    viewHolder.check.setChecked(true);
                }
            }else {
                viewHolder.headNo.setVisibility(View.GONE);
            }
            viewHolder.item1.setText(position + 1 + "");
            viewHolder.item2.setText(list.get(position).getVatDyeNo() + "");
            viewHolder.item3.setText(list.get(position).getColorNo() + "");
            viewHolder.item4.setText(list.get(position).getClothNo() + "");
            viewHolder.item5.setText(list.get(position).getNum() + "");
            viewHolder.item6.setText(list.get(position).getReadNum() + "");
            viewHolder.item8.setText(list.get(position).getApplyNo() + "");
            switch (list.get(position).getFlag()) {
                case 0:
                    viewHolder.item7.setText("盘亏");
                    break;
                case 1:
                    viewHolder.item7.setText("正常");
                    break;
                case 2:
                    viewHolder.item7.setText("异常");
                    break;
                case 3:
                    viewHolder.item7.setText("盘盈");
                    break;
            }
//            设置字体颜色
            if (list.get(position).getNum() == list.get(position).getReadNum())
                viewHolder.item6.setTextColor(getResources().getColor(R.color.colorDataYesText));
            else
                viewHolder.item6.setTextColor(getResources().getColor(R.color.colorDataNoText));

            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (myItemOnTouchListener != null)
                        myItemOnTouchListener.onItemClick(position);
                }
            });
            viewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked){
                        if (App.outboundApplyDetailList.get(position).getOutBoundNo()!=null)
                            App.outboundApplyDetailList.get(position).getOutBoundNo().setOut(isChecked);
                }
            });
//          设置背景颜色
            if (position == id) {
                viewHolder.button.setVisibility(View.VISIBLE);
                viewHolder.layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorDialogTitleBG));
            } else {
                viewHolder.button.setVisibility(View.GONE);
                switch (list.get(position).getFlag()) {
                    case 0:
                        viewHolder.layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorZERO));
                        break;
                    case 1:
                        viewHolder.layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorFindEpc));
                        break;
                    case 2:
                        viewHolder.layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
                        break;
                    case 3:
                        viewHolder.layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorDataNoText));
                        break;
                }
            }
            return convertView;
        }

        class ViewHolder {
            TextView item1;
            TextView item2;
            TextView item3;
            TextView item4;
            TextView item5;
            TextView item6;
            TextView item7;
            TextView item8;
            Button button;
            LinearLayout layout;
            LinearLayout headNo;
            TextView text1;
            CheckBox check;

        }
    }
}
