package com.meritumads.elements;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.meritumads.pojo.Banner;
import com.meritumads.settings.MsAdsSdk;
import com.meritumads.settings.PreRollStatus;
import com.meritumads.settings.SafeClickListener;
import com.meritumads.settings.Util;

public class ImageSponsorView extends androidx.appcompat.widget.AppCompatImageView {

    private android.os.Handler handler, handlerSkip;

    public VideoDelegate videoDelegate1;
    private Banner banner;

    private Runnable runnable, runnableSkip;
    private TextView skip;

    private long closeDelay = 0;

    public ImageSponsorView(@NonNull Context context) {
        super(context);
    }

    public ImageSponsorView(@NonNull Context context, Banner banner, VideoDelegate videoDelegate, TextView skip, long closeDelay) {
        super(context);
        this.banner = banner;
        this.videoDelegate1 = videoDelegate;
        this.skip = skip;
        this.closeDelay = closeDelay;
    }


    public void loadImage(){
        Glide.with(getContext())
                .load(banner.getMediaUrl() + "?=" + banner.getMediaTs())
                .signature(new ObjectKey(banner.getMediaTs()))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ImageSponsorView.this);
        ImageSponsorView.this.setOnClickListener(new SafeClickListener() {
            @Override
            public void onSingleClick(View v) {
                Util.collectUserStats(banner.getBannerId(), "click", MsAdsSdk.getInstance().getUserId());
                if(banner.getApiActiveNonActive().equals("1")){
                    String response = MsAdsSdk.getInstance().getApiLinkService().openApiLink(banner.getAndroidSubLink());
                    Util.openWebView(response);
                }else {
                    Util.openWebView(banner.getAndroidSubLink());
                }
            }
        });


    }


    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if(visibility == 0){
            if(banner.getDuration() > 0){
                    handler = new android.os.Handler();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if(videoDelegate1!=null){
                                videoDelegate1.videoDelegate(PreRollStatus.IMAGE_FINISHED, banner.getBannerId());
                            }
                        }
                    };
                    handler.postDelayed(runnable, (long)banner.getDuration()*1000);
                }
            handlerSkip = new android.os.Handler();
            runnableSkip = new Runnable() {
                @Override
                public void run() {
                    skip.setVisibility(View.VISIBLE);
                }
            };
            handlerSkip.postDelayed(runnableSkip, closeDelay * 1000);
            skip.setOnClickListener(new SafeClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if(videoDelegate1!=null){
                        videoDelegate1.videoDelegate(PreRollStatus.IMAGE_FINISHED, banner.getBannerId());
                    }
                }
            });
        }else if(visibility == 8){
            if(handler!=null && runnable!=null){
                handler.removeCallbacks(runnable);
                handler = null;
                runnable = null;
            }
            if(handlerSkip!=null && runnableSkip!=null){
                handlerSkip.removeCallbacks(runnableSkip);
                handlerSkip = null;
                runnableSkip = null;
            }
        }
    }
}
