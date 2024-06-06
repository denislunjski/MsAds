package com.meritumads.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "position", strict = false)
public class MsAdsPosition {

    @Attribute(name = "position_id", required = false)
    private String positionId = "";

    @Attribute(name = "developer_id", required = false)
    private String developerId = "";

    @Attribute(name = "position_type", required = false)
    private String positionType = "";

    @Attribute(name = "in_list_position", required = false)
    private int inListPosition = 0;

    @Attribute(name = "media_position", required = false)
    private String videoPosition = "";

    @Attribute(name = "replay_mode", required = false)
    private String replayMode = "";

    @Attribute(name = "close_delay", required = false)
    private float closeDelay = 0f;

    @Attribute(name = "rotation_delay", required = false)
    private float rotationDelay = 0f;

    @Attribute(name = "popup_width_perc", required = false)
    private float popupWidthPerc = 99f;

    @Attribute(name = "popup_height_perc", required = false)
    private float popupHeightPerc = 99f;

    @Attribute(name = "popup_title", required = false)
    private String popupTitle = "";

    @Attribute(name = "popup_title_color", required = false)
    private String popupTitleColor = "";

    @Attribute(name = "popup_message_color", required = false)
    private String popupMessageColor = "";

    @Attribute(name = "popup_message", required = false)
    private String popupMessage = "";

    @Attribute(name = "popup_background_color", required = false)
    private String popupBackgroundColor = "";

    @Attribute(name = "popup_delay", required = false)
    private float popupDelay = 0f;

    @Attribute(name = "popup_richtext", required = false)
    private String popupRichText = "";

    @Attribute(name = "box_width", required = false)
    private String boxWidth = "";

    @Attribute(name = "box_height", required = false)
    private String boxHeight = "";

    @Attribute(name = "webview", required = false)
    private String webview = "";

    @Attribute(name = "after_preroll_url", required = false)
    private String afterPrerollUrl = "";

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
    private float boxRatio = 0f;

    @Attribute(name = "states", required = false)
    private String states = "";

    @ElementList(name = "banner", required = false, inline = true)
    private ArrayList<MsAdsBanner> banners = new ArrayList<MsAdsBanner>();

    public String getPopupTitleColor() {
        return popupTitleColor;
    }

    public String getPopupMessageColor() {
        return popupMessageColor;
    }

    public float getPopupWidthPerc() {
        return popupWidthPerc;
    }

    public float getPopupHeightPerc() {
        return popupHeightPerc;
    }

    public String getBoxWidth() {
        return boxWidth;
    }

    public String getBoxHeight() {
        return boxHeight;
    }

    public String getAfterPrerollUrl() {
        return afterPrerollUrl;
    }

    public String getStates() {
        return states;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public float getCloseDelay() {
        return closeDelay;
    }

    public String getPopupBackgroundColor() {
        return popupBackgroundColor;
    }

    public void setPopupBackgroundColor(String popupBackgroundColor) {
        this.popupBackgroundColor = popupBackgroundColor;
    }

    public String getPopupRichText() {
        return popupRichText;
    }

    public String getWebview() {
        return webview;
    }


    public String getPositionId() {
        return positionId;
    }

    public String getPositionType() {
        return positionType;
    }

    public int getInListPosition() {
        return inListPosition;
    }


    public String getVideoPosition() {
        return videoPosition;
    }

    public String getReplayMode() {
        return replayMode;
    }

    public float getRotationDelay() {
        return rotationDelay;
    }

    public String getPopupTitle() {
        return popupTitle;
    }

    public void setPopupTitleColor(String popupTitleColor) {
        this.popupTitleColor = popupTitleColor;
    }

    public void setPopupMessageColor(String popupMessageColor) {
        this.popupMessageColor = popupMessageColor;
    }

    public String getPopupMessage() {
        return popupMessage;
    }

    public float getPopupDelay() {
        return popupDelay;
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

    public float getBoxRatio() {
        return boxRatio;
    }

    public ArrayList<MsAdsBanner> getBanners() {
        return banners;
    }
}
