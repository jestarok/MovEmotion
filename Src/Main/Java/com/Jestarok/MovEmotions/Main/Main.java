package com.Jestarok.MovEmotions.Main;

import com.Jestarok.MovEmotions.Controladores.MainController;
import com.Jestarok.MovEmotions.Encapsulaciones.WS.DatosPelicula;
import com.Jestarok.MovEmotions.Servicios.PeliculaServices;
import com.Jestarok.MovEmotions.utilidades.Utilidades;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        Utilidades.agregarSerializacionUniRest();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Main/Main.fxml"));
        Scene scene = new Scene(root,600,400);
        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();


        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }
}