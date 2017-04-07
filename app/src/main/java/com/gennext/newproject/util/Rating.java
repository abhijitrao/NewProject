package com.gennext.newproject.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Abhijit on 16-Dec-16.
 */

public class Rating {
    private static final String APP_NAME="np";
    private static final String RATING_COUNTER="rc"+APP_NAME;
    private static final String APP_COUNTER="app"+APP_NAME;
    private static final String REMIND_INTERVAL="riterval"+APP_NAME;
    private static int remindInterval=2;
    private static int ratingCounter=4;

    public static boolean showRating(Context context) {
        int rInterval=getRemindInterval(context);
        if(rInterval<=remindInterval){
            setAppCounter(context);
            int appCount=getAppCounter(context);
            int rCounter=getRatingCounter(context);
            if (appCount==rCounter){
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
    }

    public static void setAppCounter(Context context) {
        int current=getAppCounter(context);
        current++;
        SavePrefInt(context, APP_COUNTER, current);
    }
    public static int getAppCounter(Context context) {
        if(context!=null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            int data = sharedPreferences.getInt(APP_COUNTER, 1);
            return data;
        }else{
            return 0;
        }
    }

    public static void setRatingCounter(Context context, int counter) {
        SavePrefInt(context, RATING_COUNTER, counter);
    }
    public static int getRatingCounter(Context context) {
        if(context!=null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            int data = sharedPreferences.getInt(RATING_COUNTER, ratingCounter);
            return data;
        }else{
            return 0;
        }
    }

    public static void remindMeLater(Context context) {
        int rLeter=getRatingCounter(context);
        setRatingCounter(context,rLeter+ratingCounter);
        setRemindInterval(context);
    }

    public static void stopRating(Context context) {
        SavePrefInt(context, REMIND_INTERVAL, 100);
    }

    public static void setRemindInterval(Context context) {
        int rInterval=getRemindInterval(context);
        rInterval++;
        SavePrefInt(context, REMIND_INTERVAL, rInterval);
    }
    public static int getRemindInterval(Context context) {
        if(context!=null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            int data = sharedPreferences.getInt(REMIND_INTERVAL, 1);
            return data;
        }else{
            return 0;
        }
    }

    public static void SavePrefInt(Context context,String key, int value) {
        if(context!=null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            editor.apply();
        }
    }
}
