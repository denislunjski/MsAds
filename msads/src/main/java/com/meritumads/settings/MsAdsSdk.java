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

    protected ArrayList<Position> inListBanners;
    protected LinkedHashMap<String, String> inListBannersIds;
    protected ArrayList<Position> popupBanners;
    protected ArrayList<Position> fullScreenBanners;
    protected ArrayList<Position> prerollBanners;

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

    /**
     * Begin a load of ms ads
     *
     * This is the main class for executing MsAds banners
     * Trough that method you can call two init methods
     * @return
     */
    public static MsAdsSdk getInstance() {
        if(mainService == null){
            mainService = new MainService();
        }
        return mainService;
    }

    /**
     * Begin of loading ms ads. It works as async task. It can be called in any position in application
     * where you have application context
     *
     * This method starts download of ms ads that are setup in admin environment.
     * @param context - application context
     * @param appId - main app id that is setup in admin
     * @param token - token is connection to specific environment that you will get in admin
     */
    public void init(Context context, String appId, String token) {
        this.context = context;
        this.appId = appId;
        this.token = token;
        setupMainSetting();
        mainService.downloadData(appId, token, null);
    }

    /**
     * Begin of loading ms ads. It works as async task. It can be called in any position in application
     * where you have application context
     *
     * This method starts download of ms ads that are setup in admin environment.
     * @param context - application context
     * @param appId - main app id that is setup in admin
     * @param token  - token is connection to specific environment that you will get in admin
     * @param msAdsDelegate - interface that returns response of downloading data, responses are OK-everything is okay
     *                      or ERROR-specific reason what is the problem.
     */
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

    /**
     * status of opening screen. When user taps on banner it opens sponsor link
     *  1 - opens webView inside application
     *  0 - open mobile webView, outside application
     * @return
     */
    public int getWebviewDroid() {
        return webviewDroid;
    }

    private int getRegisAfterRunDroid() {
        return regisAfterRunDroid;
    }

    private int getGuestAfterRunDroid() {
        return guestAfterRunDroid;
    }

    /**
     * returns list of in list banner ids. It is used to setup banners
     * in recyclerview or scrollview. You can pass specific id of banner that you want to show
     * or you can just iterate all ids to be shown in recyclerView
     * @return
     */
    public LinkedHashMap<String, String> getInListBannersIds() {
        return inListBannersIds;
    }

    /**
     * returns list of popup banners setup in admin
     * @return
     */
    public ArrayList<Position> getPopupBanners() {
        return popupBanners;
    }

    /**
     * returns list of fullScreen banners setup in admin
     * @return
     */
    public ArrayList<Position> getFullScreenBanners() {
        return fullScreenBanners;
    }

    /**
     * returns list of preRoll banners setup in admin
     * @return
     */
    public ArrayList<Position> getPrerollBanners() {
        return prerollBanners;
    }

    /**
     * return list of in list banners setup in admin
     * @return inListBanners
     */
    public ArrayList<Position> getInListBanners() {
        return inListBanners;
    }

    /**
     * internal use, required for nice presentation of views and videos
     * @return screenWidth
     */
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

    /**
     * returns hashColor of arrow back color used in sdk
     * @return String
     */
    public String getArrowBackColor() {
        return arrowBackColor;
    }

    /**
     * Setup of arrow back color used in sdk, example is local webView that contains
     * back arrow in actionbar
     * Color has to be in hash format("#ffffff")
     * @param arrowBackColor -arrow back color
     */
    public void setArrowBackColor(String arrowBackColor) {
        if(arrowBackColor.contains("#")) {
            this.arrowBackColor = arrowBackColor;
        }
    }

    /**
     * returns hashColor of actionBar color used in sdk
     * @return String
     */
    public String getActionBarColor() {
        return actionBarColor;
    }

    /**
     * Setup of action bar color for screens used in sdk, example is local webView
     * that goes to sponsor
     * Color has to be in hash format("#ffffff")
     * @param actionBarColor - action bar color
     */
    public void setActionBarColor(String actionBarColor) {
        if(actionBarColor.contains("#")) {
            this.actionBarColor = actionBarColor;
        }
    }
}
