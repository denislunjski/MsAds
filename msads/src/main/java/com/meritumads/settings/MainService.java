package com.meritumads.settings;

import android.util.Log;

import androidx.annotation.NonNull;

import com.meritumads.elements.BannerTypes;
import com.meritumads.elements.MsAdsDelegate;
import com.meritumads.pojo.AdsApplication;
import com.meritumads.pojo.Banner;
import com.meritumads.pojo.Campaign;
import com.meritumads.pojo.DeviceInfo;
import com.meritumads.pojo.MainXml;
import com.meritumads.pojo.Position;
import com.meritumads.retrofit.ApiUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MainService extends MsAdsSdk{

    public void callDeviceInfo(String appId, String token, MsAdsDelegate msAdsDelegate){

        ApiUtil.getDeviceInfo().getData().enqueue(new Callback<DeviceInfo>() {
            @Override
            public void onResponse(Call<DeviceInfo> call, Response<DeviceInfo> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode().equals("0")){
                        deviceCountry = response.body().getData().getCountry();
                        deviceState = response.body().getData().getState();
                        downloadData(appId, token, msAdsDelegate);
                    }else{
                        error = response.body().getCode();
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
            public void onFailure(Call<DeviceInfo> call, Throwable t) {
                t.printStackTrace();
                error = t.toString();
                if(msAdsDelegate!=null){
                    msAdsDelegate.onMsAdsResult("ERROR");
                }
            }
        });
    }

    public void downloadData(String appId, String token, MsAdsDelegate msAdsDelegate){

        ApiUtil.getMainXmlService().getMainXml(Constants.mainXml + appId + Constants.xml+ token).enqueue(new Callback<MainXml>() {
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
                                prepareCampignEvents(body.getAdsApplication().getCampaigns().get(i));
                                if (checkIfCampaignIsActive(body.getAdsApplication().getCampaigns().get(i))) {
                                    if (body.getAdsApplication().getCampaigns().get(i).getPositions() != null && body.getAdsApplication().getCampaigns().get(i).getPositions().size() > 0) {
                                        for (int j = 0; j < body.getAdsApplication().getCampaigns().get(i).getPositions().size(); j++) {
                                            if (Util.isTimeEnabled(context, body.getAdsApplication().getCampaigns().get(i).getPositions().get(j))) {
                                                if (body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getPositionType().equals("1")) {
                                                    if(checkIfCountryStateIsActive(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j))) {
                                                        filterStates(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                        inListBanners.add(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                    }
                                                } else if (body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getPositionType().equals("2")) {
                                                    if(checkIfCountryStateIsActive(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j))) {
                                                        filterStates(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                        prerollBanners.add(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                    }
                                                } else if (body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getPositionType().equals("3")) {
                                                    if(checkIfCountryStateIsActive(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j))) {
                                                        filterStates(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                        handlePopupButtons(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                        popupBanners.add(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                    }
                                                } else if (body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getPositionType().equals("4")) {
                                                    if(checkIfCountryStateIsActive(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j))) {
                                                        filterStates(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
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
                        inListBannerIds.put(inListBanners.get(i).getDeveloperId(), inListBanners.get(i).getInListPosition());
                    }
                }
                if (msAdsDelegate != null) {
                    msAdsDelegate.onMsAdsResult("OK");
                }
            }catch (Exception e){
                e.printStackTrace();
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

    private void handlePopupButtons(Position position) {

        if(position.getBanners()!=null && position.getBanners().size()>0){
            Collections.sort(position.getBanners(), new Comparator<Banner>() {
                @Override
                public int compare(Banner banner, Banner t1) {
                    return banner.getBannerType().compareTo(t1.getBannerType());
                }
            });

            Collections.sort(position.getBanners(), new Comparator<Banner>() {
                @Override
                public int compare(Banner banner, Banner t1) {
                    return banner.getButtonNumber().compareTo(t1.getButtonNumber());
                }
            });
            if(position.getPopupBackgroundColor().equals("")){
                position.setPopupBackgroundColor("#ffffff");
            }
            if(position.getPopupTitleColor().equals("")){
                position.setPopupTitleColor("#ffffff");
            }
            if(position.getPopupMessageColor().equals("")){
                position.setPopupMessageColor("#ffffff");
            }
            for(int i = 0; i < position.getBanners().size(); i++){
                if(position.getBanners().get(i).getBannerType().equals(BannerTypes.popupButton)){
                    if(position.getBanners().get(i).getPopupButtonColortext().equals("")){
                        position.getBanners().get(i).setPopupButtonColortext("#ffffff");
                    }
                    if(position.getBanners().get(i).getPopupButtonColorback().equals("")){
                        position.getBanners().get(i).setPopupButtonColorback("#7F7F7F");
                    }
                    for(int j = 0; j < position.getBanners().size(); j++){
                        if(position.getBanners().get(i).getButtonNumber().equals(position.getBanners().get(j).getButtonNumber())
                                && (position.getBanners().get(j).getBannerType().equals(BannerTypes.popupBtnImage)
                                || position.getBanners().get(j).getBannerType().equals(BannerTypes.popupBtnIconImage))){
                            if(position.getBanners().get(j).getBannerType().equals(BannerTypes.popupBtnImage)){
                                position.getBanners().get(i).setInternalBannerType(1);
                            }else if(position.getBanners().get(j).getBannerType().equals(BannerTypes.popupBtnIconImage)){
                                position.getBanners().get(i).setInternalBannerType(2);
                            }
                            position.getBanners().get(i).setMediaUrl(position.getBanners().get(j).getMediaUrl());
                            position.getBanners().get(i).setMediaTs(position.getBanners().get(j).getMediaTs());
                            position.getBanners().remove(j);
                            j--;
                            i--;
                        }
                    }
                }
            }
        }
    }

    private void prepareCampignEvents(Campaign campaign) {

        String[] splitedEvents = campaign.getMaxCustomEvents().split(";");
        if(splitedEvents!=null && splitedEvents.length > 0){
            for(int i = 0; i < splitedEvents.length; i++){
                String[] splitEvent = splitedEvents[i].split(",");
                if(splitEvent.length == 2){
                    campaign.getListOfEvents().put(splitEvent[0], splitEvent[1]);
                }
            }
        }
    }

    private void setupApplicationParameters(AdsApplication adsApplication) {

        activeDroid = adsApplication.getAndroidActive();
        regisStatusDroid = adsApplication.getRegisStatusDroid();
        guestStatusDroid = adsApplication.getGuestStatusDroid();
        webviewDroid = adsApplication.getWebViewDroid();
        regisAfterRunDroid = adsApplication.getRegisAfterRunDroid();
        guestAfterRunDroid = adsApplication.getGuestAfterRunDroid();

    }

    private boolean checkIfCampaignIsActive(Campaign campaign) {
        boolean temp = false;
        if(Util.checkActiveTime(campaign.getTimeStart(), campaign.getTimeEnd())){
            temp = true;
        }
        return temp;
    }

    private boolean checkIfCountryStateIsActive(Position position) {
        boolean temp = false;
        if(position.getStates().equals("")){
            temp = true;
        }else {
            if (position.getStates().contains(deviceState)) {
                temp = true;
            }
        }
        return temp;
    }

    private void filterStates(Position position) {

        if (position.getBanners().size() > 0) {
            for (int i = 0; i < position.getBanners().size(); i++) {
                if(position.getBanners().get(i).getStates().length()>0) {
                    if (!position.getBanners().get(i).getStates().contains(MsAdsSdk.getInstance().getDeviceState())) {
                        position.getBanners().remove(i);
                        i--;
                    }
                }
            }
        }
    }


    public OpenApiLinkService getApiLinkService(){
        if(openApiLinkService!=null) {
            return openApiLinkService;
        }else{
            OpenApiLinkService openApiLinkService1 = new OpenApiLinkService() {
                @Override
                public String openApiLink(String link) {
                    return link;
                }
            };
            return openApiLinkService1;
        }
    }

    public LinkedHashMap<String, String> getListOfFilters() {
        if(activeFilters!=null){
            return activeFilters;
        }else{
            return new LinkedHashMap<>();
        }
    }
}
