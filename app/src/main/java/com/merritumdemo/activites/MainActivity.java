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
import com.merritumdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MsAdsDelegate {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //initialization of MsAdsSdk
        MsAdsSdk.getInstance().init(getApplicationContext(), "1",
                "ms_ads_74f5834cd46ba2.28278241_64f5834cd46be5.24455451", this);

        MsAdsSdk.getInstance().setArrowBackColor("#fff000");
        MsAdsSdk.getInstance().setActionBarColor("#FF0000");


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
                    MsAdsPopups.getInstance(MainActivity.this);
                }
            });

            binding.fullScreenBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MsAdsFullScreen.getInstance(MainActivity.this);
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