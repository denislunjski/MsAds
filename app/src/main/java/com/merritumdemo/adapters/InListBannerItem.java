package com.merritumdemo.adapters;

public class InListBannerItem extends Item{


    String developerId = "";

    public InListBannerItem(String developerId) {
        this.developerId = developerId;
    }

    @Override
    public int getTypeItem() {
        return Item.IN_LIST_BANNER;
    }

    public String getDeveloperId() {
        return developerId;
    }
}
