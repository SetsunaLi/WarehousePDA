package com.example.ridko.warehousepda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.application.App;
import com.zebra.rfid.api3.RFIDReader;
import com.zebra.rfid.api3.ReaderDevice;

import java.util.ArrayList;

/**
 * Created by qvfr34 on 2/6/2015.
 * 斑马连接设备列表监听
 */
public class ReaderListAdapter extends ArrayAdapter<ReaderDevice> {
    private final ArrayList<ReaderDevice> readersList;
    private final Context context;
    private final int resourceId;

    public ReaderListAdapter(Context context, int resourceId, ArrayList<ReaderDevice> readersList) {
        super(context, resourceId, readersList);
        this.context = context;
        this.resourceId = resourceId;
        this.readersList = readersList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReaderDevice reader = readersList.get(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resourceId, null);
        }
        CheckedTextView checkedTextView = (CheckedTextView) convertView.findViewById(R.id.reader_checkedtextview);
        checkedTextView.setTextColor(context.getResources().getColor(R.color.colorHomeButton2));
        checkedTextView.setText(reader.getName() + "\n" + reader.getAddress());

        LinearLayout readerDetail = (LinearLayout) convertView.findViewById(R.id.reader_detail);
        RFIDReader rfidReader = reader.getRFIDReader();
        if (rfidReader != null && rfidReader.isConnected()) {
            checkedTextView.setChecked(true);
            readerDetail.setVisibility(View.VISIBLE);
//            System.out.println("rfidReader " + rfidReader.ReaderCapabilities.getSerialNumber() +" "+  App.isBatchModeInventoryRunning);
            if (App.isBatchModeInventoryRunning != null && App.isBatchModeInventoryRunning) {
                ((TextView) convertView.findViewById(R.id.tv_model)).setText(context.getResources().getString(R.string.batch_mode_running_title));
                ((TextView) convertView.findViewById(R.id.tv_serial)).setText(context.getResources().getString(R.string.batch_mode_running_title));
            }
            else if (rfidReader.ReaderCapabilities.getModelName() != null && rfidReader.ReaderCapabilities.getSerialNumber() != null ) {
                ((TextView) convertView.findViewById(R.id.tv_model)).setText(rfidReader.ReaderCapabilities.getModelName());
                ((TextView) convertView.findViewById(R.id.tv_serial)).setText(rfidReader.ReaderCapabilities.getSerialNumber());
            }

        } else {
            readerDetail.setVisibility(View.GONE);
//            checkedTextView.setChecked(false);
        }
        return convertView;
    }
}
