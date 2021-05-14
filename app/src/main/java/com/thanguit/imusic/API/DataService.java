package com.thanguit.imusic.API;

import com.thanguit.imusic.models.Slider;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET("slider.php")
    Call<List<Slider>> getSlider();
}
