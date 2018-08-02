package com.cs.lgubustracker.pref;

import android.content.Context;
import android.content.SharedPreferences;

/***********************************
 * Created by Farhat on 5/6/2018.  *
 ***********************************/

public class PreferenceManager {

    private final static String APP_CONFIGURATION = "configurations";
    private final static String USERNAME_PREF = "username_pref";
    private final static String PASSWORD_PREF = "password_pref";
    private final static String LOGIN_TOKEN = "token_pref";


    private PreferenceManager() {
    }

    public static boolean setConfiguration(Context appContext, String key, String value) {

        try {
            SharedPreferences.Editor editor = appContext.getSharedPreferences(APP_CONFIGURATION, Context.MODE_PRIVATE).edit();
            editor.putString(key, value);
            editor.commit();
            return true;
        } catch (Exception e) {

            return false;
        }
    }


    public static String getConfiguration(Context appContext, String key) {
        String conf = "";
        try {
            SharedPreferences pref = appContext.getSharedPreferences(APP_CONFIGURATION, Context.MODE_PRIVATE);
            conf = pref.getString(key, null);

        } catch (Exception e) {

            conf = "";
        }
        return conf;
    }


    public static boolean setBooleanConfiguration(Context appContext, String key, boolean value) {

        try {
            SharedPreferences.Editor editor = appContext.getSharedPreferences(APP_CONFIGURATION, Context.MODE_PRIVATE).edit();
            editor.putBoolean(key, value);
            editor.commit();
            return true;
        } catch (Exception e) {

            return false;
        }
    }


    public static boolean getBooleanConfiguration(Context appContext, String key) {
        boolean conf = false;
        try {
            SharedPreferences pref = appContext.getSharedPreferences(APP_CONFIGURATION, Context.MODE_PRIVATE);

            conf = pref.getBoolean(key, false);

        } catch (Exception e) {
            conf = false;
        }
        return conf;
    }

    public static boolean setLongConfiguration(Context appContext, String key, long value) {
        try {
            SharedPreferences.Editor editor = appContext.getSharedPreferences(APP_CONFIGURATION, Context.MODE_PRIVATE).edit();
            editor.putLong(key, value);
            editor.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static long getLongConfiguration(Context appContext, String key) {
        long conf = 0;
        try {
            SharedPreferences pref = appContext.getSharedPreferences(APP_CONFIGURATION, Context.MODE_PRIVATE);
            conf = pref.getLong(key, 0);

        } catch (Exception e) {
            conf = 0;
        }
        return conf;
    }




    public static boolean setUserName(Context appContext, String dashBoardUrl) {
        boolean status = false;

        status = setConfiguration(appContext, USERNAME_PREF, dashBoardUrl);

        return status;
    }

    public static String getUserName(Context appContext) {
        String url = getConfiguration(appContext, USERNAME_PREF);
        return url;
    }

    public static boolean setPassword(Context appContext, String password) {
        boolean status = false;

        status = setConfiguration(appContext, PASSWORD_PREF, password);

        return status;
    }

    public static String getPassword(Context appContext) {
        String url = getConfiguration(appContext, PASSWORD_PREF);
        if (url == null) {
            return null;
        } else {
            return url;
        }
    }

    public static boolean setToken(Context appContext, String password) {
        boolean status = false;

        status = setConfiguration(appContext, LOGIN_TOKEN, password);

        return status;
    }

    public static String getToken(Context appContext) {
        String url = getConfiguration(appContext, LOGIN_TOKEN);
        if (url == null) {
            return null;
        } else {
            return url;
        }
    }
}

