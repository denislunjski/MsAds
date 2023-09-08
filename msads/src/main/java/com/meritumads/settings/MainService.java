package com.meritumads.settings;

import android.app.Activity;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;

import com.meritumads.pojo.AdsApplication;
import com.meritumads.pojo.Campaign;
import com.meritumads.pojo.MainXml;
import com.meritumads.pojo.Position;
import com.meritumads.retrofit.ApiUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MainService extends MsAdsSdk{


    public void downloadData(String appId, String token, MsAdsDelegate msAdsDelegate){

        ApiUtil.getMainXmlService().getMainXml(appId, token).enqueue(new Callback<MainXml>() {
            @Override
            public void onResponse(Call<MainXml> call, Response<MainXml> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("OK")) {
                        handleResponse(response.body(), msAdsDelegate);
                    }else{
                        error = response.body().getStatus();
                        if(msAdsDelegate!=null){
                            msAdsDelegate.onMsAdsResult(response.body().getTerm());
                        }
                    }
                }else{
                    try {
                        error = response.errorBody().string();
                    } catch (IOException e) {
                        error = e.toString();
                    }
                    if(msAdsDelegate!=null){
                        msAdsDelegate.onMsAdsResult("ERROR");
                    }
                }
            }

            @Override
            public void onFailure(Call<MainXml> call, Throwable t) {
                t.printStackTrace();
                error = t.toString();
                if(msAdsDelegate!=null){
                    msAdsDelegate.onMsAdsResult("ERROR");
                }
            }
        });

    }

    private void handleResponse(MainXml body, MsAdsDelegate msAdsDelegate) {
        if(body!=null){
            try {
                if (body.getAdsApplication() != null) {
                    setupApplicationParameters(body.getAdsApplication());
                    if (body.getAdsApplication().getAndroidActive() == 1) {
                        if (body.getAdsApplication().getCampaigns() != null && body.getAdsApplication().getCampaigns().size() > 0) {
                            for (int i = 0; i < body.getAdsApplication().getCampaigns().size(); i++) {
                                if (checkIfCampaignIsActive(body.getAdsApplication().getCampaigns().get(i))) {
                                    if (body.getAdsApplication().getCampaigns().get(i).getPositions() != null && body.getAdsApplication().getCampaigns().get(i).getPositions().size() > 0) {
                                        for (int j = 0; j < body.getAdsApplication().getCampaigns().get(i).getPositions().size(); j++) {
                                            if (body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getStatus().equals("1")) {
                                                if (Util.isTimeEnabled(context, body.getAdsApplication().getCampaigns().get(i).getPositions().get(j))) {
                                                    for(int z = 0; z < body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getBanners().size(); z++) {
                                                        body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getBanners().get(i)
                                                                .setMainCampaignUrl(body.getAdsApplication().getCampaigns().get(i).getUrlTarget());
                                                    }
                                                    if (body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getPositionType().equals("1")) {
                                                        inListBanners.add(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                    } else if (body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getPositionType().equals("2")) {
                                                        prerollBanners.add(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                    } else if (body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getPositionType().equals("3")) {
                                                        popupBanners.add(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                    } else if (body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getPositionType().equals("4")) {
                                                        fullScreenBanners.add(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if(inListBanners.size()>0){
                    Collections.sort(inListBanners, new Comparator<Position>() {
                        @Override
                        public int compare(Position o1, Position o2) {
                            return o1.getInListPosition() - o2.getInListPosition();
                        }
                    });
                    for(int i = 0; i < inListBanners.size(); i++){
                        inListBannersIds.put(String.valueOf(inListBanners.get(i).getInListPosition()), inListBanners.get(i).getPositionId());
                    }
                }
                if (msAdsDelegate != null) {
                    msAdsDelegate.onMsAdsResult("OK");
                }
            }catch (Exception e){
                if (msAdsDelegate != null) {
                    msAdsDelegate.onMsAdsResult("Error parsing data");
                }
            }
        }else{
            if(msAdsDelegate!=null){
                msAdsDelegate.onMsAdsResult("ERROR");
            }
        }
    }

    private void setupApplicationParameters(AdsApplication adsApplication) {

        activeDroid = adsApplication.getAndroidActive();
        appName = adsApplication.getAppName();
        regisStatusDroid = adsApplication.getRegisStatusDroid();
        guestStatusDroid = adsApplication.getGuestStatusDroid();
        webviewDroid = adsApplication.getWebViewDroid();
        regisAfterRunDroid = adsApplication.getRegisAfterRunDroid();
        guestAfterRunDroid = adsApplication.getGuestAfterRunDroid();

    }

    private boolean checkIfCampaignIsActive(Campaign campaign) {
        boolean temp = false;

        if(campaign.getStatus().equals("1")){;
            //if(Util.checkCountry(context, campaign.getCountries())){
                if(Util.checkActiveTime(campaign.getTimeStart(), campaign.getTimeEnd())){
                    temp = true;
                }
            //}
        }
        return temp;
    }
}
