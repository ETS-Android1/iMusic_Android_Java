package com.thanguit.imusic.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.services.SettingLanguage;

public class AppCompat extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataLocalManager.init(this);

        SettingLanguage settingLanguage = new SettingLanguage(this);
        settingLanguage.Update_Language(DataLocalManager.getLanguage());
    }
}
