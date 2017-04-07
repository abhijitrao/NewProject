package com.gennext.newproject.global;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gennext.newproject.R;


public class CompactFragment extends Fragment {
    private AlertDialog alertDialog;

    public boolean APICheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        } else {
            return false;
        }
    }

    public void initToolBar(final Activity act, View v, String title) {
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        AppCompatActivity activity = (AppCompatActivity) act;
        activity.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideKeybord(act);
                        getFragmentManager().popBackStack();
                    }
                }

        );
    }
    public void initToolBarForActivity(final Activity act, View v, String title) {
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        AppCompatActivity activity = (AppCompatActivity) act;
        activity.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideKeybord(act);
                        act.finish();
                    }
                }

        );
    }


    public boolean isOnline() {
        boolean conn = false;
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
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



    public void closeFragmentDialog(String dialogName) {
        FragmentManager fragmentManager = getFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if (fragment != null) {
            fragment.dismiss();
        }
    }

    protected void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showpopupalert(String title, String description, int finishType) {
        showPopupAlert(title, description, finishType,3);
    }

    public void showPopupAlert(String title, String description, int finishType,int popupTimeOutInSec) {
//        PopupDialog dialog=PopupDialog.newInstance()
//        PopupAlert popupAlert = PopupAlert.newInstance(title, description, finishType, popupTimeOutInSec);
//        AppAnimation.setDialogAnimation(getContext(),popupAlert);
//        addFragment(popupAlert,"popupAlert");
    }


    public static void hideKeybord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    public void showProgressDialog(Context context, String msg) {
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage(msg);
//        progressDialog.setIndeterminate(false);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        PDialog cDialog=PDialog.newInstance(context,msg);
//        alertDialog=cDialog.show();
    }

    public void hideProgressDialog() {
//        if (progressDialog != null)
//            progressDialog.dismiss();
        if (alertDialog != null)
            alertDialog.dismiss();
    }



    public String LoadPref(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String data = sharedPreferences.getString(key, "");
        return data;
    }

    public void SavePref(String key, String value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }


    public String getSt(int id) {
        return getResources().getString(id);
    }


    protected void replaceFragment(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    protected void setFragment(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    public void addFragment(Fragment fragment, String tag) {
        addFragment(fragment, android.R.id.content, tag);
    }

    public void addFragment(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
    public void addFragmentWithoutBackStack(Fragment fragment, String tag) {
        addFragmentWithoutBackStack(fragment, android.R.id.content, tag);
    }
    public void addFragmentWithoutBackStack(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(container, fragment, tag);
        transaction.commitAllowingStateLoss();
    }

    public void setUnSetFragment(FragmentManager manager, int containerId, Fragment addFragment, String fragTag
            , Fragment rFrag1) {
        FragmentTransaction ft = manager.beginTransaction();
        if (addFragment != null) {
            if (addFragment.isAdded()) { // if the fragment is already in container
                ft.show(addFragment);
            } else { // fragment needs to be added to frame container
                ft.add(containerId, addFragment, fragTag);
            }
        }

        // Hide other fragments
        if (rFrag1 != null && rFrag1.isAdded()) {
            ft.hide(rFrag1);
        }

        ft.commit();
    }
}
