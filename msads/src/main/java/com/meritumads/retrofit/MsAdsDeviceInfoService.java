package com.meritumads.retrofit;

import com.meritumads.pojo.MsAdsDeviceInfo;
import com.meritumads.settings.MsAdsConstants;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MsAdsDeviceInfoService {

    @POST(MsAdsConstants.deviceInfo)
    Call<MsAdsDeviceInfo> getData(@Body RequestBody deviceData);

}
