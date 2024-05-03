package com.meritumads.settings;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.meritumads.elements.PopupDelegate;

public abstract class MsAdsPopups {

    private static PopupsHandler popupsHandler;

    public static void getInstance(String developerId, Activity activity){
        runPopups(developerId, activity, null, null);
    }

    public static void getInstance(String developerId, Fragment fragment){
        runPopups(developerId,null, fragment, null);
    }

    public static void getInstance(String developerId, Activity activity, PopupDelegate popupDelegate){
        runPopups(developerId, activity, null, popupDelegate);
    }

    public static void getInstance(String developerId, Fragment fragment, PopupDelegate popupDelegate){
        runPopups(developerId, null, fragment, popupDelegate);
    }

    private static void runPopups(String developerId, Activity activity, Fragment fragment, PopupDelegate popupDelegate){

        if(popupsHandler == null){
            popupsHandler = new PopupsHandler();
            popupsHandler.init(developerId, activity, fragment, popupDelegate);
        }else{
            popupsHandler.init(developerId, activity, fragment, popupDelegate);
        }

    }

}


