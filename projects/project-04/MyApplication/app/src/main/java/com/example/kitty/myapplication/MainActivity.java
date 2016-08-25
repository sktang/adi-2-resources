package com.example.kitty.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kitty.myapplication.Interfaces.OpenWeatherInterface;
import com.example.kitty.myapplication.WeatherModels.Model;
import com.example.kitty.myapplication.fragments.MainFragment;

import java.text.SimpleDateFormat;
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
        setContentView(R.layout.activity_main);

        setViews();
        setWeatherApi();
        setCallback();

        // if location permission isn't granted yet, prompt permission request
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }

        // if permission is already granted, then get location and weather
        getCurrentLocation();
        getCurrentWeather();

        startMainFragment();
    }

    //region Permissions

    /**
     * This method returns to us the results of our permission request
     *
     * @param requestCode  The original code we sent the request with. If this code doesn't match, its not our result.
     * @param permissions  Array of permissions in the order they were asked
     * @param grantResults Results for each permission ( granted or not granted ) in the same order of permission array
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        getCurrentLocation();
        getCurrentWeather();

        startMainFragment();

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //endregion Permissions

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

        if(fragmentManager.findFragmentByTag(MainFragment.parkFragTag) == null) {
            fragmentTransaction.add(R.id.fragment_container, mainFragment);
            fragmentTransaction.addToBackStack(fragTag);
            fragmentTransaction.commit();
        }

    }

    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //checked in onCreate
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
                    double fahrenheit = 1.8 * (currentTempCel - 273) + 32;
                    int fahrenheitInt = ((int) fahrenheit);
                    String temp = String.valueOf(fahrenheitInt);

                    //ToDo: figure out why degree symbol isn't showing
                    curTempTV.setText(temp + (char) 0x00B0);
                    updateSunriseSunset(sunriseTV, sunriseTime);
                    updateSunriseSunset(sunsetTV, sunsetTime);
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

    private void updateSunriseSunset(TextView textView, long time){
        long sunTimestamp = time * 1000L;
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        textView.setText(localDateFormat.format(sunTimestamp));
    }
}
