package com.meritumads.settings;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.meritumads.elements.MsAdsPopupDelegate;

public abstract class MsAdsPopups {

    private static MsAdsPopupsHandler popupsHandler;

    public static void getInstance(String developerId, Activity activity){
        runPopups(developerId, activity, null, null);
    }

    public static void getInstance(String developerId, Fragment fragment){
        runPopups(developerId,null, fragment, null);
    }

    public static void getInstance(String developerId, Activity activity, MsAdsPopupDelegate popupDelegate){
        runPopups(developerId, activity, null, popupDelegate);
    }

    public static void getInstance(String developerId, Fragment fragment, MsAdsPopupDelegate popupDelegate){
        runPopups(developerId, null, fragment, popupDelegate);
    }

    private static void runPopups(String developerId, Activity activity, Fragment fragment, MsAdsPopupDelegate popupDelegate){

        if(popupsHandler == null){
            popupsHandler = new MsAdsPopupsHandler();
            popupsHandler.init(developerId, activity, fragment, popupDelegate);
        }else{
            popupsHandler.init(developerId, activity, fragment, popupDelegate);
        }

    }

}


