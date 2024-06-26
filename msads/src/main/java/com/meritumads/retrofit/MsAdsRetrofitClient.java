package com.meritumads.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meritumads.settings.MsAdsConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MsAdsRetrofitClient {

    private static Retrofit retrofit = null;
    private static Retrofit retrofitJson = null;

    private static Retrofit retrofitPlain = null;

    public static Retrofit getGzipXmlClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(MsAdsConstants.timeout, TimeUnit.SECONDS);
        builder.readTimeout(MsAdsConstants.timeout, TimeUnit.SECONDS);
        builder.addNetworkInterceptor((Interceptor.Chain chain) -> {
            Request req = chain.request();
            Headers.Builder headersBuilder = req.headers().newBuilder();
            Response res = chain.proceed(req.newBuilder().headers(headersBuilder.build()).build());
            return res.newBuilder()
                    .header("Content-Encoding", "gzip")
                    .header("Content-Type", "application/xml")
                    .header("Accept", "application/xml")
                    .header("Accept-Encoding", "gzip")
                    .build();
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(MsAdsConstants.mainLink)
                .client(builder.build())
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .build();
        return retrofit;
    }

    public static Retrofit getXmlClient() {


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(MsAdsConstants.timeout, TimeUnit.SECONDS);
        builder.readTimeout(MsAdsConstants.timeout, TimeUnit.SECONDS);
        builder.addNetworkInterceptor((Interceptor.Chain chain) -> {
            Request req = chain.request();
            Headers.Builder headersBuilder = req.headers().newBuilder();
            Response res = chain.proceed(req.newBuilder().headers(headersBuilder.build()).build());
            return res.newBuilder()
                    .header("Content-Type", "application/xml")
                    .build();
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(MsAdsConstants.mainLink)
                .client(builder.build())
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .build();
        return retrofit;
    }

    public static Retrofit getJsonClient(){
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setLenient()
                .create();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(MsAdsConstants.timeout, TimeUnit.SECONDS);
        builder.readTimeout(MsAdsConstants.timeout, TimeUnit.SECONDS);
        builder.addNetworkInterceptor((Interceptor.Chain chain) -> {
            Request req = chain.request();
            Headers.Builder headersBuilder = req.headers().newBuilder();
            Response res = chain.proceed(req.newBuilder().headers(headersBuilder.build()).build());
            return res.newBuilder().build();
        });
        if (retrofitJson==null) {
            retrofitJson = new Retrofit.Builder()
                    .baseUrl(MsAdsConstants.mainLink)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(builder.build())
                    .build();
        }
        return retrofitJson;
    }

    public static Retrofit getTextPlainClient(){
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setLenient()
                .create();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(MsAdsConstants.timeout, TimeUnit.SECONDS);
        builder.readTimeout(MsAdsConstants.timeout, TimeUnit.SECONDS);
        builder.addNetworkInterceptor((Interceptor.Chain chain) -> {
            Request req = chain.request();
            Headers.Builder headersBuilder = req.headers().newBuilder();
            Response res = chain.proceed(req.newBuilder().headers(headersBuilder.build()).build());
            return res.newBuilder()
                    .header("Content-Type", "text/plain")
                    .build();
        });
        if (retrofitPlain==null) {
            retrofitPlain = new Retrofit.Builder()
                    .baseUrl(MsAdsConstants.mainLink)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(builder.build())
                    .build();
        }
        return retrofitPlain;
    }


}
