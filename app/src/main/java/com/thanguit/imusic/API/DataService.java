package com.thanguit.imusic.API;

import com.thanguit.imusic.models.Album;
import com.thanguit.imusic.models.Playlist;
import com.thanguit.imusic.models.Slider;
import com.thanguit.imusic.models.Theme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET("slider.php")
    Call<List<Slider>> getSlider();

    @GET("playlistforcurrentday.php")
    Call<List<Playlist>> getPlaylistCurrentDay();

    @GET("themecurrentday.php")
    Call<List<Theme>> getThemeCurrentDay();

    @GET("albumcurrentday.php")
    Call<List<Album>> getAlbumCurrentDay();
}
