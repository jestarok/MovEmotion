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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    Button btnBuscarID = new Button();
    @FXML
    Button btnBuscarNombre = new Button();
    @FXML
    Button Bayify = new Button();

    @FXML
    TextField txtPeliculaId = new TextField();

    @FXML
    TextField txtPeliculaNombre = new TextField();


    public void inicializarApp(Main mainApp) {
        System.out.println("Inicializando el Punto de Venta...");
        this.mainApp = mainApp;

    }

    public void buscarPeliculaPorNombre() {

        //txtpelicula.setText("i=tt1640571");


        try {
            List<ResumenPelicula> lista = PeliculaServices.getInstance().listaPeliculasPorBusqueda(txtPeliculaNombre.getText());
            if (lista.size() > 1) {
                visualizarListadoCliente(lista);
            } else if(lista.size() == 1){
                PeliculaServices.getInstance().buscarDescripcionPelicula(lista.get(0).getImdbID());
            }/* else {
                        visualizarListadoPeliculas(lista);
                    }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(txtPeliculaNombre.getText().toString());
    }


    private void completarDatosCliente(ResumenPelicula tmp){
        resumenPeliculaSeleccionado = tmp;
        try {
            String desc = PeliculaServices.getInstance().buscarDescripcionPelicula(tmp.getImdbID());
            System.out.println(desc);
        }catch (Exception e){
            e.printStackTrace();
        }
        txtPeliculaNombre.setText(""+tmp.getTitle());
        txtPeliculaId.setText(""+tmp.getImdbID());
    }

    public  void callBayes(){
        PeliculaServices.getInstance().readTest();
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
