package com.emt.sostenible.activities;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.emt.sostenible.R;
import com.emt.sostenible.here.MapController;
import com.emt.sostenible.here.MapGeocoder;
import com.emt.sostenible.here.MapRouting;
import com.emt.sostenible.logic.LocationService;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.GeocodeResult;
import com.here.android.mpa.search.ResultListener;
import com.here.services.HereLocationApiClient;
import com.here.services.location.LocationServices;

import java.io.File;
import java.util.List;

public class BasicMapActivity extends Activity {

    private View routing;

    private MapController map;

    private AutoCompleteTextView searcher;

    private LocationService locationService;

    private PositioningManager posMan = new PositioningManager("GPS");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        map = new MapController(this);
        searcher = findViewById(R.id.searcher);

        searcher.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onSearchButtonClicked(v);
                return true;
            }
        });

        routing = findViewById(R.id.routing_view);
        locationService = LocationService.getInstance(this);
    }

    public void onCenterButtonClicked(View view) {
        if (locationService.askForPermissions()) {
            Location location = locationService.getActualLocation();
            //setear localizacion

            if (location != null) map.setCenter(location);
        }
    }

    public void onSearchButtonClicked(View view)
    {
        MapGeocoder geo = new MapGeocoder();
        geo.search(searcher.getText().toString(), new ResultListener<List<GeocodeResult>>() {
            @Override
            public void onCompleted(List<GeocodeResult> geocodeResults, ErrorCode errorCode) {
                map.setCenter(geocodeResults.get(0).getLocation());
            }
        });
    }

    public void openRoutingButton(View view)
    {
        changeHeader(true);

    }

    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    @Override
    public void onBackPressed() {
        changeHeader(false);
    }

    private void changeHeader(boolean main)
    {
        routing.setVisibility(main ? View.VISIBLE : View.INVISIBLE);
    }
}