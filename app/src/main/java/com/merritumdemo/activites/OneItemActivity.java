package com.merritumdemo.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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


        LinkedHashMap<String, String> listOfbanners = MsAdsSdk.getInstance().getInListBannersIds();

        MsAdsBanners.getInstance(listOfbanners.get("1"), binding.oneItem.getRoot());

    }
}