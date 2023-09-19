package com.meritumads.settings;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.meritumads.elements.AdsAdapter;
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
            AdsAdapter adsAdapter = new AdsAdapter(position.getBanners(), view,
                    position.getRotationDelay(), null, null, "0");
            view.setAdapter(adsAdapter);
            if(position.getBanners().size()>1)


            view.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(position == adsAdapter.getCount()-1) {
                        Log.i("item_visibility", "visible");
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }
}
