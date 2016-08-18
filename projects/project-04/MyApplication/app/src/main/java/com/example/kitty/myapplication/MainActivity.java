package com.example.kitty.myapplication;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kitty.myapplication.Interfaces.OpenWeatherInterface;
import com.example.kitty.myapplication.WeatherModels.Model;
import com.example.kitty.myapplication.fragments.MainFragment;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String baseURL = "http://api.openweathermap.org/";
    private static final String appid = "49cfc0e9ac185a3c66fe59313e33e0ed";
    private static final String fragTag = "fragTag";

    private OpenWeatherInterface openWeatherInterface;
    private Callback<Model> callback;

    private TextView curTempTV, sunriseTV, sunsetTV;

    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setViews();
        setWeatherApi();
        setCallback();

        getCurrentLocation();
        getCurrentWeather();

        startMainFragment();
    }

    private void setViews() {
        curTempTV = (TextView) findViewById(R.id.activity_main_temperature_tv);
        sunriseTV = (TextView) findViewById(R.id.activity_main_sunrise_time_tv);
        sunsetTV = (TextView) findViewById(R.id.activity_main_sunset_time_tv);
    }

    private void startMainFragment() {
        // create a new main fragment
        MainFragment mainFragment = new MainFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_container, mainFragment);
        fragmentTransaction.addToBackStack(fragTag);
        fragmentTransaction.commit();
    }

    private void getCurrentLocation() {
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    private void getCurrentWeather() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()){
            Toast.makeText(MainActivity.this, R.string.failed_to_connect, Toast.LENGTH_LONG).show();
            return;
        }
        openWeatherInterface.getWeather(latitude, longitude, appid).enqueue(callback);
    }

    private void setWeatherApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeatherInterface = retrofit.create(OpenWeatherInterface.class);
    }

    private void setCallback(){
        callback = new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                try {
                    double currentTempCel = response.body().getMain().getTemp().doubleValue();
                    long sunriseTime = response.body().getSys().getSunrise();
                    long sunsetTime = response.body().getSys().getSunset();

                    //ToDo: update to have option of Feh or Cel
                    //String tempString = String.valueOf(Util.getFeh(currentTempCel));
                    curTempTV.setText(String.valueOf(currentTempCel) + (char) 0x00B0);
                    updateSunriseSunset(sunriseTV, getString(R.string.sunrise), sunriseTime);
                    updateSunriseSunset(sunsetTV, getString(R.string.sunset), sunsetTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                t.printStackTrace();
            }
        };
    }

    private void updateSunriseSunset(TextView textView, String prefix, long time){
        long sunsetTimestamp = time * 1000L; // TODO check if words without the 1000L
        Date sunsetDate = new Date(sunsetTimestamp);
        textView.setText(prefix + String.valueOf(sunsetDate) );
    }
}
