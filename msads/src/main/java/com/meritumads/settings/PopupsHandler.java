package com.meritumads.settings;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.meritumads.elements.BannerPopup;
import com.meritumads.elements.PopupDelegate;
import com.meritumads.pojo.Position;

import java.util.ArrayList;

class PopupsHandler implements PopupDelegate {

    ArrayList<BannerPopup> listOfPopups;

    public PopupsHandler() {
    }

    public void init(Activity activity, Fragment fragment){
        listOfPopups = new ArrayList<>();
        ArrayList<Position> popupBanners = MsAdsSdk.getInstance().popupBanners;
        for(int i = 0; i < popupBanners.size(); i++){
            BannerPopup bannerPopup = new BannerPopup(popupBanners.get(i), this, activity, fragment);
            listOfPopups.add(bannerPopup);
        }
        showPopups();
    }

    void showPopups() {

        if(listOfPopups.size()>0){
            if(listOfPopups.get(0) instanceof BannerPopup){
                ((BannerPopup)listOfPopups.get(0)).showDialog();
            }
        }

    }

    @Override
    public void popupDelegate(String popupId) {
        if(listOfPopups.size()>0){
            listOfPopups.remove(0);
        }
        showPopups();
    }
}
