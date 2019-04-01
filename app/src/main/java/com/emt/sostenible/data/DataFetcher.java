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
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import org.json.*;



public class DataFetcher {

    //Mapa de paradas indexado por enteros que representan el numero de linea
    private static Map<Integer, ArrayList<Parada>> stopsMap;
    //Lista de paradas
    private static Parada[] listaParadas;


    public DataFetcher(final Context context)
    {
        try {
            InputStream is = context.getAssets().open("json/localizacionParadas.json");
        }catch (IOException e) {
            e.printStackTrace();
        }


    }


    //Coge un @param InputStream de tipo csv y omite la primera linea y lo tokeniza en una array de dos dimensiones
    private LinkedList<String[]> csvToString(InputStream in){
        Scanner s = new Scanner(in);
        LinkedList<String[]> list = new LinkedList();
        s.nextLine();
        while(s.hasNextLine()){
            String temps = s.nextLine();
            String[] temp = temps.split(",");
            list.add(temp);
        }
        return list;
    }

    public Map<Line, Parada> getAllStops()
    {
        Map toReturn = new Hashtable<Integer, ArrayList<Parada>>();

        return null;
    }

    /*
    @Deprecated
    private JSONArray converter( InputStream input)
    {
        JSONObject convertido;
        try {
            StringBuilder stBuild = new StringBuilder();
            Scanner sc = new Scanner(input);
            while(sc.hasNextLine()){
                stBuild.append(sc.nextLine());
            }
            sc.close();
            input.close();
            String result = stBuild.toString();

            return null;

        }   catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }


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

        try{
            InputStream is = context.getAssets().open("json/Texts/stops.txt");
            JSONObject o = converter(is);
            System.out.println(o.toString());
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    */
}
