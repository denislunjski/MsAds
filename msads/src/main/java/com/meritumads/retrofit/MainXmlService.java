package com.meritumads.retrofit;

import com.meritumads.pojo.MainXml;
import com.meritumads.settings.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface MainXmlService {

    @GET
    Call<MainXml> getMainXml(@Url String mainUrl);

}
