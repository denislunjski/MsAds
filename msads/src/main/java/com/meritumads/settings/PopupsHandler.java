package com.meritumads.settings;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.meritumads.elements.BannerPopup;
import com.meritumads.elements.PopupDelegate;
import com.meritumads.pojo.Position;

import java.util.ArrayList;

class PopupsHandler {


    public PopupsHandler() {
    }

    public void init(String developerId, Activity activity, Fragment fragment, PopupDelegate popupDelegate){
        ArrayList<Position> popupBanners = MsAdsSdk.getInstance().popupBanners;
        if(popupBanners!=null && popupBanners.size() > 0){
            for(int i = 0; i < popupBanners.size(); i++) {
                if(popupBanners.get(i).getDeveloperId().equals(developerId)) {
                    BannerPopup bannerPopup = new BannerPopup(popupBanners.get(i), popupDelegate, activity, fragment);
                    bannerPopup.showDialog();
                }
            }
        }else{
            return;
        }
    }
}
