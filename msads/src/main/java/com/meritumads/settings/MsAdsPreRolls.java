package com.meritumads.settings;

import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.recyclerview.widget.RecyclerView;

import com.meritumads.elements.PreRollHolder;

public abstract class MsAdsPreRolls {

    private static PreRollBanner preRollBanner;

    public static void getInstance(String developerId, PreRollHolder layoutHolder){
        runBanners(developerId, layoutHolder, null, null, null);
    }
    public static void getInstance(String developerId, PreRollHolder layoutHolder, PreRollService preRollService){
        runBanners(developerId, layoutHolder, preRollService, null, null);
    }

    public static void getInstance(String developerId, PreRollHolder layoutHolder, RecyclerView recyclerView){
        runBanners(developerId, layoutHolder, null, recyclerView, null);
    }

    public static void getInstance(String developerId, PreRollHolder layoutHolder, ScrollView scrollView){
        runBanners(developerId, layoutHolder, null, null, scrollView);
    }

    public static void getInstance(String developerId, PreRollHolder layoutHolder, RecyclerView recyclerView, PreRollService preRollService){
        runBanners(developerId, layoutHolder, preRollService, recyclerView, null);
    }

    public static void getInstance(String developerId, PreRollHolder layoutHolder, ScrollView scrollView, PreRollService preRollService){
        runBanners(developerId, layoutHolder, preRollService,null, scrollView);
    }

    private static void runBanners(String bannerId, PreRollHolder view, PreRollService preRollService, RecyclerView recyclerView, ScrollView scrollView){
            preRollBanner = new PreRollBanner();
            preRollBanner.init(bannerId, view, preRollService, recyclerView, scrollView);
    }

}
