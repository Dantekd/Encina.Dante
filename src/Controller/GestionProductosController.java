/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Json.JsonProductos;
import Json.JsonProveedores;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

/**
 *
 * @author Usuario
 */
public class GestionProductosController implements Initializable {

    @FXML
    private AnchorPane paneSistema;
    @FXML
    private ListView<Proveedor> ViewProveedores;
    @FXML
    private ListView<Producto> ViewProductos;
    @FXML
    private Button btnagregar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnVerCaros;
    @FXML
    private Button btnValorTotal;

    private List<Producto> ListaProducto;

    private List<Proveedor> ListaProvedores;

    //ruta del txt (precio > 500000)
    private static final String RUTA_CAROS = "src/txt/productos_caros.txt";

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ListaProducto = JsonProductos.importar();
        ListaProvedores = JsonProveedores.importar();
        actualizarListView();
        exportarProductosCaros();
    }

    @FXML
    private void agregar(ActionEvent event) throws IOException {

        abrirVentanaModal(null);

        JsonProductos.exportar(ListaProducto);
        exportarProductosCaros();

        System.out.println("Estoy en agregar");
    }

    @FXML
    private void eliminar(ActionEvent event) {

        Producto seleccionado = (Producto) this.ViewProductos.getSelectionModel().getSelectedItem();

        System.out.println("Estoy en eliminar");
        if (seleccionado != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar elección");
            alerta.setHeaderText("¿Estás seguro que querés eliminar el producto?");
            alerta.setContentText(seleccionado.getCodigo() + " " + seleccionado.getMarca());

            Optional<ButtonType> resultado = alerta.showAndWait();
            
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

                this.ListaProducto.remove(seleccionado);
                this.actualizarListView();

            }
            JsonProductos.exportar(ListaProducto);
            exportarProductosCaros();

            //para exportar
        }

    }

    @FXML
    private void modificar(ActionEvent event) throws IOException {

        Producto productoAModificar
                = (Producto) ViewProductos.getSelectionModel().getSelectedItem();

        if (productoAModificar != null) {
            abrirVentanaModal(productoAModificar);
        }

        JsonProductos.exportar(ListaProducto);
        
        //aca se exportan los productos ca
        exportarProductosCaros();

        System.out.println("Estoy en modificar");
    }

    private void abrirVentanaModal(Producto productoExistente)throws IOException {

        try {

            FXMLLoader loader= new FXMLLoader(getClass().getResource("/FXML/Info_Productos.fxml"));

            Scene scene = new Scene(loader.load());

            Info_ProductosController controller= loader.getController();

            controller.setListaProductosActual(ListaProducto);
            controller.setProducto(productoExistente);

            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setScene(scene);

            stage.showAndWait();

            Producto resultado = controller.getProducto();
            //Esto sirve para verificar que se hizo si se agrego, se modifico o se evita agregar un producto repetido
            if (resultado != null) {

                if (productoExistente == null) {

                    if (!ListaProducto.contains(resultado)) {
                        ListaProducto.add(resultado);
                    }

                } else {
                    //Recorre la lista hasta encontrar el producto
                    for (int i = 0; i < ListaProducto.size(); i++) {

                        if (ListaProducto.get(i).equals(productoExistente)) {

                            ListaProducto.set(i, resultado);
                            break;

                        }
                    }
                }

                actualizarListView();
            }
            //devuelve el error
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//Aca limpia y pone los valores actuales de productos y proveedores para que este actualizado

    private void actualizarListView() {
        ViewProductos.getItems().clear();

        ViewProductos.getItems().addAll(ListaProducto);

        ViewProveedores.getItems().clear();

        ViewProveedores.getItems().setAll(ListaProvedores);
    }


    
    
    
    
    //genera el .txt con todos los productos cuyo precio sea mayor a 500000
    private void exportarProductosCaros() {

        try (FileWriter writer = new FileWriter(RUTA_CAROS)) {
            
            writer.append("Listado de productos con precio superior a $500.000\n");
            writer.append("_____________________________________________________\n");

            boolean hayCaros = false;
            // Recorre la lista de productos y si el precio es mayor de 500000 lo devuelve
            for (Producto p : ListaProducto) {
                if (p.getPrecio() > 500000) {
                    writer.append(p.toString()).append("\n");
                    hayCaros = true;
                }
            }

            if (!hayCaros) {
                writer.append("(No hay productos con precio superior a $500.000)\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //muestra los productos caros
    @FXML
    private void verProductosCaros(ActionEvent event) {

        try {

            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_CAROS)));

            abrirVentanaTexto("Productos con precio > $500.000", contenido);

        } catch (IOException e) {
            mostrarAviso("Todavía no se generó el archivo de productos caros.");
        }
    }

    //abre otra ventana mostrando el valor total del inventario (precio * stock de cada producto)
    @FXML
    private void verValorTotal(ActionEvent event) {

        double total = 0;

        for (Producto p : ListaProducto) {
            total += p.getPrecio() * p.getStock();
        }

        String texto = String.format(Locale.forLanguageTag("es-AR"), "Valor total del inventario: $ %,.2f", total);

        abrirVentanaTexto("Valor total del inventario", texto);
    }

    //ventana generica para mostrar texto (la reutilizo para las dos funcionalidades adicionales)
    private void abrirVentanaTexto(String titulo, String contenido) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/VerTexto.fxml"));

            Scene scene = new Scene(loader.load());

            VerTextoController controller = loader.getController();
            controller.setContenido(contenido);

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAviso(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Aviso");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
