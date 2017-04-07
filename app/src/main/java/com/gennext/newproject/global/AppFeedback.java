package com.gennext.newproject.global;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gennext.newproject.R;
import com.gennext.newproject.model.Model;
import com.gennext.newproject.util.ApiCall;
import com.gennext.newproject.util.ApiCallError;
import com.gennext.newproject.util.AppAnimation;
import com.gennext.newproject.util.AppSettings;
import com.gennext.newproject.util.AppUser;
import com.gennext.newproject.util.JsonParser;
import com.gennext.newproject.util.RequestBuilder;


/**
 * Created by Abhijit on 14-Oct-16.
 */

public class AppFeedback extends CompactFragment implements ApiCallError.ErrorListener{
    private Button btnOK;
    private FragmentManager manager;
    AssignTask assignTask;
    private EditText etFeedback;
    private AppAnimation anim;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (assignTask != null) {
            assignTask.onAttach(activity);
        }
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
        if (assignTask != null) {
            assignTask.onDetach();
        }
    }

    public static AppFeedback newInstance(Activity act) {
        AppFeedback appFeedback=new AppFeedback();
        AppAnimation.setDialogAnimation(act,appFeedback);
        return appFeedback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_app_feedback, container, false);
        manager = getFragmentManager();
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {
        btnOK = (Button) v.findViewById(R.id.btn_popup);
        LinearLayout llClose = (LinearLayout) v.findViewById(R.id.ll_whitespace);
        etFeedback = (EditText) v.findViewById(R.id.et_alert_feedback);


        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeybord(getActivity());
                manager.popBackStack();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeybord(getActivity());
                sendFeedback(etFeedback.getText().toString(),
                        AppUser.getUserId(getActivity()));
            }
        });
    }

    private void sendFeedback(String feedback, String regId) {
        assignTask = new AssignTask(getActivity(), feedback, regId);
        assignTask.execute(AppSettings.SEND_FEEDBACK);
    }

    @Override
    public void onErrorRetryClick(DialogFragment dialog) {
        sendFeedback( etFeedback.getText().toString(),
                AppUser.getUserId(getActivity()));
    }

    @Override
    public void onErrorCancelClick(DialogFragment dialog) {

    }



    private class AssignTask extends AsyncTask<String, Void, Model> {
        Activity activity;
        private String feedback;

        public void onAttach(Activity activity) {
            // TODO Auto-generated method stub
            this.activity = activity;
        }

        public void onDetach() {
            // TODO Auto-generated method stub
            this.activity = null;
        }

        public AssignTask(Activity activity, String feedback, String regId) {
            this.feedback = feedback;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(activity, "Processing please wait...");
        }

        @Override
        protected Model doInBackground(String... urls) {
            String response;
            String consultantId = AppUser.getUserId(activity);
            response = ApiCall.POST(urls[0], RequestBuilder.FeedbackDetail(consultantId, feedback));
            JsonParser jsonParser = new JsonParser();
            return jsonParser.defaultParser(response);
        }

        @Override
        protected void onPostExecute(Model result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (activity != null) {
                hideProgressDialog();
                if (result != null) {
                    if (result.getOutput().equals("success")) {
                        PopupAlert.newInstance("Feedback",result.getOutputMsg(),PopupAlert.POPUP_DIALOG)
                                .show(getFragmentManager(),"popupAlert");
                    } else if (result.getOutput().equals("failure")) {
                        PopupAlert.newInstance("Feedback",result.getOutputMsg(),PopupAlert.POPUP_DIALOG)
                                .show(getFragmentManager(),"popupAlert");
                    } else {
                        PopupAlert.newInstance("Alert",result.getOutputMsg(),PopupAlert.POPUP_DIALOG)
                                .show(getFragmentManager(),"popupAlert");
                    }
                } else {
                    ApiCallError.newInstance(JsonParser.ERRORMESSAGE,AppFeedback.this)
                            .show(getFragmentManager(),"apiCallError");

                }
            }
        }
    }
}