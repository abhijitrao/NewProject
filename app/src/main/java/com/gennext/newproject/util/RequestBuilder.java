package com.gennext.newproject.util;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Abhijit on 13-Dec-16.
 */

public class RequestBuilder {
    public static RequestBody Default(String consultantId) {
        RequestBody formBody = new FormBody.Builder()
                .addEncoded("consultantId", consultantId)
                .build();
        return formBody;
    }
    public static RequestBody LoginBody(String username, String password) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("userId", username)
                .addEncoded("password", password)
                .build();
        return formBody;
    }

    public static RequestBody FeedbackDetail(String consultantId, String feedback) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("consultantId", consultantId)
                .addEncoded("feedback", feedback)
                .build();
        return formBody;
    }

    public static RequestBody UpdateDomain(String userId, String domainId, String domainName) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("userId", userId)
                .addEncoded("domainId", domainId)
                .addEncoded("domainName", domainName)
                .build();
        return formBody;
    }

    public static RequestBody DeleteTimeSlot(String consultantId, String slotId, String date, String sTime
            , String eTime) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("consultantId", consultantId)
                .addEncoded("date", date)
                .addEncoded("startTime", sTime)
                .addEncoded("endTime", eTime)
                .build();
        return formBody;
    }

    public static RequestBody EditTimeSlot(String consultantId, String slotId, String date, String sTime,
                                           String eTime, String daya) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("id", slotId)
                .addEncoded("startTime", sTime)
                .addEncoded("endTime", eTime)
                .build();
        return formBody;
    }
    // consultantId,statusId,date,time
    public static RequestBody UpdateStatus(String consultantId, String date,String time,String statusId
            , String remarks) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("consultantId", consultantId)
                .addEncoded("statusId", statusId)
                .addEncoded("date", date)
                .addEncoded("time", time)
                .addEncoded("remark", remarks)
                .build();
        return formBody;
    }

    // consultantId,date,time
    public static RequestBody SlotCancel(String consultantId, String date,String time) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("consultantId", consultantId)
                .addEncoded("date", date)
                .addEncoded("time", time)
                .build();
        return formBody;
    }

    public static RequestBody SlotReportToAdmin(String consultantId, String userId, String slotId) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("consultantId", consultantId)
                .addEncoded("userId", userId)
                .addEncoded("slotId", slotId)
                .build();
        return formBody;
    }

    //consultantId,startDate,endDate,startTime,endTime,days
     public static RequestBody AddTimeSlot(String consultantId, String startDate, String endDate, String startTime
            , String endTime, String sltDaysIds) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("consultantId", consultantId)
                .addEncoded("startDate", startDate)
                .addEncoded("endDate", endDate)
                .addEncoded("startTime", startTime)
                .addEncoded("endTime", endTime)
                .addEncoded("days", sltDaysIds)
                .build();
        return formBody;
    }

    public static RequestBody ConsultantFeedback(String consultantId, String date, String time, String feedback) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("consultantId", consultantId)
                .addEncoded("date", date)
                .addEncoded("time", time)
                .addEncoded("feedback", feedback)
                .build();
        return formBody;
    }

    public static RequestBody UpdateCRecord(String consultantId,String callJson) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("consultantId", consultantId)
                .addEncoded("callJson", callJson)
                .build();
        return formBody;
    }

    public static RequestBody verifyOtpMobile(String mobile, String otp) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("mobile", mobile)
                .addEncoded("otp", otp)
                .build();
        return formBody;
    }

    public static RequestBody verifyMobile(String mobile) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("mobile", mobile)
                .build();
        return formBody;
    }

    public static RequestBody verifyEmail(String email) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("email", email)
                .build();
        return formBody;
    }

    public static RequestBody verifyOtpEmail(String email, String otp) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("email", email)
                .addEncoded("otp", otp)
                .build();
        return formBody;
    }

    public static RequestBody createPassThroughMobile(String password, String input) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("mobile", input)
                .addEncoded("password", password)
                .build();
        return formBody;
    }

    public static RequestBody createPassThroughEmail(String password, String input) {
        RequestBody formBody =  new FormBody.Builder()
                .addEncoded("email", input)
                .addEncoded("password", password)
                .build();
        return formBody;
    }

    public static RequestBody ErrorReport(String report) {
        RequestBody formBody = new FormBody.Builder()
                .addEncoded("reportLog", report)
                .build();
        return formBody;
    }
}
