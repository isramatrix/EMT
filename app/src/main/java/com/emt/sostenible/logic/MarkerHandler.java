package com.emt.sostenible.logic;

import android.app.Activity;
import android.content.Context;
import com.here.android.mpa.mapping.Map;
import org.json.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class MarkerHandler {
    private Map mapa;
    private Context context;

    public MarkerHandler(Map mapa){
        this.mapa = mapa;
        this.context = context;
    }

    public boolean initialize(){
        BufferedReader br = null;
        FileReader fr = null;
        try{
            File f = new File("./localizacionParadas.json");
            fr = new FileReader(f);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }



}
