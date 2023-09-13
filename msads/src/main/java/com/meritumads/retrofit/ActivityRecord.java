package com.meritumads.retrofit;

import org.simpleframework.xml.Element;

public class ActivityRecord {

    @Element(name = "action_type", required = false)
    private  String actionType = "";

}
