package com.merritumdemo.activites;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meritumads.settings.MsAdsSdk;
import com.merritumdemo.R;
import com.merritumdemo.databinding.FragmentTestActivityBinding;


public class TestActivityFragment extends DialogFragment {


    FragmentTestActivityBinding binding;

    public TestActivityFragment() {
        // Required empty public constructor
    }


    public static TestActivityFragment newInstance(String param1, String param2) {
        TestActivityFragment fragment = new TestActivityFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding = FragmentTestActivityBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MsAdsSdk.getInstance().resumeVideo("csh_news_banner");
    }
}