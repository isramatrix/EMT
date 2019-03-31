package com.emt.sostenible.here;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.GeocodeRequest2;
import com.here.android.mpa.search.GeocodeResult;
import com.here.android.mpa.search.Location;
import com.here.android.mpa.search.ResultListener;

import java.util.List;

public class MapGeocoder implements ResultListener<List<Location>> {

    public MapGeocoder()
    {

    }

    public MapGeocoder setContext()
    {
       return null;
    }

    public void search(String location, ResultListener<List<GeocodeResult>> resultListener)
    {
        new GeocodeRequest2(location).setSearchArea(
                new GeoCoordinate(39.4078969, -0.4315509, 0.0), 500
        ).execute(resultListener);
    }

    @Override
    public void onCompleted(List<Location> locationList, ErrorCode errorCode)
    {
        if (errorCode != ErrorCode.NONE) {
            // Handle error

        } else {
            // Process result data
        }
    }
}
