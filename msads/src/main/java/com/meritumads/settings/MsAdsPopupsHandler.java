package com.meritumads.settings;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.meritumads.elements.MsAdsBannerPopup;
import com.meritumads.elements.MsAdsPopupDelegate;
import com.meritumads.pojo.MsAdsPosition;

import java.util.ArrayList;

class MsAdsPopupsHandler {


    public MsAdsPopupsHandler() {
    }

    public void init(String developerId, Activity activity, Fragment fragment, MsAdsPopupDelegate popupDelegate){
        ArrayList<MsAdsPosition> popupBanners = MsAdsSdk.getInstance().popupBanners;
        if(popupBanners!=null && popupBanners.size() > 0){
            for(int i = 0; i < popupBanners.size(); i++) {
                if(popupBanners.get(i).getDeveloperId().equals(developerId)) {
                    MsAdsBannerPopup bannerPopup = new MsAdsBannerPopup(popupBanners.get(i), popupDelegate, activity, fragment);
                    bannerPopup.showDialog();
                }
            }
        }else{
            return;
        }
    }
}
