package com.emt.sostenible.data;

import android.content.Context;

import java.io.IOException;

public class StopFetcher {

    public StopFetcher(Context context)
    {
        try {
            context.getAssets().open("localizacionParadas.json");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
