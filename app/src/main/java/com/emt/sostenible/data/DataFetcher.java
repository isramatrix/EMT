package com.emt.sostenible.data;

import android.content.Context;
import android.util.ArrayMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class DataFetcher {

    //Mapa de paradas indexado por enteros que representan el numero de linea
    private static Map<Integer, ArrayList<Parada>> stopsMap;
    //Lista de paradas
    private static Parada[] paradas;

    public DataFetcher(final Context context)
    {
        try {
            context.getAssets().open("json/localizacionParadas.json");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Line, Parada> getAllStops()
    {
        Map toReturn = new Hashtable<Integer, ArrayList<Parada>>();

        return null;
    }

}
