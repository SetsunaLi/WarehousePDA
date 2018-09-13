package com.example.ridko.warehousepda.application;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.ridko.warehousepda.activity.MainActivity;
import com.example.ridko.warehousepda.client.OutboundDetail;
import com.example.ridko.warehousepda.common.MaxLimitArrayList;
import com.example.ridko.warehousepda.inventory.InventoryListItem;
import com.zebra.rfid.api3.Antennas;
import com.zebra.rfid.api3.BEEPER_VOLUME;
import com.zebra.rfid.api3.DYNAMIC_POWER_OPTIMIZATION;
import com.zebra.rfid.api3.Events;
import com.zebra.rfid.api3.RFIDReader;
import com.zebra.rfid.api3.RFModeTable;
import com.zebra.rfid.api3.ReaderDevice;
import com.zebra.rfid.api3.Readers;
import com.zebra.rfid.api3.RegulatoryConfig;
import com.zebra.rfid.api3.StartTrigger;
import com.zebra.rfid.api3.StopTrigger;
import com.zebra.rfid.api3.TagStorageSettings;
import com.zebra.rfid.api3.UNIQUE_TAG_REPORT_SETTING;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by mumu on 2018/4/3.
 */

public class App extends  android.app.Application {

    private static boolean activityVisible;
    private static final int PROWER_MAX=30;
    private static final int PROWER_MIN=5;
    public static String USER_NAME;
    public static String USER_ID;
    public static String LOADTIME;
    public static String STATION;
    public static String SYSTEM_VERSION;
    public static String IP;
    public static String PORT;
    public static String DEVICE_NO;
    public static boolean SYSTEM_PUSH=true;
    public static int ARITHMETIC=1;
    public static int START_Q=4;
    public static int MIN_Q=0;
    public static int MAX_Q=15;
    public static int STORAGE=0;
    public static int AREA=3;
    public static int PRWOER=30;
    public static int WORK_TIME=65535;
    public static int FREE_TIME=65535;
    public static List<OutboundDetail> detilList;
    public static boolean isReturn=false;

    public static boolean  ISBUY=true;
    public static boolean BIND=true;
    public static RFIDReader mConnectedReader;

    //Variable to keep track of the unique tags seen
    public static volatile int UNIQUE_TAGS = 0;

    //variable to keep track of the total tags seen
    public static volatile int TOTAL_TAGS = 0;
    //Arraylist to keeptrack of the tagIDs to act as adapter for autocomplete text views
    public static ArrayList<String> tagIDs;
    //variable to store the tag read rate
    public static volatile int TAG_READ_RATE = 0;
    //Boolean to keep track of whether the inventory is running or not
    public static volatile boolean mIsInventoryRunning;
    public static int inventoryMode = 0;
    public static int memoryBankId = -1;
    public static Boolean isBatchModeInventoryRunning;
    public static String accessControlTag;
    public static String locateTag;
    //Variable to maintain the RR started time to maintain the read rate
    public static volatile long mRRStartedTime;
    //    public static PreFilters[] preFilters = null;
    public static boolean isAccessCriteriaRead = false;
    public static int preFilterIndex = -1;
    //For Notification
    public static volatile int INTENT_ID = 100;
    public static MainActivity.EventHandler eventHandler;
    public static TreeMap<String, Integer> inventoryList = new TreeMap<String, Integer>();
    public static HashMap<String, String> versionInfo = new HashMap<>(5);

    //Arraylist to keeptrack of the tags read for Inventory
    public static ArrayList<InventoryListItem> tagsReadInventory = new MaxLimitArrayList();
    public static boolean isGettingTags;
    public static boolean EXPORT_DATA;
    public static ReaderDevice mConnectedDevice;
    public static boolean isLocatingTag;
    //
    public static StartTrigger settings_startTrigger;
    public static StopTrigger settings_stopTrigger;
    public static short TagProximityPercent = -1;
    public static TagStorageSettings tagStorageSettings;
    public static int batchMode;
    public static Events.BatteryData BatteryData = null;
    public static DYNAMIC_POWER_OPTIMIZATION dynamicPowerSettings;
    public static boolean is_disconnection_requested;
    public static boolean is_connection_requested;
    //App Settings
    public static volatile boolean AUTO_DETECT_READERS;
    public static volatile boolean AUTO_RECONNECT_READERS;
    public static volatile boolean NOTIFY_READER_AVAILABLE;
    public static volatile boolean NOTIFY_READER_CONNECTION;
    public static volatile boolean NOTIFY_BATTERY_STATUS;
    //Beeper
    public static BEEPER_VOLUME beeperVolume = null;
    // Singulation control
    public static Antennas.SingulationControl singulationControl;
    // regulatory
    public static RegulatoryConfig regulatory;
    public static Boolean regionNotSet = false;
    // antenna
    public static RFModeTable rfModeTable;
    public static Antennas.AntennaRfConfig antennaRfConfig;
    public static int[] antennaPowerLevel;
    public static Readers readers;
    public static ReaderDevice mReaderDisappeared;
    public static boolean isActivityVisible() {
        return activityVisible;
    }
    public static UNIQUE_TAG_REPORT_SETTING reportUniquetags = null;

    public static void activityResumed(){
        activityVisible=true;
    }
    public static void activityPaused(){
        activityVisible=false;
    }
    public static void toastShow(Context context,String string,int flag){
            Toast.makeText(context, string, flag).show();
    }
}
