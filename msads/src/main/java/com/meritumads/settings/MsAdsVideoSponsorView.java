package com.meritumads.settings;

import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meritumads.elements.MsAdsFullScreenPopup;
import com.meritumads.elements.MsAdsVideoDelegate;
import com.meritumads.pojo.MsAdsBanner;

public class MsAdsVideoSponsorView extends VideoView {

    private Rect mCurrentViewRect = new Rect();
    int stopPosition = 0;
    private final String HIT_TYPE_CLICK = "4";

    private boolean videoStarted = false;

    private int pos;

    private boolean isVisibleToUser = false;


    public MsAdsVideoSponsorView(Context context) {
        super(context);
    }

    public MsAdsVideoSponsorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsAdsVideoSponsorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MsAdsVideoSponsorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void loadVideo(Context context, MsAdsBanner banner, RecyclerView recyclerView, ScrollView scrollView, MsAdsVideoDelegate videoDelegate,
                          String replayMode, MsAdsFullScreenPopup msAdsFullScreenPopup, String developerId, int pos) {

        this.pos = pos;
        if (!banner.getMediaUrl().contains("http")) {
            banner.setMediaUrl("http://" + banner.getMediaUrl());
        }
        if (MsAdsUtil.isNetworkConnected(context)) {
            try {
                final MediaController mediaController = new MediaController(context);
                final Uri video = Uri.parse(banner.getMediaUrl());
                this.setVideoURI(video);
                mediaController.setVisibility(View.GONE);
                int screenWidth = MsAdsSdk.getInstance().getScreenWidth();
                float ratio = Math.min((float) screenWidth / banner.getWidth(), (float) screenWidth / banner.getHeight());
                int height = Math.round((float) ratio * banner.getHeight());
                this.getLayoutParams().height = height;


                this.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(final MediaPlayer mediaPlayer) {
                        if(msAdsFullScreenPopup!=null) {
                            if (replayMode.equals("1")) {
                                mediaPlayer.setLooping(true);
                            }
                        }
                        MsAdsVideoSponsorView.this.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (MsAdsVideoSponsorView.this.isPlaying()) {
                                    stopPosition = MsAdsVideoSponsorView.this.getCurrentPosition();
                                    MsAdsVideoSponsorView.this.pause();
                                }
                                MsAdsUtil.collectUserStats(banner.getBannerId(), "click", MsAdsSdk.getInstance().getUserId(), banner.getFiltersForStats());
                                MsAdsUtil.openWebView(banner.getAndroidSubLink());
                                if(msAdsFullScreenPopup!=null){
                                    msAdsFullScreenPopup.removeDialog();
                                }
                            }
                        });

                    }
                });


                MsAdsVideoSponsorView.this.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        if(videoDelegate!=null){
                            videoDelegate.videoDelegate(MsAdsPreRollStatus.VIDEO_FINISHED, banner.getBannerId());
                            if(!developerId.equals(""))
                                MsAdsSdk.getInstance().listOfPlayingVideos.remove(developerId + "-" + String.valueOf(pos));
                        }
                    }
                });

                MsAdsVideoSponsorView.this.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        return true;
                    }
                });


                if(recyclerView!=null) {
                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (MsAdsVideoSponsorView.this.isPlaying()) {
                                if (getVisibilitPercentHeight(MsAdsVideoSponsorView.this) < 50) {
                                    stopPosition = MsAdsVideoSponsorView.this.getCurrentPosition();
                                    MsAdsVideoSponsorView.this.pause();

                                }
                            } else if (!MsAdsVideoSponsorView.this.isPlaying()) {
                                if (getVisibilitPercentHeight(MsAdsVideoSponsorView.this) >= 50) {
                                    MsAdsVideoSponsorView.this.requestFocus();
                                    MsAdsVideoSponsorView.this.seekTo(stopPosition);
                                    MsAdsVideoSponsorView.this.start();
                                }
                            }
                        }
                    });
                }

                if(scrollView!=null){
                    scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                            if (MsAdsVideoSponsorView.this.isPlaying()) {
                                if (getVisibilitPercentHeight(MsAdsVideoSponsorView.this) < 50) {
                                    stopPosition = MsAdsVideoSponsorView.this.getCurrentPosition();
                                    MsAdsVideoSponsorView.this.pause();

                                }
                            } else if (!MsAdsVideoSponsorView.this.isPlaying()) {
                                if (getVisibilitPercentHeight(MsAdsVideoSponsorView.this) >= 50) {
                                    MsAdsVideoSponsorView.this.requestFocus();
                                    MsAdsVideoSponsorView.this.seekTo(stopPosition);
                                    MsAdsVideoSponsorView.this.start();
                                }
                            }
                        }
                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MsAdsVideoSponsorView.this.getLayoutParams().height = 0;
        }


    }


    public void loadVideo(Context context, MsAdsBanner banner, RecyclerView recyclerView, ScrollView scrollView, MsAdsVideoDelegate videoDelegate,
                          String replayMode, MsAdsFullScreenPopup msAdsFullScreenPopup, String developerId) {

        if (!banner.getMediaUrl().contains("http")) {
            banner.setMediaUrl("http://" + banner.getMediaUrl());
        }
        if (MsAdsUtil.isNetworkConnected(context)) {
            try {
                final MediaController mediaController = new MediaController(context);
                final Uri video = Uri.parse(banner.getMediaUrl());
                this.setVideoURI(video);
                mediaController.setVisibility(View.GONE);
                int screenWidth = MsAdsSdk.getInstance().getScreenWidth();
                float ratio = Math.min((float) screenWidth / banner.getWidth(), (float) screenWidth / banner.getHeight());
                int height = Math.round((float) ratio * banner.getHeight());
                this.getLayoutParams().height = height;

                MsAdsVideoSponsorView.this.start();

                if(!developerId.equals(""))
                    MsAdsSdk.getInstance().listOfPlayingVideos.put(developerId, MsAdsVideoSponsorView.this);


                this.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(final MediaPlayer mediaPlayer) {
                        if(msAdsFullScreenPopup!=null) {
                            if (replayMode.equals("1")) {
                                mediaPlayer.setLooping(true);
                            }
                        }
                        MsAdsVideoSponsorView.this.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (MsAdsVideoSponsorView.this.isPlaying()) {
                                    stopPosition = MsAdsVideoSponsorView.this.getCurrentPosition();
                                    MsAdsVideoSponsorView.this.pause();
                                }
                                MsAdsUtil.collectUserStats(banner.getBannerId(), "click", MsAdsSdk.getInstance().getUserId(), banner.getFiltersForStats());
                                MsAdsUtil.openWebView(banner.getAndroidSubLink());
                                if(msAdsFullScreenPopup!=null){
                                    msAdsFullScreenPopup.removeDialog();
                                }
                            }
                        });

                    }
                });


                MsAdsVideoSponsorView.this.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        if(videoDelegate!=null){
                            videoDelegate.videoDelegate(MsAdsPreRollStatus.VIDEO_FINISHED, banner.getBannerId());
                            if(!developerId.equals(""))
                                MsAdsSdk.getInstance().listOfPlayingVideos.remove(developerId);
                        }
                    }
                });

                MsAdsVideoSponsorView.this.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        return true;
                    }
                });


                if(recyclerView!=null) {
                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if(isVisibleToUser){
                                if (MsAdsVideoSponsorView.this.isPlaying()) {
                                    if (getVisibilitPercentHeight(MsAdsVideoSponsorView.this) < 50) {
                                        stopPosition = MsAdsVideoSponsorView.this.getCurrentPosition();
                                        MsAdsVideoSponsorView.this.pause();

                                    }
                                } else if (!MsAdsVideoSponsorView.this.isPlaying()) {
                                    if (getVisibilitPercentHeight(MsAdsVideoSponsorView.this) >= 50) {
                                        MsAdsVideoSponsorView.this.requestFocus();
                                        MsAdsVideoSponsorView.this.seekTo(stopPosition);
                                        MsAdsVideoSponsorView.this.start();
                                    }
                                }
                            }
                        }
                    });
                }

                if(scrollView!=null){
                    scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                            if(isVisibleToUser) {
                                if (MsAdsVideoSponsorView.this.isPlaying()) {
                                    if (getVisibilitPercentHeight(MsAdsVideoSponsorView.this) < 50) {
                                        stopPosition = MsAdsVideoSponsorView.this.getCurrentPosition();
                                        MsAdsVideoSponsorView.this.pause();

                                    }
                                } else if (!MsAdsVideoSponsorView.this.isPlaying()) {
                                    if (getVisibilitPercentHeight(MsAdsVideoSponsorView.this) >= 50) {
                                        MsAdsVideoSponsorView.this.requestFocus();
                                        MsAdsVideoSponsorView.this.seekTo(stopPosition);
                                        MsAdsVideoSponsorView.this.start();
                                    }
                                }
                            }
                        }
                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MsAdsVideoSponsorView.this.getLayoutParams().height = 0;
        }


    }

    public int getVisibilitPercentHeight(View currentView) {
        int percent = 100;

        currentView.getLocalVisibleRect(mCurrentViewRect);
        int height = currentView.getHeight();
        try {
            if (viewIsPartiallyHiddenTop()) {
                // view is partially hidden behind the top edge
                percent = (height - mCurrentViewRect.top) * 100 / height;
            } else if (viewIsPartiallyHiddenBottom(height)) {
                percent = mCurrentViewRect.bottom * 100 / height;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return percent;
    }

    public int getVisibilitPercentWidth(View currentView) {
        int percent = 100;

        currentView.getLocalVisibleRect(mCurrentViewRect);
        int width = currentView.getWidth();
        try {
            if (viewIsPartiallyHiddenLeft()) {
                // view is partially hidden behind the top edge
                percent = (width - mCurrentViewRect.left) * 100 / width;
            } else if (viewIsPartiallyHiddenRight(width)) {
                percent = mCurrentViewRect.right * 100 / width;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return percent;
    }

    private boolean viewIsPartiallyHiddenRight(int width) {
        return mCurrentViewRect.left > 0 && mCurrentViewRect.right < width;
    }

    private boolean viewIsPartiallyHiddenLeft() {
        return mCurrentViewRect.left > 0;
    }


    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }

    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    public void refresh() {
        if (getVisibilitPercentHeight(this) >= 50) {
            this.requestFocus();
            this.seekTo(stopPosition);
            this.start();
        }
    }

    public void resumeVideo(){
        if(isVisibleToUser){
            if (getVisibilitPercentHeight(MsAdsVideoSponsorView.this) >= 50) {
                MsAdsVideoSponsorView.this.requestFocus();
                MsAdsVideoSponsorView.this.seekTo(stopPosition);
                MsAdsVideoSponsorView.this.start();
            }
        }
    }

    public void pauseVideo(){
        stopPosition = MsAdsVideoSponsorView.this.getCurrentPosition();
        MsAdsVideoSponsorView.this.pause();
    }

    public boolean isVisibleToUser() {
        return isVisibleToUser;
    }

    public void setVisibleToUser(boolean visibleToUser) {
        isVisibleToUser = visibleToUser;
    }
}
