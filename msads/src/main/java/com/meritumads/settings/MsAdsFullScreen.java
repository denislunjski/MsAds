package com.meritumads.settings;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.meritumads.elements.PopupDelegate;

public abstract class MsAdsFullScreen {

    private static FullScreenHandler fullScreenHandler;

    public static void getInstance(String developerId, Activity activity){
        runFullScreens(developerId, activity, null, null);
    }

    public static void getInstance(String developerId, Activity activity, PopupDelegate popupDelegate){
        runFullScreens(developerId, activity, null, popupDelegate);
    }

    public static void getInstance(String developerId, Fragment fragment){
        runFullScreens(developerId, null, fragment, null);
    }

    public static void getInstance(String developerId, Fragment fragment, PopupDelegate popupDelegate){
        runFullScreens(developerId, null, fragment, popupDelegate);
    }

    private static void runFullScreens(String developerId, Activity activity, Fragment fragment, PopupDelegate popupDelegate){
        if(fullScreenHandler == null){
            fullScreenHandler = new FullScreenHandler();
            fullScreenHandler.init(developerId, activity, fragment, popupDelegate);
        }else{
            fullScreenHandler.init(developerId, activity, fragment, popupDelegate);
        }
    }

}
