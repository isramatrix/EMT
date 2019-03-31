package com.emt.sostenible.logic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.here.android.mpa.common.GeoCoordinate;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class LocationService {

    private static LocationService Instance;

    private final Context context;

    private final Activity activity;

    private LocationManager locationManager;

    private final List<Listener> listenerList;

    private Location actualLocation;

    public static LocationService getInstance(Activity activity) {
        if (Instance == null) Instance = new LocationService(activity);
        return Instance;
    }

    private LocationService(Activity activity) {

        this.activity = activity;
        this.context = activity.getApplicationContext();

        if (askForPermissions()) {
           initializeLocationGranter();
        }

        listenerList = new ArrayList<>();
    }

    public boolean askForPermissions()
    {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
         && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 0);

            return false;

        }

        if (locationManager == null) locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return true;
    }

    private void initializeLocationGranter() throws SecurityException
    {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                for (Listener l : listenerList)
                    l.allocate(location.getLatitude(), location.getLongitude());
                actualLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    public void addLocationListener(Listener listener)
    {
        listenerList.add(listener);
    }

    public Location getActualLocation()
    {
        return actualLocation;
    }

    public interface Listener
    {
        void allocate(double lat, double lon);
    }
}
