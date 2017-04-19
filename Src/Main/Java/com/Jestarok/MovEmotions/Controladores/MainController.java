package com.Jestarok.MovEmotions.Controladores;

import com.Jestarok.MovEmotions.Encapsulaciones.WS.DatosPelicula;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.ResumenPelicula;
import com.Jestarok.MovEmotions.Main.Main;
import com.Jestarok.MovEmotions.Servicios.PeliculaServices;
import com.Jestarok.MovEmotions.utilidades.IObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Jestarok on 4/19/2017.
 */
public class MainController {
    Main mainApp;
    ResumenPelicula resumenPeliculaSeleccionado;

    @FXML
    Button btnBuscar = new Button();

    @FXML
    TextField txtPelicula = new TextField();

    @FXML
    TextField txtImdbID = new TextField();

    public void inicializarApp(Main mainApp) {
        System.out.println("Inicializando el Punto de Venta...");
        this.mainApp = mainApp;
        mainApp.getPrimaryStage().getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case F2:
                        System.out.println("Presionando F2");
                        break;
                    case F3:
                        System.out.println("Presionando F3");
                        break;
                    case F4:
                        System.out.println("Presionando F4");
                        break;
                    case F5:
                        System.out.println("Presionando F5");
                        break;
                    case F6:
                        System.out.println("Presionando F6");
                        break;
                }
            }
        });

        //incluyendo el vendedor.
    }

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


    private void completarDatosCliente(ResumenPelicula tmp){
        resumenPeliculaSeleccionado = tmp;
        txtPelicula.setText(""+tmp.getTitle());
        txtImdbID.setText(""+tmp.getImdbID());
    }

    private void visualizarListadoCliente(List<ResumenPelicula> listaCliente){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/Fxml/Main/ListadoPelicula.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            ListadoPeliculaController listadoPeliculaController = loader.getController();
            listadoPeliculaController.setMainApp(mainApp);
            listadoPeliculaController.cargarClientes(listaCliente);
            listadoPeliculaController.addPeliculaSeleccionadoListener(new IObserver() {
                @Override
                public void update(Class clase, Object argumento, Enum anEnum) {
                    completarDatosCliente((ResumenPelicula) argumento);
                }
            });

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Listado de Peliculas");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            listadoPeliculaController.setVentana(dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Mostrando y esperando el cierre.
            dialogStage.showAndWait();

        }catch (Exception ex){
            ex.printStackTrace();
        }
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
