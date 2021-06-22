package com.thanguit.imusic.services;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

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

    public void Update_Language() {
        Locale locale;
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        if (DataLocalManager.getLanguage()) {
            locale = new Locale("vi"); // true: Tiếng Việt
            Locale.setDefault(locale);
        } else {
            locale = new Locale("en"); // false: Tiếng Anh
            Locale.setDefault(locale);
        }

        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

//        Configuration configuration = new Configuration();
//        configuration.locale = locale;
//        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}
