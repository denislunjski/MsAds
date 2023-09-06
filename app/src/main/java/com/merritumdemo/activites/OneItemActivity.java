package com.merritumdemo.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.meritumads.settings.MsAdsBanner;
import com.meritumads.settings.MsAdsSdk;
import com.merritumdemo.R;
import com.merritumdemo.adapters.InListBannerItem;
import com.merritumdemo.adapters.SimpleItem;
import com.merritumdemo.databinding.ActivityOneItemBinding;

import java.util.LinkedHashMap;
import java.util.Map;

public class OneItemActivity extends AppCompatActivity {

    ActivityOneItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOneItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        LinkedHashMap<String, String> listOfbanners = MsAdsSdk.getInstance().getInListBannersIds();

        MsAdsBanner.getInstance(listOfbanners.get("1"), binding.oneItem.getRoot());

    }
}