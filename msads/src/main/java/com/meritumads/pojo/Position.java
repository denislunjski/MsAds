package com.meritumads.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Position {

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
    private int closeDelay = 0;

    @Attribute(name = "rotation_delay", required = false)
    private String rotationDelay = "";

    @Attribute(name = "popup_title", required = false)
    private String popupTitle = "";

    @Attribute(name = "popup_message", required = false)
    private String popupMessage = "";

    @Attribute(name = "popup_background_color", required = false)
    private String popupBackgroundColor = "";

    @Attribute(name = "popup_delay", required = false)
    private String popupDelay = "";

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
    private ArrayList<Banner> banners = new ArrayList<Banner>();

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

    public int getCloseDelay() {
        return closeDelay;
    }

    public String getPopupBackgroundColor() {
        return popupBackgroundColor;
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

    public String getRotationDelay() {
        return rotationDelay;
    }

    public String getPopupTitle() {
        return popupTitle;
    }

    public String getPopupMessage() {
        return popupMessage;
    }

    public String getPopupDelay() {
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

    public ArrayList<Banner> getBanners() {
        return banners;
    }
}
