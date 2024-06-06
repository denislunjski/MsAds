package com.meritumads.retrofit;

public class MsAdsApiUtil {

    public MsAdsApiUtil() {
    }

    public static MainXmlService getMainXmlService(){
        return MsAdsRetrofitClient.getXmlClient().create(MainXmlService.class);
    }

    public static MsAdsDeviceInfoService getDeviceInfo(){
        return MsAdsRetrofitClient.getTextPlainClient().create(MsAdsDeviceInfoService.class);
    }

    public static MsAdsStatsService sendStats(){
        return MsAdsRetrofitClient.getTextPlainClient().create(MsAdsStatsService.class);
    }

}
