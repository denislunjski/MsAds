package com.meritumads.settings;

import android.view.ViewGroup;

import com.meritumads.pojo.Position;

public class InListPosition {

    protected static Position position;

    String error = "";

    public Position getPosition() {
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
