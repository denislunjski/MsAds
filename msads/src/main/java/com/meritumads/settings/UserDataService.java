package com.meritumads.settings;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.gson.JsonObject;
import com.meritumads.retrofit.ApiUtil;
import com.meritumads.retrofit.StatsResponse;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDataService extends Application implements DefaultLifecycleObserver {

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver((LifecycleObserver) this);
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStop(owner);
        Intent intent = new Intent(getApplicationContext(), SendDataService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //getApplicationContext().startForegroundService(intent);
        } else {
            getApplicationContext().startService(intent);
        }
    }

}
