package com.meritumads.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

public class Position {

    @Attribute(name = "states", required = false)
    private String states = "";

    @Attribute(name = "position_id", required = false)
    private String positionId = "";

    @Attribute(name = "position_type", required = false)
    private String positionType = "";

    @Attribute(name = "app_page_index", required = false)
    private String appPageIndex = "";

    @Attribute(name = "in_list_position", required = false)
    private int inListPosition = 0;

    @Attribute(name = "video_background_id", required = false)
    private String backgroundId = "";

    @Attribute(name = "video_background_url", required = false)
    private String urlBackground = "";

    @Attribute(name = "video_background_ts", required = false)
    private String urlBackgroundTs = "";

    @Attribute(name = "media_position", required = false)
    private String videoPosition = "";

    @Attribute(name = "replay_mode", required = false)
    private String replayMode = "";

    @Attribute(name = "close_delay", required = false)
    private int closeDelay = 0;

    @Attribute(name = "rotation_delay", required = false)
    private String rotationDelay = "";

    @Attribute(name = "popup_title", required = false)
    private String popupTitle = "";

    @Attribute(name = "popup_message", required = false)
    private String popupMessage = "";

    @Attribute(name = "popup_button_text", required = false)
    private String popupButtonText = "";

    @Attribute(name = "popup_button_colortext", required = false)
    private String popupButtonColortext = "";

    @Attribute(name = "popup_button_colorback", required = false)
    private String popupButtonColorback = "";

    @Attribute(name = "popup_delay", required = false)
    private String popupDelay = "";

    @Attribute(name = "status", required = false)
    private String status = "";

    @Attribute(name = "box_width", required = false)
    private String boxWidth = "";

    @Attribute(name = "box_height", required = false)
    private String boxHeight = "";

    @Attribute(name = "webview", required = false)
    private String webview = "";

    @Attribute(name = "popup_background_color", required = false)
    private String popupBackgroundColor = "";

    @Attribute(name = "popup_richtext", required = false)
    private String popupRichText = "";

    @Attribute(name = "active_period", required = false)
    private String activePeriod = "";

    @Attribute(name = "active_months", required = false)
    private String activeMonths = "";

    @Attribute(name = "active_daysm", required = false)
    private String activeDaysm = "";

    @Attribute(name = "active_daysw", required = false)
    private String activeDaysw = "";

    @Attribute(name = "active_hours", required = false)
    private String activeHours = "";

    @Attribute(name = "box_ratio", required = false)
    private String boxRatio = "";

    @Attribute(name = "popup_button_icon_url", required = false)
    private String popupButtonIconUrl = "";

    @Attribute(name = "popup_button_icon_ts", required = false)
    private String popupButtonIconTs = "";

    @ElementList(name = "banner", required = false, inline = true)
    private ArrayList<Banner> banners = new ArrayList<Banner>();

    public String getPopupBackgroundColor() {
        return popupBackgroundColor;
    }

    public String getPopupButtonIconUrl() {
        return popupButtonIconUrl;
    }

    public String getPopupButtonIconTs() {
        return popupButtonIconTs;
    }

    public String getPopupRichText() {
        return popupRichText;
    }

    public String getUrlBackgroundTs() {
        return urlBackgroundTs;
    }

    public String getWebview() {
        return webview;
    }

    public String getStates() {
        return states;
    }

    public String getPositionId() {
        return positionId;
    }

    public String getPositionType() {
        return positionType;
    }

    public String getAppPageIndex() {
        return appPageIndex;
    }

    public int getInListPosition() {
        return inListPosition;
    }

    public String getBackgroundId() {
        return backgroundId;
    }

    public String getUrlBackground() {
        return urlBackground;
    }

    public String getVideoPosition() {
        return videoPosition;
    }

    public String getReplayMode() {
        return replayMode;
    }

    public int getCloseDelay() {
        return closeDelay;
    }

    public String getRotationDelay() {
        return rotationDelay;
    }

    public String getPopupTitle() {
        return popupTitle;
    }

    public String getPopupMessage() {
        return popupMessage;
    }

    public String getPopupButtonText() {
        return popupButtonText;
    }

    public String getPopupButtonColortext() {
        return popupButtonColortext;
    }

    public String getPopupButtonColorback() {
        return popupButtonColorback;
    }

    public String getPopupDelay() {
        return popupDelay;
    }

    public String getStatus() {
        return status;
    }

    public String getActivePeriod() {
        return activePeriod;
    }

    public String getActiveMonths() {
        return activeMonths;
    }

    public String getActiveDaysm() {
        return activeDaysm;
    }

    public String getActiveDaysw() {
        return activeDaysw;
    }

    public String getActiveHours() {
        return activeHours;
    }

    public String getBoxRatio() {
        return boxRatio;
    }

    public ArrayList<Banner> getBanners() {
        return banners;
    }
}
