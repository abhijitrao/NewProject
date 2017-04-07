package com.gennext.newproject.util;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Abhijit on 13-Dec-16.
 */

public class ApiCall {
    //GET network request
    public static String GET(HttpUrl url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (IOException e){
            return "IOException "+e.toString();
        }
    }

    //POST network request
    public static String POST( String url, RequestBody body){
        String res = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        // socket timeout
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            res=response.body().string();
        } catch (IOException e) {
            return "IOException "+e.toString();
        }
        return res;
    }

    public static String upload(String url,RequestBody formBody){
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder().url(url).post(formBody).build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return "IOException "+e.toString();
        }
    }
}