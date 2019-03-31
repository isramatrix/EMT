package com.emt.sostenible.data;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public interface DataFetcherInterface {
    //devuelve las paradas de la linea proporcionada como key
    public List<Parada> getParadasDeLinea(int linea);
    public Parada[] getParadas();

}
