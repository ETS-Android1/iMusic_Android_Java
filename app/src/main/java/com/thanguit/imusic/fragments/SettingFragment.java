package com.thanguit.imusic.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.thanguit.imusic.R;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.activities.FullActivity;
import com.thanguit.imusic.services.SettingLanguage;

import java.util.Locale;

public class SettingFragment extends Fragment {
    private static final String TAG = "SettingFragment";

    private SettingLanguage settingLanguage = SettingLanguage.getInstance(getContext());

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

    @Override
    public void onResume() {
        super.onResume();
    }

    public void Mapping(View view) {
        settingLanguage.Update_Language();

        this.btnSwitchTheme = view.findViewById(R.id.btnSwitchTheme);
        this.tvVietNamese = view.findViewById(R.id.tvVietNamese);
        this.tvEnglish = view.findViewById(R.id.tvEnglish);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            this.tvVietNamese.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Can't change another language, because the device is out of date!", Toast.LENGTH_LONG).show();
        }


        if (DataLocalManager.getTheme()) {
            this.btnSwitchTheme.setMinAndMaxProgress(0.5f, 1.0f); // Tối
        } else {
            this.btnSwitchTheme.setMinAndMaxProgress(0.1f, 0.5f); // Sáng
        }

        if (DataLocalManager.getLanguage()) {
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorLight7));
        } else {
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorLight7));
        }
    }

    private void Event() {
        this.btnSwitchTheme.setOnClickListener(v -> {
            if (DataLocalManager.getTheme()) {
                DataLocalManager.setTheme(false);
                btnSwitchTheme.setMinAndMaxProgress(0.65f, 1.0f); // Sáng
                btnSwitchTheme.playAnimation();

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                DataLocalManager.setTheme(true);
                btnSwitchTheme.setMinAndMaxProgress(0.1f, 0.5f); // Tối
                btnSwitchTheme.playAnimation();

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

        this.tvVietNamese.setOnClickListener(v -> {
            DataLocalManager.setLanguage(true);

            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorLight7));

            Log.d(TAG, "VietNam: " + DataLocalManager.getLanguage());

            settingLanguage.Update_Language();

            getActivity().recreate();
        });

        this.tvEnglish.setOnClickListener(v -> {
            DataLocalManager.setLanguage(false);
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorLight7));

            Log.d(TAG, "English: " + DataLocalManager.getLanguage());

            settingLanguage.Update_Language();

            getActivity().recreate();

//            Intent intent = new Intent(getContext(), FullActivity.class);
//            startActivity(intent);
        });
    }
}