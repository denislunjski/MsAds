package com.meritumads.retrofit;

import com.meritumads.settings.MsAdsConstants;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MsAdsStatsService {

    @POST(MsAdsConstants.statsLink)
    Call<ResponseBody> sendStats(@Body RequestBody object);

}
