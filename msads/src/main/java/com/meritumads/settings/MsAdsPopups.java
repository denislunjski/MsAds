package com.meritumads.settings;

import android.app.Activity;

import androidx.fragment.app.Fragment;

public abstract class MsAdsPopups {

    private static PopupsHandler popupsHandler;

    public static void getInstance(Activity activity){
        runPopups(activity, null);
    }

    public static void getInstance(Fragment fragment){
        runPopups(null, fragment);
    }

    private static void runPopups(Activity activity, Fragment fragment){

        if(popupsHandler == null){
            popupsHandler = new PopupsHandler();
            popupsHandler.init(activity, fragment);
        }else{
            popupsHandler.init(activity, fragment);
        }

    }

}


