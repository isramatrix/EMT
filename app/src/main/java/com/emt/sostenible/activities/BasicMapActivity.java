package com.emt.sostenible.activities;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.emt.sostenible.R;
import com.emt.sostenible.here.MapController;
import com.emt.sostenible.here.MapGeocoder;
import com.emt.sostenible.logic.LocationService;
import com.emt.sostenible.view.SearchHeader;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.GeocodeResult;
import com.here.android.mpa.search.ResultListener;

import java.util.List;

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

        searchHeader.onDestinationChanged(new SearchHeader.OnTextChangedListener() {
            @Override
            public void changed(String text) {
                System.out.println(text);
                searchHeader.inflateAutoCompleteDestination(null);
            }
        });
    }

    /**
     *
     * @param view
     */
    public void onCenterButtonClicked(View view) {
        if (locationService.askForPermissions()) {
            Location location = locationService.getActualLocation();

            if (location != null) {
                map.addMarker(location);
                map.setCenter(location);
            }
        }
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