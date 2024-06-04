package com.meritumads.retrofit;

public class ApiUtil {

    public ApiUtil() {
    }

    public static MainXmlService getMainXmlService(){
        return RetrofitClient.getXmlClient().create(MainXmlService.class);
    }

    public static DeviceInfoService getDeviceInfo(){
        return RetrofitClient.getTextPlainClient().create(DeviceInfoService.class);
    }

    public static StatsService sendStats(){
        return RetrofitClient.getTextPlainClient().create(StatsService.class);
    }

}
