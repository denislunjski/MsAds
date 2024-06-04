package com.meritumads.retrofit;

import com.meritumads.pojo.DeviceInfo;
import com.meritumads.settings.Constants;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DeviceInfoService {

    @POST(Constants.deviceInfo)
    Call<DeviceInfo> getData(@Body RequestBody deviceData);

}
