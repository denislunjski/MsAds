package com.meritumads.pojo;

import org.simpleframework.xml.Attribute;

public class Banner {

    @Attribute(name = "ordnum", required = false)
    private String ordnum = "";

    @Attribute(name = "media_url", required = false)
    private String urlSource = "";

    @Attribute(name = "media_id", required = false)
    private String bannerId = "";

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

    @Attribute(name = "url_target_android", required = false)
    private String urlTarget = "";

    @Attribute(name = "ratio", required = false)
    private String ratio = "";

    private String mainCampaignUrl = "";

    public String getMainCampaignUrl() {
        return mainCampaignUrl;
    }

    public void setMainCampaignUrl(String mainCampaignUrl) {
        this.mainCampaignUrl = mainCampaignUrl;
    }

    public String getOrdnum() {
        return ordnum;
    }

    public String getUrlTarget() {
        return urlTarget;
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
