package com.gennext.newproject.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gennext.newproject.R;


public class ApiCallError extends DialogFragment {
    private static final Boolean DEFAULT_REPORTING_VALUE = false;
    private String errorMessage;
    private TextView tvDescription;
    private Dialog dialog;
    private String mTitle;
    private String mMessage;
    private ErrorListener mListener;
    private ErrorFlagListener mFlagListener;
    private int flag;
    private Boolean isReportToServer;

    public interface ErrorListener {
        void onErrorRetryClick(DialogFragment dialog);

        void onErrorCancelClick(DialogFragment dialog);
    }

    public interface ErrorFlagListener {
        void onErrorRetryClick(DialogFragment dialog, int flag);

        void onErrorCancelClick(DialogFragment dialog, int flag);
    }


    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);
    }

    public static ApiCallError newInstance(String title, String message, String errorMessage, ErrorListener listener) {
        ApiCallError fragment = new ApiCallError();
        fragment.mTitle = title;
        fragment.mMessage = message;
        fragment.mListener = listener;
        fragment.errorMessage = errorMessage;
        fragment.isReportToServer = DEFAULT_REPORTING_VALUE;
        return fragment;
    }

    public static ApiCallError newInstance(String errorMessage, ErrorListener listener) {
        ApiCallError fragment = new ApiCallError();
        fragment.mListener = listener;
        fragment.errorMessage = errorMessage;
        fragment.isReportToServer = DEFAULT_REPORTING_VALUE;
        return fragment;
    }
    public static ApiCallError newInstance(String errorMessage,Boolean isReportToServer, ErrorListener listener) {
        ApiCallError fragment = new ApiCallError();
        fragment.mListener = listener;
        fragment.errorMessage = errorMessage;
        fragment.isReportToServer = isReportToServer;
        return fragment;
    }

    public static ApiCallError newInstance(String errorMessage, int flag, ErrorFlagListener listener) {
        ApiCallError fragment = new ApiCallError();
        fragment.mFlagListener = listener;
        fragment.flag = flag;
        fragment.errorMessage = errorMessage;
        fragment.isReportToServer = DEFAULT_REPORTING_VALUE;
        return fragment;
    }
    public static ApiCallError newInstance(String errorMessage,Boolean isReportToServer, int flag, ErrorFlagListener listener) {
        ApiCallError fragment = new ApiCallError();
        fragment.mFlagListener = listener;
        fragment.flag = flag;
        fragment.errorMessage = errorMessage;
        fragment.isReportToServer = isReportToServer;
        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.alert_dialog, null);
        dialogBuilder.setView(v);
        ImageView ivAbout = (ImageView) v.findViewById(R.id.iv_alert_dialog_about);
        Button button1 = (Button) v.findViewById(R.id.btn_alert_dialog_button1);
        Button button2 = (Button) v.findViewById(R.id.btn_alert_dialog_button2);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_alert_dialog_title);
        tvDescription = (TextView) v.findViewById(R.id.tv_alert_dialog_detail);
        if (mTitle == null) {
            if(isReportToServer){
                mTitle = getString(R.string.server_error_reporting_tag);
                mMessage = getString(R.string.server_error_reporting_msg);
            }else {
                mTitle = getString(R.string.server_time_out_tag);
                mMessage = getString(R.string.server_time_out_msg);
            }
        }
        tvTitle.setText(mTitle);
        tvDescription.setText(mMessage);
        if(isReportToServer){
            button1.setText("Report");
        }else {
            button1.setText("Retry");
        }
        button2.setText("Cancel");
        ivAbout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (errorMessage != null) {
                    tvDescription.setText(errorMessage);
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
                if(isReportToServer){
                    new ErrorReportingTask().execute(errorMessage);
                }else{
                    if (mListener != null) {
                        mListener.onErrorRetryClick(ApiCallError.this);
                    } else if (mFlagListener != null) {
                        mFlagListener.onErrorRetryClick(ApiCallError.this, flag);
                    }
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
                if (mListener != null) {
                    mListener.onErrorCancelClick(ApiCallError.this);
                } else if (mFlagListener != null) {
                    mFlagListener.onErrorCancelClick(ApiCallError.this, flag);
                }
            }
        });

        dialog = dialogBuilder.create();
        dialog.show();

        return dialog;
    }


    private class ErrorReportingTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... report) {
            String response = ApiCall.POST(AppSettings.REPORT_SERVER_ERROR, RequestBuilder.ErrorReport(report[0]));
            return null;
        }
    }
}
