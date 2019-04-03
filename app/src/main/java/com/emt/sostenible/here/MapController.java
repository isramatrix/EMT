package com.emt.sostenible.here;

import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.location.Location;

import com.emt.sostenible.R;
import com.emt.sostenible.data.DataFetcher;
import com.emt.sostenible.data.Route;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.Identifier;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.mapping.MapProxyObject;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.mapping.MapTransitLayer;
import com.here.android.mpa.mapping.TransitDatabase;
import com.here.android.mpa.mapping.TransitLineObject;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.RoutePlan;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapController {

    // map embedded in the map fragment
    private Map map = null;

    // map fragment embedded in this activity
    private final MapFragment mapFragment;

    private final Context context;

    public MapController(Activity context)
    {
        this.context = context.getBaseContext();
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

                        mapFragment.getMapGesture().addOnGestureListener(
                            new MapTouchAdapter(map), 1, false
                        );

                        // Set the map center to the Vancouver region (no animation)
                        map.setCenter(new GeoCoordinate(39.4178969, -0.4115509, 0.0), Map.Animation.NONE);

                        // Set the zoom level to the average between min and max
                        map.setZoomLevel((map.getMaxZoomLevel() + map.getMinZoomLevel()) / 1.5);
                        map.setMapScheme(Map.Scheme.NORMAL_DAY_TRANSIT);

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

    public void addRoute(MapRoute route)
    {
        map.addMapObject(route);
    }

    public void addRoutes(List<MapRoute> routes) { for (MapRoute r : routes) addRoute(r); }

    /**
     *  Añadir marcador de persona pasandole Location
     */
    public void addPersona(Location loct){

        //Generar marcador
        GeoCoordinate aux = new GeoCoordinate(loct.getLatitude(),loct.getLongitude());
        MapMarker marca = new MapMarker(aux);
        //Modificar icono
        marca.setDescription("Estoy aqui");
//        Image icono;
//        marca.setIcon();

        // Añadir marcador
        map.addMapObject(marca);

    }

    public void addMarker(double lat, double lon)
    {
        map.addMapObject(new MapMarker(new GeoCoordinate(lat, lon, 0.0)));
    }
}
