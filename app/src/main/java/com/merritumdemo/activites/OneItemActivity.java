package com.merritumdemo.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.Toast;

import com.meritumads.settings.InListPosition;
import com.meritumads.settings.MsAdsBanners;
import com.meritumads.settings.MsAdsSdk;
import com.merritumdemo.databinding.ActivityOneItemBinding;

import java.util.LinkedHashMap;

public class OneItemActivity extends AppCompatActivity {

    ActivityOneItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOneItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        /**
         * Example how you can use filters to remove some banners that you want to ignore by some filter
         * It is important to call removeBannerByFilter before showing any of banners
         */
        LinkedHashMap<String, String> bannerFIlters = MsAdsSdk.getInstance().getBannerFilters("home_banners", "Banner for Florida");
        if(bannerFIlters.get("User status").equals("Ignore")){
            String response =  MsAdsSdk.getInstance().removeBannerByFilter("home_banners", "Banner for Florida");
            Log.i("response", response);
        }

        /**
         * Example showing how can be in list banner also shown anywhere else in application
         * use developer_id and send view in what you want to add content
         */
        MsAdsBanners.getInstance("home_banners", binding.getRoot());

        /**
         * Example showing how to get specific position via developer_id
         * InListPosition contains error parameter which is response if there is position or not
         * OK response is for existing position, or something else if there is no position
         */
        InListPosition banner = MsAdsBanners.getInListBanner("das_banne");

        if(banner.getError().equals("OK")){
            banner.show(binding.contentHolder);
        }else{
            Toast.makeText(getApplicationContext(), banner.getError(), Toast.LENGTH_LONG).show();
        }

    }
}