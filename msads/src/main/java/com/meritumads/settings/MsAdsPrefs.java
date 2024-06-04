package com.meritumads.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class MsAdsPrefs {

    private static final String XML = "ms_ads_prefs_file.xml";

    private static SharedPreferences getPreferences(Context c) {
        SharedPreferences sp = c.getSharedPreferences(XML, Context.MODE_PRIVATE);
        return  sp;
    }

    public static void setDeviceId (Context c, String value){
        SharedPreferences sp = getPreferences(c);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("key_device_id", value);
        editor.commit();
    }

    public static String getDeviceId(Context c){
        return  getPreferences(c).getString("key_device_id", "0");
    }

}
