package com.thanguit.imusic.API;

public class APIService {
    private static String url = "https://imusicapi.000webhostapp.com/Server/";

    public static DataService getService() {
        return APIRetrofitClient.getClient(url).create(DataService.class);
    }
}
