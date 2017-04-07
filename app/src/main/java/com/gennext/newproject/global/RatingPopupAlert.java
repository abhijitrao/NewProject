package com.gennext.newproject.global;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gennext.newproject.R;
import com.gennext.newproject.util.Rating;


/**
 * Created by Abhijit on 14-Oct-16.
 */

public class RatingPopupAlert extends CompactFragment {
    private TextView tvTitle, tvDesc;
    private Button btnRateUs,btnRemindLater;
    private String title, desc;
    private FragmentManager manager;
    private LinearLayout llWhitespace;

    private static int SPLASH_TIME_OUT = 3000;

    private Context context;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context=null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.popup_rating_alert, container, false);
        manager = getFragmentManager();
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {
        tvTitle = (TextView) v.findViewById(R.id.tv_popup_title);
        tvDesc = (TextView) v.findViewById(R.id.tv_popup_description);
        llWhitespace = (LinearLayout) v.findViewById(R.id.ll_whitespace);
        btnRateUs = (Button) v.findViewById(R.id.btn_popup1);
        btnRemindLater = (Button) v.findViewById(R.id.btn_popup2);
        title = "Rate this App";
        String appName = getSt(R.string.app_name);
        desc = "If you enjoy using " + appName + ", please take a moment to rate it. Thanks for your support!";
        tvTitle.setText(title);
        tvDesc.setText(desc);

        llWhitespace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                manager.popBackStack();
            }
        });

        btnRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rating.stopRating(context);
                try {
                    Uri uri = Uri.parse("market://details?id="+getActivity().getPackageName()+"");
                    Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goMarket);
                }catch (ActivityNotFoundException e){
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+getActivity().getPackageName()+"");
                    Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goMarket);
                }
                manager.popBackStack();
            }
        });

        btnRemindLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rating.remindMeLater(context);
                manager.popBackStack();
            }
        });

//        startTimerForMainActivity();
    }




    private void startTimerForMainActivity() {
        // TODO Auto-generated method stub
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(context!=null)
                manager.popBackStack();
            }
        }, SPLASH_TIME_OUT);
    }


}