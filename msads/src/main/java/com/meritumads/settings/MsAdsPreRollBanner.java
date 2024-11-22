package com.meritumads.settings;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.meritumads.R;
import com.meritumads.elements.MsAdsBannerTypes;
import com.meritumads.elements.MsAdsImageSponsorView;
import com.meritumads.elements.MsAdsPreRollHolder;
import com.meritumads.elements.MsAdsVideoDelegate;
import com.meritumads.pojo.MsAdsBanner;
import com.meritumads.pojo.MsAdsPosition;

import java.util.ArrayList;
import java.util.Map;

class MsAdsPreRollBanner implements MsAdsVideoDelegate {

    int itherator = 0;

    private MsAdsVideoSponsorView videoView;

    private ArrayList<MsAdsBanner> tempBanners;

    private MsAdsPreRollHolder holderNative;

    private RelativeLayout.LayoutParams params;
    private MsAdsPosition position;

    private MsAdsPreRollService preRollService;

    private String developerId = "";

    private android.os.Handler handler;

    private Runnable runnable;

    private RecyclerView recyclerView;
    private ScrollView scrollView;

    public void init(String developerId, MsAdsPreRollHolder preRollHolderNative, MsAdsPreRollService preRollService, RecyclerView recyclerView, ScrollView scrollView){
        this.developerId = developerId;
        this.preRollService = preRollService;
        this.recyclerView = recyclerView;
        this.scrollView = scrollView;
        ArrayList<MsAdsPosition> positions = MsAdsSdk.getInstance().prerollBanners;
        if(positions!=null) {
            for (int i = 0; i < positions.size(); i++) {
                if (developerId.equals(positions.get(i).getDeveloperId())) {
                    show(positions.get(i), preRollHolderNative);
                    break;
                }
            }
        }
    }


    private void show(MsAdsPosition position, MsAdsPreRollHolder view) {

        this.position = position;
        if(view == null){
            return;
        }
        holderNative = view;
        holderNative.setVisibility(View.VISIBLE);
        if(view instanceof MsAdsPreRollHolder){

            float boxRatio = 0f;
            for(int i = 0; i < position.getBanners().size(); i++){
                if((float)position.getBanners().get(i).getHeight()/(float) position.getBanners().get(i).getWidth()> boxRatio){
                    boxRatio = (float)position.getBanners().get(i).getHeight()/(float) position.getBanners().get(i).getWidth();
                }
            }
            params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.width = MsAdsSdk.getInstance().getScreenWidth();
            //params.height = (int) ((int) MsAdsSdk.getInstance().getScreenWidth() * boxRatio);
            //params.height = MATCH_PARENT;
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            holderNative.setLayoutParams(params);

            if(position.getBanners().size()>0){
                if(MsAdsSdk.getInstance().getListOfActiveFilters().size()>0){
                    for(int i = 0; i < position.getBanners().size(); i++){
                        if(position.getBanners().get(i).getFilters().length()>0){
                            boolean isFilterActive = false;
                            ArrayList<String> filteredFilters = new ArrayList<>();
                            for(Map.Entry<String, String> entry: MsAdsSdk.getInstance().getListOfActiveFilters().entrySet()) {
                                String[] temp = entry.getKey().split("-");
                                if(!position.getBanners().get(i).getFilters().equals("")) {
                                    if (position.getBanners().get(i).getFilters().contains(temp[0] + "," + entry.getValue())) {
                                        filteredFilters.add(temp[0] +"," + entry.getValue());
                                        isFilterActive = true;
                                    }
                                }
                            }
                            if(!isFilterActive){
                                position.getBanners().remove(i);
                                i--;
                            }else{
                                position.getBanners().get(i).setFiltersForStats(filteredFilters);
                            }
                        }
                    }
                }
            }

            if(position.getBanners()!=null && position.getBanners().size()>0) {
                tempBanners = new ArrayList<>();
                for(MsAdsBanner banner: position.getBanners()){
                    if(!banner.getBannerType().equals(MsAdsBannerTypes.backgroundImage)){
                        tempBanners.add(banner);
                    }else{
                        Glide.with(view.getContext())
                                .load(banner.getMediaUrl() + "?=" + banner.getMediaTs())
                                .signature(new ObjectKey(banner.getMediaTs()))
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        view.setBackground(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }
                                });
                        MsAdsUtil.collectUserStats(banner.getBannerId(), "impression", MsAdsSdk.getInstance().getUserId(), banner.getFiltersForStats());
                    }
                }

                    showBanners();


            }

        }
    }

    private void showBanners() {

        holderNative.removeAllViews();

        boolean skipAdded = false;
        if(tempBanners!=null && tempBanners.size()>0) {
            if (handler != null && runnable != null) {
                handler.removeCallbacks(runnable);
                handler = null;
                runnable = null;
            }
            ImageView skip = new ImageView(holderNative.getContext());
            skip.setBackground(ContextCompat.getDrawable(holderNative.getContext(), R.drawable.close_x));
            skip.setVisibility(View.GONE);
            RelativeLayout.LayoutParams paramsForSkip = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            paramsForSkip.addRule(RelativeLayout.ALIGN_PARENT_END);
            paramsForSkip.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            paramsForSkip.setMargins(0, 0, 15, 15);
            paramsForSkip.width = dpToPx(holderNative.getContext());
            paramsForSkip.height = dpToPx(holderNative.getContext());
            skip.setLayoutParams(paramsForSkip);
            if (itherator <= tempBanners.size() - 1) {
                if (tempBanners.get(itherator).getBannerType().equals(MsAdsBannerTypes.video)) {
                    if (videoView != null) {
                        videoView = null;
                    }
                    videoView = new MsAdsVideoSponsorView(holderNative.getContext());
                    RelativeLayout.LayoutParams paramsForVideoAndImage = new RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                    paramsForVideoAndImage.width = MsAdsSdk.getInstance().getScreenWidth();
                    paramsForVideoAndImage.height = WRAP_CONTENT;
                    paramsForVideoAndImage.addRule(RelativeLayout.CENTER_IN_PARENT);
                    videoView.setLayoutParams(paramsForVideoAndImage);
                    videoView.loadVideo(holderNative.getContext(), tempBanners.get(itherator), recyclerView, scrollView, this::videoDelegate, position.getReplayMode(), null, "");
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            skip.setVisibility(View.VISIBLE);
                        }
                    }, (long) position.getCloseDelay() * 1000);
                    skip.setOnClickListener(new MsAdsSafeClickListener() {
                        @Override
                        public void onSingleClick(View v) {
                            videoDelegate(MsAdsPreRollStatus.VIDEO_FINISHED, tempBanners.get(itherator).getBannerId());
                        }
                    });
                    holderNative.addView(videoView, 0);
                    holderNative.addView(skip, 1);
                    skipAdded = true;
                    MsAdsUtil.collectUserStats(tempBanners.get(itherator).getBannerId(), "impression", MsAdsSdk.getInstance().getUserId(), tempBanners.get(itherator).getFiltersForStats());

                } else {
                    MsAdsImageSponsorView sponsorImage = new MsAdsImageSponsorView(holderNative.getContext(),
                            tempBanners.get(itherator), this::videoDelegate, skip, (long) position.getCloseDelay());
                    RelativeLayout.LayoutParams paramsForVideoAndImage = new RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                    paramsForVideoAndImage.width = MsAdsSdk.getInstance().getScreenWidth();
                    paramsForVideoAndImage.height = WRAP_CONTENT;
                    paramsForVideoAndImage.addRule(RelativeLayout.CENTER_IN_PARENT);
                    sponsorImage.setLayoutParams(paramsForVideoAndImage);
                    sponsorImage.loadImage();

                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            skip.setVisibility(View.VISIBLE);
                        }
                    }, (long) position.getCloseDelay() * 1000);
                    skip.setOnClickListener(new MsAdsSafeClickListener() {
                        @Override
                        public void onSingleClick(View v) {
                            videoDelegate(MsAdsPreRollStatus.IMAGE_FINISHED, tempBanners.get(itherator).getBannerId());
                        }
                    });

                    holderNative.addView(sponsorImage, 0);
                    holderNative.addView(skip, 1);
                    skipAdded = true;
                    MsAdsUtil.collectUserStats(tempBanners.get(itherator).getBannerId(), "impression", MsAdsSdk.getInstance().getUserId(), tempBanners.get(itherator).getFiltersForStats());
                }
            }
        }
        if(!skipAdded){
            TextView skip = new TextView(holderNative.getContext());
            skip.setText("SKIP");
            skip.setTextColor(Color.parseColor("#ffffff"));
            skip.setBackground(ContextCompat.getDrawable(holderNative.getContext(), R.drawable.rounded_skip_layout));
            skip.setVisibility(View.GONE);
            RelativeLayout.LayoutParams paramsForSkip = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            paramsForSkip.addRule(RelativeLayout.ALIGN_PARENT_END);
            paramsForSkip.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            paramsForSkip.setMargins(0, 0, 15, 15);
            skip.setLayoutParams(paramsForSkip);

            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    skip.setVisibility(View.VISIBLE);
                }
            }, (long) position.getCloseDelay() * 1000);
            skip.setOnClickListener(new MsAdsSafeClickListener() {
                @Override
                public void onSingleClick(View v) {
                    videoDelegate(MsAdsPreRollStatus.NO_CONTENT_DEFINED_ONLY_BACKGROUND_FINISHED, "");
                }
            });

            holderNative.addView(skip);
        }

    }

    @Override
    public void videoDelegate(String response, String id) {
        if(response.equals(MsAdsPreRollStatus.VIDEO_FINISHED)){
            if(itherator+1 <= tempBanners.size()-1) {
                itherator++;
                showBanners();
                if (preRollService != null) {
                    preRollService.preRollVideoImageDelegate(developerId, MsAdsPreRollStatus.VIDEO_FINISHED, id);
                }
            }else if(itherator == tempBanners.size()-1){
                if(preRollService!=null){
                    preRollService.preRollVideoImageDelegate(developerId, MsAdsPreRollStatus.ALL_CONTENT_FINISHED, id);
                }
            }
        }else if(response.equals(MsAdsPreRollStatus.IMAGE_FINISHED)){
            if(itherator+1 <= tempBanners.size()-1) {
                itherator++;
                showBanners();
                if (preRollService != null) {
                    preRollService.preRollVideoImageDelegate(developerId, MsAdsPreRollStatus.IMAGE_FINISHED, id);
                }
            }else if(itherator == tempBanners.size()-1){
                if(preRollService!=null){
                    preRollService.preRollVideoImageDelegate(developerId, MsAdsPreRollStatus.ALL_CONTENT_FINISHED, id);
                }
            }
        }else if(response.equals(MsAdsPreRollStatus.NO_CONTENT_DEFINED_ONLY_BACKGROUND_FINISHED)){
            if(preRollService!=null){
                preRollService.preRollVideoImageDelegate(developerId, MsAdsPreRollStatus.ALL_CONTENT_FINISHED, id);
            }
        }
    }

    public int dpToPx(Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        return px;
    }
}
