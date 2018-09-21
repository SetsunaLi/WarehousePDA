package com.example.ridko.warehousepda.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.activity.MainActivity;
import com.example.ridko.warehousepda.application.App;
import com.example.ridko.warehousepda.client.OutboundDetail;
import com.example.ridko.warehousepda.common.ResponseHandlerInterfaces;
import com.example.ridko.warehousepda.entity.DemoEntity1;
import com.example.ridko.warehousepda.entity.DemoEntity2;
import com.example.ridko.warehousepda.inventory.InventoryListItem;
import com.example.ridko.warehousepda.listener.MyItemOnTouchListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mumu on 2018/3/31.
 */

public class OutDemoFragment2 extends Fragment implements ResponseHandlerInterfaces.ResponseTagHandler,
        ResponseHandlerInterfaces.TriggerEventHandler{

    @Bind(R.id.list1)
    ListView list1;
    @Bind(R.id.button_blink)
    Button buttonBlink;
    @Bind(R.id.button_ok)
    Button buttonOk;

//    private List<OutboundDetail> mylist;
    private Map<String,Integer> epcIdMap;
    private Set<String> epcSet;
    private DemoAdapter adapter;
    private String memoryBankID = "none";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_layout2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private int selectID = -1;

    //可以不要
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());
        initData();
        if (adapter==null)
        adapter = new DemoAdapter(getContext(), R.layout.list_item_4_demo, App.detilList);
        list1.setAdapter(adapter);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.selectItem(i);
                adapter.notifyDataSetChanged();
                selectID = i;
            }
        });
    }

    private void initData() {
        if (epcIdMap==null)
        epcIdMap=new HashMap<>();
        epcIdMap.clear();
        if (epcSet==null)
        epcSet=new ArraySet<>();
        epcSet.clear();
        for (int i=0;i<App.detilList.size();i++){
            if (App.detilList.get(i).getFlag()==1)
                epcSet.add(App.detilList.get(i).getEpc());
            epcIdMap.put(App.detilList.get(i).getEpc(),i);
        }
        if (App.detilList==null)
            App.detilList=new ArrayList<>();
    }
//    扫描刷新数据
    private void cleanData(){
        if (epcSet!=null)
            epcSet.clear();
        for (OutboundDetail detail:App.detilList){
            detail.setFlag(0);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        epcSet.clear();
        if (App.mIsInventoryRunning)
            ((MainActivity) getActivity()).inventoryStartOrStop(buttonBlink);
    }

    private void blinkDialog() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View blinkView = inflater.inflate(R.layout.dialog_stock_removal, null);
        Button no = (Button) blinkView.findViewById(R.id.dialog_no);
        Button yes = (Button) blinkView.findViewById(R.id.dialog_yes);
        TextView text = (TextView) blinkView.findViewById(R.id.dialog_text);
        text.setText(R.string.text61);
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
//                返回上一层

                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.button_blink,R.id.button_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_blink:
                if (!App.mIsInventoryRunning)
                cleanData();
//                扫描开始
                ((MainActivity) getActivity()).inventoryStartOrStop(buttonBlink);
                break;
            case R.id.button_ok:
                App.isReturn=true;
                getFragmentManager().popBackStack();
                break;
        }
    }
    /*方法在寻读状态设置为在读写器断开连接是停止*/
    public void resetInventoryDetail(){
        if (getActivity() != null) {
            if (buttonBlink != null)
                buttonBlink.setText(getString(R.string.start_title));
        }
    }
    public String getMemoryBankID() {
        return memoryBankID;
    }
    String epc;
    @Override
    public void handleTagResponse(InventoryListItem inventoryListItem, boolean isAddedToList) {
        if (isAddedToList)
            epc=inventoryListItem.getText();
        if (!epcSet.contains(epc)){
            epcSet.add(epc);
            if(epcIdMap.containsKey(epc)){
                App.detilList.get(epcIdMap.get(epc)).setFlag(1);
//                可能这里要异步操作
                if (adapter!=null)
                    adapter.notifyDataSetChanged();
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

    class DemoAdapter extends ArrayAdapter<OutboundDetail> {
        private List<OutboundDetail> list;
        private LayoutInflater mInflater;

        public DemoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<OutboundDetail> objects) {
            super(context, resource, objects);
            this.list = objects;
            this.mInflater = LayoutInflater.from(context);
        }

        private int id = -255;

        public void selectItem(int id) {
            this.id = id;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_4_demo, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.item1 = (TextView) convertView.findViewById(R.id.item1);
                viewHolder.item2 = (TextView) convertView.findViewById(R.id.item2);
                viewHolder.item3 = (TextView) convertView.findViewById(R.id.item3);
                viewHolder.item4 = (TextView) convertView.findViewById(R.id.item4);
                viewHolder.item5 = (TextView) convertView.findViewById(R.id.item5);
                viewHolder.item6 = (TextView) convertView.findViewById(R.id.item6);
                viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.head);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.item1.setText(position+1 + "");
            viewHolder.item2.setText(list.get(position).getTicketNo()+"");
            viewHolder.item3.setText(list.get(position).getClothNo()+"");
            viewHolder.item4.setText(list.get(position).getWeight()+"");
            viewHolder.item5.setText(list.get(position).getVateDye()+"");
            viewHolder.item6.setText(list.get(position).getEpc()+"");

            if (position == id) {
                viewHolder.layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorDialogTitleBG));
            } else {
                switch (list.get(position).getFlag()){
                    case 0:
                        viewHolder.layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorZERO));
                        break;
                    case 1:
                        viewHolder.layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorFindEpc));
                        break;
                    case 2:
                        viewHolder.layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
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
            LinearLayout layout;
        }
    }
}
