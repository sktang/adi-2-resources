package dev.kitty.pawhood.fragments;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.kitty.pawhood.helpers.YelpHelper;
import dev.kitty.pawhood.R;
import dev.kitty.pawhood.adapters.RecyclerViewAdapter;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kitty on 8/22/16.
 */
public class ParksFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, YelpHelper.OnResponseFinished, RecyclerViewAdapter.OnRecyclerViewItemClickListener {

    private GoogleApiClient googleApiClient;
    private Location location;
    private LocationRequest locationRequest = new LocationRequest();
    private DetailFragment detailFragment;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;
    private boolean mapVisible;
    private boolean currentLocationShowing = false;
    private boolean dogParksOnlyFilter = false;

    private static final String TAG = "ParksFragment";

    private FloatingActionButton fab;
    private FloatingActionButton filterFab;
    private GoogleMap map;

    private SupportMapFragment supportMapFragment;
    private View mapFragment;
    private YelpHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectGoogleAPIClient();
        helper = new YelpHelper(getActivity(), ParksFragment.this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_parks, container, false);
        setViews(v);
        setOnClicks();
        mapVisible = true;
        return v;
    }

    private void setViews(View v) {
        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.fragment_parks_map);
        recyclerView = (RecyclerView) v.findViewById(R.id.fragment_parks_recycler_view);
        Log.d("recyclerview", recyclerView + "");
        mapFragment = v.findViewById(R.id.fragment_parks_map);
        Log.d("mapfrag", mapFragment + "");
        fab = (FloatingActionButton) v.findViewById(R.id.fragment_parks_fab);
        filterFab = (FloatingActionButton) v.findViewById(R.id.fragment_parks_filter_fab);
    }


    private void setOnClicks() {
        Log.d("onclick", "onclick");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchingBtwListMap();
            }
        });
        filterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateParkType();
            }
        });
    }

    // region Google Maps

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true); //permission checked earlier
        Log.d(TAG, "onMapReady: " + location.getLatitude() + location.getLongitude());
        if (!currentLocationShowing) {
            map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            currentLocationShowing = true;
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
        HashMap<String, String> params = new HashMap<>();
        String searchTerm = dogParksOnlyFilter ? "dog parks" : "parks";
        params.put("term", searchTerm);
        helper.getBusinesses(params, location);
    }

    // endregion


    // region Permission

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
            this.location = location;
            supportMapFragment.getMapAsync(this);
        }
        map.setMyLocationEnabled(true);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // endregion


    // region Google Location Connection

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("onConnected", "yes!!!");
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            this.location = location;
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

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    // endregion


    // region Yelp API

    @Override
    public void onBusinessReceived(ArrayList<Business> businesses) {
        for (int i = 0; i < businesses.size(); i++) {
            Marker currentMarker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(businesses.get(i).location().coordinate().latitude(), businesses.get(i).location().coordinate().longitude()))
                    .title(businesses.get(i).name())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            currentMarker.setTag(businesses.get(i));
        }

        rvLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(rvLayoutManager);
        rvAdapter = new RecyclerViewAdapter(businesses, this);
        recyclerView.setAdapter(rvAdapter);

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (!marker.getTitle().equals("Current Location")) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    if (detailFragment == null) {
                        detailFragment = new DetailFragment();
                    }

                    detailFragment.setCurrentPark((Business) marker.getTag());
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, detailFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    // endregion

    // region Recycler View

    private void switchingBtwListMap() {
        if (mapVisible) {
            mapFragment.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mapVisible = false;
            fab.setImageResource(R.drawable.ic_map_white_24dp);
        } else {
            recyclerView.setVisibility(View.GONE);
            mapFragment.setVisibility(View.VISIBLE);
            mapVisible = true;
            fab.setImageResource(R.drawable.ic_view_list_white_24dp);
        }
    }

    @Override
    public void onItemClick(Business currentBusiness) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (detailFragment == null) {
            detailFragment = new DetailFragment();
        }

        detailFragment.setCurrentPark(currentBusiness);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // endregion

    private void updateParkType() {
        map.clear();
        map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title(getString(R.string.current_location_string)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        HashMap<String, String> params = new HashMap<>();
        if (dogParksOnlyFilter) {
            params.put("term", getString(R.string.allPark));
            helper.getBusinesses(params, location);
            filterFab.setImageResource(R.drawable.ic_pets_white_24dp);
        } else {
            params.put("term", getString(R.string.dogPark));
            helper.getBusinesses(params, location);
            filterFab.setImageResource(R.drawable.ic_landscape_white_24dp);
        }
        dogParksOnlyFilter = !dogParksOnlyFilter;
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



//    public long getPlaceId(Business currentBusiness) {
//
//        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//        List<Address> address = null;
//
//        if (fetchType == USE_ADDRESS_NAME) {
//            try {
//                geocoder.getFromLocation(currentBusiness.location().coordinate().latitude(), currentBusiness.location().coordinate().longitude(), 1);
//            } catch (IOException e) {
//                Log.e(TAG, getString(R.string.service_not_available_string), e);
//            }
//        } else {
//            Log.e(TAG, getString(R.string.unknown_type_string));
//        }
//
//        if (address != null && address.size() > 0)
//            return address.get(0).;
//
//        return null;
//
//
//
//        geocoder.geocode({'location': latlng}, function(results, status) {
//            if (status === google.maps.GeocoderStatus.OK) {
//                if (results[1]) {
//                    console.log(results[1].place_id);
//                } else {
//                    window.alert('No results found');
//                }
//            } else {
//                window.alert('Geocoder failed due to: ' + status);
//            }
//        });
//
//    }
}
