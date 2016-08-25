package com.example.kitty.myapplication.Helpers;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.kitty.myapplication.R;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kitty on 8/16/16.
 */
public class YelpHelper {

    //yelpAPI call info
    private String consumerKey = "fhgAMIfQfd7Butv1o149fg";
    private String consumerSecret = "utJEKI87sRfKIPY-A5CbMJCDim0";
    private String token = "U4CJZMBLYHIZnuuYHHTCCJZberzUp7hQ";
    private String tokenSecret = "Zmu_txwCCLJrRSeDAcXm_Q7mLpo";

    YelpAPI yelpAPI;
    Context context;
    ArrayList<Business> resultsBusinesses;
    private static OnResponseFinished onResponseFinished;

    //boolean to check retrofit synchronicity
    public interface OnResponseFinished {
        void onBusinessReceived(ArrayList<Business> buisnesses);

    }


    public YelpHelper(Context context, OnResponseFinished onResponseFinished) {
        YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token, tokenSecret);
        yelpAPI = apiFactory.createAPI();
        this.onResponseFinished = onResponseFinished;
        this.context = context;
    }


    //Gets Buisnesses by Params
    public void getBusinesses(Map<String, String> params, Location location) {
        CoordinateOptions coordinateOptions = CoordinateOptions.builder().latitude(location.getLatitude()).longitude(location.getLongitude()).build();

        Log.d("lat and lon for Busis", location.getLatitude() + " ," + location.getLongitude());
        Call<SearchResponse> call = yelpAPI.search(coordinateOptions, params);

        call.enqueue(new Callback<SearchResponse>() {

            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                resultsBusinesses = searchResponse.businesses();
                onResponseFinished.onBusinessReceived(resultsBusinesses);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(context, R.string.failed_to_connect, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
