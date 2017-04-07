package com.gennext.newproject;

/**
 * Created by Abhijit on 14-Jul-16.
 */


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void initToolBar(String title) {
        Toolbar toolbar = null;
        if (!TextUtils.isEmpty(title)) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);

            toolbar.setNavigationIcon(R.mipmap.ic_back);
            toolbar.setNavigationOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }
            );
        }
    }

    public boolean APICheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        } else {
            return false;
        }
    }

    public Toolbar setToolBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_menu);
        return toolbar;
    }

    public void closeFragmentDialog(String dialogName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if (fragment != null) {
            fragment.dismiss();
        }
    }



    public void showPDialog(Context context, String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideKeybord(Activity act) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(act.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }




    public String LoadPref(String key) {
        return LoadPref(BaseActivity.this, key);
    }

    public String LoadPref(Context context, String key) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPreferences.getString(key, "");
        } else {
            return "";
        }
    }

    public void SavePref(String key, String value) {
        SavePref(BaseActivity.this, key, value);
    }

    public void SavePref(Context context, String key, String value) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }
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

    public boolean isOnline() {
        boolean conn = false;
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(BaseActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        if (haveConnectedWifi == true || haveConnectedMobile == true) {
            conn = true;
        } else {
            conn = false;
        }

        return conn;
    }


    public String getSt(int id) {

        return getResources().getString(id);
    }

    private void EnableMobileIntent() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.provider.Settings.ACTION_SETTINGS);
        startActivity(intent);

    }

    public void showToast(String txt) {
        // Inflate the Layout
        Toast toast = Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }


    protected void replaceFragment(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment, tag);
        transaction.addToBackStack("tag");
        transaction.commit();
    }

    protected void setFragment(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    protected void addFragment(Fragment fragment, String tag) {
        addFragment(fragment, android.R.id.content, tag);
    }

    protected void addFragment(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    protected void addFragmentWithoutBackstack(Fragment fragment, String tag) {
        addFragmentWithoutBackstack(fragment,android.R.id.content, tag);
    }
    protected void addFragmentWithoutBackstack(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(container, fragment, tag);
        transaction.commit();
    }

    // for 2 tabs
    public void setTabFragment(int containerId, Fragment addFragment, String fragTag
            , Fragment rFrag1, Fragment rFrag2, Fragment rFrag3) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addFragment != null) {
            if (addFragment.isAdded()) { // if the fragment is already in container
                ft.show(addFragment);
            } else { // fragment needs to be added to frame container
                ft.add(containerId, addFragment, fragTag);
            }
        }

        // Hide 1st fragments
        if (rFrag1 != null && rFrag1.isAdded()) {
            ft.hide(rFrag1);
        }
        // Hide 2nd fragments
        if (rFrag2 != null && rFrag2.isAdded()) {
            ft.hide(rFrag2);
        }
        // Hide 2nd fragments
        if (rFrag3 != null && rFrag3.isAdded()) {
            ft.hide(rFrag3);
        }

        ft.commit();
    }
}