package com.meritumads.elements;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.meritumads.R;
import com.meritumads.pojo.Position;
import com.meritumads.settings.MsAdsSdk;

public class FullScreenPopup {

    private Dialog dialog;
    private Position position;
    private PopupDelegate popupDelegate;
    private Activity activity;
    private Fragment fragment;

    public FullScreenPopup(Position position, PopupDelegate popupDelegate, Activity activity, Fragment fragment) {
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

        dialog.setContentView(R.layout.full_screen_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView close = dialog.findViewById(R.id.txt_close);
        ImageView backgroundImg = dialog.findViewById(R.id.background_img);
        HeightWrappingViewPager heightWrappingViewPager  = dialog.findViewById(R.id.main_banner_holder);
        setupPositionOnScreen(heightWrappingViewPager);

        /*
        Glide.with(activity!=null ? activity.getApplicationContext() : fragment.getContext())
                .load(position.getUrlBackground() + "?=" + position.getUrlBackgroundTs())
                .signature(new ObjectKey(position.getUrlBackgroundTs()))
                .into(backgroundImg);

         */

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                close.setVisibility(View.VISIBLE);
            }
        }, position.getCloseDelay()*1000);

        AdsAdapter adsAdapter = new AdsAdapter(position.getBanners(), heightWrappingViewPager, position.getRotationDelay(), null, null, position.getReplayMode());
        heightWrappingViewPager.setAdapter(adsAdapter);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDialog();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER_VERTICAL;


        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    private void setupPositionOnScreen(HeightWrappingViewPager heightWrappingViewPager) {

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) heightWrappingViewPager.getLayoutParams();

        if(position.getVideoPosition().equals("0")){
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            heightWrappingViewPager.setLayoutParams(layoutParams);
        }else if(position.getVideoPosition().equals("1")){
            layoutParams.addRule(RelativeLayout.ALIGN_TOP);
            heightWrappingViewPager.setLayoutParams(layoutParams);
        }else if(position.getVideoPosition().equals("2")){
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            heightWrappingViewPager.setLayoutParams(layoutParams);
        }else if(position.getVideoPosition().equals("3")){
            layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM);
            heightWrappingViewPager.setLayoutParams(layoutParams);
        }else{
            //stavi ga na centar
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            heightWrappingViewPager.setLayoutParams(layoutParams);
        }

    }

    public void removeDialog(){
        if(dialog!=null)
            dialog.dismiss();
        if(popupDelegate!=null)
            popupDelegate.popupDelegate("");
    }

}
