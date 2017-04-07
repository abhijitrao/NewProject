package com.gennext.newproject.global;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gennext.newproject.R;


public class PopupAlert extends DialogFragment {
    public static final int ACTIVITY = 1, FRAGMENT = 2, POPUP_DIALOG = 3;
    private Dialog dialog;
    private String mTitle;
    private String mMessage;
    private int finishType,popupTimeOutInSec;


    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);
    }

    public static PopupAlert newInstance(String title, String message,int finishType) {
        PopupAlert fragment = new PopupAlert();
        fragment.mTitle = title;
        fragment.mMessage = message;
        fragment.finishType=finishType;
        fragment.popupTimeOutInSec=0;
        return fragment;
    }
    public static PopupAlert newInstance(String title, String message,int finishType,int popupTimeOutInSec) {
        PopupAlert fragment = new PopupAlert();
        fragment.mTitle = title;
        fragment.mMessage = message;
        fragment.finishType=finishType;
        fragment.popupTimeOutInSec=popupTimeOutInSec;
        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.frag_popup_alert, null);
        dialogBuilder.setView(v);
        Button btnOk = (Button) v.findViewById(R.id.btn_popup);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_popup_title);
        TextView tvDescription = (TextView) v.findViewById(R.id.tv_popup_description);

        tvTitle.setText(mTitle);
        tvDescription.setText(mMessage);
        btnOk.setText("Ok");


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish(finishType);
            }
        });

        if (popupTimeOutInSec != 0) {
            startTimerForMainActivity();
        }


        dialog = dialogBuilder.create();
        dialog.show();

        return dialog;
    }

    private void finish(int finishType) {
        if (finishType == ACTIVITY) {
            getActivity().finish();
        } else if (finishType == FRAGMENT) {
            getFragmentManager().popBackStack();
        }
    }

    private void startTimerForMainActivity() {
        // TODO Auto-generated method stub
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if(dialog!=null) {
                    if (finishType == ACTIVITY) {
                        getActivity().finish();
                    } else if (finishType == POPUP_DIALOG) {
                        dialog.dismiss();
                    } else {
                        getFragmentManager().popBackStack();
                    }
                }
            }
        }, (popupTimeOutInSec*1000));
    }


}
