/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package encinadanteparcial2;

import Controller.GestionProductosController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class TiendaComercial  extends Application {
   @Override
    public void start(Stage stage)throws Exception{
            System.out.println(getClass().getResource("/FXML/GestionProductos.fxml"));
            
    FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/GestionProductos.fxml")); 
        
        Scene scene = new Scene(loader.load());    
       
        GestionProductosController controller = loader.getController();

        
       
        stage.setScene(scene);
        
        stage.setTitle("Sistema de Gestión de Productos ");
        
        stage.show(); 
     //esto es para que al guardar se cierre   
     
     
    }
    public static void main(String[] args) {
        System.out.println("Entró al main");

        Application.launch(args);
    }

}
   