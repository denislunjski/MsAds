package com.meritumads.settings;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;

import com.meritumads.elements.WebViewSponsorActivity;
import com.meritumads.pojo.Position;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Util {


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

    public static boolean isTimeEnabled(Context context, Position campaignPosition) {
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

    private static boolean checkDaysInMonth(Calendar calendar, Position campaignPosition) {
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

    private static boolean checkDaysInWeek(Calendar calendar, Position campaignPosition) {
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

    private static boolean checkHoursInDay(Calendar calendar, Position campaignPosition) {
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

    public static void openWebView(String url, int webview){

        String result = "";
        try {
            result = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            result = url;
        }
        if(webview == 1){
            Intent i = new Intent(MsAdsSdk.getInstance().context, WebViewSponsorActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("main_link", result);
            MsAdsSdk.getInstance().context.startActivity(i);
        }else{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MsAdsSdk.getInstance().context.startActivity(browserIntent);
        }


    }
}
