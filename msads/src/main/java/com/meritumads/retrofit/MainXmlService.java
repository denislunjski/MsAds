package com.meritumads.retrofit;

import com.meritumads.pojo.MsAdsMainXml;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MainXmlService {

    @GET
    Call<MsAdsMainXml> getMainXml(@Url String mainUrl);

}
