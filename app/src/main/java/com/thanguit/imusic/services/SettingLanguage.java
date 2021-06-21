package com.thanguit.imusic.services;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.activities.FullActivity;

import java.util.Locale;

public class SettingLanguage {
    private static SettingLanguage instance;
    private Context context;

    private SettingLanguage(Context context) {
        this.context = context;
        DataLocalManager.init(context);
    }

    public static SettingLanguage getInstance(Context context) {
        if (instance == null) {
            synchronized (SettingLanguage.class) {
                if (instance == null) {
                    instance = new SettingLanguage(context);
                }
            }
        }
        return instance;
    }

    public void UpdateLanguage() {
        Locale locale;

        if (DataLocalManager.getLanguage()) {
            locale = new Locale("en");
            Locale.setDefault(locale);
        } else {
            locale = new Locale("vn");
            Locale.setDefault(locale);
        }

        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

//        configuration.locale = locale; // Cấu hình lại ngôn ngữ hệ thống
//        getContext().getResources().updateConfiguration(configuration, getContext().getResources().getDisplayMetrics()); // Cập nhật lại strings.xml

//        Intent intent = new Intent(context, FullActivity.class);
//        context.startActivity(intent);
    }
}
