package com.meritumads.retrofit;

import com.meritumads.pojo.DeviceInfo;
import com.meritumads.settings.Constants;

import java.util.ConcurrentModificationException;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DeviceInfoService {

    @GET(Constants.deviceInfo)
    Call<DeviceInfo> getData();

}
