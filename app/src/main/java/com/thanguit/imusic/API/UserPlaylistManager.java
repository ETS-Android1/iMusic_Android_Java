package com.thanguit.imusic.API;

import android.util.Log;

import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPlaylistManager {
    private static final String TAG = "UserPlaylistManager";
    private static UserPlaylistManager instance;

    private ArrayList<Song> favoriteSongArrayList = new ArrayList<>();


    public static UserPlaylistManager getInstance() {
        if (instance == null) {
            instance = new UserPlaylistManager();
        }
        return instance;
    }

    public List<Integer> GetID_FavoriteSong() {
        List<Integer> idFavoriteSong = new ArrayList<>();

        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Song>> callBack = dataService.getFavoriteSongUser(DataLocalManager.getUserID());
        callBack.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                favoriteSongArrayList = (ArrayList<Song>) response.body();

                if (favoriteSongArrayList != null) {
                    for (int i = 0; i < favoriteSongArrayList.size(); i++) {
                        idFavoriteSong.add(favoriteSongArrayList.get(i).getId());

                        Log.d(TAG, "ID bài hát yêu thích: " + idFavoriteSong.get(i).toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d(TAG, "GetID_FavoriteSong(Error): " + t.getMessage());
            }
        });

        return idFavoriteSong;
    }

    public boolean Is_Exist_Favorite_Song(int id) {
        List<Integer> idFavoriteSong = GetID_FavoriteSong();
        return idFavoriteSong.contains(id);
    }

}
