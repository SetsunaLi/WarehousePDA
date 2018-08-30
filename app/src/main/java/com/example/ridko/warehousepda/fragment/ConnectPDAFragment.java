package com.example.ridko.warehousepda.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.activity.MainActivity;
import com.example.ridko.warehousepda.adapter.ReaderListAdapter;
import com.example.ridko.warehousepda.application.App;
import com.example.ridko.warehousepda.common.Constants;
import com.example.ridko.warehousepda.common.CustomProgressDialog;
import com.zebra.rfid.api3.InvalidUsageException;
import com.zebra.rfid.api3.OperationFailureException;
import com.zebra.rfid.api3.RFIDResults;
import com.zebra.rfid.api3.ReaderDevice;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mumu on 2018/8/28.
 */

public class ConnectPDAFragment extends Fragment {
    @Bind(R.id.bondedReadersTitle)
    TextView bondedReadersTitle;
    @Bind(R.id.bondedReadersList)
    ListView pairedListView;
    @Bind(R.id.empty)
    TextView tv_emptyView;

    private DeviceConnectTask deviceConnectTask;
    private CustomProgressDialog progressDialog;
    // The on-click listener for all devices in the ListViews
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> av, View v, int pos, long arg3) {
            if (MainActivity.isBluetoothEnabled()) {
                // Get the device MAC address, which is the last 17 chars in the View
                ReaderDevice readerDevice = readerListAdapter.getItem(pos);
                if (App.mConnectedReader == null) {

                    if (deviceConnectTask == null || deviceConnectTask.isCancelled()) {
                        App.is_connection_requested = true;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            deviceConnectTask = new DeviceConnectTask(readerDevice, "Connecting with " + readerDevice.getName(), getReaderPassword(readerDevice.getName()));
                            deviceConnectTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            deviceConnectTask = new DeviceConnectTask(readerDevice, "Connecting with " + readerDevice.getName(), getReaderPassword(readerDevice.getName()));
                            deviceConnectTask.execute();
                        }
                    }
                } else {
                    {
                        if (App.mConnectedReader.isConnected()) {
                            App.is_disconnection_requested = true;
                            try {
                                App.mConnectedReader.disconnect();
                            } catch (InvalidUsageException e) {
                                e.printStackTrace();
                            } catch (OperationFailureException e) {
                                e.printStackTrace();
                            }
                            //
                            bluetoothDeviceDisConnected(App.mConnectedDevice);
//                            if (App.NOTIFY_READER_CONNECTION)
//                                sendNotification(Constants.ACTION_READER_DISCONNECTED, "Disconnected from " + App.mConnectedReader.getHostName());
                            //
                            MainActivity.clearSettings();
                        }
                        if (!App.mConnectedReader.getHostName().equalsIgnoreCase(readerDevice.getName())) {
                            App.mConnectedReader = null;
                            if (deviceConnectTask == null || deviceConnectTask.isCancelled()) {
                                App.is_connection_requested = true;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                    deviceConnectTask = new DeviceConnectTask(readerDevice, "Connecting with " + readerDevice.getName(), getReaderPassword(readerDevice.getName()));
                                    deviceConnectTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                } else {
                                    deviceConnectTask = new DeviceConnectTask(readerDevice, "Connecting with " + readerDevice.getName(), getReaderPassword(readerDevice.getName()));
                                    deviceConnectTask.execute();
                                }
                            }
                        } else {
                            App.mConnectedReader = null;
                        }
                    }
                }
                // Create the result Intent and include the MAC address
            } else
                Toast.makeText(getActivity(), getResources().getString(R.string.error_bluetooth_disabled), Toast.LENGTH_SHORT).show();
        }
    };
    public static ConnectPDAFragment newInstance() {
        return new ConnectPDAFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //    这里加载视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_readers_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public static ArrayList<ReaderDevice> readersList = new ArrayList<>();
    private ReaderListAdapter readerListAdapter;
    //这里写界面
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        readersList.clear();
        loadPairedDevices();
        if (App.mConnectedDevice != null) {
            int index = readersList.indexOf(App.mConnectedDevice);
            if (index != -1) {
                readersList.remove(index);
                readersList.add(index, App.mConnectedDevice);
            } else {
                App.mConnectedDevice = null;
                App.mConnectedReader = null;
            }
        }

        readerListAdapter = new ReaderListAdapter(getActivity(), R.layout.readers_list_item, readersList);

        if (readerListAdapter.getCount() == 0) {
            pairedListView.setEmptyView(tv_emptyView);
        } else
            pairedListView.setAdapter(readerListAdapter);

        pairedListView.setOnItemClickListener(mDeviceClickListener);
        pairedListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void loadPairedDevices() {
        readersList.addAll(App.readers.GetAvailableRFIDReaderList());
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
    /**
     * method to get connect password for the reader
     *
     * @param address - device BT address
     * @return connect password of the reader
     */
    private String getReaderPassword(String address) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.READER_PASSWORDS, 0);
        return sharedPreferences.getString(address, null);
    }
    public void bluetoothDeviceDisConnected(ReaderDevice device) {
        if (deviceConnectTask != null && !deviceConnectTask.isCancelled() && deviceConnectTask.getConnectingDevice().getName().equalsIgnoreCase(device.getName())) {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            if (deviceConnectTask != null)
                deviceConnectTask.cancel(true);
        }
        if (device != null) {
            changeTextStyle(device);
        } else
            Constants.logAsMessage(Constants.TYPE_ERROR, "ReadersListFragment", "deviceName is null or empty");
        MainActivity.clearSettings();
    }
    /**
     * check/un check the connected/disconnected reader list item
     *
     * @param device device to be updated
     */
    private void changeTextStyle(ReaderDevice device) {
        int i = readerListAdapter.getPosition(device);
        if (i >= 0) {
            readerListAdapter.remove(device);
            readerListAdapter.insert(device,i);
            readerListAdapter.notifyDataSetChanged();
        }
    }
    /**
     * method to update connected reader device in the readers list on device connected event
     *
     * @param device device to be updated
     */
    public void bluetoothDeviceConnected(ReaderDevice device) {
//        if (deviceConnectTask != null)
//            deviceConnectTask.cancel(true);
        if (device != null) {
            App.mConnectedDevice = device;
            App.is_connection_requested = false;
            changeTextStyle(device);
        } else
            Constants.logAsMessage(Constants.TYPE_ERROR, "ReadersListFragment", "deviceName is null or empty");
    }
    /**
     * method to update reader device in the readers list on device connection failed event
     *
     * @param device device to be updated
     */
    public void bluetoothDeviceConnFailed(ReaderDevice device) {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        if (deviceConnectTask != null)
            deviceConnectTask.cancel(true);
        if (device != null)
            changeTextStyle(device);
        else
            Constants.logAsMessage(Constants.TYPE_ERROR, "ReadersListFragment", "deviceName is null or empty");

//        sendNotification(Constants.ACTION_READER_CONN_FAILED, "Connection Failed!! was received");

        App.mConnectedReader = null;
        App.mConnectedDevice = null;
    }
    /**
     * async task to go for BT connection with reader
     */
    private class DeviceConnectTask extends AsyncTask<Void, String, Boolean> {
        private final ReaderDevice connectingDevice;
        private String prgressMsg;
        private OperationFailureException ex;
        private String password;

        DeviceConnectTask(ReaderDevice connectingDevice, String prgressMsg, String Password) {
            this.connectingDevice = connectingDevice;
            this.prgressMsg = prgressMsg;
            this.password = Password;
        }

        //        该回调函数在任务被执行之后立即由UI线程调用。这个步骤通常用来建立任务，在用户接口（UI）上显示进度条。
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new CustomProgressDialog(getActivity(), prgressMsg);
            progressDialog.show();
        }
        //该回调函数由后台线程在onPreExecute()方法执行结束后立即调用。通常在这里执行耗时的后台计算。
// 计算的结果必须由该函数返回，并被传递到onPostExecute()中。
// 在该函数内也可以使用publishProgress(Progress...)来发布一个或多个进度单位(unitsof progress)。 这些值将会在onProgressUpdate(Progress...)中被发布到UI线程。
        @Override
        protected Boolean doInBackground(Void... a) {
            try {
                if (password != null)
                    connectingDevice.getRFIDReader().setPassword(password);
                connectingDevice.getRFIDReader().connect();//延时操作、要抛出异常
                if (password != null) {//储存到手机里面
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(Constants.READER_PASSWORDS, 0).edit();
                    editor.putString(connectingDevice.getName(), password);
                    editor.commit();
                }
            } catch (InvalidUsageException e) {
                e.printStackTrace();
            } catch (OperationFailureException e) {
                e.printStackTrace();
                ex = e;
            }
            if (connectingDevice.getRFIDReader().isConnected()) {//判断是否链接成功
                App.mConnectedReader = connectingDevice.getRFIDReader();
                try {
                    App.mConnectedReader.Events.addEventsListener(App.eventHandler);
                } catch (InvalidUsageException e) {
                    e.printStackTrace();
                } catch (OperationFailureException e) {
                    e.printStackTrace();
                }
                connectingDevice.getRFIDReader().Events.setBatchModeEvent(true);
                connectingDevice.getRFIDReader().Events.setReaderDisconnectEvent(true);
                connectingDevice.getRFIDReader().Events.setBatteryEvent(true);
                connectingDevice.getRFIDReader().Events.setInventoryStopEvent(true);
                connectingDevice.getRFIDReader().Events.setInventoryStartEvent(true);
                // if no exception in connect
                if (ex == null) {
                    try {
                        MainActivity.UpdateReaderConnection(false);
                    } catch (InvalidUsageException e) {
                        e.printStackTrace();
                    } catch (OperationFailureException e) {
                        e.printStackTrace();
                    }
                } else {
                    MainActivity.clearSettings();
                }
                return true;
            } else {
                return false;
            }
        }
        //完成后台任务：onPostExecute(Result),当后台计算结束后调用。后台计算的结果会被作为参数传递给这一函数。
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            progressDialog.cancel();//任务结束取消提示框
            //以下操作为更新设备列表样式，和全局类设备实例
            if (ex != null) {
                if (ex.getResults() == RFIDResults.RFID_CONNECTION_PASSWORD_ERROR) {
//                    showPasswordDialog(connectingDevice);
                    bluetoothDeviceConnected(connectingDevice);
                } else if (ex.getResults() == RFIDResults.RFID_BATCHMODE_IN_PROGRESS) {
                    App.isBatchModeInventoryRunning = true;
                    App.mIsInventoryRunning = true;
                    bluetoothDeviceConnected(connectingDevice);
//                    if (App.NOTIFY_READER_CONNECTION)
//                        sendNotification(Constants.ACTION_READER_CONNECTED, "Connected to " + connectingDevice.getName());
                    //Events.StatusEventData data = App.mConnectedReader.Events.GetStatusEventData(RFID_EVENT_TYPE.BATCH_MODE_EVENT);
//                    Intent detailsIntent = new Intent(getActivity(), MainActivity.class);
//                    detailsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    detailsIntent.putExtra(RFID_EVENT_TYPE.BATCH_MODE_EVENT.toString(), 0/*data.BatchModeEventData.get_RepeatTrigger()*/);
//                    startActivity(detailsIntent);
                } else if (ex.getResults() == RFIDResults.RFID_READER_REGION_NOT_CONFIGURED) {
                    bluetoothDeviceConnected(connectingDevice);
                    App.regionNotSet = true;
//                    sendNotification(Constants.ACTION_READER_STATUS_OBTAINED, getString(R.string.set_region_msg));
//                    Intent detailsIntent = new Intent(getActivity(), SettingsDetailActivity.class);
//                    detailsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    detailsIntent.putExtra(Constants.SETTING_ITEM_ID, 7);
//                    startActivity(detailsIntent);
                } else
                    bluetoothDeviceConnFailed(connectingDevice);
            } else {
                if (result) {
//                    if (App.NOTIFY_READER_CONNECTION)
//                        sendNotification(Constants.ACTION_READER_CONNECTED, "Connected to " + connectingDevice.getName());
                    bluetoothDeviceConnected(connectingDevice);
                } else {
                    bluetoothDeviceConnFailed(connectingDevice);
                }
            }
            deviceConnectTask = null;
        }
        //取消任务：onCancelled ()，在调用AsyncTask的cancel()方法时调用
        @Override
        protected void onCancelled() {
            deviceConnectTask = null;
            super.onCancelled();
        }

        public ReaderDevice getConnectingDevice() {
            return connectingDevice;
        }
    }
}
