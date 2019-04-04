package com.emt.sostenible.activities;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.emt.sostenible.R;
import com.emt.sostenible.data.DataFetcher;
import com.emt.sostenible.here.EMTRoutePlanner;
import com.emt.sostenible.here.MapController;
import com.emt.sostenible.here.geocoder.String2GeoParser;
import com.emt.sostenible.logic.LocationService;
import com.emt.sostenible.view.RouteInfo;
import com.emt.sostenible.view.SearchHeader;
import com.here.android.mpa.common.GeoCoordinate;

import java.util.Map;

public class BasicMapActivity extends Activity {

    private MapController map;

    private LocationService locationService;

    private SearchHeader searchHeader;

    private RouteInfo routeInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = new MapController(this);
        searchHeader = findViewById(R.id.routing_view);
        routeInfo = findViewById(R.id.bottom_info);
        locationService = LocationService.getInstance(this);

        searchHeader.setOnSearchButtonClicked(searchRouteTo());
        searchHeader.setOnDestinationChanged(destinationTextChangedListener());
        searchHeader.setOnOriginChanged(originTextChangedListener());
    }

    /**
     *
     * @param view
     */
    public void onCenterButtonClicked(View view) {
        if (locationService.askForPermissions()) {
            Location location = locationService.getActualLocation();

            if (location != null) {

                map.setCenter(location);

                map.addPersona(location);

                //Necesitamos pasarle una array list de TransitStopObject
                try {
                    map.AddParadas(DataFetcher.getDataFetcher(null).getStops());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Initializes a lambda class extending OnSearchButtonListener interface
     * in order to capture SearchHeader search button click and redirect the
     * given location to the map path tracer.
     *
     * @see com.emt.sostenible.view.SearchHeader.OnSearchButtonListener
     * @return the expected lambda class
     */
    private SearchHeader.OnSearchButtonListener searchRouteTo()
    {
        return new SearchHeader.OnSearchButtonListener() {
            @Override
            public void onClick(GeoCoordinate destine, GeoCoordinate origin, SearchHeader.SearchType searchType) {

                // Gets the actual location of the user.
                Location actualLocation =
                        LocationService.getInstance(null).getActualLocation();

                // Converts the location to a GeoCoordinate point.
                if (origin == null)
                    origin = new GeoCoordinate(actualLocation.getLatitude(),  actualLocation.getLongitude());

                // Initializes and starts the RoutePlanner.
                EMTRoutePlanner routePlan =
                        new EMTRoutePlanner(searchType, 5, origin, destine);
                searchHeader.visibilityHeader(false);

                // Traces the resulted path.
                routePlan.traceWithHours(map, routeInfo);
            }
        };
    }

    /**
     * Initializes a lambda class extending OnTextChangedListener interface
     * in order to capture SearchHeader inputs and redirect the text to the
     * map place searcher.
     *
     * @see com.emt.sostenible.view.SearchHeader.OnTextChangedListener
     * @return the expected lambda class.
     */
    private SearchHeader.OnTextChangedListener destinationTextChangedListener()
    {
        return new SearchHeader.OnTextChangedListener() {
            @Override
            public void changed(String text) {
                if (!text.isEmpty()) map.searchPlaces(text, onDestinationRoutesParsed());
            }
        };
    }
    private SearchHeader.OnTextChangedListener originTextChangedListener()
    {
        return new SearchHeader.OnTextChangedListener() {
            @Override
            public void changed(String text) {
                if (!text.isEmpty()) map.searchPlaces(text, onOriginRoutesParsed());
            }
        };
    }

    /**
     * Initializes a lambda class extending ParseCompletedListener interface
     * in order to listen the succeeding of the places search and inflate, thus,
     * the AutoCompleteText of the SearchHeader.
     *
     * @see com.emt.sostenible.here.geocoder.String2GeoParser.ParseCompletedListener
     * @see SearchHeader
     * @return the expectes lambda class.
     */
    private String2GeoParser.ParseCompletedListener onDestinationRoutesParsed()
    {
        return new String2GeoParser.ParseCompletedListener() {
            @Override
            public void parsed(Map<String, GeoCoordinate> locations) {
                searchHeader.inflateAutoCompleteDestination(locations);
            }
        };
    }
    private String2GeoParser.ParseCompletedListener onOriginRoutesParsed()
    {
        return new String2GeoParser.ParseCompletedListener() {
            @Override
            public void parsed(Map<String, GeoCoordinate> locations) {
                searchHeader.inflateAutoCompleteOrigins(locations);
            }
        };
    }

    /**
     * Delegate method which is captured when search button on header was pressed.
     * @param view
     */
    public void onSearchButtonClicked(View view)
    {
        searchHeader.visibilityHeader(true);
        routeInfo.show(false);
    }

    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    @Override
    public void onBackPressed() {
        searchHeader.visibilityHeader(false);
        routeInfo.show(false);
    }
}