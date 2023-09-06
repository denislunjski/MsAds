package com.meritumads.retrofit;

import com.meritumads.pojo.MainXml;
import com.meritumads.settings.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MainXmlService {

    @POST(Constants.mainXml)
    @FormUrlEncoded
    Call<MainXml> getMainXml(@Field("client_app_id") String appId, @Field("token") String token);

}
