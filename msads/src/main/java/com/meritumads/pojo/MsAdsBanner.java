package com.meritumads.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "banner", strict = false)
public class MsAdsBanner {

    @Attribute(name = "states", required = false)
    private String states = "";

    @Attribute(name = "filters", required = false)
    private String filters = "";

    @Attribute(name = "banner_name", required = false)
    private String bannerName = "";

    @Attribute(name = "banner_type", required = false)
    private String bannerType = "";

    @Attribute(name = "ordnum", required = false)
    private int ordnum = 0;

    @Attribute(name = "media_id", required = false)
    private String bannerId = "";

    @Attribute(name = "media_url", required = false)
    private String mediaUrl = "";

    @Attribute(name = "media_type", required = false)
    private String mediaType = "";

    @Attribute(name = "media_ts", required = false)
    private String mediaTs = "";

    @Attribute(name = "width", required = false)
    private int width = 0;

    @Attribute(name = "height", required = false)
    private int height = 0;

    @Attribute(name = "duration", required = false)
    private float duration = 0f;

    @Attribute(name = "android_sublink", required = false)
    private String androidSubLink = "";

    @Attribute(name = "android_sublink_api_link", required = false)
    private String apiActiveNonActive = "";

    @Attribute(name = "popup_button_text", required = false)
    private String popupButtonText = "";

    @Attribute(name = "popup_button_colorback", required = false)
    private String popupButtonColorback = "";

    @Attribute(name = "popup_button_colortext", required = false)
    private String popupButtonColortext = "";

    @Attribute(name = "next_time_btn", required = false)
    private String nextTimeBtn = "";

    @Attribute(name = "ratio", required = false)
    private String ratio = "";

    @Attribute(name = "button_number", required = false)
    private String buttonNumber = "";

    ArrayList<String> filtersForStats = new ArrayList<>();

    public ArrayList<String> getFiltersForStats() {
        return filtersForStats;
    }

    public void setFiltersForStats(ArrayList<String> filtersForStats) {
        this.filtersForStats = filtersForStats;
    }

    private int internalBannerType = 0; //1 is for full image, 2 is for button icon

    public int getInternalBannerType() {
        return internalBannerType;
    }

    public void setInternalBannerType(int internalBannerType) {
        this.internalBannerType = internalBannerType;
    }

    public String getBannerName() {
        return bannerName;
    }

    public String getStates() {
        return states;
    }

    public String getFilters() {
        return filters;
    }

    public String getBannerType() {
        return bannerType;
    }

    public String getApiActiveNonActive() {
        return apiActiveNonActive;
    }

    public String getPopupButtonText() {
        return popupButtonText;
    }

    public String getPopupButtonColorback() {
        return popupButtonColorback;
    }

    public String getPopupButtonColortext() {
        return popupButtonColortext;
    }

    public void setPopupButtonColorback(String popupButtonColorback) {
        this.popupButtonColorback = popupButtonColorback;
    }

    public void setPopupButtonColortext(String popupButtonColortext) {
        this.popupButtonColortext = popupButtonColortext;
    }

    public String getNextTimeBtn() {
        return nextTimeBtn;
    }

    public String getButtonNumber() {
        return buttonNumber;
    }

    public int getOrdnum() {
        return ordnum;
    }

    public String getAndroidSubLink() {
        return androidSubLink;
    }

    public String getBannerId() {
        return bannerId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setMediaTs(String mediaTs) {
        this.mediaTs = mediaTs;
    }

    public String getMediaTs() {
        return mediaTs;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getDuration() {
        return duration;
    }

    public String getRatio() {
        return ratio;
    }
}
