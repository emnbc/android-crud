package com.emnbc.androidcrud.ui.notifications;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.emnbc.androidcrud.MainActivity;
import com.emnbc.androidcrud.R;
import com.emnbc.androidcrud.services.RunTimer;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private RunTimer runTimer;
    private Button btn;
    private Button btnCrd;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String startSnd;
    private String stopSnd;
    private int colorGreen;
    private int colorRed;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);

        startSnd = getResources().getString(R.string.sending_state_start);
        stopSnd = getResources().getString(R.string.sending_state_stop);

        colorGreen = ContextCompat.getColor(getContext(), R.color.color_green);
        colorRed = ContextCompat.getColor(getContext(), R.color.color_red);

        runTimer = ((MainActivity) getActivity()).getRunTimer();
        btn = (Button) root.findViewById(R.id.buttonToggle);
        btnCrd = (Button) root.findViewById(R.id.buttonCoord);
        initBtn(btn, runTimer.getStatus());

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        ////// start location
//        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new myLocationListener();
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//        } else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
//        }

//        getLocation();

        ////// end location

        addListenerOnButtons(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationManager.removeUpdates(locationListener);
    }

    public void addListenerOnButtons(View v) {

        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (runTimer.getStatus()) {
                            runTimer.stopSending();
                            btn.setText(startSnd);
                            btn.setBackgroundColor(colorGreen);
                        } else {
                            runTimer.startSending();
                            btn.setText(stopSnd);
                            btn.setBackgroundColor(colorRed);
                        }

                    }
                }
        );

        btnCrd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getLocation();

                    }
                }
        );


    }

    private void initBtn(Button btn, Boolean state) {
        if (state) {
            btn.setText(stopSnd);
            btn.setBackgroundColor(colorRed);
        } else {
            btn.setText(startSnd);
            btn.setBackgroundColor(colorGreen);
        }
    }

    private void getLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
        }
    }
}

class MyLocationListener implements LocationListener {
    private LayoutInflater mInflater;

    public MyLocationListener(Activity activity) {
        mInflater = LayoutInflater.from(activity);
    }

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