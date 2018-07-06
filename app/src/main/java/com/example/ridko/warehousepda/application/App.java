package com.example.ridko.warehousepda.application;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

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

    public static boolean  ISBUY=true;
    public static boolean BIND=true;


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
