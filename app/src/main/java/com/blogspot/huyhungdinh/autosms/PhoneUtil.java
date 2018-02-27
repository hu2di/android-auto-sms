package com.blogspot.huyhungdinh.autosms;


import android.content.Context;
import android.content.SharedPreferences;

public class PhoneUtil {

    private static final String KEY_SAVING = "auto.sms.saving";
    private static final String KEY_ALL_PHONE = "KEY_ALL_PHONE";

    public static void addOnePhone(Context context, String phone) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_SAVING, Context.MODE_PRIVATE);
        int count = getOnePhone(context, phone);
        count++;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(phone, count);
        editor.apply();
    }

    public static int getOnePhone(Context context, String phone) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_SAVING, Context.MODE_PRIVATE);
        return preferences.getInt(phone, 0);
    }

    public static void addAllPhone(Context context, String phone) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_SAVING, Context.MODE_PRIVATE);
        String allPhone = getAllPhone(context);
        allPhone = allPhone + ";" + phone;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ALL_PHONE, allPhone);
        editor.apply();
    }

    public static String getAllPhone(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_SAVING, Context.MODE_PRIVATE);
        return preferences.getString(KEY_ALL_PHONE, "");
    }
}
