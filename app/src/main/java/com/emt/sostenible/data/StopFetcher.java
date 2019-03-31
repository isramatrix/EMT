package com.emt.sostenible.data;

import android.content.Context;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StopFetcher {

    private Map<Line, List<Parada>> stopsMap;

    public StopFetcher(final Context context)
    {
        try {
            context.getAssets().open("localizacionParadas.json");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Line, Parada> getAllStops()
    {
        return null;
    }

}
