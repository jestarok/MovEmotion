package com.Jestarok.MovEmotions.Controladores;

import com.Jestarok.MovEmotions.Encapsulaciones.WS.DatosPelicula;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.ResumenPelicula;
import com.Jestarok.MovEmotions.Servicios.PeliculaServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * Created by Jestarok on 4/19/2017.
 */
public class MainController {

    @FXML
    Button btnBuscar = new Button();

    @FXML
    TextField txtPelicula = new TextField();

    public void buscarPeliculaPorNombre() {

        //txtpelicula.setText("i=tt1640571");


        try {
            List<ResumenPelicula> lista = PeliculaServices.getInstance().listaPeliculasPorBusqueda(txtPelicula.getText());
            if (lista.size() > 1) {
                //visualizarListadoCliente(lista);
            } else if(lista.size() == 1){
                PeliculaServices.getInstance().buscarDescripcionPelicula(lista.get(0).getImdbID());
            }/* else {
                        visualizarListadoPeliculas(lista);
                    }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(txtPelicula.getText().toString());
    }



/*
        StackPane root = new StackPane();
        root.getChildren().addAll(txtpelicula,btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        */

    public void cerrar(){
        System.out.println("Cerrando la aplicación");
        System.exit(0);
    }
}
