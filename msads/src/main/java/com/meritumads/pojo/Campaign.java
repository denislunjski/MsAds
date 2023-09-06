package com.meritumads.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

public class Campaign {

    @Attribute(name = "countries", required = false)
    private String  countries = "";

    @Attribute(name = "campaign_id", required = false)
    private String campaignId = "";

    @Attribute(name = "campaign_name", required = false)
    private String campaignName = "";

    @Attribute(name = "url_target", required = false)
    private String urlTarget = "";

    @Attribute(name = "app_id", required = false)
    private String appId = "";

    @Attribute(name = "status", required = false)
    private String status = "";

    @Attribute(name = "preroll_between_count", required = false)
    private String  prerollBetweenCount= "";

    @Attribute(name = "max_impressions", required = false)
    private String maxImpressions = "";

    @Attribute(name = "max_clicks", required = false)
    private String maxClicks = "";

    @Attribute(name = "time_start", required = false)
    private String timeStart = "";

    @Attribute(name = "time_end", required = false)
    private String timeEnd = "";

    @ElementList(name = "position", required = false, inline = true)
    private ArrayList<Position> positions = new ArrayList<>();

    public String getCountries() {
        return countries;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public String getUrlTarget() {
        return urlTarget;
    }

    public String getAppId() {
        return appId;
    }

    public String getStatus() {
        return status;
    }

    public String getPrerollBetweenCount() {
        return prerollBetweenCount;
    }

    public String getMaxImpressions() {
        return maxImpressions;
    }

    public String getMaxClicks() {
        return maxClicks;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }
}
