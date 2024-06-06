package com.meritumads.settings;

import android.os.SystemClock;
import android.view.View;

public abstract class MsAdsSafeClickListener implements View.OnClickListener {

    private int defaultInterval = 500;
    private long mLastClickTime;

    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        v.startAnimation(MsAdsUtil.animateBtn());
        long currentClickTime= SystemClock.uptimeMillis();
        long elapsedTime=currentClickTime-mLastClickTime;
        mLastClickTime=currentClickTime;
        if(elapsedTime<=defaultInterval)
            return;

        onSingleClick(v);
    }

}