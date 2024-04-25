package com.meritumads.pojo;

import org.simpleframework.xml.Attribute;

import java.util.LinkedHashMap;

public class Banner {

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
    private String urlSource = "";

    @Attribute(name = "media_type", required = false)
    private String mediaType = "";

    @Attribute(name = "media_ts", required = false)
    private String uuidTs = "";

    @Attribute(name = "width", required = false)
    private int width = 0;

    @Attribute(name = "height", required = false)
    private int height = 0;

    @Attribute(name = "duration", required = false)
    private String duration = "";

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

    @Attribute(name = "close_btn", required = false)
    private String closeBtn = "";

    @Attribute(name = "ratio", required = false)
    private String ratio = "";

    @Attribute(name = "button_number", required = false)
    private String buttonNumber = "";

    private LinkedHashMap<String, String> listOfFilters = new LinkedHashMap<>();

    public LinkedHashMap<String, String> getListOfFilters() {
        return listOfFilters;
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

    public String getNextTimeBtn() {
        return nextTimeBtn;
    }

    public String getCloseBtn() {
        return closeBtn;
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

    public String getUrlSource() {
        return urlSource;
    }

    public void setUrlSource(String urlSource) {
        this.urlSource = urlSource;
    }

    public String getUuidTs() {
        return uuidTs;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getDuration() {
        return duration;
    }

    public String getRatio() {
        return ratio;
    }
}
