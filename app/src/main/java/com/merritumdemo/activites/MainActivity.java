package com.merritumdemo.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.meritumads.elements.MsAdsDelegate;
import com.meritumads.settings.MsAdsFullScreen;
import com.meritumads.settings.MsAdsPopups;
import com.meritumads.settings.MsAdsSdk;
import com.meritumads.settings.MsAdsOpenApiLinkService;
import com.merritumdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MsAdsDelegate {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //initialization of MsAdsSdk
        MsAdsSdk.getInstance().init(getApplicationContext(),  "212",
               "ms_ads_666ff4fdc406c0.93898688_666ff4fdc406f3.01463578", this);

        MsAdsSdk.getInstance().setArrowBackColor("#fff000");
        MsAdsSdk.getInstance().setActionBarColor("#FF0000");
        MsAdsSdk.getInstance().setFragmentManager(getSupportFragmentManager());



        /*
         * setup of apiLink service
         * If your are using api services for opening sponsor, and have to pass some parameters trough link
         * you can make that here
         * After user clicks on image or video sponsor and in admin setting api link is 1
         * it will return sponsor link on openApiLink and here you can add parameters.
         * After that just return new link trough return
         * After that sdk will handle all the rest opening the sponsor
         */
        MsAdsSdk.getInstance().setApiLinkService(new MsAdsOpenApiLinkService() {
            @Override
            public String openApiLink(String link) {
                link = link + "?test=123";
                return link;
            }
        });

        MsAdsSdk.getInstance().setActiveFilter("User_status", "3");
        MsAdsSdk.getInstance().setActiveFilter("Gender", "0");

    }

    @Override
    public void onMsAdsResult(String response) {
        if(response.equals("OK")){
            //download successfull
            Log.i("ms_ads_msg", response);
            binding.inListBanners.setVisibility(MsAdsSdk.getInstance().getInListBannersIds().size()>0 ? View.VISIBLE : View.GONE);
            binding.singleBanner.setVisibility(MsAdsSdk.getInstance().getInListBannersIds().size()>0 ? View.VISIBLE : View.GONE);
            binding.prerollBanner.setVisibility(MsAdsSdk.getInstance().getPrerollBanners().size()>0 ? View.VISIBLE : View.GONE);
            binding.popupBanner.setVisibility(MsAdsSdk.getInstance().getPopupBanners().size()>0 ? View.VISIBLE : View.GONE);
            binding.fullScreenBanner.setVisibility(MsAdsSdk.getInstance().getFullScreenBanners().size()>0 ? View.VISIBLE : View.GONE);

            binding.inListBanners.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), ListActivity.class);
                    startActivity(i);
                }
            });

            binding.singleBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), OneItemActivity.class);
                    startActivity(i);
                }
            });

            binding.popupBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MsAdsPopups.getInstance("roj_main_popup_normal", MainActivity.this);
                }
            });

            binding.fullScreenBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MsAdsFullScreen.getInstance("csh_home_full", MainActivity.this);
                }
            });

            binding.prerollBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), PreRollActivity.class);
                    startActivity(i);
                }
            });

        }else{
            //something is wrong, check message
            Log.i("ms_ads_msg", response);
        }

    }
}