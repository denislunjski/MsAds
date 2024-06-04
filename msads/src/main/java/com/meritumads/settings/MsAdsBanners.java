package com.meritumads.settings;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meritumads.pojo.Position;

import java.util.ArrayList;

public abstract class MsAdsBanners {

    private static  ArrayList<View> addedBanners = new ArrayList<>();

    private static InListBanner inListBanner;


    public static void getInstance(String developerId, ViewGroup layoutHolder){
        runBanners(developerId, layoutHolder, null, null);
    }

    public static void getInstance(String developerId, ViewGroup view, RecyclerView recyclerView){
        runBanners(developerId, view, recyclerView, null);
    }

    public static void getInstance(String developerId, ViewGroup view, ScrollView scrollView){
        runBanners(developerId, view, null, scrollView);
    }

    /**
     * This function returns specific Position, if there is no list of in list banners or
     * no banner with developer id, you can check error message
     * if everything is okay, error response is OK
     * @param developerId
     * @return specific position for developer id
     */
    public static InListPosition getInListBanner(String developerId){
        InListPosition inListBanner = new InListPosition();

        ArrayList<Position> inListBanners = MsAdsSdk.getInstance().inListBanners;
        if(inListBanners !=null && inListBanners.size()>0) {
            for (int i = 0; i < inListBanners.size(); i++) {
                if (developerId.equals(inListBanners.get(i).getDeveloperId())) {
                    inListBanner.position = inListBanners.get(i);
                    inListBanner.error = "OK";
                    break;
                }
            }
        }else{
            inListBanner.error = "There is no active banners in list at the moment, please check all data with admin app";
        }
        if(inListBanner.position == null){
            inListBanner.error = "There is no existing banner with this developer id";
        }
        return inListBanner;
    }

    private static void runBanners(String developerId, ViewGroup view, RecyclerView recyclerView, ScrollView scrollView){
        if(inListBanner == null){
            inListBanner = new InListBanner();
        }

        if(!addedBanners.contains(view)) {
            inListBanner.init(developerId, view, recyclerView, scrollView);
            addedBanners.add(view);
            MsAdsSdk.getInstance().inListBannersAlreadyUsed = true;
        }

        if(recyclerView!=null){
            recyclerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(@NonNull View v) {

                }

                @Override
                public void onViewDetachedFromWindow(@NonNull View v) {
                    if(addedBanners != null && addedBanners.size() > 0){
                        addedBanners.clear();
                    }

                }
            });
        }
        if(scrollView != null){
            scrollView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(@NonNull View v) {

                }

                @Override
                public void onViewDetachedFromWindow(@NonNull View v) {
                    if(addedBanners != null && addedBanners.size() > 0){
                        addedBanners.clear();
                    }
                }
            });
        }
        if(scrollView == null && recyclerView == null){
            if(view!=null){
                view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(@NonNull View v) {

                    }

                    @Override
                    public void onViewDetachedFromWindow(@NonNull View v) {
                        if(addedBanners != null && addedBanners.size() > 0){
                            addedBanners.clear();
                        }
                    }
                });
            }
        }
    }

}
