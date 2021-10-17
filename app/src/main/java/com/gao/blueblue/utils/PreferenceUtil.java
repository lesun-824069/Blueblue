package com.gao.blueblue.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.gao.blueblue.AppContext;

/**
 * @program: Blueblue
 * @description:
 * @author: wuzewen
 * @create: 2021-10-16 19:57
 **/
public class PreferenceUtil {


    private static Context mContext = AppContext.getContext();


    public static final class PreKey {

        public static final String SESSION_TOKEN = "session_token";
        public static final String AUTO_LOGIN = "auto_login";
        public static final String USER_INFO = "userInfo";
    }

    public static final class MapName {
        public static final String LOGIN = "login";
        public static final String TEAM = "team";
        public static final String OTHER = "other";
    }

    public static void putString(String mapName, String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(mapName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static String getString(String mapName, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(mapName, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void putBoolean(String mapName, String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(mapName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static boolean getBoolean(String mapName, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(mapName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
}
