package com.Jestarok.MovEmotions.Encapsulaciones.WS;

/**
 * Created by Jestarok on 4/18/2017.
 */
public class DatosPelicula {

    private String Title;
    private String Year;
    String Rated;
    String Released;
    String Runtime;
    String Genre;
    String Director;
    String Writer;
    String Actors;
    private String Plot;
    String Language;
    String Country;
    String Awards;
    String Poster;
    Object Ratings;
    int Metascore;
    double imdbRating;
    String imdbVotes;
    String imdbID;
    String Type;
    String DVD;
    String BoxOffice;
    String Production;
    String Website;
    private boolean Response;


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public boolean isResponse() {
        return Response;
    }

    public void setResponse(boolean response) {
        Response = response;
    }
}
