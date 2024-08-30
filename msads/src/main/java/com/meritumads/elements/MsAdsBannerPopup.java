package com.meritumads.elements;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.meritumads.R;
import com.meritumads.elements.adapter.MsAdsGeneraltem;
import com.meritumads.elements.adapter.MsAdsItem;
import com.meritumads.pojo.MsAdsBanner;
import com.meritumads.pojo.MsAdsPosition;
import com.meritumads.settings.MsAdsSdk;
import com.meritumads.settings.MsAdsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MsAdsBannerPopup {

    private Dialog dialog;
    private MsAdsPosition position;
    private WebView webview;
    private MsAdsPopupDelegate popupDelegate;
    private Activity activity;
    private Fragment fragment;

    boolean closeBtnAdded = false, headerImgAdded = false;

    MsAdsPopupAdapter popupAdapter;

    public MsAdsBannerPopup(MsAdsPosition position, MsAdsPopupDelegate popupDelegate, Activity activity, Fragment fragment) {
        this.position = position;
        this.popupDelegate = popupDelegate;
        this.activity = activity;
        this.fragment = fragment;
    }

    public void showDialog(){

        if(activity!=null){
            dialog = new Dialog(activity);
        }else if(fragment!=null){
            dialog = new Dialog(fragment.getActivity());
        }else{
            return;
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return true;
            }
        });

        dialog.setContentView(R.layout.msads_sponsor_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        popupAdapter = new MsAdsPopupAdapter(this);
        RecyclerView recyclerView = dialog.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(activity != null ?
                activity.getApplicationContext()
                : fragment.getContext(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(popupAdapter);

        webview = dialog.findViewById(R.id.webview);
        ImageView topIcon = dialog.findViewById(R.id.top_icon);
        RelativeLayout close = dialog.findViewById(R.id.close);
        ImageView closeImg = dialog.findViewById(R.id.img_close);
        TextView closeTxt = dialog.findViewById(R.id.txt_close);
        ImageView backgroundImg = dialog.findViewById(R.id.background_img);
        CardView popupBackColor = dialog.findViewById(R.id.popup_card_back);
        CardView recyclerHolder = dialog.findViewById(R.id.card_view_recycler_holder);


        popupBackColor.setCardBackgroundColor(Color.parseColor(position.getPopupBackgroundColor()));

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
            Collections.sort(position.getBanners(), new Comparator<MsAdsBanner>() {
                @Override
                public int compare(MsAdsBanner banner1, MsAdsBanner banner2) {
                    return banner1.getOrdnum() - banner2.getOrdnum();
                }
            });
            LinkedHashMap<String, MsAdsBanner> tempBanners = new LinkedHashMap<>();
            boolean stastCollected = false;
            for (int i = 0; i < position.getBanners().size(); i++) {
                if(!position.getBanners().get(i).getBannerId().equals("") && stastCollected == false){
                    stastCollected = true;
                    MsAdsUtil.collectUserStats(position.getBanners().get(i).getBannerId(), "impression", MsAdsSdk.getInstance().getUserId(),
                            position.getBanners().get(i).getFiltersForStats());
                }
                tempBanners.put(position.getBanners().get(i).getBannerType(), position.getBanners().get(i));
            }

            if (tempBanners.get(MsAdsBannerTypes.backgroundImage) != null) {
                Glide.with(activity != null ? activity.getApplicationContext() : fragment.getContext())
                        .load(tempBanners.get(MsAdsBannerTypes.backgroundImage).getMediaUrl() + "?="
                                + tempBanners.get(MsAdsBannerTypes.backgroundImage).getMediaTs())
                        .signature(new ObjectKey(tempBanners.get(MsAdsBannerTypes.backgroundImage).getMediaTs()))
                        .into(backgroundImg);

            }
            if (tempBanners.get(MsAdsBannerTypes.popupRoundedHeaderImage) != null) {
                topIcon.setVisibility(View.VISIBLE);
                Glide.with(activity != null ? activity.getApplicationContext() : fragment.getContext())
                        .load(tempBanners.get(MsAdsBannerTypes.popupRoundedHeaderImage).getMediaUrl() + "?="
                                + tempBanners.get(MsAdsBannerTypes.popupRoundedHeaderImage).getMediaTs())
                        .signature(new ObjectKey(tempBanners.get(MsAdsBannerTypes.popupRoundedHeaderImage).getMediaTs()))
                        .into(topIcon);


                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) recyclerHolder.getLayoutParams();
                params.topMargin = (int) (90 * (activity != null ? activity.getApplicationContext() : fragment.getContext()).getResources().getDisplayMetrics().density);
                recyclerHolder.setLayoutParams(params);

                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) popupBackColor.getLayoutParams();
                params1.topMargin = (int) (40 * (activity != null ? activity.getApplicationContext() : fragment.getContext()).getResources().getDisplayMetrics().density);
                popupBackColor.setLayoutParams(params1);

            }
            if (tempBanners.get(MsAdsBannerTypes.popupHeaderImage) != null) {
                headerImgAdded = true;
                popupAdapter.addItem(new MsAdsGeneraltem(tempBanners.get(MsAdsBannerTypes.popupHeaderImage), MsAdsItem.TYPE_IMAGE));
            }
            if (tempBanners.get(MsAdsBannerTypes.popupMiddleImage) != null) {
                popupAdapter.addItem(new MsAdsGeneraltem(tempBanners.get(MsAdsBannerTypes.popupMiddleImage), MsAdsItem.TYPE_IMAGE));
            }
            for (int i = 0; i < position.getBanners().size(); i++) {
                if (position.getBanners().get(i).getBannerType().equals(MsAdsBannerTypes.popupButton)) {
                    if(position.getBanners().get(i).getNextTimeBtn().equals("1")){
                        closeBtnAdded = true;
                        popupAdapter.addItem(new MsAdsGeneraltem(position.getBanners().get(i), MsAdsItem.TYPE_EXIT_BTN));
                    }else {
                        popupAdapter.addItem(new MsAdsGeneraltem(position.getBanners().get(i), MsAdsItem.TYPE_SPONSOR_BTN));
                    }
                }

            }

            if (tempBanners.get(MsAdsBannerTypes.buttonClose) != null) {
                closeBtnAdded = true;
                if (tempBanners.get(MsAdsBannerTypes.buttonClose).getMediaUrl().length() > 0) {
                    closeImg.setVisibility(View.VISIBLE);
                    closeTxt.setVisibility(View.GONE);
                    Glide.with(activity != null ? activity.getApplicationContext() : fragment.getContext())
                            .load(tempBanners.get(MsAdsBannerTypes.buttonClose).getMediaUrl() + "?="
                                    + tempBanners.get(MsAdsBannerTypes.buttonClose).getMediaTs())
                            .signature(new ObjectKey(tempBanners.get(MsAdsBannerTypes.buttonClose).getMediaTs()))
                            .into(closeImg);
                } else {
                    if (tempBanners.get(MsAdsBannerTypes.buttonClose).getPopupButtonText().length() > 0) {
                        closeImg.setVisibility(View.GONE);
                        closeTxt.setText(tempBanners.get(MsAdsBannerTypes.buttonClose).getPopupButtonText());
                        closeTxt.setTextColor(Color.parseColor(tempBanners.get(MsAdsBannerTypes.buttonClose).getPopupButtonColortext()));
                        Drawable drawable = closeTxt.getBackground();
                        DrawableCompat.setTint(drawable, Color.parseColor(tempBanners.get(MsAdsBannerTypes.buttonClose).getPopupButtonColorback()));
                        //gore desno text
                    } else {
                        closeImg.setVisibility(View.VISIBLE);
                        closeTxt.setVisibility(View.GONE);
                        closeImg.setImageDrawable(ContextCompat.getDrawable(activity != null ?
                                activity.getApplicationContext() : fragment.getContext(), R.drawable.close_x));
                    }
                }
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        close.setVisibility(View.VISIBLE);
                    }
                }, (long)position.getCloseDelay() * 1000);
                closeTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeDialog();
                    }
                });
                closeImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeDialog();
                    }
                });
                if (tempBanners.get(MsAdsBannerTypes.popupRoundedHeaderImage) != null) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) close.getLayoutParams();
                    params.topMargin = (int) (50 * (activity != null ? activity.getApplicationContext() : fragment.getContext()).getResources().getDisplayMetrics().density);
                    close.setLayoutParams(params);
                } else {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) close.getLayoutParams();
                    params.topMargin = (int) (10 * (activity != null ? activity.getApplicationContext() : fragment.getContext()).getResources().getDisplayMetrics().density);
                    close.setLayoutParams(params);
                }

            }

            if (tempBanners.get(MsAdsBannerTypes.popupBottomImage) != null) {
                popupAdapter.addItem(new MsAdsGeneraltem(tempBanners.get(MsAdsBannerTypes.popupBottomImage), MsAdsItem.TYPE_IMAGE));
            }


            if (!closeBtnAdded) {
                closeImg.setVisibility(View.VISIBLE);
                closeTxt.setVisibility(View.GONE);
                closeImg.setImageDrawable(ContextCompat.getDrawable(activity != null ?
                        activity.getApplicationContext() : fragment.getContext(), R.drawable.close_x));
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        close.setVisibility(View.VISIBLE);
                    }
                }, (long)position.getCloseDelay() * 1000);

                closeTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeDialog();
                    }
                });
                closeImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeDialog();
                    }
                });

                if (tempBanners.get(MsAdsBannerTypes.popupRoundedHeaderImage) != null) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) close.getLayoutParams();
                    params.topMargin = (int) (50 * (activity != null ? activity.getApplicationContext() : fragment.getContext()).getResources().getDisplayMetrics().density);
                    close.setLayoutParams(params);
                } else {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) close.getLayoutParams();
                    params.topMargin = (int) (10 * (activity != null ? activity.getApplicationContext() : fragment.getContext()).getResources().getDisplayMetrics().density);
                    close.setLayoutParams(params);
                }
            }


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.gravity = Gravity.CENTER_VERTICAL;

            int pos = 0;
            if (headerImgAdded) {
                pos = 1;
            }

            if (position.getWebview().equals("0")) {
                if(position.getPopupTitle().length()>0 || position.getPopupMessage().length()>0) {
                    popupAdapter.addItem(pos, new MsAdsGeneraltem(position, MsAdsItem.TYPE_TEXTS));
                }
            } else {
                if(position.getPopupRichText().length()>0) {
                    popupAdapter.addItem(pos, new MsAdsGeneraltem(position, MsAdsItem.TYPE_WEBVIEW));
                }
            }

            float percentageWidth = ((float) position.getPopupWidthPerc() / 100);
            float percentageHeight = ((float) position.getPopupHeightPerc() / 100);

            lp.width = (int) (MsAdsSdk.getInstance().getScreenWidth() * percentageWidth);
            lp.height = (int) (MsAdsSdk.getInstance().getScreenHeight() * percentageHeight);

            dialog.show();
            dialog.getWindow().setAttributes(lp);


        }
    }



    public void removeDialog(){
        if(dialog!=null)
            dialog.dismiss();
        if(popupDelegate!=null)
            popupDelegate.popupDelegate("Popup closed");
    }

    public int dpToPx(Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
        return px;
    }

}
