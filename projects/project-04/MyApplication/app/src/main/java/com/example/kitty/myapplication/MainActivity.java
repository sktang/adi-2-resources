package com.example.kitty.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kitty.myapplication.Interfaces.OpenWeatherInterface;
import com.example.kitty.myapplication.models.weatherModels.Main;
import com.example.kitty.myapplication.models.weatherModels.Model;
import com.example.kitty.myapplication.fragments.MainFragment;
import com.example.kitty.myapplication.services.Firebase;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String baseURL = "http://api.openweathermap.org/";
    private static final String appid = "49cfc0e9ac185a3c66fe59313e33e0ed";
    private static final String fragTag = "fragTag";

//    private FirebaseAnalytics firebaseAnalytics;
    private OpenWeatherInterface openWeatherInterface;
    private Callback<Model> callback;
    private TextView curTempTV, sunriseTV, sunsetTV;

    private FirebaseDatabase database;
    private DatabaseReference msgRef;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setWeatherApi();
        setCallback();

        // if location permission isn't granted yet, prompt permission request
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }

        // if permission is already granted, then get location and weather
        getCurrentLocation();
        startMainFragment();

        setFirebase();
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
        startMainFragment();

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //endregion Permissions

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:

                startMainFragment();

                return true;

            case R.id.action_alert:

                sendAlert();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void setViews() {
        curTempTV = (TextView) findViewById(R.id.activity_main_temperature_tv);
        sunriseTV = (TextView) findViewById(R.id.activity_main_sunrise_time_tv);
        sunsetTV = (TextView) findViewById(R.id.activity_main_sunset_time_tv);
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void startMainFragment() {
        // create a new main fragment
        MainFragment mainFragment = new MainFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentManager.findFragmentByTag(MainFragment.parkFragTag) == null) {
            fragmentTransaction.add(R.id.fragment_container, mainFragment);
            fragmentTransaction.addToBackStack(fragTag);
            fragmentTransaction.commit();
        }

    }

    private void getCurrentLocation() throws SecurityException {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //checked in onCreate
        if (location == null) {
            locationManager.requestSingleUpdate(
                    LocationManager.GPS_PROVIDER, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            getCurrentWeather(location);
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    }, Looper.myLooper());
        }
    }

    private void getCurrentWeather(Location location) {
        if (location == null) {
            return;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()){
            Toast.makeText(MainActivity.this, R.string.failed_to_connect, Toast.LENGTH_LONG).show();
            return;
        }
        openWeatherInterface.getWeather(location.getLongitude(), location.getLatitude(), appid).enqueue(callback);
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

    private void setFirebase() {
//        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "8");
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "MainCreated");
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
//        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        database = FirebaseDatabase.getInstance();
    }

    private void sendAlert() {
        Toast.makeText(MainActivity.this, R.string.feature_not_available, Toast.LENGTH_SHORT).show();
    }
}
