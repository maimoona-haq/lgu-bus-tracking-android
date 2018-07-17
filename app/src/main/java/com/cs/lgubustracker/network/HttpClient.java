package com.cs.lgubustracker.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/***********************************
 * Created by Babar on 5/6/2018.  *
 ***********************************/

public class HttpClient {

    private final static int CONNECTION_TIME_OUT = 30 * 1000;
    private static final String TAG = "HttpClient";
    
    
    public static synchronized JSONObject sendPostRequest(Context appContext, String requestURL,String data) {
        JSONObject responseJob = null;
        URL url;
        String response = "";
        try {
            Log.d(TAG,"[HttpCleint] inside sendPostRequest() POST request : ");
            //printParams(appContext, postDataParams);
            requestURL = requestURL.replaceAll("\\s+","%20");
            url = new URL(requestURL);
            HttpURLConnection conn;
            if(requestURL.startsWith("https")) {
                conn = (HttpsURLConnection) url.openConnection();
            }else{
                conn = (HttpURLConnection) url.openConnection();
            }
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(CONNECTION_TIME_OUT);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            os.close();
            Log.d(TAG,"[HttpCleint] inside sendPostRequest() POST request to URL : " + url);
            int responseCode = conn.getResponseCode();
            Log.d(TAG,"[HttpCleint] inside sendPostRequest() Response Code : " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                return responseJob;

            }
            Log.d(TAG,"[HttpCleint] inside sendPostRequest() Response : " + response.toString());
            if(response.charAt(0) == '{') {
                responseJob = new JSONObject(response.toString());
            }else{
                responseJob = new JSONObject();
                responseJob.put("data",response.toString());
                responseJob.put("code","100");
            }
            //responseJob = new JSONObject(response);
            return responseJob;
        } catch (Exception e) {
            //AnalyticsUtil.updateEventAction(appContext, appContext.getResources().getString(R.string.event_connect_exception_request_to_server) + e.toString());
            Log.d(TAG,"[HttpClient] inside sendPostRequest() Exception is : " + e.toString());
            try {
                responseJob = new JSONObject();
                responseJob.put("code","400");
                responseJob.put("exception",e.toString());

            } catch (Exception ex) {
                Log.d(TAG,"[HttpCleint] inside sendGet()-> Exception is : "+ex.toString());
            }
            return responseJob;
        }

    }
    public static synchronized boolean sendPostRequestLogout(Context appContext, String requestURL,String token) {
        JSONObject responseJob = null;
        URL url;
        String response = "";
        try {
            Log.d(TAG,"[HttpCleint] inside sendPostRequestLogout() POST request : ");
            //printParams(appContext, postDataParams);
            requestURL = requestURL.replaceAll("\\s+","%20");
            url = new URL(requestURL);
            HttpURLConnection conn;
            if(requestURL.startsWith("https")) {
                conn = (HttpsURLConnection) url.openConnection();
            }else{
                conn = (HttpURLConnection) url.openConnection();
            }
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Token token="+token);
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(CONNECTION_TIME_OUT);
            conn.setRequestMethod("DELETE");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            //writer.write(data);
            writer.flush();
            writer.close();
            os.close();
            Log.d(TAG,"[HttpCleint] inside sendPostRequest() POST request to URL : " + url);
            int responseCode = conn.getResponseCode();
            Log.d(TAG,"[HttpCleint] inside sendPostRequest() Response Code : " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
              /*  String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }*/
              return true;
            } else {
                return false;

            }

        } catch (Exception e) {
            //AnalyticsUtil.updateEventAction(appContext, appContext.getResources().getString(R.string.event_connect_exception_request_to_server) + e.toString());
            Log.d(TAG,"[HttpClient] inside sendPostRequest() Exception is : " + e.toString());
            try {
                responseJob = new JSONObject();
                responseJob.put("code","400");
                responseJob.put("exception",e.toString());

            } catch (Exception ex) {
                Log.d(TAG,"[HttpCleint] inside sendGet()-> Exception is : "+ex.toString());
            }
            return false;
        }

    }

    public static boolean isMobileOrWifiConnectivityAvailable(Context ctx) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;


        try {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected()) {
                        haveConnectedWifi = true;
                    }
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected()) {
                        haveConnectedMobile = true;
                    }
            }
        } catch (Exception e) {
            //DebugLog.console("[ConnectionVerifier] inside isInternetOn() Exception is : " + e.toString());
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
