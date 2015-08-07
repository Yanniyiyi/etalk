package com.yanni.etalk.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by macbookretina on 27/06/15.
 */
public class EtalkSharedPreference {
    static final String PREF_USER_ID = "user_id";
    static final String PREF_USER_NO = "user_no";
    static final String PREF_USER_NAME = "user_name";
    static final String PREF_USER_LEVEL = "user_level";
    static final String PREF_USER_SCORE = "user_score";
    static final String PREF_TOKEN = "token";
    static final String PREF_LOGIN_STATE = "login_state";
    static final String PREF_IS_FIRST_TIME = "is_first_time";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setPrefLoginState(Context context, boolean state) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(PREF_LOGIN_STATE, state);
        editor.commit();
    }


    public static void setPrefUserId(Context context, String userId) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_ID, userId);
        editor.commit();
    }

    public static void setPrefUserNo(Context context, String userNo) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_NO, userNo);
        editor.commit();
    }

    public static void setPrefUserName(Context context, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static void setPrefUserLevel(Context context, String userLevel) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_LEVEL, userLevel);
        editor.commit();
    }

    public static void setPrefUserScore(Context context, String userScore) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_SCORE, userScore);
        editor.commit();
    }

    public static void setPrefToken(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_TOKEN, token);
        editor.commit();
    }


    public static void setPrefIsFirstTime(Context context, String appVersion){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_IS_FIRST_TIME, appVersion);
        editor.commit();
    }

    public static boolean getPrefLoginState(Context context) {
        return getSharedPreferences(context).getBoolean(PREF_LOGIN_STATE, false);
    }

    public static String getPrefUserId(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_ID, "");
    }

    public static String getPrefUserNo(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_NO, "");
    }

    public static String getPrefUserName(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_NAME, "");
    }


    public static String getPrefUserLevel(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_LEVEL, "");
    }

    public static String getPrefUserScore(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_SCORE, "");
    }

    public static String getPrefToken(Context context) {
        return getSharedPreferences(context).getString(PREF_TOKEN, "");
    }

    public static String getPrefIsFirstTime(Context context) {
        return getSharedPreferences(context).getString(PREF_IS_FIRST_TIME,"");
    }



    public static void clearPreference(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(PREF_LOGIN_STATE);
        editor.remove(PREF_TOKEN);
        editor.remove(PREF_USER_ID);
        editor.remove(PREF_USER_LEVEL);
        editor.remove(PREF_USER_NAME);
        editor.remove(PREF_USER_NO);
        editor.remove(PREF_USER_SCORE);
       // editor.clear();
        editor.commit();
    }
}
