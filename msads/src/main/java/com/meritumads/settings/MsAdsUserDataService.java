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
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStop(owner);
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MsAdsBackupWorker.class).build();
        WorkManager.getInstance (context).enqueue(request);

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(BackupWorker.class).build();
            WorkManager.getInstance (getApplicationContext()).enqueue(request);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getApplicationContext().startForegroundService(intent);
        } else {
            getApplicationContext().startService(intent);
        }

         */
    }

}
