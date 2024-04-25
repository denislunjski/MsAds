package com.meritumads.settings;

import android.util.Log;

import androidx.annotation.NonNull;

import com.meritumads.elements.MsAdsDelegate;
import com.meritumads.pojo.AdsApplication;
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
                                                        prepareFilters(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                        inListBanners.add(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                    }
                                                } else if (body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getPositionType().equals("2")) {
                                                    if(checkIfCountryStateIsActive(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j))) {
                                                        prepareFilters(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                        prerollBanners.add(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                    }
                                                } else if (body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getPositionType().equals("3")) {
                                                    if(checkIfCountryStateIsActive(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j))) {
                                                        prepareFilters(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                        popupBanners.add(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
                                                    }
                                                } else if (body.getAdsApplication().getCampaigns().get(i).getPositions().get(j).getPositionType().equals("4")) {
                                                    if(checkIfCountryStateIsActive(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j))) {
                                                        prepareFilters(body.getAdsApplication().getCampaigns().get(i).getPositions().get(j));
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

    private void prepareFilters(Position position) {

        if(position.getBanners()!=null && position.getBanners().size()>0){
            for(int i = 0; i < position.getBanners().size(); i++) {
                if(position.getBanners().get(i).getFilters().length() > 0){
                    String[] splitedFilters = position.getBanners().get(i).getFilters().split(";");
                    if(splitedFilters!=null && splitedFilters.length>0){
                        for(int j = 0; j < splitedFilters.length; j++) {
                            String[] splitFilter = splitedFilters[j].split(",");
                            if(splitFilter.length == 2){
                                position.getBanners().get(i).getListOfFilters().put(splitFilter[0], splitFilter[1]);
                            }
                        }
                    }
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
        if(position.getStates().contains(deviceState)){
            temp = true;
        }
        return temp;
    }

    public LinkedHashMap<String, String> bannerFilters(String developerId, String bannerName){
        LinkedHashMap<String, String> temp = new LinkedHashMap<>();
        if(inListBanners.size()>0) {
            for (int i = 0; i < inListBanners.size(); i++) {
                if(inListBanners.get(i).getDeveloperId().equals(developerId)){
                    if(inListBanners.get(i).getBanners()!=null && inListBanners.get(i).getBanners().size()>0){
                        for(int j = 0; j < inListBanners.get(i).getBanners().size(); j++){
                            if(inListBanners.get(i).getBanners().get(j).getBannerName().equals(bannerName)){
                                temp = inListBanners.get(j).getBanners().get(j).getListOfFilters();
                                if(temp == null)
                                    temp = new LinkedHashMap<>();
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(prerollBanners.size()>0) {
            for (int i = 0; i < prerollBanners.size(); i++) {
                if(prerollBanners.get(i).getDeveloperId().equals(developerId)){
                    if(prerollBanners.get(i).getBanners()!=null && prerollBanners.get(i).getBanners().size()>0){
                        for(int j = 0; j < prerollBanners.get(i).getBanners().size(); j++){
                            if(prerollBanners.get(i).getBanners().get(j).getBannerName().equals(bannerName)){
                                temp = prerollBanners.get(j).getBanners().get(j).getListOfFilters();
                                if(temp == null)
                                    temp = new LinkedHashMap<>();
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(popupBanners.size()>0) {
            for (int i = 0; i < popupBanners.size(); i++) {
                if(popupBanners.get(i).getDeveloperId().equals(developerId)){
                    if(popupBanners.get(i).getBanners()!=null && popupBanners.get(i).getBanners().size()>0){
                        for(int j = 0; j < popupBanners.get(i).getBanners().size(); j++){
                            if(popupBanners.get(i).getBanners().get(j).getBannerName().equals(bannerName)){
                                temp = popupBanners.get(j).getBanners().get(j).getListOfFilters();
                                if(temp == null)
                                    temp = new LinkedHashMap<>();
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(fullScreenBanners.size()>0) {
            for (int i = 0; i < fullScreenBanners.size(); i++) {
                if(fullScreenBanners.get(i).getDeveloperId().equals(developerId)){
                    if(fullScreenBanners.get(i).getBanners()!=null && fullScreenBanners.get(i).getBanners().size()>0){
                        for(int j = 0; j < fullScreenBanners.get(i).getBanners().size(); j++){
                            if(fullScreenBanners.get(i).getBanners().get(j).getBannerName().equals(bannerName)){
                                temp = fullScreenBanners.get(j).getBanners().get(j).getListOfFilters();
                                if(temp == null)
                                    temp = new LinkedHashMap<>();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return temp;
    }

    public String removeBanner(String developerId, String bannerName) {
        String temp = "";
        if(inListBanners.size()>0) {
            for (int i = 0; i < inListBanners.size(); i++) {
                if(inListBanners.get(i).getDeveloperId().equals(developerId)){
                    if(inListBanners.get(i).getBanners()!=null && inListBanners.get(i).getBanners().size()>0){
                        for(int j = 0; j < inListBanners.get(i).getBanners().size(); j++){
                            if(inListBanners.get(i).getBanners().get(j).getBannerName().equals(bannerName)){
                                inListBanners.get(i).getBanners().remove(j);
                                temp = "banner removed";
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(prerollBanners.size()>0) {
            for (int i = 0; i < prerollBanners.size(); i++) {
                if(prerollBanners.get(i).getDeveloperId().equals(developerId)){
                    if(prerollBanners.get(i).getBanners()!=null && prerollBanners.get(i).getBanners().size()>0){
                        for(int j = 0; j < prerollBanners.get(i).getBanners().size(); j++){
                            if(prerollBanners.get(i).getBanners().get(j).getBannerName().equals(bannerName)){
                                prerollBanners.get(i).getBanners().remove(j);
                                temp = "banner removed";
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(popupBanners.size()>0) {
            for (int i = 0; i < popupBanners.size(); i++) {
                if(popupBanners.get(i).getDeveloperId().equals(developerId)){
                    if(popupBanners.get(i).getBanners()!=null && popupBanners.get(i).getBanners().size()>0){
                        for(int j = 0; j < popupBanners.get(i).getBanners().size(); j++){
                            if(popupBanners.get(i).getBanners().get(j).getBannerName().equals(bannerName)){
                                popupBanners.get(i).getBanners().remove(j);
                                temp = "banner removed";
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(fullScreenBanners.size()>0) {
            for (int i = 0; i < fullScreenBanners.size(); i++) {
                if(fullScreenBanners.get(i).getDeveloperId().equals(developerId)){
                    if(fullScreenBanners.get(i).getBanners()!=null && fullScreenBanners.get(i).getBanners().size()>0){
                        for(int j = 0; j < fullScreenBanners.get(i).getBanners().size(); j++){
                            if(fullScreenBanners.get(i).getBanners().get(j).getBannerName().equals(bannerName)){
                                fullScreenBanners.get(i).getBanners().remove(j);
                                temp = "banner removed";
                                break;
                            }
                        }
                    }
                }
            }
        }
        return temp;
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

}
