package com.example.kitty.myapplication.fragments;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kitty.myapplication.Helpers.DatabaseHelper;
import com.example.kitty.myapplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Date;

/**
 * Created by kitty on 8/18/16.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MapFragment";
    private static final long INTERVAL = 1000; //1 second
    private static final long FASTEST_INTERVAL = 1000; // 1 second

    private GoogleApiClient googleApiClient;
    private GoogleMap map;
    private Location priorLocation;
    private Location curLocation;
    private LocationRequest locationRequest;
    private SupportMapFragment supportMapFragment;
//    private View mapFragment;
    private FloatingActionButton startStopFab;
    private DatabaseHelper helper;

    private Date startTime;

    private boolean currentLocationShowing = false;
    private float distance = 0;
    private boolean tracking = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectGoogleAPIClient();
        getDatabaseHelper();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_maps, container, false);
        setViews(v);
        setOnClicks();
        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            this.curLocation = location;
            supportMapFragment.getMapAsync(this);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //region Maps Code

    /**
     * Called when map is ready for use
     *
     * Add a marker, animate and move camera
     *
     * Make API call to get business near location
     * @param googleMap GoogleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true); //permission checked earlier
        Log.d(TAG, "onMapReady: " + curLocation.getLatitude() + curLocation.getLongitude());
        if (!currentLocationShowing) {
            map.addMarker(new MarkerOptions().position(new LatLng(curLocation.getLatitude(), curLocation.getLongitude())).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            currentLocationShowing = true;
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(curLocation.getLatitude(), curLocation.getLongitude())));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(curLocation.getLatitude(), curLocation.getLongitude()), 15));
    }

    @Override
    public void onLocationChanged(Location location) {
        this.priorLocation = this.curLocation;
        this.curLocation = location;
        drawLine();
        addDistance();
    }

    //endregion


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            this.curLocation = location;
            supportMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStop() {
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    private void setViews(View v) {
        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.fragment_map);
//        mapFragment = v.findViewById(R.id.fragment_map);
        startStopFab = (FloatingActionButton) v.findViewById(R.id.fragment_map_start_stop_fab);
    }

    private void setOnClicks() {
        startStopFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tracking) {
                    stopTracking();
                    tracking = false;
                    // ToDo: add popup dialog to show stat
                } else {
                    startTracking();
                    startStopFab.setImageResource(R.drawable.cast_ic_notification_stop_live_stream);
                    tracking = true;
                }
            }
        });
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    // creating google API Client if does not yet exist and connect it
    private void connectGoogleAPIClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();
    }

    private void drawLine() {
        map.addPolyline((new PolylineOptions())
                        .add(new LatLng(priorLocation.getLatitude(), priorLocation.getLongitude()),
                                new LatLng(curLocation.getLatitude(), curLocation.getLongitude()))
                        .width(5)
                        .color(Color.BLUE)
                        .geodesic(true));
        // move camera to zoom on current location
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(curLocation.getLatitude(), curLocation.getLongitude()), 15));
    }

    private void addDistance() {
        float[] temp = new float[1];
        Location.distanceBetween(priorLocation.getLatitude(), priorLocation.getLongitude(),
                curLocation.getLatitude(), curLocation.getLongitude(), temp);
        distance += temp[0];
    }

    @TargetApi(24)
    private void startTracking() {
        createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this); //permission requested earlier
        this.startTime = Calendar.getInstance().getTime();
    }

    @TargetApi(24)
    private void stopTracking() {
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }

        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
        // to fix to detect local timezone
        sdfDate.setTimeZone(TimeZone.getTimeZone("PST"));
        String date = sdfDate.format(Calendar.getInstance().getTime());

        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        // to fix to detect local timezone
        sdfTime.setTimeZone(TimeZone.getTimeZone("PST"));
        String start = sdfTime.format(this.startTime);

        Date endTime = Calendar.getInstance().getTime();
        long timeDiff = endTime.getTime() - startTime.getTime();
        long diffSeconds = timeDiff / 1000 % 60;
        long diffMinutes = timeDiff / (60 * 1000) % 60;
        long diffHours = timeDiff / (60 * 60 * 1000) % 24;
        String diff = diffHours + ":" + diffMinutes + ":" + diffSeconds;
        helper.insertHistory(date, start, distance, diff);
        createDialog(date, start, diff);
    }

    @TargetApi(24)
    private void createDialog(String date, String startTime, String timeDiff) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(0);

        builder.setMessage("Date: " + date + "\n" + "Start Time: " + startTime + "\n" + "Total Distance: "
                + df.format(distance) + "m" + "\n" + "Total Time: " + timeDiff)
                .setTitle(R.string.dialog_title)
                .setCancelable(false)
                .setPositiveButton("View History", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // ToDo: to go history fragment
                        Toast.makeText(getActivity(), "to go history frag", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getDatabaseHelper() {
        if(helper == null) {
            helper = DatabaseHelper.getInstance(getActivity());
        }
    }

}
