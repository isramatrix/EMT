package com.emt.sostenible.data;

import android.content.Context;
import android.util.ArrayMap;
import android.util.JsonReader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.json.*;



public class DataFetcher {

    //Listas de objetos
    private static Stop[] stops;
    private static StopTime[] stopTimes;
    private static Frequency[] frequencies;
    private static Route[] routes;
    private static Shape[] shapes;
    private static Agency[] agencies;
    private static Calendar[] calendars;
    private static CalendarDate[] calendarDates;
    private static Trip[] trips;
    private static Estation[] estations;
    private RequestQueue queue;
   // private List<String> CosasNazis = new ArrayList<String>();


    //Implementing a singleton class
    private static DataFetcher dataFetcher;
    private static Context context;
    private String[] ficheros = {
            "json/Texts/agency.txt",
            "json/Texts/calendar.txt",
            "json/Texts/calendar_dates.txt",
            "json/Texts/frequencies.txt",
            "json/Texts/routes.txt",
            "json/Texts/shapes.txt",
            "json/Texts/stop_times.txt",
            "json/Texts/stops.txt",
            "json/Texts/trips.txt"
    };

    /**
     * Implementa un singleton llamado data fetcher
     *
     * @param contexto usado la primera vez que este se llama para crear el contexto y cargar cosas, ignorado el resto de veces
     * @return la unica instancia existente de data fetcher
     */
    public static DataFetcher getDataFetcher(final Context contexto) throws Exception {
        if (dataFetcher == null && contexto == null)
            throw new Exception("Please put a valid context to create a new data fetcher");
        else if (dataFetcher == null && contexto != null) {
            context = contexto;
            dataFetcher = new DataFetcher();
        }
        return dataFetcher;

    }

    public static DataFetcher getDataFetcher() throws Exception {
        return getDataFetcher();
    }

    public boolean loadAgencies() {
        return load(0);
    }

    public boolean loadCalendars() {
        return load(1);
    }

    public boolean loadCalendarDates() {
        return load(2);
    }

    public boolean loadFrequencies() {
        return load(3);
    }

    public boolean loadRoutes() {
        return load(4);
    }

    public boolean loadShapes() {
        return load(5);
    }

    public boolean loadStopTimes() {
        return load(6);
    }

    public boolean loadStops() {
        return load(7);
    }

    public boolean loadTrips() {
        return load(8);
    }


    private boolean load(int index) {
        try {
            InputStream is = context.getAssets().open(ficheros[index]);
            switch (index) {
                case 0:
                    agencies =
                            processAgencies(is);
                    break;
                case 1:
                    calendars =
                            processCalendar(is);
                    break;
                case 2:
                    calendarDates =
                            processCalendarDate(is);
                    break;
                case 3:
                    frequencies =
                            processFrequencies(is);
                    break;
                case 4:
                    routes =
                            processRoutes(is);
                    break;
                case 5:
                    shapes =
                            processShape(is);
                    break;
                case 6:
                    stopTimes =
                            processStopTime(is);
                    break;
                case 7:
                    stops =
                            processStops(is);
                    break;
                case 8:
                    trips =
                            processTrip(is);
                    break;
            }
            is.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private DataFetcher() {
    }

    //Coge un @param InputStream de tipo csv y omite la primera linea y lo tokeniza en una array de dos dimensiones
    private LinkedList<String[]> csvToString(InputStream in) {
        Scanner s = new Scanner(in);
        LinkedList<String[]> list = new LinkedList();
        String original = s.nextLine();
        while (s.hasNextLine()) {
            String temps = s.nextLine();
            String[] temp = temps.split(",");
            list.add(temp);
        }
        return list;
    }

    //Apartir de aqui procesamos todos los .txt creando arrays de objetos mucha mierda que no me representa.

    private Stop[] processStops(InputStream is) {
        try {
            LinkedList<String[]> stopsList = csvToString(is);
            Stop[] stops = new Stop[stopsList.size()];
            int i = 0;
            for (String[] s : stopsList) {
                stops[i] = new Stop(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], "");
                i++;
            }
            return stops;
        } catch (Exception e) {
            e.printStackTrace();
            return new Stop[0];
        }
    }

    public static Stop[] getStops() {
        if (stops == null) dataFetcher.loadStops();
        return stops;
    }

    public static StopTime[] getStopTimes() {
        if (stopTimes == null) dataFetcher.loadStopTimes();
        return stopTimes;
    }

    public static Frequency[] getFrequencies() {
        if (frequencies == null) dataFetcher.loadFrequencies();
        return frequencies;
    }

    public static Route[] getRoutes() {
        if (routes == null) dataFetcher.loadRoutes();
        return routes;
    }

    public static Shape[] getShapes() {
        if (shapes == null) dataFetcher.loadShapes();
        return shapes;
    }

    public static Agency[] getAgencies() {
        if (agencies == null) dataFetcher.loadAgencies();
        return agencies;
    }

    public static Calendar[] getCalendars() {
        if (calendars == null) dataFetcher.loadCalendars();
        return calendars;
    }

    public static CalendarDate[] getCalendarDates() {
        if (calendarDates == null) dataFetcher.loadCalendarDates();
        return calendarDates;
    }

    public static Trip[] getTrips() {
        if (trips == null) dataFetcher.loadTrips();
        return trips;
    }

    //BAD BUT FUNCTIONAL CODE, DO NOT LOOK NOR CHANGE
    //Done for prefetching efficiency purposes
    private Agency[] processAgencies(InputStream is) {
        try {
            LinkedList<String[]> agenciesList = csvToString(is);
            Agency[] agencies = new Agency[agenciesList.size()];
            int i = 0;
            for (String[] s : agenciesList) {
                agencies[i] = new Agency(s[0], s[1], s[2], s[3], s[4], s[5]);
                i++;
            }
            return agencies;

        } catch (Exception e) {
            e.printStackTrace();
            return new Agency[0];
        }
    }

    private Frequency[] processFrequencies(InputStream is) {
        try {
            LinkedList<String[]> frequenciesList = csvToString(is);
            Frequency[] frecuencies = new Frequency[frequenciesList.size()];
            int i = 0;
            for (String[] s : frequenciesList) {
                frecuencies[i] = new Frequency(s[0], s[1], s[2], s[3]);
                i++;
            }
            return frecuencies;

        } catch (Exception e) {
            e.printStackTrace();
            return new Frequency[0];
        }
    }

    private Route[] processRoutes(InputStream is) {
        try {
            LinkedList<String[]> routesList = csvToString(is);
            Route[] routes = new Route[routesList.size()];
            int i = 0;
            for (String[] s : routesList) {
                routes[i] = new Route(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8]);
                i++;
            }
            return routes;

        } catch (Exception e) {
            e.printStackTrace();
            return new Route[0];
        }
    }

    private Calendar[] processCalendar(InputStream is) {
        try {
            LinkedList<String[]> calendarsList = csvToString(is);
            Calendar[] calendars = new Calendar[calendarsList.size()];
            int i = 0;
            for (String[] s : calendarsList) {
                calendars[i] = new Calendar(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9]);
                i++;
            }
            return calendars;

        } catch (Exception e) {
            e.printStackTrace();
            return new Calendar[0];
        }
    }

    private CalendarDate[] processCalendarDate(InputStream is) {
        try {
            LinkedList<String[]> calendarDatesList = csvToString(is);
            CalendarDate[] calendarDates = new CalendarDate[calendarDatesList.size()];
            int i = 0;
            for (String[] s : calendarDatesList) {
                calendarDates[i] = new CalendarDate(s[0], s[1], s[2]);
                i++;
            }
            return calendarDates;

        } catch (Exception e) {
            e.printStackTrace();
            return new CalendarDate[0];
        }
    }

    private Shape[] processShape(InputStream is) {
        try {
            LinkedList<String[]> shapesList = csvToString(is);
            Shape[] shapes = new Shape[shapesList.size()];
            int i = 0;
            for (String[] s : shapesList) {
                shapes[i] = new Shape(s[0], s[1], s[2], s[3], s[4]);
                i++;
            }
            return shapes;

        } catch (Exception e) {
            e.printStackTrace();
            return new Shape[0];
        }
    }

    private StopTime[] processStopTime(InputStream is) {
        try {
            LinkedList<String[]> stopTimesList = csvToString(is);
            StopTime[] stopTimes = new StopTime[stopTimesList.size()];
            int i = 0;
            for (String[] s : stopTimesList) {
                stopTimes[i] = new StopTime(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], "");
                i++;
            }
            return stopTimes;

        } catch (Exception e) {
            e.printStackTrace();
            return new StopTime[0];
        }
    }

    private Trip[] processTrip(InputStream is) {
        try {
            LinkedList<String[]> tripsList = csvToString(is);
            Trip[] trips = new Trip[tripsList.size()];
            int i = 0;
            for (String[] s : tripsList) {
                trips[i] = new Trip(s[0], s[1], s[2], s[3], s[4], s[5], s[6]);
                i++;
            }
            return trips;

        } catch (Exception e) {
            e.printStackTrace();
            return new Trip[0];
        }
    }



    private Estation[] obtenerDatosVolley() {
        String url = "https://api.waqi.info/mapq/bounds/?bounds=39.27223818849068,-0.693511962890625,39.601032583320894,-0.03227233886718751&inc=placeholders&k=_2Y2EvHBxICVocIyNASSJWXmpjdA4+PStSFlY3Yg==&_=1554378570700";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                            Estation[] estations = new Estation[response.length()];

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject mJsonObject = response.getJSONObject(i);
                        String name = mJsonObject.getString("lat");
                        String lon = mJsonObject.getString("lon");
                        String city = mJsonObject.getString("city");
                        String idx = mJsonObject.getString("idx");
                        String stamp = mJsonObject.getString("stamp");
                        String pol = mJsonObject.getString("pol");
                        String x = mJsonObject.getString("x");
                        String aqi = mJsonObject.getString("aqi");
                        String tz = mJsonObject.getString("tz");
                        String utime = mJsonObject.getString("utime");
                        String img = mJsonObject.getString("img");

                        estations[i] = new Estation(name,lon,city,idx,stamp,pol,x,aqi,tz,utime,img);

                        //CosasNazis.add(name);
                        //CosasNazis.add(lon);
                        //CosasNazis.add(city);
                        //CosasNazis.add(idx);
                        //CosasNazis.add(stamp);
                        //CosasNazis.add(pol);
                        //CosasNazis.add(x);
                        //CosasNazis.add(aqi);
                        //CosasNazis.add(tz);
                        //CosasNazis.add(utime);
                        //CosasNazis.add(img);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

        return estations;
    }
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
