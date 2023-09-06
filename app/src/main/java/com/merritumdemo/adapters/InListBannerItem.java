package com.merritumdemo.adapters;

public class InListBannerItem extends Item{


    String bannerId = "";

    public InListBannerItem(String bannerId) {
        this.bannerId = bannerId;
    }

    @Override
    public int getTypeItem() {
        return Item.IN_LIST_BANNER;
    }

    public String getBannerId() {
        return bannerId;
    }
}
