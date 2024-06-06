package com.merritumdemo.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.meritumads.settings.MsAdsInListPosition;
import com.meritumads.settings.MsAdsBanners;
import com.merritumdemo.databinding.ActivityOneItemBinding;

public class OneItemActivity extends AppCompatActivity {

    ActivityOneItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOneItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        MsAdsInListPosition banner = MsAdsBanners.getInListBanner("das_banne");

        if(banner.getError().equals("OK")){
            banner.show(binding.contentHolder);
        }else{
            Toast.makeText(getApplicationContext(), banner.getError(), Toast.LENGTH_LONG).show();
        }

    }
}