package com.meritumads.settings;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MsAdsUserDataService implements DefaultLifecycleObserver {

    private Context context;
    public MsAdsUserDataService(Context context) {
        this.context = context;
    }

    public void addObserver(){
        ProcessLifecycleOwner.get().getLifecycle().addObserver((LifecycleObserver) this);
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
        MsAdsSdk.getInstance().setAppInBackground(false);
    }


    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStop(owner);
        MsAdsSdk.getInstance().setAppInBackground(true);
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MsAdsBackupWorker.class).build();
        WorkManager.getInstance (context).enqueue(request);
    }
}
