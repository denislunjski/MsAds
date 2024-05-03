package com.meritumads.settings;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.meritumads.elements.FullScreenPopup;
import com.meritumads.elements.PopupDelegate;
import com.meritumads.pojo.Position;

import java.util.ArrayList;

public class FullScreenHandler {

    public void init(String developerId, Activity activity, Fragment fragment, PopupDelegate popupDelegate){
        ArrayList<Position> popupBanners = MsAdsSdk.getInstance().fullScreenBanners;
        if(popupBanners!=null && popupBanners.size()>0) {
            for (int i = 0; i < popupBanners.size(); i++) {
                if(popupBanners.get(i).getDeveloperId().equals(developerId)) {
                    FullScreenPopup bannerPopup = new FullScreenPopup(popupBanners.get(i), popupDelegate, activity, fragment);
                    bannerPopup.showDialog();
                }
            }
        }else{
            return;
        }
    }

}
