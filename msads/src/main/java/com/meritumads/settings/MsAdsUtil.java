package com.meritumads.settings;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meritumads.elements.MsAdsWebViewSponsorActivity;
import com.meritumads.pojo.MsAdsPosition;
import com.meritumads.pojo.MsAdsUserData;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class MsAdsUtil {


    public static boolean checkActiveTime(String timeStart, String timeEnd) {
        boolean temp = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            if(timeEnd.equals("")){
                Date startDate = sdf.parse(timeStart);
                Date todayDate = new Date();
                if(todayDate.after(startDate)){
                    temp = true;
                }
            }else{
                Date startDate = sdf.parse(timeStart);
                Date todayDate = new Date();
                Date endDate = sdf.parse(timeEnd);
                if(todayDate.after(startDate) && todayDate.before(endDate)){
                    temp = true;
                }
            }
        }catch (Exception e){}

        return temp;
    }

    public static boolean isTimeEnabled(MsAdsPosition campaignPosition) {
        boolean temp = false;
        Date startDate, endDate, nowDate = null;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            if(campaignPosition.getActivePeriod().equals("")){
                if(campaignPosition.getActiveMonths().equals("*")){
                    temp = checkDaysInMonth(calendar, campaignPosition);
                }else{
                    List<String> activeMonths = Arrays.asList(campaignPosition.getActiveMonths().split(","));
                    if(activeMonths.contains(String.valueOf(calendar.get(Calendar.MONTH)))){
                        temp = checkDaysInMonth(calendar, campaignPosition);
                    }else{
                        temp = false;
                    }
                }
            }else{
                String[] splitTime = campaignPosition.getActivePeriod().split("\\|");
                calendar.setTime(simpleDateFormat.parse(splitTime[0]));
                startDate = calendar.getTime();
                calendar.setTime(simpleDateFormat.parse(splitTime[1]));
                endDate = calendar.getTime();
                nowDate = new Date();
                if(nowDate.after(startDate) && nowDate.before(endDate)){
                    temp = true;
                }else{
                    temp = false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return temp;
    }

    private static boolean checkDaysInMonth(Calendar calendar, MsAdsPosition campaignPosition) {
        boolean temp = false;
        if(campaignPosition.getActiveDaysm().equals("*")){
            temp = checkDaysInWeek(calendar, campaignPosition);
        }else {
            List<String> activeDaysInMonth = Arrays.asList(campaignPosition.getActiveDaysm().split(","));
            if(activeDaysInMonth.contains(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)-1))){
                temp = checkDaysInWeek(calendar, campaignPosition);
            }else{
                temp = false;
            }
        }
        return temp;
    }

    private static boolean checkDaysInWeek(Calendar calendar, MsAdsPosition campaignPosition) {
        boolean temp = false;
        if(campaignPosition.getActiveDaysw().equals("*")){
            temp = checkHoursInDay(calendar, campaignPosition);
        }else{
            List<String> daysInWeek = Arrays.asList(campaignPosition.getActiveDaysw().split(","));
            if(daysInWeek.contains(String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)-1))){
                temp = checkHoursInDay(calendar, campaignPosition);
            }else{
                temp = false;
            }
        }
        return temp;
    }

    private static boolean checkHoursInDay(Calendar calendar, MsAdsPosition campaignPosition) {
        boolean temp = false;
        if(campaignPosition.getActiveHours().equals("*")){
            temp = true;
        }else{
            List<String> hoursInDay = Arrays.asList(campaignPosition.getActiveHours().split(","));
            if(hoursInDay.contains(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)))){
                temp = true;
            }else{
                temp = false;
            }
        }
        return temp;
    }

    public static boolean checkCountry(Context context, String countries) {
        boolean temp = false;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String country = telephonyManager.getNetworkCountryIso();
        if(countries.toLowerCase().contains(telephonyManager.getNetworkCountryIso().toLowerCase())){
            temp = true;
        }
        return temp;
    }

    public static boolean isNetworkConnected(Context context) {

        boolean isNetworkAvailable = false;
        if (context != null) {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connManager.getActiveNetworkInfo();

            if (netInfo != null) {
                if (netInfo.isConnectedOrConnecting()) // I think this is better to write then ===> netInfo.isConnected()
                    isNetworkAvailable = true;
            } else
                isNetworkAvailable = false;
        } else
            isNetworkAvailable = false;
        return isNetworkAvailable;
    }

    public static void openWebView(String url){

        String result = "";
        try {
            result = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            result = url;
        }
        int webView = MsAdsSdk.getInstance().getWebviewDroid();
        if(webView == 1){
            Intent i = new Intent(MsAdsSdk.getInstance().context, MsAdsWebViewSponsorActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("main_link", result);
            MsAdsSdk.getInstance().context.startActivity(i);
        }else{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MsAdsSdk.getInstance().context.startActivity(browserIntent);
        }


    }

    public static Animation animateBtn() {
        return new AlphaAnimation(1.0f, 0.2f);
    }

    public static String randomString(){
        Random r = new Random();
        String[] allowedChar = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n",
                "o","p","r","q","s","t","u","v","x","y","w","z"};
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 4; i++){
            sb.append(allowedChar[r.nextInt(allowedChar.length)]);
        }
        return sb.toString();
    }

    public static void collectUserStats(String mediaId, String action, String userId){

        String temp = MsAdsSdk.getInstance().getUserData();
        Gson gson = new Gson();
        try {
            ArrayList<MsAdsUserData> jsonArray = gson.fromJson(MsAdsSdk.getInstance().getUserData(), new TypeToken<ArrayList<MsAdsUserData>>() {
            }.getType());

            if (jsonArray!=null){
                if(jsonArray.size() == 0){
                    createNewArrayAndAddItem(mediaId, action, userId);
                }else{
                    addDataToExistingArray(mediaId, action, userId, jsonArray);
                }
            }else{
                createNewArrayAndAddItem(mediaId, action, userId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void addDataToExistingArray(String mediaId, String action, String userId, ArrayList<MsAdsUserData> arrayList) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        boolean addNewData = false;
        if(arrayList!=null && arrayList.size()>0){
            for(MsAdsUserData userData: arrayList){
                if(userData.getMedia_id().equals(mediaId) && userData.getAction().equals(action) && userData.getDatetime().equals(time)){
                    userData.setQuantity(userData.getQuantity() + 1);
                    addNewData = false;
                    break;
                }else{
                    addNewData = true;
                }
            }
            if(addNewData){
                MsAdsUserData userDataNew = new MsAdsUserData();
                userDataNew.setMedia_id(mediaId);
                userDataNew.setState(MsAdsSdk.getInstance().getDeviceState());
                userDataNew.setAction(action);
                userDataNew.setUser_id(userId);
                userDataNew.setQuantity(userDataNew.getQuantity() + 1);
                userDataNew.setDatetime(time);

                arrayList.add(userDataNew);
            }
            Gson gson = new Gson();
            String temp = gson.toJson(arrayList);
            MsAdsSdk.getInstance().setUserData(temp);
        }


    }

    private static void createNewArrayAndAddItem(String mediaId, String action, String userId) {

        String hitTime = "";

        MsAdsUserData userData = new MsAdsUserData();
        userData.setMedia_id(mediaId);
        userData.setState(MsAdsSdk.getInstance().getDeviceState());
        userData.setAction(action);
        userData.setUser_id(userId);
        userData.setQuantity(userData.getQuantity() + 1);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = new Date();
        hitTime = simpleDateFormat.format(date);
        userData.setDatetime(hitTime);

        ArrayList<MsAdsUserData> arrayList = new ArrayList<>();
        arrayList.add(userData);

        Gson gson = new Gson();
        String temp = gson.toJson(arrayList);

        MsAdsSdk.getInstance().setUserData(temp);

    }

}
