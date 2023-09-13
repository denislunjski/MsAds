package com.meritumads.settings;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.meritumads.elements.BannerPopup;
import com.meritumads.elements.FullScreenPopup;
import com.meritumads.elements.PopupDelegate;
import com.meritumads.pojo.Position;

import java.util.ArrayList;

public class FullScreenHandler implements PopupDelegate {

    private ArrayList<FullScreenPopup> listOfFullScreens;

    public void init(Activity activity, Fragment fragment){
        listOfFullScreens = new ArrayList<>();
        ArrayList<Position> popupBanners = MsAdsSdk.getInstance().fullScreenBanners;
        for(int i = 0; i < popupBanners.size(); i++){
            FullScreenPopup bannerPopup = new FullScreenPopup(popupBanners.get(i), this, activity, fragment);
            listOfFullScreens.add(bannerPopup);
        }
        show();
    }

    void show() {

        if(listOfFullScreens.size()>0){
            if(listOfFullScreens.get(0) instanceof FullScreenPopup){
                ((FullScreenPopup)listOfFullScreens.get(0)).showDialog();
            }
        }

    }

    @Override
    public void popupDelegate(String popupId) {
        if(listOfFullScreens.size()>0){
            listOfFullScreens.remove(0);
        }
        show();
    }
}
