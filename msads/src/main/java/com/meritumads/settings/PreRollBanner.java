package com.meritumads.settings;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.meritumads.elements.AdsAdapter;
import com.meritumads.elements.PreRollAdapter;
import com.meritumads.elements.PreRollHolder;
import com.meritumads.pojo.Position;

import java.util.ArrayList;

class PreRollBanner {

    public void init(String bannerId, ViewPager view) {

        ArrayList<Position> positions = MsAdsSdk.getInstance().prerollBanners;
        for(int i = 0; i < positions.size(); i++){
            if(bannerId.equals(positions.get(i).getPositionId())){
                show(positions.get(i), view);
                break;
            }
        }
    }

    private void show(Position position, ViewPager view) {

        if(view == null){
            return;
        }
        if(view instanceof PreRollHolder){
            PreRollAdapter adsAdapter = new PreRollAdapter(position.getBanners());
            view.setAdapter(adsAdapter);
        }
    }
}
