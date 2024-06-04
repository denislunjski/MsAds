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
import com.meritumads.elements.adapter.Generaltem;
import com.meritumads.elements.adapter.Item;
import com.meritumads.pojo.Banner;
import com.meritumads.pojo.Position;
import com.meritumads.settings.MsAdsSdk;
import com.meritumads.settings.Util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class BannerPopup {

    private Dialog dialog;
    private Position position;
    private WebView webview;
    private PopupDelegate popupDelegate;
    private Activity activity;
    private Fragment fragment;

    boolean closeBtnAdded = false, headerImgAdded = false;

    PopupAdapter popupAdapter;

    public BannerPopup(Position position, PopupDelegate popupDelegate, Activity activity, Fragment fragment) {
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

        dialog.setContentView(R.layout.sponsor_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        popupAdapter = new PopupAdapter(this);
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
                            for(Map.Entry<String, String> entry: MsAdsSdk.getInstance().getListOfActiveFilters().entrySet()) {
                                String[] temp = entry.getKey().split("-");
                                if(position.getBanners().get(i).getFilters().contains(temp[0] +"," + entry.getValue())){
                                    isFilterActive = true;
                                }
                            }
                            if(!isFilterActive){
                                position.getBanners().remove(i);
                                i--;
                            }
                        }
                    }
                }
            }


        if(position.getBanners()!=null && position.getBanners().size()>0) {
            Collections.sort(position.getBanners(), new Comparator<Banner>() {
                @Override
                public int compare(Banner banner1, Banner banner2) {
                    return banner1.getOrdnum() - banner2.getOrdnum();
                }
            });
            LinkedHashMap<String, Banner> tempBanners = new LinkedHashMap<>();
            for (int i = 0; i < position.getBanners().size(); i++) {
                if(i == 0){
                    Util.collectUserStats(position.getBanners().get(i).getBannerId(), "impression", MsAdsSdk.getInstance().getUserId());
                }
                tempBanners.put(position.getBanners().get(i).getBannerType(), position.getBanners().get(i));
            }

            if (tempBanners.get(BannerTypes.backgroundImage) != null) {
                Glide.with(activity != null ? activity.getApplicationContext() : fragment.getContext())
                        .load(tempBanners.get(BannerTypes.backgroundImage).getMediaUrl() + "?="
                                + tempBanners.get(BannerTypes.backgroundImage).getMediaTs())
                        .signature(new ObjectKey(tempBanners.get(BannerTypes.backgroundImage).getMediaTs()))
                        .into(backgroundImg);

            }
            if (tempBanners.get(BannerTypes.popupRoundedHeaderImage) != null) {
                topIcon.setVisibility(View.VISIBLE);
                Glide.with(activity != null ? activity.getApplicationContext() : fragment.getContext())
                        .load(tempBanners.get(BannerTypes.popupRoundedHeaderImage).getMediaUrl() + "?="
                                + tempBanners.get(BannerTypes.popupRoundedHeaderImage).getMediaTs())
                        .signature(new ObjectKey(tempBanners.get(BannerTypes.popupRoundedHeaderImage).getMediaTs()))
                        .into(topIcon);


                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) recyclerHolder.getLayoutParams();
                params.topMargin = (int) (90 * (activity != null ? activity.getApplicationContext() : fragment.getContext()).getResources().getDisplayMetrics().density);
                recyclerHolder.setLayoutParams(params);

                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) popupBackColor.getLayoutParams();
                params1.topMargin = (int) (40 * (activity != null ? activity.getApplicationContext() : fragment.getContext()).getResources().getDisplayMetrics().density);
                popupBackColor.setLayoutParams(params1);

            }
            if (tempBanners.get(BannerTypes.popupHeaderImage) != null) {
                headerImgAdded = true;
                popupAdapter.addItem(new Generaltem(tempBanners.get(BannerTypes.popupHeaderImage), Item.TYPE_IMAGE));
            }
            if (tempBanners.get(BannerTypes.popupMiddleImage) != null) {
                popupAdapter.addItem(new Generaltem(tempBanners.get(BannerTypes.popupMiddleImage), Item.TYPE_IMAGE));
            }
            for (int i = 0; i < position.getBanners().size(); i++) {
                if (position.getBanners().get(i).getBannerType().equals(BannerTypes.popupButton)) {
                    if(position.getBanners().get(i).getNextTimeBtn().equals("1")){
                        closeBtnAdded = true;
                        popupAdapter.addItem(new Generaltem(position.getBanners().get(i), Item.TYPE_EXIT_BTN));
                    }else {
                        popupAdapter.addItem(new Generaltem(position.getBanners().get(i), Item.TYPE_SPONSOR_BTN));
                    }
                }

            }

            if (tempBanners.get(BannerTypes.buttonClose) != null) {
                closeBtnAdded = true;
                if (tempBanners.get(BannerTypes.buttonClose).getMediaUrl().length() > 0) {
                    closeImg.setVisibility(View.VISIBLE);
                    closeTxt.setVisibility(View.GONE);
                    Glide.with(activity != null ? activity.getApplicationContext() : fragment.getContext())
                            .load(tempBanners.get(BannerTypes.buttonClose).getMediaUrl() + "?="
                                    + tempBanners.get(BannerTypes.buttonClose).getMediaTs())
                            .signature(new ObjectKey(tempBanners.get(BannerTypes.buttonClose).getMediaTs()))
                            .into(closeImg);
                } else {
                    if (tempBanners.get(BannerTypes.buttonClose).getPopupButtonText().length() > 0) {
                        closeImg.setVisibility(View.GONE);
                        closeTxt.setText(tempBanners.get(BannerTypes.buttonClose).getPopupButtonText());
                        closeTxt.setTextColor(Color.parseColor(tempBanners.get(BannerTypes.buttonClose).getPopupButtonColortext()));
                        Drawable drawable = closeTxt.getBackground();
                        DrawableCompat.setTint(drawable, Color.parseColor(tempBanners.get(BannerTypes.buttonClose).getPopupButtonColorback()));
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
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeDialog();
                    }
                });
                if (tempBanners.get(BannerTypes.popupRoundedHeaderImage) != null) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) close.getLayoutParams();
                    params.topMargin = (int) (50 * (activity != null ? activity.getApplicationContext() : fragment.getContext()).getResources().getDisplayMetrics().density);
                    close.setLayoutParams(params);
                } else {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) close.getLayoutParams();
                    params.topMargin = (int) (10 * (activity != null ? activity.getApplicationContext() : fragment.getContext()).getResources().getDisplayMetrics().density);
                    close.setLayoutParams(params);
                }

            }

            if (tempBanners.get(BannerTypes.popupBottomImage) != null) {
                popupAdapter.addItem(new Generaltem(tempBanners.get(BannerTypes.popupBottomImage), Item.TYPE_IMAGE));
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

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeDialog();
                    }
                });

                if (tempBanners.get(BannerTypes.popupRoundedHeaderImage) != null) {
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
                popupAdapter.addItem(pos, new Generaltem(position, Item.TYPE_TEXTS));
            } else {
                popupAdapter.addItem(pos, new Generaltem(position, Item.TYPE_WEBVIEW));
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
