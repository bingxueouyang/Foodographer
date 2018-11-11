package com.foodie.foodographer;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class SearchBar extends AppCompatActivity {
    String name;
    String location;
    android.widget.SearchView nameSearch;
    android.widget.SearchView locationSearch;
    private FusedLocationProviderClient mFusedLocationClient;
    private static Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        nameSearch = findViewById(R.id.name_search);
        nameSearch.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                gotoSearch(name, location);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                name = nameSearch.getQuery().toString();
                return true;
            }
        });
        locationSearch = findViewById(R.id.location_search);
        locationSearch.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                gotoSearch(name, location);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                location = locationSearch.getQuery().toString();
                return true;
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        //createLocationRequest();
        //getLastLocation();
    }


    public void gotoSearch(String rest_name, String rest_location) {

        Intent intent = new Intent(SearchBar.this, SearchResult.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", rest_name);
        if(rest_location!=null&&!rest_location.equals("")) {
            Log.i("nulla","its not null"+rest_location);
            bundle.putString("location", rest_location);
        }
        else{
            createLocationRequest();
            getLastLocation();
        }
        /*else{
            bundle.putString("latitude", String.valueOf(Homepage.getmLocation().getLatitude()));
            bundle.putString("longitude", String.valueOf(Homepage.getmLocation().getLongitude()) );
        }*/
        intent.putExtras(bundle);
        startActivity(intent);
    }




    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    protected void createLocationRequest() {
        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                            mLocation = location;
                            //Log.i("lat",String.valueOf(location.getLatitude()));
                            //Log.i("long",String.valueOf(location.getLongitude()));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    public static Location getmLocation(){
        Log.i("longitude",String.valueOf(mLocation.getLongitude()));

        return mLocation;
    }
}