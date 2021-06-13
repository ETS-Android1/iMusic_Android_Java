package com.thanguit.imusic.API;

import com.thanguit.imusic.models.Album;
import com.thanguit.imusic.models.Playlist;
import com.thanguit.imusic.models.Slider;
import com.thanguit.imusic.models.Song;
import com.thanguit.imusic.models.Status;
import com.thanguit.imusic.models.Theme;
import com.thanguit.imusic.models.User;
import com.thanguit.imusic.models.UserPlaylist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {

    @GET("slider.php")
    Call<List<Slider>> getSlider();

    @GET("playlistforcurrentday.php")
    Call<List<Playlist>> getPlaylistCurrentDay();

    @GET("themecurrentday.php")
    Call<List<Theme>> getThemeCurrentDay();

    @GET("albumcurrentday.php")
    Call<List<Album>> getAlbumCurrentDay();

    @GET("songlove.php")
    Call<List<Song>> getSongChart();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Song>> getSongWithPlaylist(@Field("Pla_ID") int id);

    @FormUrlEncoded
    @POST("searchsong.php")
    Call<List<Song>> getSongSearch(@Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("getuserfromid.php")
    Call<List<User>> getUserFromID(@Field("id") String id);

    @FormUrlEncoded
    @POST("addnewuser.php")
    Call<List<User>> addNewUser(@Field("id") String id, @Field("name") String name, @Field("email") String email, @Field("img") String img, @Field("isDark") String isDark, @Field("isEnglish") String isEnglish);

    @FormUrlEncoded
    @POST("getfavoritesongfromid.php")
    Call<List<Song>> getFavoriteSongUser(@Field("id") String id);

    @FormUrlEncoded
    @POST("adddeletefavoritesong.php")
    Call<List<Status>> addDeleteFavoriteSong(@Field("userID") String userID, @Field("songID") int songID);

    @FormUrlEncoded
    @POST("getuserplaylist.php")
    Call<List<UserPlaylist>> getUserPlaylist(@Field("userID") String userID);

    @FormUrlEncoded
    @POST("addupdatedeleteuserplaylist.php")
    Call<List<Status>> addUpdateDeleteUserPlaylist(@Field("action") String action, @Field("playlistID") int playlistID, @Field("userID") String userID, @Field("playlistName") String playlistName);

    @FormUrlEncoded
    @POST("getsonguserplaylist.php")
    Call<List<Song>> getSongUserPlaylist(@Field("userID") String userID, @Field("playlistID") int playlistID);
}
