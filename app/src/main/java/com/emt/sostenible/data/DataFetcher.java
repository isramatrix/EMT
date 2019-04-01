package com.emt.sostenible.data;

import android.content.Context;
import android.util.ArrayMap;
import android.util.JsonReader;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import org.json.*;



public class DataFetcher {

    //Mapa de paradas indexado por enteros que representan el numero de linea
    private static Map<Integer, ArrayList<Parada>> stopsMap;
    //Lista de paradas
    private static Parada[] listaParadas;
    private static JSONObject paradas;

    private static JSONObject Convertido ;


    public DataFetcher(final Context context)
    {
        try {
            InputStream is = context.getAssets().open("json/localizacionParadas.json");
            StringBuilder stBuild = new StringBuilder();
            Scanner sc = new Scanner(is);
            while(sc.hasNextLine()){
                stBuild.append(sc.nextLine());
            }
            sc.close();
            is.close();
            String result = stBuild.toString();
            paradas = new JSONObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONArray features = paradas.getJSONArray("features");
            JSONArray coordinates = features.getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates");
            System.out.println(coordinates.toString());
        }catch(JSONException e){
            e.printStackTrace();
        }

    }


    @Deprecated
    private JSONObject converter( InputStream input)
    {
        try {
            StringBuilder stBuild = new StringBuilder();
            Scanner sc = new Scanner(input);
            while(sc.hasNextLine()){
                stBuild.append(sc.nextLine());
            }
            sc.close();
            input.close();
            String result = stBuild.toString();
            Convertido = new JSONObject(result);

            return Convertido;
        }   catch (Exception e) {
            e.printStackTrace();
        } finally {
            return Convertido;
        }

    }




    public Map<Line, Parada> getAllStops()
    {
        Map toReturn = new Hashtable<Integer, ArrayList<Parada>>();

        return null;
    }

}
