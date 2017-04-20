package com.Jestarok.MovEmotions.Controladores;

import com.Jestarok.MovEmotions.Encapsulaciones.WS.DatosPelicula;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.ResumenPelicula;
import com.Jestarok.MovEmotions.Main.Main;
import com.Jestarok.MovEmotions.utilidades.IObserver;
import com.Jestarok.MovEmotions.utilidades.SubjectHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by Francis Cáceres on 19/4/2017.
 */
public class ListadoPeliculaController {
    private Main mainApp;

    private Stage ventana;

    @FXML
    private TextField txt_Buscador;

    @FXML
    private TableView<ResumenPelicula> tabla;

    @FXML
    private TableColumn<ResumenPelicula, String> idCol, nombreCol;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory( new PropertyValueFactory<>("imdbID"));
        nombreCol.setCellValueFactory( new PropertyValueFactory<>("Title"));

    }
    private SubjectHelper peliculaSeleccionadoListener = new SubjectHelper();

    private ObservableList<ResumenPelicula> data = FXCollections.observableArrayList();


    public void cargarClientes(List<ResumenPelicula> listaArticulo){
        //limpiar
        data.clear();
        //seteando la información
        for(ResumenPelicula p : listaArticulo){
            data.add(new ResumenPelicula(p));
        }
        //Implementando buscador en la tabla de clientes
        //Filtro para interactuar con la lista de clientes
        FilteredList<ResumenPelicula> filteredList = new FilteredList<>(data, e -> true);

        //Implementando el textField con un listener
        txt_Buscador.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(ResumenPelicula ->{
                //Si esta vacio, despliega todas las instancias
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                //Buscando por nombre, id y rnc respectivamente.
                if (ResumenPelicula.getTitle().toString().contains(newValue))
                    return true;
                else if (ResumenPelicula.getImdbID().toString().contains(newValue))
                    return true;


                return false;
            });
        });

        SortedList<ResumenPelicula> sorted = new SortedList<>(filteredList);

        sorted.comparatorProperty().bind(tabla.comparatorProperty());

        tabla.setItems(sorted);
    }

    /**
     *
     * @param observer
     */
    public void addPeliculaSeleccionadoListener(IObserver observer){
        peliculaSeleccionadoListener.addObserver(observer);
    }

    @FXML
    public void aceptar(){
        System.out.println("Aceptando...");
        ResumenPelicula tmp = tabla.getSelectionModel().getSelectedItem();
        peliculaSeleccionadoListener.notify(null, tmp, null);
        ventana.close();
    }

    @FXML
    public void cancelar(){
        System.out.println("Cancelar...");
        ventana.close();
    }


    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }

    public void setVentana(Stage ventana){
        this.ventana = ventana;
    }

}
