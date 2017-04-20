package com.Jestarok.MovEmotions.Servicios;

import com.Jestarok.MovEmotions.Encapsulaciones.Data.Peliculas;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.DatosPelicula;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.ResumenPelicula;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.RetornoConsultaPeliculas;
import com.Jestarok.MovEmotions.Main.Main;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jestarok on 4/18/2017.
 */
public class PeliculaServices {
    private static PeliculaServices instance;
    ArrayList<String> WBExciting;
    ArrayList<String> WBSadness;
    ArrayList<String> WBToughtfull;
    ArrayList<String> WBTerror;
    public String URL = "http://www.omdbapi.com/?";

    private PeliculaServices(){
        WBExciting = new ArrayList<>();
        WBSadness = new ArrayList<>();
        WBToughtfull = new ArrayList<>();
        WBTerror = new ArrayList<>();
        cargarWordBags();
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
            File file = new File("C:\\Users\\mc\\Desktop\\x.txt");
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
            Path file = Paths.get("C:\\Users\\mc\\Desktop\\y.txt");
            Files.write(file, descripciones, Charset.forName("UTF-8"));
        }
        catch (Exception e){e.printStackTrace();}
    }

    public  void  readTest(){
        ArrayList<String> test = new ArrayList<>();
        try {
            File file = new File("C:\\Users\\mc\\Desktop\\z.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                test.add(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PeliculaServices.getInstance().Bayify(test);
    }

    public String buscarDescripcionPelicula(String filtro)throws Exception{
        ArrayList<String> descripciones = new ArrayList<>();
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

        try {
            descripciones.add(tmp.getPlot());
            Path file = Paths.get("C:\\Users\\mc\\Desktop\\y.txt");
            Files.write(file, descripciones, Charset.forName("UTF-8"));
        }
        catch (Exception e){e.printStackTrace();}

        return tmp.getPlot();
    }

    public void cargarWordBags(){
        try {

            File file = new File(Main.class.getResource("/mining/Exciting.txt").getPath().toString());
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                WBExciting.add(line);
            }
            System.out.println(WBExciting.size());
            fileReader.close();

            file = new File(Main.class.getResource("/mining/Sadness.txt").getPath().toString());
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            stringBuffer = new StringBuffer();

            while ((line = bufferedReader.readLine()) != null) {
                WBSadness.add(line);
            }
            System.out.println(WBSadness.size());
            fileReader.close();

            file = new File(Main.class.getResource("/mining/Toughtfull.txt").getPath().toString());
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            stringBuffer = new StringBuffer();

            while ((line = bufferedReader.readLine()) != null) {
                WBToughtfull.add(line);
            }
            System.out.println(WBToughtfull.size());
            fileReader.close();

            file = new File(Main.class.getResource("/mining/Terror.txt").getPath().toString());
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            stringBuffer = new StringBuffer();

            while ((line = bufferedReader.readLine()) != null) {
                WBTerror.add(line);
            }
            System.out.println(WBTerror.size());
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void Bayify(ArrayList<String> TestBag){
        float excitingWords = 0, sadWords = 0, thoughtfullWords = 0, scaryWords = 0;
        float excitingProbability = 0, sadProbability = 0, thoughtfullProbability = 0, scaryProbability = 0;
        float excitingPercent = 0, sadPercent = 0, thoughtfullPercent = 0, scaryPercent = 0;

        for (String s : TestBag
             ) {
            if(WBExciting.contains(s)){
                excitingWords++;
            }
            if(WBSadness.contains(s)){
                sadWords++;
            }
            if(WBToughtfull.contains(s)){
                thoughtfullWords++;
            }
            if(WBTerror.contains(s)){
                scaryWords++;
            }
        }
        System.out.println(excitingWords + " " + WBExciting.size());
        excitingProbability  = excitingWords/WBExciting.size();
        sadProbability = sadWords/WBSadness.size();
        thoughtfullProbability = thoughtfullWords/WBToughtfull.size();
        scaryProbability = scaryWords/WBTerror.size();

        System.out.println("porciento" + excitingProbability+"%");
        System.out.println("porciento" + sadProbability+"%");
        System.out.println("porciento" + thoughtfullProbability+"%");
        System.out.println("porciento" + scaryProbability+"%");

        excitingPercent  = excitingWords/TestBag.size();
        sadPercent = sadWords/TestBag.size();
        thoughtfullPercent = thoughtfullWords/TestBag.size();
        scaryPercent = scaryWords/TestBag.size();

        System.out.println("probabilidad" + excitingPercent+"%");
        System.out.println("probabilidad" + sadPercent+"%");
        System.out.println("probabilidad" + thoughtfullPercent+"%");
        System.out.println("probabilidad" + scaryPercent+"%");

        float mayor = Math.max(Math.max(excitingProbability,sadProbability),Math.max(thoughtfullProbability,scaryProbability));
        String probabilidad;
        if(mayor == excitingProbability){
            probabilidad = "Emocionante";
        } else if(mayor == sadProbability){
            probabilidad = "Triste";
        } else if(mayor == thoughtfullProbability){
            probabilidad = "Pensativo";
        }else if(mayor == scaryProbability){
            probabilidad = "Atemorizante";
        }else{
            probabilidad = "indeterminado";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado");
        alert.setContentText(probabilidad);
        alert.showAndWait();
    }
}
