package com.meritumads.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Root(name = "campaign", strict = false)
public class MsAdsCampaign {

    @Attribute(name = "max_custom_events", required = false)
    private String maxCustomEvents = "";

    @Attribute(name = "campaign_id", required = false)
    private String campaignId = "";

    @Attribute(name = "campaign_name", required = false)
    private String campaignName = "";

    @Attribute(name = "max_impressions", required = false)
    private String maxImpressions = "";

    @Attribute(name = "max_clicks", required = false)
    private String maxClicks = "";

    @Attribute(name = "time_start", required = false)
    private String timeStart = "";

    @Attribute(name = "time_end", required = false)
    private String timeEnd = "";

    @ElementList(name = "position", required = false, inline = true)
    private ArrayList<MsAdsPosition> positions = new ArrayList<>();

    private LinkedHashMap<String, String> listOfEvents = new LinkedHashMap<>();

    public LinkedHashMap<String, String> getListOfEvents() {
        return listOfEvents;
    }

    public String getMaxCustomEvents() {
        return maxCustomEvents;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public String getCampaignName() {
        return campaignName;
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

    public ArrayList<MsAdsPosition> getPositions() {
        return positions;
    }
}
