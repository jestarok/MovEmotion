package com.Jestarok.MovEmotions.Servicios;

import com.Jestarok.MovEmotions.Encapsulaciones.Data.Peliculas;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.DatosPelicula;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.ResumenPelicula;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.RetornoConsultaPeliculas;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

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
        List<ResumenPelicula> lista = new ArrayList<>(1);
        String ruta = URL+"s="+filtro;
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

    public String buscarDescripcionPelicula(String filtro)throws Exception{
       String ruta = URL+"i="+filtro+"&plot=full";
        System.out.println("Ruta "+ruta);
        //No puede ser vacio...
        if(filtro.trim().isEmpty()){
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

   /* public RetornoDataArticulo consultarDatosArticulo(String articuloId,String clienteID) throws Exception{
        String ruta2 = URL+"/consultarArticuloFacturar ";
        //List<PerfilPrecio> lista = consultarPerfilPrecio(articuloId,clienteID);
        //segundo request, este llama la nueva funcionalidad del api "/consultarArticuloFacturar"
        HttpResponse<RetornoDataArticulo> jsonResponse2 = Unirest.post(ruta2)
                .header("accept", "application/json")
                .field("secureToken", Main.usuarioMovil.getTokenFacturacion())
                .field("articuloId", articuloId)
                .field("clienteId", clienteID)
                .field("monedaId", 31)//lista.get(0).getMoneda().getMonedaId())
                .asObject(RetornoDataArticulo.class);
        RetornoDataArticulo tmp2 =  jsonResponse2.getBody();
        System.out.println("RetornoDataArticulo  "+ tmp2.toString());
        return tmp2;
    }*/
}
