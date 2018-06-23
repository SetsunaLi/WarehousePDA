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
    public static String DEPARTMENT;
    public static String STATION;
    public static String STATUS;

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
