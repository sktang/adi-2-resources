package dev.kitty.pawhood.Interfaces;

import dev.kitty.pawhood.models.weatherModels.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by jeanweatherwax on 7/13/16.
 */
public interface OpenWeatherInterface {

    @GET("/data/2.5/weather")
    Call<Model> getWeather(@Query("lat") double lat,
                           @Query("lon") double lon,
                           @Query("appid") String appid);

}
