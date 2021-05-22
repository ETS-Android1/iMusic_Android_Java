package com.thanguit.imusic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.thanguit.imusic.R;

public class SettingFragment extends Fragment {

    private LottieAnimationView btnSwitchTheme;
    private boolean switchTheme = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapping(view);
        Event();
    }

    public void Mapping(View view) {
        this.btnSwitchTheme = (LottieAnimationView) view.findViewById(R.id.btnSwitchTheme);
    }

    private void Event() {
        this.btnSwitchTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchTheme) {
                    btnSwitchTheme.setMinAndMaxProgress(0.65f, 1.0f);
                    btnSwitchTheme.playAnimation();
                    switchTheme = false;
                } else {
                    btnSwitchTheme.setMinAndMaxProgress(0.1f, 0.5f);
                    btnSwitchTheme.playAnimation();
                    switchTheme = true;
                }
            }
        });
    }
}