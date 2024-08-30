package com.meritumads.elements;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.meritumads.R;
import com.meritumads.elements.adapter.MsAdsGeneraltem;
import com.meritumads.elements.adapter.MsAdsHolder;
import com.meritumads.elements.adapter.MsAdsItem;
import com.meritumads.settings.MsAdsSdk;
import com.meritumads.settings.MsAdsSafeClickListener;
import com.meritumads.settings.MsAdsUtil;

import java.util.ArrayList;

public class MsAdsPopupAdapter extends RecyclerView.Adapter<MsAdsHolder> {

    private MsAdsBannerPopup bannerPopup;

    public MsAdsPopupAdapter(MsAdsBannerPopup bannerPopup) {
        this.bannerPopup = bannerPopup;
    }

    private ArrayList<MsAdsItem> items = new ArrayList<MsAdsItem>();
    @NonNull
    @Override
    public MsAdsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

        if(viewType == MsAdsItem.TYPE_IMAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msads_image_layout, parent, false);
        }else if(viewType == MsAdsItem.TYPE_EXIT_BTN){
            view =LayoutInflater.from(parent.getContext()).inflate(R.layout.msads_popup_button_layout, parent, false);
        }else if(viewType == MsAdsItem.TYPE_SPONSOR_BTN){
            view =LayoutInflater.from(parent.getContext()).inflate(R.layout.msads_popup_button_layout, parent, false);
        }else if(viewType == MsAdsItem.TYPE_SPONSOR_IMAGE){
            view =LayoutInflater.from(parent.getContext()).inflate(R.layout.msads_popup_button_layout, parent, false);
        }else if(viewType == MsAdsItem.TYPE_TEXTS){
            view =LayoutInflater.from(parent.getContext()).inflate(R.layout.msads_popup_text_layout, parent, false);
        }else if(viewType == MsAdsItem.TYPE_WEBVIEW){
            view =LayoutInflater.from(parent.getContext()).inflate(R.layout.msads_popup_webview_layout, parent, false);
        }

        return new MsAdsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsAdsHolder holder, int position) {

        switch (getItemViewType(position)){
            case MsAdsItem.TYPE_IMAGE: bindImage(holder, position);break;
            case MsAdsItem.TYPE_SPONSOR_BTN: bindSponsor(holder, position); break;
            case MsAdsItem.TYPE_EXIT_BTN: bindExitBtn(holder, position);break;
            case MsAdsItem.TYPE_SPONSOR_IMAGE: bindSponsorImage(holder, position);break;
            case MsAdsItem.TYPE_TEXTS: bindText(holder, position);break;
            case MsAdsItem.TYPE_WEBVIEW: bindWebView(holder, position); break;
        }

    }

    private void bindWebView(MsAdsHolder holder, int position) {

        View view = holder.itemView;

        MsAdsGeneraltem generaltem = (MsAdsGeneraltem)items.get(position);

        WebView webView = view.findViewById(R.id.msads_web_view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.loadData(generaltem.getPosition().getPopupRichText(), "text/html; charset=utf-8", "UTF-8");

    }

    private void bindText(MsAdsHolder holder, int position) {

        View view = holder.itemView;

        MsAdsGeneraltem generaltem = (MsAdsGeneraltem)items.get(position);
        TextView title = view.findViewById(R.id.title);
        TextView text = view.findViewById(R.id.text);

        title.setText(generaltem.getPosition().getPopupTitle());
        text.setText(generaltem.getPosition().getPopupMessage());

        title.setTextColor(Color.parseColor(generaltem.getPosition().getPopupTitleColor()));
        text.setTextColor(Color.parseColor(generaltem.getPosition().getPopupMessageColor()));


    }

    private void bindSponsorImage(MsAdsHolder holder, int position) {

        View view = holder.itemView;

        MsAdsGeneraltem generaltem = (MsAdsGeneraltem)items.get(position);
        RelativeLayout btn = view.findViewById(R.id.btn);

        btn.setOnClickListener(new MsAdsSafeClickListener() {
            @Override
            public void onSingleClick(View v) {
                MsAdsUtil.collectUserStats(generaltem.getBanner().getBannerId(), "click", MsAdsSdk.getInstance().getUserId(), generaltem.getBanner().getFiltersForStats());
                if (generaltem.getBanner().getApiActiveNonActive().equals("1")) {
                    String response = MsAdsSdk.getInstance().getApiLinkService()
                            .openApiLink(generaltem.getBanner().getAndroidSubLink());
                    MsAdsUtil.openWebView(response);
                } else {
                    MsAdsUtil.openWebView(generaltem.getBanner().getAndroidSubLink());
                }
                if(bannerPopup!=null){
                    bannerPopup.removeDialog();
                }
            }
        });
    }

    private void bindExitBtn(MsAdsHolder holder, int position) {

        View view = holder.itemView;

        MsAdsGeneraltem generaltem = (MsAdsGeneraltem)items.get(position);

        TextView text = view.findViewById(R.id.sponsor_name);
        RelativeLayout btn = view.findViewById(R.id.btn);

        text.setText(generaltem.getBanner().getPopupButtonText());
        text.setTextColor(Color.parseColor(generaltem.getBanner().getPopupButtonColortext()));
        Drawable drawable = btn.getBackground();
        DrawableCompat.setTint(drawable, Color.parseColor(generaltem.getBanner().getPopupButtonColorback()));

        btn.setOnClickListener(new MsAdsSafeClickListener() {
            @Override
            public void onSingleClick(View v) {
                bannerPopup.removeDialog();
            }
        });

    }

    private void bindSponsor(MsAdsHolder holder, int position) {

        MsAdsGeneraltem generaltem = (MsAdsGeneraltem)items.get(position);

        View popupBtnView = holder.itemView;
        RelativeLayout btn = popupBtnView.findViewById(R.id.btn);
        ImageView icon = popupBtnView.findViewById(R.id.icon);
        TextView text = popupBtnView.findViewById(R.id.sponsor_name);
        ImageView backImg = popupBtnView.findViewById(R.id.back_image);
        backImg.setImageDrawable(null);
        icon.setImageDrawable(null);
        text.setText("");
        btn.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.msads_rounded_layout));
        if(generaltem.getBanner().getInternalBannerType() == 1){
            Glide.with(holder.itemView.getContext())
                    .load(generaltem.getBanner().getMediaUrl() + "?="
                            + generaltem.getBanner().getMediaTs())
                    .signature(new ObjectKey(generaltem.getBanner().getMediaTs()))
                    .into(backImg);
        }else if(generaltem.getBanner().getInternalBannerType() == 2){
            Glide.with(holder.itemView.getContext())
                    .load(generaltem.getBanner().getMediaUrl() + "?="
                            + generaltem.getBanner().getMediaTs())
                    .signature(new ObjectKey(generaltem.getBanner().getMediaTs()))
                    .into(icon);
            text.setText(generaltem.getBanner().getPopupButtonText());
            text.setTextColor(Color.parseColor(generaltem.getBanner().getPopupButtonColortext()));
            Drawable drawable = btn.getBackground();
            DrawableCompat.setTint(drawable, Color.parseColor(generaltem.getBanner().getPopupButtonColorback()));
        }else{
            text.setText(generaltem.getBanner().getPopupButtonText());
            text.setTextColor(Color.parseColor(generaltem.getBanner().getPopupButtonColortext()));
            Drawable drawable = btn.getBackground();
            DrawableCompat.setTint(drawable, Color.parseColor(generaltem.getBanner().getPopupButtonColorback()));
        }
        btn.setOnClickListener(new MsAdsSafeClickListener() {
            @Override
            public void onSingleClick(View v) {
                MsAdsUtil.collectUserStats(generaltem.getBanner().getBannerId(), "click", MsAdsSdk.getInstance().getUserId(), generaltem.getBanner().getFiltersForStats());
                if (generaltem.getBanner().getApiActiveNonActive().equals("1")) {
                    String response = MsAdsSdk.getInstance().getApiLinkService().openApiLink(generaltem.getBanner().getAndroidSubLink());
                    MsAdsUtil.openWebView(response);
                } else {
                    MsAdsUtil.openWebView(generaltem.getBanner().getAndroidSubLink());
                }
                if(bannerPopup!=null){
                    bannerPopup.removeDialog();
                }
            }
        });
    }

    private void bindImage(MsAdsHolder holder, int position) {

        View view = holder.itemView;
        ImageView image = view.findViewById(R.id.msads_image);

        MsAdsGeneraltem generaltem = (MsAdsGeneraltem)items.get(position);

        Glide.with(holder.itemView.getContext())
                .load(generaltem.getBanner().getMediaUrl() + "?="
                        + generaltem.getBanner().getMediaTs())
                .signature(new ObjectKey(generaltem.getBanner().getMediaTs()))
                .into(image);

    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getTypeItem();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(MsAdsItem item){
        items.add(item);
        notifyDataSetChanged();
    }

    public void addItem(int pos, MsAdsItem item){
        items.add(pos, item);
        notifyDataSetChanged();
    }

}
