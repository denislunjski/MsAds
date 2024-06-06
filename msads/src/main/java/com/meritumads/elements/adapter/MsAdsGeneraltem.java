package com.meritumads.elements.adapter;

import com.meritumads.pojo.MsAdsBanner;
import com.meritumads.pojo.MsAdsPosition;

public class MsAdsGeneraltem extends MsAdsItem {

    MsAdsBanner banner;
    MsAdsPosition position;

    int type = 0;

    public MsAdsGeneraltem(MsAdsBanner banner, int type) {
        this.banner = banner;
        this.type = type;
    }

    public MsAdsGeneraltem(MsAdsPosition position, int type) {
        this.position = position;
        this.type = type;
    }

    public MsAdsPosition getPosition() {
        return position;
    }

    public MsAdsBanner getBanner() {
        return banner;
    }

    public int getType() {
        return type;
    }

    @Override
    public int getTypeItem() {
        return type;
    }
}
