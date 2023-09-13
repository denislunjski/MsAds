package com.meritumads.settings;

import android.app.Activity;

import androidx.fragment.app.Fragment;

public abstract class MsAdsFullScreen {

    private static FullScreenHandler fullScreenHandler;

    public static void getInstance(Activity activity){
        runFullScreens(activity, null);
    }

    public static void getInstance(Fragment fragment){
        runFullScreens(null, fragment);
    }

    private static void runFullScreens(Activity activity, Fragment fragment){
        if(fullScreenHandler == null){
            fullScreenHandler = new FullScreenHandler();
            fullScreenHandler.init(activity, fragment);
        }else{
            fullScreenHandler.init(activity, fragment);
        }
    }

}
