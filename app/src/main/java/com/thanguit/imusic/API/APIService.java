package com.thanguit.imusic.API;

public class APIService { // Class này là class trung gian sẽ kết nối 2 class kia lại với nhau
    private static final String url = "https://imusicapi.000webhostapp.com/Server/"; // Server chùa của mình nè Hihi 😂

    public static DataService getService() { // Dữ liệu trả về cho DataService
        // Gửi cấu hình lên
        return APIRetrofitClient.getClient(url).create(DataService.class);
    }
}