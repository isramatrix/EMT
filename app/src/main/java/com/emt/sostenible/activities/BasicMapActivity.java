package com.emt.sostenible.activities;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.emt.sostenible.R;
import com.emt.sostenible.data.DataFetcher;
import com.emt.sostenible.here.EMTRoutePlanner;
import com.emt.sostenible.data.DataFetcher;
import com.emt.sostenible.here.MapController;
import com.emt.sostenible.logic.LocationService;
import com.emt.sostenible.view.SearchHeader;
import com.here.android.mpa.common.GeoCoordinate;

public class BasicMapActivity extends Activity {

    private MapController map;

    private LocationService locationService;

    private SearchHeader searchHeader;

    private ImageButton button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = new MapController(this);
        searchHeader = findViewById(R.id.routing_view);
        locationService = LocationService.getInstance(this);

        searchHeader.setOnSearchButtonClicked(searchRouteTo());
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
                /*
                map.addPersona(location);

                //Necesitamos pasarle una array list de TransitStopObject
                try {
                    map.AddParadas(DataFetcher.getDataFetcher(null).getStops());
                }catch(Exception e){
                    e.printStackTrace();
                }

*/
            }
        }
    }

    private SearchHeader.OnSearchButtonListener searchRouteTo()
    {
        return new SearchHeader.OnSearchButtonListener() {
            @Override
            public void onClick(String text, SearchHeader.SearchType searchType) {

                Location actualLocation = LocationService.getInstance(null).getActualLocation();

                EMTRoutePlanner routePlan = new EMTRoutePlanner(searchType, 2,
                        new GeoCoordinate(actualLocation.getLatitude()-0.01, actualLocation.getLongitude()-0.04),
                        new GeoCoordinate(actualLocation.getLatitude(), actualLocation.getLongitude()-0.01)
                );
                searchHeader.visibilityHeader(false);

                routePlan.traceWithColor(map, Color.RED);
            }
        };
    }

    /**
     *
     * @param view
     */
    public void onSearchButtonClicked(View view)
    {
        searchHeader.visibilityHeader(true);
    }

    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    @Override
    public void onBackPressed() {
        searchHeader.visibilityHeader(false);
    }
}