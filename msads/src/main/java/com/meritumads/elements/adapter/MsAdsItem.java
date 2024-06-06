package com.meritumads.elements.adapter;

public abstract class MsAdsItem {

    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_SPONSOR_BTN = 2;
    public static final int TYPE_EXIT_BTN = 3;

    public static final int TYPE_WEBVIEW = 4;

    public static final int TYPE_TEXTS = 5;
    public static final int TYPE_SPONSOR_IMAGE = 6;

    public abstract int getTypeItem();
}
