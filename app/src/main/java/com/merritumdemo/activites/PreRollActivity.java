package com.merritumdemo.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.meritumads.settings.MsAdsPreRolls;
import com.meritumads.settings.PreRollService;
import com.meritumads.settings.PreRollStatus;
import com.merritumdemo.R;
import com.merritumdemo.databinding.ActivityPreRollBinding;

public class PreRollActivity extends AppCompatActivity implements PreRollService {

    ActivityPreRollBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreRollBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MsAdsPreRolls.getInstance("welc_preroll", binding.prerollHolder, this);

    }

    @Override
    public void preRollVideoImageDelegate(String developerId, String response, String imgVideoId) {

        Log.i("preroll_response - " +response, imgVideoId);
        if(response.equals(PreRollStatus.ALL_CONTENT_FINISHED)){
            binding.prerollHolder.setVisibility(View.GONE);
        }else if(response.equals(PreRollStatus.IMAGE_FINISHED)){
            if(imgVideoId.equals("124")){
                //do something
            }
        }else if(response.equals(PreRollStatus.VIDEO_FINISHED)){

        }

    }
}