package com.emt.sostenible.here;

import android.app.Activity;
import android.location.Location;

import com.emt.sostenible.R;
import com.emt.sostenible.logic.MarkerHandler;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;

import java.io.File;

public class MapController {

    // map embedded in the map fragment
    private Map map = null;

    // map fragment embedded in this activity
    private final MapFragment mapFragment;

    public MapController(Activity context)
    {
        // Search for the map fragment to finish setup by calling init().
        mapFragment = (MapFragment) context.getFragmentManager().findFragmentById(R.id.mapfragment);


        // Set up disk cache path for the map service for this application
        // It is recommended to use a path under your application folder for storing the disk cache.
        boolean success = com.here.android.mpa.common.MapSettings.setIsolatedDiskCacheRootPath(
                context.getApplicationContext().getExternalFilesDir(null) + File.separator + ".here-maps",
                "EMTService");

        if (success) {
            mapFragment.init(new OnEngineInitListener() {
                @Override
                public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
                    if (error == OnEngineInitListener.Error.NONE) {
                        // retrieve a reference of the map from the map fragment
                        map = mapFragment.getMap();

                        // Set the map center to the Vancouver region (no animation)
                        map.setCenter(new GeoCoordinate(39.4178969, -0.4115509, 0.0), Map.Animation.NONE);

                        // Set the zoom level to the average between min and max
                        map.setZoomLevel((map.getMaxZoomLevel() + map.getMinZoomLevel()) / 1.5);
                        map.setMapScheme(Map.Scheme.NORMAL_DAY);

                        MarkerHandler m = new MarkerHandler(map);
                        m.initialize();

                    } else {
                        System.out.println("ERROR: Cannot initialize Map Fragment");
                    }
                }
            });
        }
    }

    public void setCenter(Location location)
    {
        map.setCenter(new GeoCoordinate(location.getLatitude(), location.getLongitude(), 0.0), Map.Animation.LINEAR);
    }

    public void setCenter(com.here.android.mpa.search.Location location)
    {
        map.setCenter(new GeoCoordinate(location.getCoordinate().getLatitude(),
                location.getCoordinate().getLongitude(), 0.0), Map.Animation.LINEAR);
    }

    public void setZoom(float zoom) {
        map.setZoomLevel(zoom);
    }

    public void addRoute(MapRouting mapRouting)
    {
        mapRouting.setOnCalculateRouteFinished(map);
    }
}
