package com.gennext.newproject.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.PhoneNumberUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Abhijit on 23-Dec-16.
 */
public class Utility {

    private static int arrayIndexFindingByMatchString(List<String>list, String userSTring){
        return Arrays.asList(list).indexOf(userSTring);
    }

    public String encodeUrl(String res) {
        String UrlEncoding = null;
        try {
            UrlEncoding = URLEncoder.encode(res, "UTF-8");

        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return UrlEncoding;
    }

    public static String LoadPref(Context context, String key) {
        if(context!=null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String data = sharedPreferences.getString(key, "");
            return data;

        }
        return "";
    }
    public static void SavePref(Context context,String key, String name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (context != null && name != null) {
            editor.putString(key, name);
            editor.apply();
        }
    }

    public static String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    public static void getWhatsAppMsg(Activity activity) {
        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setType("text/plain");
        String text = "Sorry For Interruption,I'm Just Trying Something";
        waIntent.setPackage("com.whatsapp");
        if (waIntent != null) {
            waIntent.putExtra(Intent.EXTRA_TEXT, text);//
            activity.startActivity(Intent.createChooser(waIntent, "Share with"));
        }
    }
    public static void openWhatsAppMsgToSpecificPerson(Activity activity,String number) {
//        Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:<whatsappnumber>");
//        intent.setpackage("com.whatsapp");
//        startActivity(intent);
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");

        activity.startActivity(sendIntent);
    }
    public static void sendWhatsAppMsgToSpecificPerson(Activity activity,String number,String message) {
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);

        activity.startActivity(sendIntent);
    }


}
