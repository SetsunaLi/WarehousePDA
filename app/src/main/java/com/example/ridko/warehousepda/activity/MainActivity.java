package com.example.ridko.warehousepda.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.adapter.DrawerListAdapter;
import com.example.ridko.warehousepda.application.App;
import com.example.ridko.warehousepda.been.DrawerListContent;
import com.example.ridko.warehousepda.common.Constants;
import com.example.ridko.warehousepda.common.Inventorytimer;
import com.example.ridko.warehousepda.data_export.DataExportTask;
import com.example.ridko.warehousepda.fragment.AboutFragment;
import com.example.ridko.warehousepda.fragment.CheckClothFragment;
import com.example.ridko.warehousepda.fragment.ConnectPDAFragment;
import com.example.ridko.warehousepda.fragment.CutClothFragment;
import com.example.ridko.warehousepda.fragment.DeviceManagmentFragment;
import com.example.ridko.warehousepda.fragment.HomeFragment;
import com.example.ridko.warehousepda.fragment.OutDemoFragment1;
import com.example.ridko.warehousepda.fragment.OutDemoManagmentFragment;
import com.example.ridko.warehousepda.fragment.SpecialStorageFragment;
import com.example.ridko.warehousepda.fragment.StockRemovalFragment;
import com.example.ridko.warehousepda.fragment.StockUpFragment;
import com.example.ridko.warehousepda.fragment.UnBindAndBindFragment;
import com.zebra.rfid.api3.BATCH_MODE;
import com.zebra.rfid.api3.InvalidUsageException;
import com.zebra.rfid.api3.MEMORY_BANK;
import com.zebra.rfid.api3.OperationFailureException;
import com.zebra.rfid.api3.ReaderDevice;
import com.zebra.rfid.api3.Readers;
import com.zebra.rfid.api3.RfidEventsListener;
import com.zebra.rfid.api3.RfidReadEvents;
import com.zebra.rfid.api3.RfidStatusEvents;
import com.zebra.rfid.api3.START_TRIGGER_TYPE;
import com.zebra.rfid.api3.TAG_FIELD;
import com.zebra.rfid.api3.TagAccess;
import com.zebra.rfid.api3.TagData;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
,Readers.RFIDReaderEventHandler{
    //Tag to identify the currently displayed fragment
    protected static final String TAG_CONTENT_FRAGMENT = "ContentFragment";
    @Bind(R.id.content_frame)
    FrameLayout mFrame;
    @Bind(R.id.left_drawer)
    ListView mDrawerList;
    //导航布局
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerlayout;

    //     导航数组
    private String[] mOptionTitles;
    //    ActionBar样式
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private Boolean isTriggerRepeat;
    private boolean pc = false;
    private boolean rssi = false;
    private boolean phase = false;
    private boolean channelIndex = false;
    private boolean tagSeenCount = false;
    private boolean isDeviceDisconnected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        App.eventHandler = new EventHandler();
        Inventorytimer.getInstance().setActivity(this);
        initializeConnectionSettings();

        if (App.readers == null) {
            App.readers = new Readers();
        }
        App.readers.attach(this);//reader的一个广播接收器
        if (!isBluetoothEnabled()) {//如果没有开蓝牙就开蓝牙
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableIntent);
        }

        mTitle = mDrawerTitle = getTitle();
        mOptionTitles = getResources().getStringArray(R.array.options_array);
//        mDrawerlayout.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.START);
//        在list里面修改左边列表(数据样式等等)
        mDrawerList.setAdapter(new DrawerListAdapter(this, R.layout.item_drawer_list, DrawerListContent.ITEMS));
        mDrawerList.setOnItemClickListener(new DrawerItemListener());
        //激活ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerlayout,
                R.mipmap.ic_drawer,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            /**
             * 当一个抽屉完全关闭的时候调用此方法
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                int drawableRsourceId = getActionBarIcon();
                if (drawableRsourceId != -1)
                    getSupportActionBar().setIcon(drawableRsourceId);
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
           /*     getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();*/
            }

            /**
             * 当一个抽屉被完全打开的时候被调用
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerView.setClickable(true);
                getSupportActionBar().setTitle(mDrawerTitle);
                getSupportActionBar().setIcon(R.drawable.about);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerlayout.setDrawerListener(mActionBarDrawerToggle);
//        if(savedInstanceState!=null)
        selectItem(0);

    }

 /*   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }*/

    /* Called whenever we call invalidateOptionsMenu() */
    boolean drawerOpen = false;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        drawerOpen = mDrawerlayout.isDrawerOpen(mDrawerList);
//        不止为何导致actionbar变大
       /* if (drawerOpen){
//            当抽屉打开时，隐藏键盘
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getCurrentFocus()!=null)
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            if (getSupportActionBar()!=null){
            //隐藏标签，如果它们是可见的。
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            }
        }else{
            if (getSupportActionBar()!=null){
                if (getSupportFragmentManager()!=null&&getSupportFragmentManager().findFragmentByTag(TAG_CONTENT_FRAGMENT)!=null)
                    getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            }
        }*/

        return super.onPrepareOptionsMenu(menu);
    }

    protected Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }
//返回键设置效果方法
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONTENT_FRAGMENT);
        if (fragment != null && (fragment instanceof AboutFragment || fragment instanceof HomeFragment || fragment instanceof
                CheckClothFragment || fragment instanceof CutClothFragment || fragment instanceof DeviceManagmentFragment
                || fragment instanceof SpecialStorageFragment || fragment instanceof StockUpFragment || fragment instanceof StockRemovalFragment
        )) {

            //update the selected item in the drawer and the title
//            mDrawerList.setItemChecked(0, true);
            selectItem(0);
            setTitle(mOptionTitles[0]);
            //We are handling back pressed for saving pre-filters settings. Notify the appropriate fragment.
            //{@link BaseReceiverActivity # onBackPressed should be called by the fragment when the processing is done}
            //super.onBackPressed();

            if (fragment instanceof HomeFragment) {
                ((HomeFragment) fragment).onBackPressed();
            } else {

            }
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action buttons
//        右上角actionBar监听
      /*  switch (item.getItemId()) {
            case R.id.action_info:
                aboutClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
          }*/
        return false;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.activityPaused();
    }

    //特殊入库
    public void specialClicked(View view) {
        selectItem(1);
    }

    //    解绑与绑定
    public void unBindOrBindClicked(View view) {
        selectItem(2);
    }

    //    剪布
    public void cuttingClothClicked(View view) {
        selectItem(3);
    }

    //    查布
    public void checkingClothClicked(View view) {
        selectItem(4);
    }

    //    备货
    public void stockUpClicked(View view) {
        selectItem(5);
    }

    //    出库
    public void stockRemovalClicked(View view) {
        selectItem(6);
    }

    //    设备管理
    public void deviceManagmentClicked(View view) {
        selectItem(7);
    }

    //    关于
    public void aboutClicked(View view) {
        selectItem(8);
    }

    public void aboutClicked() {
        selectItem(8);
    }

//    demo
    public void outDemoClick(View view){selectItem(9);}

//    连接手持机
    public void connectPDA(View view){selectItem(10);}
//    寻读开关按钮
    protected boolean isInventoryAborted;
    public  void inventoryStatOrStop(View v){
        Button button = (Button) v;
        if (isBluetoothEnabled()) {
            if (App.mConnectedReader != null && App.mConnectedReader.isConnected()) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONTENT_FRAGMENT);
                if (!App.mIsInventoryRunning) {//如果不在扫描
                    clearInventoryData();
                    button.setText("STOP");
                    //Here we send the inventory command to start reading the tags
//                    设置询标的寻读区域，demo通过Spinner选择
                  /*  if (fragment != null && fragment instanceof OutDemoFragment1) {//如果是询标的界面，则通过复选框设置询标模式
                        Spinner memoryBankSpinner = ((Spinner) findViewById(R.id.inventoryOptions));
                        memoryBankSpinner.setSelection(App.memoryBankId);
                        memoryBankSpinner.setEnabled(false);
//                      重置
                        ((OutDemoFragment1) fragment).resetTagsInfo();
                    }*/
                    //set flag value
                    isInventoryAborted = false;
                    App.mIsInventoryRunning = true;
                    getTagReportingfields();//没看懂，JDK固定方法？
                    //判断是否为轮询操作，和询标模式
                    if (fragment != null && fragment instanceof OutDemoFragment1 && !((OutDemoFragment1) fragment).getMemoryBankID().equalsIgnoreCase("none")) {//匹配字段，忽略大小写
                        //If memory bank is selected, call read command with appropriate memory bank
                        inventoryWithMemoryBank(((OutDemoFragment1) fragment).getMemoryBankID());
                    } else {
                        /*if (fragment != null && fragment instanceof RapidReadFragment) {
                            App.memoryBankId = -1;
                            ((RapidReadFragment) fragment).resetTagsInfo();
                        }*/
                        // unique read is enabled启用了独特的阅读
                        if(App.reportUniquetags!= null && App.reportUniquetags.getValue() == 1) {
                            App.mConnectedReader.Actions.purgeTags();
                        }
                        //Perform inventory 执行库存
//                        寻读
                        if (App.inventoryMode == 0) {
                            try {
                                App.mConnectedReader.Actions.Inventory.perform();
                            } catch (InvalidUsageException e) {
                                e.printStackTrace();
                            } catch (final OperationFailureException e) {
                                e.printStackTrace();
                                {
//                                    没电操作
                                  /*  runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONTENT_FRAGMENT);
                                            if (fragment instanceof ResponseHandlerInterfaces.ResponseStatusHandler)
                                                ((ResponseHandlerInterfaces.ResponseStatusHandler) fragment).handleStatusResponse(e.getResults());
                                            sendNotification(Constants.ACTION_READER_STATUS_OBTAINED, e.getVendorMessage());
                                        }
                                    });*/
                                }
                            }
                            if (App.batchMode != -1) {
                                if (App.batchMode == BATCH_MODE.ENABLE.getValue())
                                    App.isBatchModeInventoryRunning = true;
                            }
                        }
                    }
                } else if (App.mIsInventoryRunning) {
                    button.setText("START");
                    isInventoryAborted = true;
                    //Here we send the abort command to stop the inventory
//                    停止指令
                    try {
                        App.mConnectedReader.Actions.Inventory.stop();
                        if (((App.settings_startTrigger != null && (App.settings_startTrigger.getTriggerType() == START_TRIGGER_TYPE.START_TRIGGER_TYPE_HANDHELD || App.settings_startTrigger.getTriggerType() == START_TRIGGER_TYPE.START_TRIGGER_TYPE_PERIODIC)))
                                || (App.isBatchModeInventoryRunning != null && App.isBatchModeInventoryRunning))
                            operationHasAborted();
                    } catch (InvalidUsageException e) {
                        e.printStackTrace();
                    } catch (OperationFailureException e) {
                        e.printStackTrace();
                    }

                }
            } else
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_disconnected), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_bluetooth_disabled), Toast.LENGTH_SHORT).show();
    }
    private void getTagReportingfields() {
        pc = false;
        phase = false;
        channelIndex = false;
        rssi = false;
        if (App.tagStorageSettings != null) {
            TAG_FIELD[] tag_field = App.tagStorageSettings.getTagFields();
            for (int idx = 0; idx < tag_field.length; idx++) {
                if (tag_field[idx] == TAG_FIELD.PEAK_RSSI)
                    rssi = true;
                if (tag_field[idx] == TAG_FIELD.PHASE_INFO)
                    phase = true;
                if (tag_field[idx] == TAG_FIELD.PC)
                    pc = true;
                if (tag_field[idx] == TAG_FIELD.CHANNEL_INDEX)
                    channelIndex = true;
                if (tag_field[idx] == TAG_FIELD.TAG_SEEN_COUNT)
                    tagSeenCount = true;
            }
        }
    }
    /**
     * Method to call when we want inventory to happen with memory bank parameters
     *
     * @param memoryBankID id of the memory bank
     */
    public void inventoryWithMemoryBank(String memoryBankID) {
        if (isBluetoothEnabled()) {
            if (App.mConnectedReader != null && App.mConnectedReader.isConnected()) {
                TagAccess tagAccess = new TagAccess();
                TagAccess.ReadAccessParams readAccessParams = tagAccess.new ReadAccessParams();
                //Set the param values
                readAccessParams.setByteCount(0);
                readAccessParams.setByteOffset(0);

                if ("RESERVED".equalsIgnoreCase(memoryBankID))
                    readAccessParams.setMemoryBank(MEMORY_BANK.MEMORY_BANK_RESERVED);
                if ("EPC".equalsIgnoreCase(memoryBankID))
                    readAccessParams.setMemoryBank(MEMORY_BANK.MEMORY_BANK_EPC);
                if ("TID".equalsIgnoreCase(memoryBankID))
                    readAccessParams.setMemoryBank(MEMORY_BANK.MEMORY_BANK_TID);
                if ("USER".equalsIgnoreCase(memoryBankID))
                    readAccessParams.setMemoryBank(MEMORY_BANK.MEMORY_BANK_USER);
                try {//配置读写器
                    //Read command with readAccessParams and accessFilter as null to read all the tags
//                    读取命令，使用readAccessParams和accessFilter作为null来读取所有标签（开始读标）
                    App.mConnectedReader.Actions.TagAccess.readEvent(readAccessParams, null, null);
                } catch (InvalidUsageException e) {
                    e.printStackTrace();
                } catch (OperationFailureException e) {
                    e.printStackTrace();
//                    没电
                   /* Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONTENT_FRAGMENT);
                    if (fragment instanceof ResponseHandlerInterfaces.ResponseStatusHandler)
                        ((ResponseHandlerInterfaces.ResponseStatusHandler) fragment).handleStatusResponse(e.getResults());*/
                    Toast.makeText(getApplicationContext(), e.getVendorMessage(), Toast.LENGTH_SHORT).show();
                }

            } else
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_disconnected), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_bluetooth_disabled), Toast.LENGTH_SHORT).show();
    }
    /**
     * Method to change operation status and ui in app on recieving abort status
     * 在接收中断状态下改变操作状态和ui的方法
     */
    private void operationHasAborted() {
        //retrieve get tags if inventory in batch mode got aborted
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONTENT_FRAGMENT);
        if (App.isBatchModeInventoryRunning != null && App.isBatchModeInventoryRunning) {
            if (isInventoryAborted) {
                App.isBatchModeInventoryRunning = false;
                isInventoryAborted = true;
                App.isGettingTags = true;
                if (App.settings_startTrigger == null) {
                    new AsyncTask<Void, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            try {
                                if (App.mConnectedReader.isCapabilitiesReceived())
                                    UpdateReaderConnection(false);
                                else
                                    UpdateReaderConnection(true);
                                // update fields before getting tags
                                getTagReportingfields();
                                //
                                App.mConnectedReader.Actions.getBatchedTags();
                            } catch (InvalidUsageException e) {
                                e.printStackTrace();
                            } catch (OperationFailureException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute();
                } else
                    App.mConnectedReader.Actions.getBatchedTags();
            }
        }

        if (App.mIsInventoryRunning) {
            if (isInventoryAborted) {
                App.mIsInventoryRunning = false;
                isInventoryAborted = false;
                isTriggerRepeat = null;
                if (Inventorytimer.getInstance().isTimerRunning())
                    Inventorytimer.getInstance().stopTimer();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (fragment instanceof OutDemoFragment1)
                            ((OutDemoFragment1) fragment).resetInventoryDetail();
                      /*  else if (fragment instanceof RapidReadFragment)
                            ((RapidReadFragment) fragment).resetInventoryDetail();*/
                        //export Data to the file
                        if (App.EXPORT_DATA)
                            if (App.tagsReadInventory != null && !App.tagsReadInventory.isEmpty()) {
                                new DataExportTask(getApplicationContext(), App.tagsReadInventory, App.mConnectedReader.getHostName(), App.TOTAL_TAGS, App.UNIQUE_TAGS, App.mRRStartedTime).execute();
                            }
                    }
                });
            }
        }//定位的时候
        /*else if (App.isLocatingTag) {
            if (isLocationingAborted) {
                App.isLocatingTag = false;
                isLocationingAborted = false;
                if (fragment instanceof LocationingFragment)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((LocationingFragment) fragment).resetLocationingDetails(false);
                        }
                    });
            }
        }*/
    }

    //pda按钮监听？
   /* private void readerReconnected(ReaderDevice readerDevice) {
        // store app reader
        App.mConnectedDevice = readerDevice;
        App.mConnectedReader = readerDevice.getRFIDReader();
        //
        if (App.isBatchModeInventoryRunning != null && App.isBatchModeInventoryRunning) {
            clearInventoryData();
            App.mIsInventoryRunning = true;
            App.memoryBankId = 0;
            startTimer();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONTENT_FRAGMENT);
            if (fragment instanceof ResponseHandlerInterfaces.BatchModeEventHandler)
                ((ResponseHandlerInterfaces.BatchModeEventHandler) fragment).batchModeEventReceived();
        } else
            try {
                UpdateReaderConnection(false);
            } catch (InvalidUsageException e) {
                e.printStackTrace();
            } catch (OperationFailureException e) {
                e.printStackTrace();
            }
        // connect call
        bluetoothDeviceConnected(readerDevice);
    }*/
    //点击以改变当前UI状态
    private void selectItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = HomeFragment.newInstance();
                break;
            case 1:
                fragment = SpecialStorageFragment.newInstance();
                break;
            case 2:
                fragment = UnBindAndBindFragment.newInstance();
                break;
            case 3:
                fragment = CutClothFragment.newInstance();
                break;
            case 4:
                fragment = CheckClothFragment.newInstance();
                break;
            case 5:
                fragment = StockUpFragment.newInstance();
                break;
            case 6:
                fragment = StockRemovalFragment.newInstance();
                break;
            case 7:
                fragment = DeviceManagmentFragment.newInstance();
                break;
            case 8:
                fragment = AboutFragment.newInstance();
                break;
            case 9:
                fragment = OutDemoManagmentFragment.newInstance();
                break;
            case 10:
                fragment = ConnectPDAFragment.newInstance();
                break;
            default:
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (position == 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, TAG_CONTENT_FRAGMENT).commit();
        } else if (position == 2) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, TAG_CONTENT_FRAGMENT).addToBackStack(null).commit();
        } else {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, TAG_CONTENT_FRAGMENT).addToBackStack(null).commit();
        }
        mDrawerList.setItemChecked(position, true);
        setTitle(mOptionTitles[position]);
        mDrawerlayout.closeDrawer(mDrawerList);
    }

    //获取当前显示的动作栏Action图标的方法
    private int getActionBarIcon() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONTENT_FRAGMENT);
        if (fragment instanceof HomeFragment)
            return R.drawable.about;
        else if (fragment instanceof SpecialStorageFragment)
            return R.drawable.about;
        else if (fragment instanceof UnBindAndBindFragment)
            return R.drawable.about;
        else if (fragment instanceof CutClothFragment)
            return R.drawable.about;
        else if (fragment instanceof CheckClothFragment)
            return R.drawable.about;
        else if (fragment instanceof StockUpFragment)
            return R.drawable.about;
        else if (fragment instanceof DeviceManagmentFragment)
            return R.drawable.about;
        else if (fragment instanceof StockRemovalFragment)
            return R.drawable.about;
        else if (fragment instanceof AboutFragment)
            return R.drawable.about;
        else
            return R.drawable.about;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.list_layout && drawerOpen) {
            mDrawerlayout.closeDrawers();
        }
    }

    /**
     * method lear inventory data like total tags, unique tags, read rate etc..
     */
    public void clearInventoryData() {
        App.TOTAL_TAGS = 0;
        App.mRRStartedTime = 0;
        App.UNIQUE_TAGS = 0;
        App.TAG_READ_RATE = 0;
        if (App.tagIDs != null)
            App.tagIDs.clear();
        if (App.tagsReadInventory.size() > 0)
            App.tagsReadInventory.clear();
        if (App.tagsReadInventory.size() > 0)
            App.tagsReadInventory.clear();
        if (App.inventoryList != null && App.inventoryList.size() > 0)
            App.inventoryList.clear();
    }
//    以下内容全为开机和连接斑马手持机操作，包括实现两个监听
    private void initializeConnectionSettings() {
        SharedPreferences settings = getSharedPreferences(Constants.APP_SETTINGS_STATUS, 0);
        App.AUTO_DETECT_READERS = settings.getBoolean(Constants.AUTO_DETECT_READERS, true);
        App.AUTO_RECONNECT_READERS = settings.getBoolean(Constants.AUTO_RECONNECT_READERS, false);
        App.NOTIFY_READER_AVAILABLE = settings.getBoolean(Constants.NOTIFY_READER_AVAILABLE, false);
        App.NOTIFY_READER_CONNECTION = settings.getBoolean(Constants.NOTIFY_READER_CONNECTION, false);
        App.NOTIFY_BATTERY_STATUS = settings.getBoolean(Constants.NOTIFY_BATTERY_STATUS, true);
        App.EXPORT_DATA = settings.getBoolean(Constants.EXPORT_DATA, false);
    }

    /**
     * method to clear reader's settings on disconnection
     */
    public static void clearSettings() {
        App.antennaPowerLevel = null;
        App.antennaRfConfig = null;
        App.singulationControl = null;
        App.rfModeTable = null;
        App.regulatory = null;
        App.batchMode = -1;
        App.tagStorageSettings = null;
        App.reportUniquetags = null;
        App.dynamicPowerSettings = null;
        App.settings_startTrigger = null;
        App.settings_stopTrigger = null;
        App.beeperVolume = null;
//        App.preFilters = null;
        if (App.versionInfo != null)
            App.versionInfo.clear();
        App.regionNotSet = false;
        App.isBatchModeInventoryRunning = null;
        App.BatteryData = null;
        App.is_disconnection_requested = false;
        App.mConnectedDevice = null;
//        App.mConnectedReader = null;
    }
    public static void UpdateReaderConnection(Boolean fullUpdate) throws InvalidUsageException, OperationFailureException {
        App.mConnectedReader.Events.setBatchModeEvent(true);
        App.mConnectedReader.Events.setReaderDisconnectEvent(true);
        App.mConnectedReader.Events.setInventoryStartEvent(true);
        App.mConnectedReader.Events.setInventoryStopEvent(true);
        App.mConnectedReader.Events.setTagReadEvent(true);
        App.mConnectedReader.Events.setHandheldEvent(true);
        App.mConnectedReader.Events.setBatteryEvent(true);
        App.mConnectedReader.Events.setPowerEvent(true);
        App.mConnectedReader.Events.setOperationEndSummaryEvent(true);

        if (fullUpdate)
            App.mConnectedReader.PostConnectReaderUpdate();

        App.regulatory = App.mConnectedReader.Config.getRegulatoryConfig();
        App.regionNotSet = false;
        App.rfModeTable = App.mConnectedReader.ReaderCapabilities.RFModes.getRFModeTableInfo(0);
        App.antennaRfConfig = App.mConnectedReader.Config.Antennas.getAntennaRfConfig(1);
        App.singulationControl = App.mConnectedReader.Config.Antennas.getSingulationControl(1);
        App.settings_startTrigger = App.mConnectedReader.Config.getStartTrigger();
        App.settings_stopTrigger = App.mConnectedReader.Config.getStopTrigger();
        App.tagStorageSettings = App.mConnectedReader.Config.getTagStorageSettings();
        App.dynamicPowerSettings = App.mConnectedReader.Config.getDPOState();
        App.beeperVolume = App.mConnectedReader.Config.getBeeperVolume();
        App.batchMode = App.mConnectedReader.Config.getBatchModeConfig().getValue();
        App.reportUniquetags = App.mConnectedReader.Config.getUniqueTagReport();
        App.mConnectedReader.Config.getDeviceVersionInfo(App.versionInfo);
        //Log.d("RFIDDEMO","SCANNERNAME: " + App.mConnectedReader.ReaderCapabilities.getScannerName());
//        获取电池状态线程
        startTimer();
    }
    public static Timer t;
    /**
     * method to start a timer task to get device battery status per every 6 sec
     */
    public static void startTimer() {
        if (t == null) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (App.mConnectedReader != null)
                            App.mConnectedReader.Config.getDeviceStatus(true, false, false);
                        else
                            stopTimer();
                    } catch (InvalidUsageException e) {
                        e.printStackTrace();
                    } catch (OperationFailureException e) {
                        e.printStackTrace();
                    }
                }
            };
            t = new Timer();
            t.scheduleAtFixedRate(task, 0, 60000);
        }
    }

    /**
     * method to stop timer
     */
    public static void stopTimer() {
        if (t != null) {
            t.cancel();
            t.purge();
        }
        t = null;
    }
    //读取数据时候的监听
    @Override
    public void RFIDReaderAppeared(ReaderDevice readerDevice) {

    }
//读取数据断开读写器时候的监听
    @Override
    public void RFIDReaderDisappeared(ReaderDevice readerDevice) {

    }

    private class DrawerItemListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (i > -1 && i < mOptionTitles.length)
                selectItem(i);
        }
    }
    /**
     * method to know whether bluetooth is enabled or not
     *
     * @return - true if bluetooth enabled
     * - false if bluetooth disabled
     */
    public static boolean isBluetoothEnabled() {
        return BluetoothAdapter.getDefaultAdapter().isEnabled();
    }
    public class EventHandler implements RfidEventsListener {

        @Override
        public void eventReadNotify(RfidReadEvents e) {
            final TagData[] myTags = App.mConnectedReader.Actions.getReadTags(100);
        }

        @Override
        public void eventStatusNotify(RfidStatusEvents rfidStatusEvents) {
            System.out.println("Status Notification: " + rfidStatusEvents.StatusEventData.getStatusEventType());
        }
    }
}
