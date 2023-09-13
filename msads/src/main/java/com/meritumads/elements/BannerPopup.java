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
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.meritumads.R;
import com.meritumads.pojo.Position;
import com.meritumads.settings.MsAdsSdk;
import com.meritumads.settings.Util;

public class BannerPopup {

    private Dialog dialog;
    private Position position;
    private WebView webview;
    private PopupDelegate popupDelegate;
    private Activity activity;
    private Fragment fragment;

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

        TextView title = dialog.findViewById(R.id.title);
        TextView subTitle =dialog.findViewById(R.id.text);
        LinearLayout middleHolder = dialog.findViewById(R.id.texts_holder);
        webview = dialog.findViewById(R.id.webview);
        ImageView topIcon = dialog.findViewById(R.id.top_icon);
        TextView close = dialog.findViewById(R.id.txt_close);
        TextView sponsorName = dialog.findViewById(R.id.sponsor_name);
        RelativeLayout button = dialog.findViewById(R.id.btn);

        if(position.getPopupButtonColorback().length()>0)
            button.getBackground().setTint(Color.parseColor(position.getPopupButtonColorback()));

        if(position.getPopupButtonColortext().length()>0)
            sponsorName.setTextColor(Color.parseColor(position.getPopupButtonColortext()));

        sponsorName.setText(position.getPopupButtonText());

        //TODO dodati timestamp za ikonicu
        Glide.with(dialog.getContext())
                .load(position.getTopIcon() + "?=" + "asdasdasd")
                .signature(new ObjectKey("asdasdasd"))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(topIcon);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                close.setVisibility(View.VISIBLE);
            }
        }, position.getCloseDelay()*1000);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDialog();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO dodaj statistiku
                Util.openWebView(handleUrl(position), MsAdsSdk.getInstance().getWebviewDroid());
                removeDialog();
            }
        });


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER_VERTICAL;

        if(position.getWebview().equals("0")){
            middleHolder.setVisibility(View.VISIBLE);
            webview.setVisibility(View.GONE);
            title.setText(position.getPopupTitle());
            subTitle.setText(position.getPopupMessage());
        }else{
            middleHolder.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) middleHolder.getLayoutParams();
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            middleHolder.setLayoutParams(layoutParams);
            handleOpeningWebView();
        }
        lp.width = (int) (MsAdsSdk.getInstance().context.getResources().getDisplayMetrics().widthPixels * 0.85f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    private void handleOpeningWebView() {

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.loadData(position.getPopupRichText(), "text/html; charset=utf-8", "UTF-8");

    }

    private String handleUrl(Position position) {
        String url = "";
        if(position.getBanners()!=null && position.getBanners().size()>0){
            url = !position.getBanners().get(0).getUrlTarget().equals("") ?
                    position.getBanners().get(0).getUrlTarget() :
                    position.getBanners().get(0).getMainCampaignUrl();
        }
        return url;
    }

    public void removeDialog(){
        if(dialog!=null)
            dialog.dismiss();
        if(popupDelegate!=null)
            popupDelegate.popupDelegate("");
    }

}
