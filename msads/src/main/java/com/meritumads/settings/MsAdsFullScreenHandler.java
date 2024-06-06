package com.meritumads.settings;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.meritumads.elements.MsAdsFullScreenPopup;
import com.meritumads.elements.MsAdsPopupDelegate;
import com.meritumads.pojo.MsAdsPosition;

import java.util.ArrayList;

public class MsAdsFullScreenHandler {

    public void init(String developerId, Activity activity, Fragment fragment, MsAdsPopupDelegate popupDelegate){
        ArrayList<MsAdsPosition> popupBanners = MsAdsSdk.getInstance().fullScreenBanners;
        if(popupBanners!=null && popupBanners.size()>0) {
            for (int i = 0; i < popupBanners.size(); i++) {
                if(popupBanners.get(i).getDeveloperId().equals(developerId)) {
                    MsAdsFullScreenPopup bannerPopup = new MsAdsFullScreenPopup(popupBanners.get(i), popupDelegate, activity, fragment);
                    bannerPopup.showDialog();
                }
            }
        }else{
            return;
        }
    }

}
