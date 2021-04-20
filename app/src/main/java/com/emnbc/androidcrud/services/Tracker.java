package com.emnbc.androidcrud.services;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.List;

public class Tracker {

    private LayoutInflater mInflater;
    private Activity mActivity;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public Tracker(Activity activity) {
        mInflater = LayoutInflater.from(activity);
        mActivity = activity;

        locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new TrackLocationListener();
    }

    public void startGettingLocation() {
        if (ActivityCompat.checkSelfPermission(mInflater.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mInflater.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
        }
    }

    public void stopGettingLocation() {
        List<String> providers = locationManager.getAllProviders();
        locationManager.removeUpdates(locationListener);
    }


    class TrackLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if(location != null){
                String longitude = "Longitude: " + location.getLongitude();
                Log.v("COORDINATES", longitude);
                String latitude = "Latitude: " + location.getLatitude();
                Log.v("COORDINATES", latitude);

                Toast.makeText(mInflater.getContext(), longitude + ", " + latitude, Toast.LENGTH_SHORT).show();
            }
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
    }
}
