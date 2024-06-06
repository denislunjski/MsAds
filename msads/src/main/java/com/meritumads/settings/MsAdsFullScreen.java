package com.meritumads.settings;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.meritumads.elements.MsAdsPopupDelegate;

public abstract class MsAdsFullScreen {

    private static MsAdsFullScreenHandler fullScreenHandler;

    public static void getInstance(String developerId, Activity activity){
        runFullScreens(developerId, activity, null, null);
    }

    public static void getInstance(String developerId, Activity activity, MsAdsPopupDelegate popupDelegate){
        runFullScreens(developerId, activity, null, popupDelegate);
    }

    public static void getInstance(String developerId, Fragment fragment){
        runFullScreens(developerId, null, fragment, null);
    }

    public static void getInstance(String developerId, Fragment fragment, MsAdsPopupDelegate popupDelegate){
        runFullScreens(developerId, null, fragment, popupDelegate);
    }

    private static void runFullScreens(String developerId, Activity activity, Fragment fragment, MsAdsPopupDelegate popupDelegate){
        if(fullScreenHandler == null){
            fullScreenHandler = new MsAdsFullScreenHandler();
            fullScreenHandler.init(developerId, activity, fragment, popupDelegate);
        }else{
            fullScreenHandler.init(developerId, activity, fragment, popupDelegate);
        }
    }

}
