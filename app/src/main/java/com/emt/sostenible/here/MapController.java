package com.emt.sostenible.here;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.util.ArraySet;
import android.util.Pair;

import java.io.IOException;

import com.emt.sostenible.R;
import com.emt.sostenible.data.DataFetcher;
import com.emt.sostenible.data.Stop;
import com.emt.sostenible.here.geocoder.String2GeoParser;
import com.emt.sostenible.view.RouteInfo;
import com.here.android.mpa.cluster.ClusterDensityRange;
import com.here.android.mpa.cluster.ClusterLayer;
import com.here.android.mpa.cluster.ClusterTheme;
import com.here.android.mpa.cluster.ImageClusterStyle;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.routing.Route;
import com.here.android.mpa.routing.RouteElement;
import com.here.android.mpa.routing.TransitRouteElement;


import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class MapController {

    // map embedded in the map fragment
    public static Map map = null;

    // map fragment embedded in this activity
    private final MapFragment mapFragment;
    private MapMarker marca;
    private MapMarker ubiPersona;
    private static Context context;

    public MapController(Activity context) {
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

                        // Set the map center to the Vancouver region (no animation)
                        map.setCenter(new GeoCoordinate(39.470059, -0.376105, 0.0), Map.Animation.NONE);

                        // Set the zoom level to the average between min and max
                        map.setZoomLevel((map.getMaxZoomLevel() + map.getMinZoomLevel()) / 1.5);
                        map.setMapScheme(Map.Scheme.NORMAL_DAY);
                        initializeStops();

                    } else {
                        System.out.println("ERROR: Cannot initialize Map Fragment");
                    }
                }
            });
        }
    }

    public void setCenter(Location location) {
        map.setCenter(new GeoCoordinate(location.getLatitude(), location.getLongitude(), 0.0), Map.Animation.LINEAR);
    }

    public void setCenter(com.here.android.mpa.search.Location location) {
        map.setCenter(new GeoCoordinate(location.getCoordinate().getLatitude(),
                location.getCoordinate().getLongitude(), 0.0), Map.Animation.LINEAR);
    }

    public void setZoom(float zoom) {
        map.setZoomLevel(zoom);
    }

    public void addRoutes(List<Route> routes, RouteInfo routeInfo)
    {
        for (Route r : routes) map.addMapObject(new MapRoute(r).setColor(Color.RED));
        setRouteInfo(routes.get(0), routeInfo);
    }

    public void searchPlaces(String regex, String2GeoParser.ParseCompletedListener listener)
    {
        new String2GeoParser(regex, map.getCenter()).parse(listener);
    }

    /**
     *  Añadir marcador de persona pasandole Location
     */
    public void addPersona(Location loct){

        if (ubiPersona != null) map.removeMapObject(ubiPersona);

        //Generar marcador

        //Modificar icon
        try {
            GeoCoordinate aux = new GeoCoordinate(loct.getLatitude(), loct.getLongitude());
            Image image = new Image();
            //Añadir png aqui
            image.setImageResource(R.drawable.location_azul_low_res);
            ubiPersona = new MapMarker(aux);
            ubiPersona.setIcon(image);
            ubiPersona.setDescription("Estoy aqui");
            map.addMapObject(ubiPersona);


        } catch (Exception e) {
            System.out.print("NOT FOUND IMAGE");
        }


    }

    public MapMarker createParada(double lat, double longi) {
        GeoCoordinate aux = new GeoCoordinate(lat,longi);
        //Generar marcador

        //Modificar icon
        try {
            Image image = new Image();
            //Añadir png aqui
            image.setImageResource(R.drawable.bus_emt);
            marca = new MapMarker(aux);
            marca.setIcon(image);
            marca.setDescription("Parada");



        } catch (Exception e) {
            System.out.print("NOT FOUND IMAGE");
        }
        return marca;

    }

    public void AddParadas(Stop[] lista){
        ClusterLayer paradas = new ClusterLayer();
        double lat = 0;
        double longi = 0;
        for(Stop parada:lista){
            lat = Double.parseDouble(parada.getStop_lat());
            longi = Double.parseDouble(parada.getStop_lon());
            paradas.addMarker(createParada(lat,longi));
        }

        Image image = new Image();
        try {
            image.setImageResource(R.drawable.bus_emt);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageClusterStyle cStyle = new ImageClusterStyle(image);
        //BasicClusterStyle cStyle = new BasicClusterStyle();
        //cStyle.setFillColor(Color.RED);
        ClusterTheme cTheme = new ClusterTheme();
        cTheme.setStyleForDensityRange(new ClusterDensityRange(ClusterDensityRange.MINIMUM_CLUSTER_DENSITY, Integer.MAX_VALUE), cStyle);


        paradas.setTheme(cTheme);

        map.addClusterLayer(paradas);

    }

    public Stop[] obtListaP(){
        Stop paradas[] = DataFetcher.getStops();
        return paradas;
    }

    private void initializeStops(){
        try {
            DataFetcher temp = DataFetcher.getDataFetcher(MapController.context);
            temp.loadStops();
            //Necesitamos pasarle una array list de TransitStopObject
            AddParadas(temp.getStops());
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            DataFetcher.getDataFetcher().loadEstations();
            //DataFetcher.getEstations();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRouteInfo(Route route, RouteInfo routeInfo)
    {
        Calendar calendar = Calendar.getInstance();

        String departure = String.format("%02d", calendar.get(Calendar.HOUR))
                + ":" + String.format("%02d", calendar.get(Calendar.MINUTE));

        calendar.add(Calendar.SECOND, route.getTtaIncludingTraffic(Route.WHOLE_ROUTE).getDuration());
        String arrival = String.format("%02d", calendar.get(Calendar.HOUR))
                + ":" + String.format("%02d", calendar.get(Calendar.MINUTE));
        routeInfo.setTimes(departure, arrival);

        Set<String> lines = new ArraySet<>();
        for (RouteElement transit : route.getRouteElements().getElements())
            if (transit.getTransitElement() != null) lines.add(transit.getTransitElement().getLineName());

        routeInfo.setLines(lines);

        routeInfo.show(true);
    }
}
