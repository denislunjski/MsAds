package com.merritumdemo.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.meritumads.settings.MsAdsPreRolls;
import com.merritumdemo.R;
import com.merritumdemo.databinding.ActivityPreRollBinding;

public class PreRollActivity extends AppCompatActivity {

    ActivityPreRollBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreRollBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MsAdsPreRolls.getInstance("2", binding.prerollHolder);

    }
}