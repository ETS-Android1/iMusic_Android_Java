package com.thanguit.imusic.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import com.thanguit.imusic.activities.FullActivity;
import com.thanguit.imusic.services.SettingLanguage;

import java.util.Locale;

public class SettingFragment extends Fragment {
    private static final String TAG = "SettingFragment";

    private SettingLanguage settingLanguage;

    private LottieAnimationView btnSwitchTheme;
    private TextView tvVietNamese;
    private TextView tvEnglish;

    private final String VIETNAMESE = "vi";
    private final String ENGLISH = "en";

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
        settingLanguage = SettingLanguage.getInstance(getContext());
        settingLanguage.Update_Language();

        this.btnSwitchTheme = view.findViewById(R.id.btnSwitchTheme);
        this.tvVietNamese = view.findViewById(R.id.tvVietNamese);
        this.tvEnglish = view.findViewById(R.id.tvEnglish);

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
            } else {
                DataLocalManager.setTheme(true);
                btnSwitchTheme.setMinAndMaxProgress(0.1f, 0.5f); // Tối
                btnSwitchTheme.playAnimation();
            }
        });

        this.tvVietNamese.setOnClickListener(v -> {
            DataLocalManager.setLanguage(true);
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorLight7));

            settingLanguage.Update_Language();

            Intent intent = new Intent(getContext(), FullActivity.class);
            startActivity(intent);
        });

        this.tvEnglish.setOnClickListener(v -> {
            DataLocalManager.setLanguage(false);
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorLight7));

            settingLanguage.Update_Language();

            Intent intent = new Intent(getContext(), FullActivity.class);
            startActivity(intent);
        });
    }

//    private void Update_Language() {
//        Locale locale;
//        if (DataLocalManager.getLanguage()) {
//            locale = new Locale(VIETNAMESE); // true: Tiếng Việt
//            Locale.setDefault(locale);
//        } else {
//            locale = new Locale(ENGLISH); // false: Tiếng Anh
//            Locale.setDefault(locale);
//        }
//        Resources resources = getActivity().getResources();
//        Configuration configuration = resources.getConfiguration();
//        configuration.setLocale(locale);
//        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//
//        Intent intent = new Intent(getContext(), FullActivity.class);
//        startActivity(intent);
//    }
}