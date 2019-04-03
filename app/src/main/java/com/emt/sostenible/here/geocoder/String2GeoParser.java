package com.emt.sostenible.here.geocoder;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.GeocodeRequest2;
import com.here.android.mpa.search.GeocodeResult;
import com.here.android.mpa.search.ResultListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class String2GeoParser implements ResultListener<List<GeocodeResult>> {

    private ParseCompletedListener listener;
    private final GeocodeRequest2 request;

    public String2GeoParser(String regex, GeoCoordinate center)
    {
        request = new GeocodeRequest2(regex);
        request.setSearchArea(center, 50);
    }

    @Override
    public void onCompleted(List<GeocodeResult> geocodeResults, ErrorCode errorCode)
    {
        Map<String, GeoCoordinate> map = new HashMap<>();

        for (GeocodeResult res : geocodeResults)
            map.put(
                    res.getLocation().getAddress().getStreet(),
                    new GeoCoordinate(res.getLocation().getCoordinate())
            );

        listener.parsed(map);
    }

    public void parse(ParseCompletedListener listener)
    {
        this.listener = listener;
        request.execute(this);
    }

    public interface ParseCompletedListener
    {
        void parsed(Map<String, GeoCoordinate> locations);
    }


}
