package com.meritumads.settings;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.meritumads.R;
import com.meritumads.elements.BannerTypes;
import com.meritumads.elements.ImageSponsorView;
import com.meritumads.elements.PreRollHolder;
import com.meritumads.elements.VideoDelegate;
import com.meritumads.elements.VideoSponsorView;
import com.meritumads.pojo.Banner;
import com.meritumads.pojo.Position;

import java.util.ArrayList;

class PreRollBanner implements VideoDelegate {

    int itherator = 0;

    private VideoSponsorView videoView;

    private ArrayList<Banner> tempBanners;

    private PreRollHolder holderNative;

    private RelativeLayout.LayoutParams params;
    private Position position;

    private PreRollService preRollService;

    private String developerId = "";

    private android.os.Handler handler;

    private Runnable runnable;

    private RecyclerView recyclerView;
    private ScrollView scrollView;

    public void init(String developerId, PreRollHolder preRollHolderNative, PreRollService preRollService, RecyclerView recyclerView, ScrollView scrollView){
        this.developerId = developerId;
        this.preRollService = preRollService;
        this.recyclerView = recyclerView;
        this.scrollView = scrollView;
        ArrayList<Position> positions = MsAdsSdk.getInstance().prerollBanners;
        if(positions!=null) {
            for (int i = 0; i < positions.size(); i++) {
                if (developerId.equals(positions.get(i).getDeveloperId())) {
                    show(positions.get(i), preRollHolderNative);
                    break;
                }
            }
        }
    }


    private void show(Position position, PreRollHolder view) {

        this.position = position;
        if(view == null){
            return;
        }
        holderNative = view;
        holderNative.setVisibility(View.VISIBLE);
        if(view instanceof PreRollHolder){
            params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.width = MsAdsSdk.getInstance().getScreenWidth();
            params.height = (int) ((int) MsAdsSdk.getInstance().getScreenWidth() * position.getBoxRatio());
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            holderNative.setLayoutParams(params);

            if(position.getBanners()!=null && position.getBanners().size()>0) {
                tempBanners = new ArrayList<>();
                for(Banner banner: position.getBanners()){
                    if(!banner.getBannerType().equals(BannerTypes.backgroundImage)){
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
                    }
                }
                if(tempBanners!=null && tempBanners.size()>0){
                    showBanners();
                }

            }

        }
    }

    private void showBanners() {

        if(handler!=null && runnable!=null){
            handler.removeCallbacks(runnable);
            handler = null;
            runnable = null;
        }
        holderNative.removeAllViews();
        TextView skip = new TextView(holderNative.getContext());
        skip.setText("SKIP");
        skip.setTextColor(Color.parseColor("#ffffff"));
        skip.setBackground(ContextCompat.getDrawable(holderNative.getContext(), R.drawable.rounded_skip_layout));
        skip.setVisibility(View.GONE);
        RelativeLayout.LayoutParams paramsForSkip = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        paramsForSkip.addRule(RelativeLayout.ALIGN_PARENT_END);
        paramsForSkip.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        paramsForSkip.setMargins(0,0,15,15);
        skip.setLayoutParams(paramsForSkip);
        if(itherator <= tempBanners.size()-1){
            if(tempBanners.get(itherator).getBannerType().equals(BannerTypes.video)){
                if(videoView!=null) {
                    videoView = null;
                }
                videoView = new VideoSponsorView(holderNative.getContext());
                RelativeLayout.LayoutParams paramsForVideoAndImage = new RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                paramsForVideoAndImage.width = MsAdsSdk.getInstance().getScreenWidth();
                paramsForVideoAndImage.height = WRAP_CONTENT;
                paramsForVideoAndImage.addRule(RelativeLayout.CENTER_IN_PARENT);
                videoView.setLayoutParams(paramsForVideoAndImage);
                //TODO dodati recyclerview i scroolview
                videoView.loadVideo(holderNative.getContext(), tempBanners.get(itherator), recyclerView, scrollView, this::videoDelegate);
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        skip.setVisibility(View.VISIBLE);
                    }
                }, position.getCloseDelay() * 1000);
                skip.setOnClickListener(new SafeClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        videoDelegate(PreRollStatus.VIDEO_FINISHED, tempBanners.get(itherator).getBannerId());
                    }
                });
                holderNative.addView(videoView, 0);
                holderNative.addView(skip, 1);

            }else{
                ImageSponsorView sponsorImage = new ImageSponsorView(holderNative.getContext(),
                        tempBanners.get(itherator), this::videoDelegate, skip, position.getCloseDelay());
                RelativeLayout.LayoutParams paramsForVideoAndImage = new RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                paramsForVideoAndImage.width = MsAdsSdk.getInstance().getScreenWidth();
                paramsForVideoAndImage.height = WRAP_CONTENT;
                paramsForVideoAndImage.addRule(RelativeLayout.CENTER_IN_PARENT);
                sponsorImage.setLayoutParams(paramsForVideoAndImage);
                sponsorImage.loadImage();

                holderNative.addView(sponsorImage, 0);
                holderNative.addView(skip, 1);
            }
        }

    }

    @Override
    public void videoDelegate(String response, String id) {
        if(response.equals(PreRollStatus.VIDEO_FINISHED)){
            if(itherator+1 <= tempBanners.size()-1) {
                itherator++;
                showBanners();
                if (preRollService != null) {
                    preRollService.preRollVideoImageDelegate(developerId, PreRollStatus.VIDEO_FINISHED, id);
                }
            }else if(itherator == tempBanners.size()-1){
                if(preRollService!=null){
                    preRollService.preRollVideoImageDelegate(developerId, PreRollStatus.ALL_CONTENT_FINISHED, id);
                }
            }
        }else if(response.equals(PreRollStatus.IMAGE_FINISHED)){
            if(itherator+1 <= tempBanners.size()-1) {
                itherator++;
                showBanners();
                if (preRollService != null) {
                    preRollService.preRollVideoImageDelegate(developerId, PreRollStatus.IMAGE_FINISHED, id);
                }
            }else if(itherator == tempBanners.size()-1){
                if(preRollService!=null){
                    preRollService.preRollVideoImageDelegate(developerId, PreRollStatus.ALL_CONTENT_FINISHED, id);
                }
            }
        }
    }
}
