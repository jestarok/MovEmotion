package com.Jestarok.MovEmotions.Servicios;

import com.Jestarok.MovEmotions.Encapsulaciones.Data.Peliculas;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.DatosPelicula;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.ResumenPelicula;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.RetornoConsultaPeliculas;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jestarok on 4/18/2017.
 */
public class PeliculaServices {
    private static PeliculaServices instance;
    public String URL = "http://www.omdbapi.com/?";

    private PeliculaServices(){

    }

    /**
     *
     * @return
     */
    public static PeliculaServices getInstance(){
        if(instance== null){
            instance = new PeliculaServices();
        }
        return instance;
    }


    /**
     *
     * @param filtro
     * @return
     */
    public List<ResumenPelicula> listaPeliculasPorBusqueda(String filtro) throws Exception{
        System.out.printf(filtro);
        String filter = filtro.replaceAll(" ","_");
        System.out.printf(filter);
        List<ResumenPelicula> lista = new ArrayList<>(1);
        String ruta = URL+"s="+filter;
        System.out.println("Ruta "+ruta);
        //No puede ser vacio...
        if(filtro.trim().isEmpty()){
            System.out.println("Trama vacia....");
            return lista;
        }

        HttpResponse<RetornoConsultaPeliculas> jsonResponse = Unirest.get(ruta)
                .header("accept", "application/json")
                .asObject(RetornoConsultaPeliculas.class);
        //verificando la información.
        RetornoConsultaPeliculas tmp = jsonResponse.getBody();
        System.out.println(tmp.getListaPeliculas().get(0).getTitle() + tmp.getListaPeliculas().get(0).getImdbID());
        if (!tmp.getResponse()) {
            System.out.println("El mensaje de error: ");
            //TODO: Retornar un tipo especifico.
            throw new RuntimeException("Error en la consulta..");
        }

        lista = tmp.getListaPeliculas();

        return lista;
    }

    public void escribirTXT(){
        ArrayList<String> codigos = new ArrayList<>();
        ArrayList<String> descripciones = new ArrayList<>();
        try {
            File file = new File("C:\\Users\\mc\\Desktop\\x2.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                codigos.add(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String temp;
        for (String s: codigos) {
            temp = "";
            try{temp = buscarDescripcionPelicula(s);} catch (Exception e){ e.printStackTrace();}
            descripciones.add(temp);
            descripciones.add("\n");
        }

        try {
            Path file = Paths.get("C:\\Users\\mc\\Desktop\\y2.txt");
            Files.write(file, descripciones, Charset.forName("UTF-8"));
        }
        catch (Exception e){e.printStackTrace();}
    }

    public String buscarDescripcionPelicula(String filtro)throws Exception{
        System.out.printf(filtro);
        String filter = filtro.replaceAll(" ","_");
        System.out.printf(filter);
       String ruta = URL+"i="+filter+"&plot=full";
        System.out.println("Ruta "+ruta);
        //No puede ser vacio...
        if(filter.trim().isEmpty()){
            System.out.println("Trama vacia....");
            return "N/A";
        }

        HttpResponse<DatosPelicula> jsonResponse = Unirest.get(ruta)
                .header("accept", "application/json")
                .asObject(DatosPelicula.class);
        //verificando la información.
        DatosPelicula tmp = jsonResponse.getBody();
        System.out.println(tmp.getTitle()+" "+ tmp.getPlot());
        if (!tmp.isResponse()) {
            System.out.println("El mensaje de error: ");
            //TODO: Retornar un tipo especifico.
            throw new RuntimeException("Error en la consulta..");
        }


        return tmp.getPlot();
    }


}
