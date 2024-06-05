package com.meritumads.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.meritumads.elements.MsAdsDelegate;
import com.meritumads.pojo.Position;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class MsAdsSdk{


    String error = "";
    String appId;
    String token;
    public Context context;
    private static MainService mainService;

    protected ArrayList<Position> inListBanners;

    protected LinkedHashMap<String, Integer> inListBannerIds;
    protected ArrayList<Position> popupBanners;
    protected ArrayList<Position> fullScreenBanners;
    protected ArrayList<Position> prerollBanners;

    LinkedHashMap<String, String> activeFilters;

    protected String deviceState = "";
    protected String deviceCountry = "";

    protected String userData = "";

    int activeDroid = -1;
    int regisStatusDroid = -1;
    int guestStatusDroid = -1;
    int webviewDroid = -1;
    int regisAfterRunDroid = -1;
    int guestAfterRunDroid = -1;

    private int screenWidth = 0;
    private int screenHeight = 0;

    private String arrowBackColor = "#ffffff";
    private String actionBarColor = "#000000";

    boolean inListBannersAlreadyUsed = false;

    OpenApiLinkService openApiLinkService;

    private String userId = "0";

    /**
     * Begin a load of ms ads
     *
     * This is the main class for executing MsAds banners
     * Trough that method you can call two init methods
     * @return main instance
     */
    //ovo je comment za test
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
        mainService.callDeviceInfo(appId, token, null);
        UserDataService userDataService = new UserDataService(context);
        userDataService.addObserver();

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
        mainService.callDeviceInfo(appId, token, msAdsDelegate);
        UserDataService userDataService = new UserDataService(context);
        userDataService.addObserver();
    }

    public String getDeviceState() {
        return deviceState;
    }

    private String getError() {
        return error;
    }

    private int getActiveDroid() {
        return activeDroid;
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
     * retrun list of in list banner ids for developers
     * @return
     */
    public LinkedHashMap<String, Integer> getInListBannersIds() {
        return inListBannerIds;
    }

    /**
     * internal use, required for nice presentation of views and videos
     * @return screenWidth
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    void setupMainSetting(){
        inListBanners = new ArrayList<>();
        inListBannerIds = new LinkedHashMap<>();
        popupBanners = new ArrayList<>();
        fullScreenBanners = new ArrayList<>();
        prerollBanners = new ArrayList<>();
        activeFilters = new LinkedHashMap<>();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) MsAdsSdk.getInstance().context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

    }


    /**
     * returns hashColor of arrow back color used in sdk
     * @return String
     */
    public String getArrowBackColor() {
        return arrowBackColor;
    }

    /*
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

    /*
     * returns hashColor of actionBar color used in sdk
     * @return String
     */
    public String getActionBarColor() {
        return actionBarColor;
    }

    /*
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

    /*
    public LinkedHashMap<String, String> getBannerFilters(String developerId, String bannerName){
        return mainService.bannerFilters(developerId, bannerName);
    }

    /*
     * remove some banner on some position by filter

    public String removeBannerByFilter(String developerId, String bannerName){
        String temp = "";
        if(!inListBannersAlreadyUsed) {
            temp = mainService.removeBanner(developerId, bannerName);
        }{
            temp = "in list postitions are already called. Cannot delete items after showing banners. Please use filters before showing content to user";
        }
        return temp;
    }
    */


    /*
     * set service for api links
     * @param openApiLinkService
     */

    public void setApiLinkService(OpenApiLinkService openApiLinkService) {
        this.openApiLinkService = openApiLinkService;
    }


    /**
     * internal use for sdk
     * @return
     */
    public OpenApiLinkService getApiLinkService(){
        return mainService.getApiLinkService();
    };

    public void setActiveFilter(String key, String value){
        if(activeFilters!=null){
            activeFilters.put(key + "-" + Util.randomString(), value);
        }
    }

    public void setListOfActiveFilters(@NonNull LinkedHashMap<String, String> listOfFilters){
        if(activeFilters!=null){
            if(listOfFilters!=null){
                for(Map.Entry<String, String> entry: listOfFilters.entrySet()){
                    activeFilters.put(entry.getKey() + "-" + Util.randomString(), entry.getValue());
                }
            }
        }
    }

    public LinkedHashMap<String, String> getListOfActiveFilters(){
        return mainService.getListOfFilters();
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
