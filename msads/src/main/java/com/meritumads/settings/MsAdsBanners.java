package com.meritumads.settings;

import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public abstract class MsAdsBanners {

    private static  ArrayList<View> addedBanners = new ArrayList<>();

    private static InListBanner inListBanner;


    public static void getInstance(String bannerId, View layoutHolder){
        runBanners(bannerId, layoutHolder, null, null);
    }

    public static void getInstance(String bannerId, View view, RecyclerView recyclerView){
        runBanners(bannerId, view, recyclerView, null);
    }

    public static void getInstance(String bannerId, View view, ScrollView scrollView){
        runBanners(bannerId, view, null, scrollView);
    }

    private static void runBanners(String bannerId, View view, RecyclerView recyclerView, ScrollView scrollView){
        if(inListBanner == null){
            inListBanner = new InListBanner();
        }

        if(!addedBanners.contains(view)) {
            inListBanner.init(bannerId, view, recyclerView, scrollView);
            addedBanners.add(view);
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
