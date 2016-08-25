//package com.example.kitty.myapplication.fragments;
//
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//
//import com.example.kitty.myapplication.R;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
///**
// * Created by kitty on 8/18/16.
// */
//public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {
//
//    private static final String TAG = "LocationActivity";
//    private static final long INTERVAL = 1000; //1 second
//    private static final long FASTEST_INTERVAL = 1000; // 1 second
//
//    private GoogleApiClient googleApiClient;
//    private GoogleMap map;
//    private Location currentLocation;
//    private LocationRequest locationRequest;
//
//    private boolean curLocationShowing = false;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) this.getActivity().getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        map.setMyLocationEnabled(true);
//
//    }
//
//    /**
//     *
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        if (location == null) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
//        } else {
//            currentLocation = location;
//            mfrag.getMapAsync(this);
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    //region Maps Code
//
//    /**
//     * Called when map is ready for use
//     *
//     * Add a marker, animate and move camera
//     *
//     * Make API call to get business near location
//     * @param googleMap GoogleMap
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map = googleMap;
//
//        // Refactor into setupMap method
//        if (!curLocationShowing) {
//            map.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//            curLocationShowing = true;
//        }
//        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())));
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15));
//
//        // ToDo: need to call YelpAPI and GoogleLocationAPI using mLocation
//    }
//
//    /**
//     *
//     * @param location
//     */
//    @Override
//    public void onLocationChanged(Location location) {
//        location = location;
//    }
//
//    //endregion
//
//    protected void createLocationRequest() {
//        locationRequest = new LocationRequest();
//        locationRequest.setInterval(INTERVAL);
//        locationRequest.setFastestInterval(FASTEST_INTERVAL);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    }
//}
