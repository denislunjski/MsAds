package com.merritumdemo.activites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.meritumads.settings.MsAdsSdk;
import com.merritumdemo.adapters.InListBannerItem;
import com.merritumdemo.adapters.MainAdapter;
import com.merritumdemo.adapters.SimpleItem;
import com.merritumdemo.databinding.ActivityListBinding;

import java.util.LinkedHashMap;

public class ListActivity extends AppCompatActivity {

    ActivityListBinding binding;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainAdapter = new MainAdapter(binding.recycler);
        binding.recycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        binding.recycler.setItemAnimator(new DefaultItemAnimator());
        binding.recycler.setAdapter(mainAdapter);

        addData();

    }

    private void addData() {

        /**
         * Example showing how can in list banners be shown in reyclerview
         * first parameter is developer_id from admin setup
         * second parameter is in_list_position from admin setup
         * in this example in_list_position is used as sort in list
         */
        LinkedHashMap<String, Integer> inListBanners = MsAdsSdk.getInstance().getInListBannersIds();

        for(int i = 0; i < 100; i++){
            mainAdapter.addItem(new SimpleItem());
        }


        mainAdapter.addItem(inListBanners.get("csh_top_home_banner"), new InListBannerItem("csh_top_home_banner"));

        //popraviti bug ako nema id taj u xml-u
        //mainAdapter.addItem(inListBanners.get("home_banners"), new InListBannerItem("home_banners"));

        //mainAdapter.addItem(inListBanners.get("das_banne"), new InListBannerItem("das_banne"));


    }
}