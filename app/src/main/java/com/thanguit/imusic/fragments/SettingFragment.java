package com.thanguit.imusic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.thanguit.imusic.R;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;

public class SettingFragment extends Fragment {
    private LottieAnimationView btnSwitchTheme;
    private TextView tvVietNamese;
    private TextView tvEnglish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DataLocalManager.init(getContext());

        Mapping(view);
        Event();
    }

    public void Mapping(View view) {
        this.btnSwitchTheme = view.findViewById(R.id.btnSwitchTheme);
        this.tvVietNamese = view.findViewById(R.id.tvVietNamese);
        this.tvEnglish = view.findViewById(R.id.tvEnglish);

        if (DataLocalManager.getTheme()) {
            this.btnSwitchTheme.setMinAndMaxProgress(0.5f, 1.0f); // Tối
        } else {
            this.btnSwitchTheme.setMinAndMaxProgress(0.1f, 0.5f); // Sáng
        }
    }

    private void Event() {
        this.btnSwitchTheme.setOnClickListener(v -> {
            if (DataLocalManager.getTheme()) {
                this.btnSwitchTheme.setMinAndMaxProgress(0.5f, 1.0f); // Tối
                this.btnSwitchTheme.playAnimation();
                DataLocalManager.setTheme(false);
            } else {
                this.btnSwitchTheme.setMinAndMaxProgress(0.1f, 0.5f); // Sáng
                this.btnSwitchTheme.playAnimation();
                DataLocalManager.setTheme(true);
            }
        });

        this.tvVietNamese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        this.tvEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}