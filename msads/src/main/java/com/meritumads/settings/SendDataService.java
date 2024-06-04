package com.meritumads.settings;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.meritumads.retrofit.ApiUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendDataService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sendData();
    }

    private void sendData() {

        if (MsAdsSdk.getInstance().getUserData().length() > 0) {

            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody requestBody = RequestBody.create(mediaType, MsAdsSdk.getInstance().getUserData());
            ApiUtil.sendStats().sendStats(requestBody).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Log.i("string_response", response.body().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MsAdsSdk.getInstance().setUserData("");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    throwable.printStackTrace();

                }
            });

        }
    }

}
