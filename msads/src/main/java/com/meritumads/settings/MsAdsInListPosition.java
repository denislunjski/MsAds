package com.meritumads.settings;

import android.view.ViewGroup;

import com.meritumads.pojo.MsAdsPosition;

public class MsAdsInListPosition {

    protected static MsAdsPosition position;

    String error = "";

    public MsAdsPosition getPosition() {
        return position;
    }

    public String getError() {
        return error;
    }

    public static void show(ViewGroup view){
        if(position!=null) {
            MsAdsBanners.getInstance(position.getDeveloperId(), view);
        }
    }
}
