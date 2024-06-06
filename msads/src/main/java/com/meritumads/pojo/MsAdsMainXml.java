package com.meritumads.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "meritum", strict = false)
public class MsAdsMainXml {

    @Attribute(name = "status", required = false)
    private String status = "";

    @Attribute(name = "term", required = false)
    private String term = "";

    @Element(name = "application", required = false)
    private MsAdsApplication adsApplication;

    public MsAdsApplication getAdsApplication() {
        return adsApplication;
    }

    public String getStatus() {
        return status;
    }

    public String getTerm() {
        return term;
    }
}
