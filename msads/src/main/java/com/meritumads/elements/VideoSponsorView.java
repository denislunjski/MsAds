package com.meritumads.elements;

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

import com.meritumads.pojo.Banner;
import com.meritumads.settings.MsAdsSdk;
import com.meritumads.settings.Util;

public class VideoSponsorView extends VideoView {

    private Rect mCurrentViewRect = new Rect();
    int stopPosition = 0;
    private final String HIT_TYPE_CLICK = "4";


    public VideoSponsorView(Context context) {
        super(context);
    }

    public VideoSponsorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoSponsorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VideoSponsorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void loadVideo(Context context, Banner banner, RecyclerView recyclerView, ScrollView scrollView) {

        if (!banner.getUrlSource().contains("http")) {
            banner.setUrlSource("http://" + banner.getUrlSource());
        }
        if (Util.isNetworkConnected(context)) {
            try {
                final MediaController mediaController = new MediaController(context);
                final Uri video = Uri.parse(banner.getUrlSource());
                this.setVideoURI(video);
                mediaController.setVisibility(View.GONE);
                int screenWidth = MsAdsSdk.getInstance().getScreenWidth();
                float ratio = Math.min((float) screenWidth / banner.getWidth(), (float) screenWidth / banner.getHeight());
                int height = Math.round((float) ratio * banner.getHeight());
                this.getLayoutParams().height = height;

                this.seekTo(stopPosition);
                this.start();

                this.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(final MediaPlayer mediaPlayer) {

                        VideoSponsorView.this.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (VideoSponsorView.this.isPlaying()) {
                                    stopPosition = VideoSponsorView.this.getCurrentPosition();
                                    VideoSponsorView.this.pause();
                                    String bannerId = banner.getBannerId();
                                    //TODO dodati skupljanje statistike i sponzora
                                    //Util.collectSponsorStats(context, bannerId , banner.getBannerPosition(), HIT_TYPE_CLICK);
                                    Util.openWebView(!banner.getUrlTarget().equals("") ? banner.getUrlTarget(): banner.getMainCampaignUrl(), MsAdsSdk.getInstance().getWebviewDroid());

                                }
                            }
                        });
                        /*
                        VideoSponsorView.this.setOnTouchListener(new OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                    if (VideoSponsorView.this.isPlaying()) {
                                        stopPosition = VideoSponsorView.this.getCurrentPosition();
                                        VideoSponsorView.this.pause();
                                        String bannerId = banner.getBannerId();
                                        //TODO dodati skupljanje statistike i sponzora
                                        //Util.collectSponsorStats(context, bannerId , banner.getBannerPosition(), HIT_TYPE_CLICK);
                                        Util.openWebView(!banner.getUrlTarget().equals("") ? banner.getUrlTarget(): banner.getMainCampaignUrl(), MsAdsSdk.getInstance().getWebviewDroid());

                                    }
                                }
                                return false;
                            }
                        });

                         */
                    }
                });

                VideoSponsorView.this.setOnErrorListener(new MediaPlayer.OnErrorListener() {

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
                            if (VideoSponsorView.this.isPlaying()) {
                                if (getVisibilitPercent(VideoSponsorView.this) < 50) {
                                    stopPosition = VideoSponsorView.this.getCurrentPosition();
                                    VideoSponsorView.this.pause();

                                }
                            } else if (!VideoSponsorView.this.isPlaying()) {
                                if (getVisibilitPercent(VideoSponsorView.this) >= 50) {
                                    VideoSponsorView.this.requestFocus();
                                    VideoSponsorView.this.seekTo(stopPosition);
                                    VideoSponsorView.this.start();
                                }
                            }
                        }
                    });
                }

                if(scrollView!=null){
                    scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                            if (VideoSponsorView.this.isPlaying()) {
                                if (getVisibilitPercent(VideoSponsorView.this) < 50) {
                                    stopPosition = VideoSponsorView.this.getCurrentPosition();
                                    VideoSponsorView.this.pause();

                                }
                            } else if (!VideoSponsorView.this.isPlaying()) {
                                if (getVisibilitPercent(VideoSponsorView.this) >= 50) {
                                    VideoSponsorView.this.requestFocus();
                                    VideoSponsorView.this.seekTo(stopPosition);
                                    VideoSponsorView.this.start();
                                }
                            }
                        }
                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            VideoSponsorView.this.getLayoutParams().height = 0;
        }


    }






    public int getVisibilitPercent(View currentView) {
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

    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }

    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    public void refresh() {
        if (getVisibilitPercent(this) >= 50) {
            this.requestFocus();
            this.seekTo(stopPosition);
            this.start();
        }
    }


    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if(visibility == 0){
            VideoSponsorView.this.requestFocus();
            VideoSponsorView.this.seekTo(stopPosition);
            VideoSponsorView.this.start();
        }else if(visibility == 8){
            stopPosition = VideoSponsorView.this.getCurrentPosition();
            VideoSponsorView.this.pause();
        }
    }

}
