/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Uso para las dos funcionalidades adicionales (ver el
 * archivo de productos caros y ver el valor total del inventario)  *
 * @author Usuario
 */
public class VerTextoController implements Initializable {

    @FXML
    private TextArea txtContenido;
    @FXML
    private Button btnCerrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //no hace falta hacer nada aca
    }

    public void setContenido(String contenido) {
        txtContenido.setText(contenido);
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}
