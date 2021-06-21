package com.thanguit.imusic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.thanguit.imusic.R;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;


public class SettingFragment extends Fragment {
    private LottieAnimationView btnSwitchTheme;
    private TextView tvVietNamese;
    private TextView tvEnglish;

    private TextView tvTheme;
    private TextView tvLanguage;

//    private SettingLanguage settingLanguage;
//
//    private final String VIETNAMESE = "vi";
//    private final String ENGLISH = "en";

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
        Update_Language();
    }

    public void Mapping(View view) {
        this.btnSwitchTheme = view.findViewById(R.id.btnSwitchTheme);
        this.tvVietNamese = view.findViewById(R.id.tvVietNamese);
        this.tvEnglish = view.findViewById(R.id.tvEnglish);
        this.tvTheme = view.findViewById(R.id.tvTheme);
        this.tvLanguage = view.findViewById(R.id.tvLanguage);

        if (DataLocalManager.getTheme()) {
            this.btnSwitchTheme.setMinAndMaxProgress(0.5f, 1.0f); // Tối
        } else {
            this.btnSwitchTheme.setMinAndMaxProgress(0.1f, 0.5f); // Sáng
        }

        if (DataLocalManager.getLanguage()) {
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorLight7));
        } else {
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorLight7));
        }

        Update_Language();
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
            DataLocalManager.setLanguage(false);
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorLight7));

            Update_Language();
        });

        this.tvEnglish.setOnClickListener(v -> {
            DataLocalManager.setLanguage(true);
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorLight7));

            Update_Language();
        });
    }

    private void Update_Language() {
        if (DataLocalManager.getLanguage()) {
            this.tvTheme.setText(getString(R.string.entvTheme));
            this.tvLanguage.setText(getString(R.string.entvLanguage));
        } else {
            this.tvTheme.setText(getString(R.string.tvTheme));
            this.tvLanguage.setText(getString(R.string.tvLanguage));
        }
    }

//    private void SettingLanguage() {
//        Locale locale;
//
//        if (DataLocalManager.getLanguage()) {
//            locale = new Locale(ENGLISH);
//            Locale.setDefault(locale);
//        } else {
//            locale = new Locale(VIETNAMESE);
//            Locale.setDefault(locale);
//        }
//
//        Resources resources = getActivity().getResources();
//        Configuration configuration = resources.getConfiguration();
//        configuration.setLocale(locale);
//        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//
////        configuration.locale = locale; // Cấu hình lại ngôn ngữ hệ thống
////        getContext().getResources().updateConfiguration(configuration, getContext().getResources().getDisplayMetrics()); // Cập nhật lại strings.xml
//
//        Intent intent = new Intent(getContext(), FullActivity.class);
//        startActivity(intent);
//    }
}