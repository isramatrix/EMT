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

    //Lista de paradas
    private static Stop[] stops;
    //Lista de Agency
    private static Agency[] agencies;
    //Lista de Calendar
    private static Calendar[] calendars;
    //Lista de CalendarDate
    private static CalendarDate[] calendarDates;


    public DataFetcher(final Context context)
    {
        try {
            InputStream is = context.getAssets().open("json/Texts/stops.txt");
            stops = processStops(is);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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
