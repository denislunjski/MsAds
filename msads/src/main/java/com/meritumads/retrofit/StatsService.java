package com.meritumads.retrofit;

import com.google.gson.JsonObject;
import com.meritumads.pojo.MainXml;
import com.meritumads.settings.Constants;

import org.json.JSONStringer;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface StatsService {

    @POST(Constants.statsLink)
    Call<ResponseBody> sendStats(@Body RequestBody object);

}
