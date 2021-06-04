package com.thanguit.imusic.SharedPreferences;

import android.content.Context;

public class DataLocalManager {
    private static DataLocalManager instance;
    private SharedPreferencesManager sharedPreferencesManager;

    private static final String IS_LOGIN = "IS_LOGIN";

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.sharedPreferencesManager = new SharedPreferencesManager(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setLogin(boolean isLogin) {
        DataLocalManager.getInstance().sharedPreferencesManager.putBooleanValue(IS_LOGIN, isLogin);
    }

    public static boolean getLogin() {
        return DataLocalManager.getInstance().sharedPreferencesManager.getBooleanValue(IS_LOGIN);
    }
}
