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
import java.util.Map;

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

        LinkedHashMap<String, String> listOfbanners = MsAdsSdk.getInstance().getInListBannersIds();

        for(int i = 0; i < 100; i++){
            mainAdapter.addItem(new SimpleItem());
        }


        for(Map.Entry<String, String> entry: listOfbanners.entrySet()){
            mainAdapter.addItem(Integer.parseInt(entry.getKey()), new InListBannerItem(entry.getValue()));
        }
    }
}