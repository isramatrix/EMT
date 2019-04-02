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

    //Listas de objetos
    private static Stop[]         stops;
    private static StopTime[]     stopTimes;
    private static Frequency[]    frequencies;
    private static Route[]        routes;
    private static Shape[]        shapes;
    private static Agency[]       agencies;
    private static Calendar[]     calendars;
    private static CalendarDate[] calendarDates;
    private static Trip[]         trips;

    //Implementing a singleton class
    private static DataFetcher dataFetcher;
    private static Context context;

    /**
     * Implementa un singleton llamado data fetcher
     * @param contexto usado la primera vez que este se llama para crear el contexto y cargar cosas
     * @return la unica instancia existente de data fetcher
     */
    public static DataFetcher getDataFetcher(final Context contexto){
        if(dataFetcher == null){
            context = contexto;
            dataFetcher = new DataFetcher(context);
        }
        return dataFetcher;
    }

    private DataFetcher(final Context context)
    {
        //Nombre de los ficheros a usar
        String[] ficheros = {
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
        for(int i = 0; i < ficheros.length; i++){
            try {
                InputStream is = context.getAssets().open(ficheros[i]);
                switch(i){
                    case 0: agencies =
                            processAgencies(is);    break;
                    case 1: calendars =
                            processCalendar(is);    break;
                    case 2: calendarDates =
                            processCalendarDate(is);break;
                    case 3: frequencies =
                            processFrequencies(is); break;
                    case 4: routes =
                            processRoutes(is);      break;
                    case 5: shapes =
                            processShape(is);       break;
                    case 6: stopTimes =
                            processStopTime(is);    break;
                    case 7: stops =
                            processStops(is);       break;
                    case 8: trips =
                            processTrip(is);        break;
                }
                is.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Coge un @param InputStream de tipo csv y omite la primera linea y lo tokeniza en una array de dos dimensiones
    private LinkedList<String[]> csvToString(InputStream in){
        Scanner s = new Scanner(in);
        LinkedList<String[]> list = new LinkedList();
        String original = s.nextLine();
        while(s.hasNextLine()){
            String temps = s.nextLine();
            String[] temp = temps.split(",");
            list.add(temp);
        }
        return list;
    }

    //Apartir de aqui procesamos todos los .txt creando arrays de objetos mucha mierda que no me representa.

    private Stop[] processStops(InputStream is){
        try {
            LinkedList<String[]> stopsList = csvToString(is);
            Stop[] stops = new Stop[stopsList.size()];
            int i = 0;
            for (String[] s: stopsList) {
                stops[i] = new Stop(s[0], s[1],s[2],s[3],s[4],s[5],s[6],s[7],s[8],"");
                i++;
            }
            return stops;

        }catch (Exception e) {
            e.printStackTrace();
            return new Stop[0];
        }
    }

    public static Stop[] getStops() {
        return stops;
    }

    public static StopTime[] getStopTimes() {
        return stopTimes;
    }

    public static Frequency[] getFrequencies() {
        return frequencies;
    }

    public static Route[] getRoutes() {
        return routes;
    }

    public static Shape[] getShapes() {
        return shapes;
    }

    public static Agency[] getAgencies() {
        return agencies;
    }

    public static Calendar[] getCalendars() {
        return calendars;
    }

    public static CalendarDate[] getCalendarDates() {
        return calendarDates;
    }

    public static Trip[] getTrips() {
        return trips;
    }

    //BAD BUT FUNCTIONAL CODE, DO NOT LOOK NOR CHANGE
    //Done for prefetching efficiency purposes
    private Agency[] processAgencies(InputStream is){
        try {
            LinkedList<String[]> agenciesList = csvToString(is);
            Agency[] agencies = new Agency[agenciesList.size()];
            int i = 0;
            for (String[] s: agenciesList) {
                agencies[i] = new Agency(s[0], s[1],s[2],s[3],s[4],s[5]);
                i++;
            }
            return agencies;

        }catch (Exception e) {
            e.printStackTrace();
            return new Agency[0];
        }
    }

    private Frequency[] processFrequencies(InputStream is){
        try {
            LinkedList<String[]> frequenciesList = csvToString(is);
            Frequency[] frecuencies = new Frequency[frequenciesList.size()];
            int i = 0;
            for (String[] s: frequenciesList) {
                frecuencies[i] = new Frequency(s[0], s[1],s[2],s[3]);
                i++;
            }
            return frecuencies;

        }catch (Exception e) {
            e.printStackTrace();
            return new Frequency[0];
        }
    }

    private Route[] processRoutes(InputStream is){
        try {
            LinkedList<String[]> routesList = csvToString(is);
            Route[] routes = new Route[routesList.size()];
            int i = 0;
            for (String[] s: routesList) {
                routes[i] = new Route(s[0], s[1],s[2],s[3],s[4],s[5],s[6],s[7],s[8]);
                i++;
            }
            return routes;

        }catch (Exception e) {
            e.printStackTrace();
            return new Route[0];
        }
    }

    private Calendar[] processCalendar(InputStream is){
        try {
            LinkedList<String[]> calendarsList = csvToString(is);
            Calendar[] calendars = new Calendar[calendarsList.size()];
            int i = 0;
            for (String[] s: calendarsList) {
                calendars[i] = new Calendar(s[0], s[1],s[2],s[3],s[4],s[5],s[6],s[7],s[8],s[9]);
                i++;
            }
            return calendars;

        }catch (Exception e) {
            e.printStackTrace();
            return new Calendar [0];
        }
    }

    private CalendarDate[] processCalendarDate(InputStream is){
        try {
            LinkedList<String[]> calendarDatesList = csvToString(is);
            CalendarDate[] calendarDates = new CalendarDate[calendarDatesList.size()];
            int i = 0;
            for (String[] s: calendarDatesList) {
                calendarDates[i] = new CalendarDate(s[0], s[1],s[2]);
                i++;
            }
            return calendarDates;

        }catch (Exception e) {
            e.printStackTrace();
            return new CalendarDate[0];
        }
    }

    private Shape[] processShape(InputStream is){
        try {
            LinkedList<String[]> shapesList = csvToString(is);
            Shape[] shapes = new Shape[shapesList.size()];
            int i = 0;
            for (String[] s: shapesList) {
                shapes[i] = new Shape(s[0], s[1],s[2],s[3],s[4]);
                i++;
            }
            return shapes;

        }catch (Exception e) {
            e.printStackTrace();
            return new Shape[0];
        }
    }

    private StopTime[] processStopTime(InputStream is){
        try {
            LinkedList<String[]> stopTimesList = csvToString(is);
            StopTime[] stopTimes = new StopTime[stopTimesList.size()];
            int i = 0;
            for (String[] s: stopTimesList) {
                stopTimes[i] = new StopTime(s[0], s[1],s[2],s[3],s[4],s[5],s[6],s[7],"");
                i++;
            }
            return stopTimes;

        }catch (Exception e) {
            e.printStackTrace();
            return new StopTime[0];
        }
    }

    private Trip[] processTrip(InputStream is){
        try {
            LinkedList<String[]> tripsList = csvToString(is);
            Trip[] trips = new Trip[tripsList.size()];
            int i = 0;
            for (String[] s: tripsList) {
                trips[i] = new Trip(s[0], s[1],s[2],s[3],s[4],s[5],s[6]);
                i++;
            }
            return trips;

        }catch (Exception e) {
            e.printStackTrace();
            return new Trip[0];
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
}
