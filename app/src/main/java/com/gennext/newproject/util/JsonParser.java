package com.gennext.newproject.util;

import android.content.Context;

import com.gennext.newproject.model.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Abhijit on 08-Dec-16.
 */
public class JsonParser {
    public static String ERRORMESSAGE = "Not Available";


    public Model defaultParser(String response) {
        Model jsonModel = new Model();
        jsonModel.setOutput("ERROR-101");
        jsonModel.setOutputMsg("ERROR-101 No msg available");
        if (response.contains("IOException")) {
            jsonModel.setOutput("internet");
            jsonModel.setOutputMsg(response);
            return jsonModel;
        }else {
            if (response.contains("[")) {
                try {
                    JSONArray mainArray = new JSONArray(response);
                    JSONObject mainObject = mainArray.getJSONObject(0);
                    if (mainObject.getString("status").equals("success")) {
                        jsonModel.setOutput("success");
                        jsonModel.setOutputMsg(mainObject.getString("message"));
                    } else if (mainObject.getString("status").equals("failure")) {
                        jsonModel.setOutput("failure");
                        jsonModel.setOutputMsg(mainObject.getString("message"));
                    }
                } catch (JSONException e) {
                    ERRORMESSAGE = e.toString() + "\n" + response;
                    jsonModel.setOutput("server");
                    jsonModel.setOutputMsg(e.toString() + "\n" + response);
                }
            } else {
                ERRORMESSAGE = response;
                jsonModel.setOutput("server");
                jsonModel.setOutputMsg(response);
            }
        }
        return jsonModel;
    }

    public Model parseLoginData(Context activity, String response) {
        Model jsonModel = new Model();
        jsonModel.setOutput("ERROR-101");
        jsonModel.setOutputMsg("ERROR-101 No msg available");
        if (response.contains("IOException")) {
            jsonModel.setOutput("internet");
            jsonModel.setOutputMsg(response);
            return jsonModel;
        }else {
            if (response.contains("[")) {
                try {
                    JSONArray mainArray = new JSONArray(response);
                    JSONObject mainObject = mainArray.getJSONObject(0);
                    if (mainObject.getString("status").equals("success")) {
                        jsonModel.setOutput("success");
                        JSONObject msgObj = mainObject.optJSONObject("message");
                        if (msgObj != null) {
                            AppUser.setLoginData(activity, msgObj);
                        }
                    } else if (mainObject.getString("status").equals("failure")) {
                        jsonModel.setOutput("failure");
                        jsonModel.setOutputMsg(mainObject.getString("message"));
                    }
                } catch (JSONException e) {
                    ERRORMESSAGE = e.toString() + "\n" + response;
                    jsonModel.setOutput("server");
                    jsonModel.setOutputMsg(e.toString() + "\n" + response);
                }
            } else {
                ERRORMESSAGE = response;
                jsonModel.setOutput("server");
                jsonModel.setOutputMsg(response);
            }
        }
        return jsonModel;
    }

}