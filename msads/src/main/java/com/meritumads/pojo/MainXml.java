package com.meritumads.pojo;

import androidx.annotation.ArrayRes;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "meritum", strict = false)
public class MainXml {

    @Attribute(name = "status", required = false)
    private String status = "";

    @Attribute(name = "term", required = false)
    private String term = "";

    @Element(name = "application", required = false)
    private AdsApplication adsApplication;

    public AdsApplication getAdsApplication() {
        return adsApplication;
    }

    public String getStatus() {
        return status;
    }

    public String getTerm() {
        return term;
    }
}
