package com.gennext.newproject.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONObject;

/**
 * Created by Abhijit on 13-Dec-16.
 */

public class AppUser {
    private static final String COMMON = "afeature";
    private static final String APP_USER = "appuser" + COMMON;
    private static final String APP_PASS = "apppass" + COMMON;


    public static void setLoginData(Context context, JSONObject msgObj) {
        if (context != null && msgObj != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = sharedPreferences.edit();
//            if (msgObj.optJSONArray("domains") != null) {
//                editor.putString(STORED_DOMAINS, msgObj.optJSONArray("domains").toString());
//            }else{
//                editor.putString(STORED_DOMAINS, "");
//            }
//            if (msgObj.optJSONArray("days") != null) {
//                editor.putString(STORED_DAYS, msgObj.optJSONArray("days").toString());
//            }else{
//                editor.putString(STORED_DAYS, "");
//            }
//            if (msgObj.optJSONArray("allStatus") != null) {
//                editor.putString(STORED_ALLSTATUS, msgObj.optJSONArray("allStatus").toString());
//            }else{
//                editor.putString(STORED_ALLSTATUS, "");
//            }
//            if (msgObj.optString("consultantId") != null) {
//                editor.putString(CONSULTANT_ID, msgObj.optString("consultantId"));
//            }else{
//                editor.putString(CONSULTANT_ID, "");
//            }

            editor.apply();
        }
    }


    public static String LoadPref(Context context, String key) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String data = sharedPreferences.getString(key, "");
            return data;
        }
        return "";
    }

    public static String getName(Activity act) {
        return "";
    }

    public static String getImage(Activity act) {
        return "";
    }


    public static void setAppUserAndPass(Context act, String username, String password) {
        Utility.SavePref(act, APP_USER, username);
        Utility.SavePref(act, APP_PASS, password);
    }

    public static String getUserId(Context activity) {
        return LoadPref(activity,APP_USER);
    }


}