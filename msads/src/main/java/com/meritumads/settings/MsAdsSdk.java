package com.meritumads.settings;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.activity.result.ActivityResultLauncher;

import com.meritumads.pojo.MainXml;
import com.meritumads.pojo.Position;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class MsAdsSdk{


    String error = "";
    String appId;
    String token;
    Context context;
    private static MainService mainService;

    private ArrayList<Position> inListBanners;
    private LinkedHashMap<String, String> inListBannersIds;
    private ArrayList<Position> popupBanners;
    private ArrayList<Position> fullScreenBanners;
    private ArrayList<Position> prerollBanners;

    int activeDroid = -1;
    String appName = "";
    int regisStatusDroid = -1;
    int guestStatusDroid = -1;
    int webviewDroid = -1;
    int regisAfterRunDroid = -1;
    int guestAfterRunDroid = -1;

    private int screenWidth = 0;

    private String arrowBackColor = "#ffffff";
    private String actionBarColor = "#000000";

    public static MsAdsSdk getInstance() {
        if(mainService == null){
            mainService = new MainService();
        }
        return mainService;
    }

    public void init(Context context, String appId, String token) {
        this.context = context;
        this.appId = appId;
        this.token = token;
        setupMainSetting();
        mainService.downloadData(appId, token, null);
    }

    public void init(Context context, String appId, String token, MsAdsDelegate msAdsDelegate) {
        this.context = context;
        this.appId = appId;
        this.token = token;
        setupMainSetting();
        mainService.downloadData(appId, token, msAdsDelegate);
    }

    private String getError() {
        return error;
    }

    private int getActiveDroid() {
        return activeDroid;
    }

    public String getAppName() {
        return appName;
    }

    private int getRegisStatusDroid() {
        return regisStatusDroid;
    }

    private int getGuestStatusDroid() {
        return guestStatusDroid;
    }

    public int getWebviewDroid() {
        return webviewDroid;
    }

    private int getRegisAfterRunDroid() {
        return regisAfterRunDroid;
    }

    private int getGuestAfterRunDroid() {
        return guestAfterRunDroid;
    }

    public LinkedHashMap<String, String> getInListBannersIds() {
        return inListBannersIds;
    }

    public ArrayList<Position> getPopupBanners() {
        return popupBanners;
    }

    public ArrayList<Position> getFullScreenBanners() {
        return fullScreenBanners;
    }

    public ArrayList<Position> getPrerollBanners() {
        return prerollBanners;
    }

    public ArrayList<Position> getInListBanners() {
        return inListBanners;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    void setupMainSetting(){
        inListBanners = new ArrayList<>();
        inListBannersIds = new LinkedHashMap<>();
        popupBanners = new ArrayList<>();
        fullScreenBanners = new ArrayList<>();
        prerollBanners = new ArrayList<>();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) MsAdsSdk.getInstance().context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
    }

    public String getArrowBackColor() {
        return arrowBackColor;
    }

    //color of back arrow in screens
    public void setArrowBackColor(String arrowBackColor) {
        if(arrowBackColor.contains("#")) {
            this.arrowBackColor = arrowBackColor;
        }
    }

    public String getActionBarColor() {
        return actionBarColor;
    }

    public void setActionBarColor(String actionBarColor) {
        if(actionBarColor.contains("#")) {
            this.actionBarColor = actionBarColor;
        }
    }
}
