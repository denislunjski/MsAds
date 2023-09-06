package com.meritumads.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

public class AdsApplication {

    @ElementList(name = "campaign", required = false, inline = true)
    private ArrayList<Campaign> campaigns = new ArrayList<Campaign>();

    @Attribute(name = "status_droid", required = false)
    private int  androidActive = -1;

    @Attribute(name = "app_name", required = false)
    private String appName = "";

    @Attribute(name = "regis_status_droid", required = false)
    private int regisStatusDroid = -1;

    @Attribute(name = "guest_status_droid", required = false)
    private int guestStatusDroid = -1;

    @Attribute(name = "webview_droid", required = false)
    private int webViewDroid = -1;

    @Attribute(name = "regis_after_run_droid", required = false)
    private int regisAfterRunDroid = -1;

    @Attribute(name = "guest_after_run_droid", required = false)
    private int guestAfterRunDroid = -1;

    public int getAndroidActive() {
        return androidActive;
    }

    public String getAppName() {
        return appName;
    }

    public int getRegisStatusDroid() {
        return regisStatusDroid;
    }

    public int getGuestStatusDroid() {
        return guestStatusDroid;
    }

    public int getWebViewDroid() {
        return webViewDroid;
    }

    public int getRegisAfterRunDroid() {
        return regisAfterRunDroid;
    }

    public int getGuestAfterRunDroid() {
        return guestAfterRunDroid;
    }

    public ArrayList<Campaign> getCampaigns() {
        return campaigns;
    }

}
