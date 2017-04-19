package com.Jestarok.MovEmotions.Encapsulaciones.WS;

import java.util.List;

/**
 * Created by mc on 4/18/2017.
 */
public class RetornoConsultaPeliculas {
    List<ResumenPelicula> Search;
    int totalResults;
    boolean Response;

    public  boolean getResponse(){
        return this.Response;
    }

    public  List<ResumenPelicula> getListaPeliculas(){
        return this.Search;
    }

}


