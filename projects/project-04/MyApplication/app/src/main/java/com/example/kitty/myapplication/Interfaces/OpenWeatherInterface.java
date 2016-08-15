package com.example.kitty.myapplication.Interfaces;

import com.example.kitty.myapplication.WeatherModels.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by jeanweatherwax on 7/13/16.
 */
public interface OpenWeatherInterface {

    @GET("/data/2.5/weather")
    Call<Model> getWeather(@Query("lat") String lat,
                           @Query("lon") String lon,
                           @Query("appid") String appid);

}
