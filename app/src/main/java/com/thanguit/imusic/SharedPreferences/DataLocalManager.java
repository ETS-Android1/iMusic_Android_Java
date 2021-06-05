package com.thanguit.imusic.SharedPreferences;

import android.content.Context;

public class DataLocalManager {
    private static DataLocalManager instance;
    private SharedPreferencesManager sharedPreferencesManager;

    private static final String USER_ID = "USER_ID";

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

    public static void deleteAllData(){
        DataLocalManager.getInstance().sharedPreferencesManager.deleteAllData();
    }

    public static void setUserID(String userID) {
        DataLocalManager.getInstance().sharedPreferencesManager.putStringValue(USER_ID, userID);
    }

    public static String getUserID() {
        return DataLocalManager.getInstance().sharedPreferencesManager.getStringValue(USER_ID);
    }
}
