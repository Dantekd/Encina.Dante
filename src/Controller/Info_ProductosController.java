/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Json.JsonProveedores;
import errores.ErrorProducto;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.util.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class Info_ProductosController implements Initializable {

    @FXML
    private ChoiceBox<String> cbTipo;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtStock;
    @FXML
    private ChoiceBox<Proveedor> cbProveedor;
    @FXML
    private TextField txtPrecio;
    @FXML
    private Button btnaceptar;
    @FXML
    private Button btncancelar;
    @FXML
    private Label lblGarantia;
    @FXML
    private TextField txtGarantia;
    @FXML
    private Label lblVencimiento;
    @FXML
    private TextField txtVencimiento;

    private Producto producto;
    private boolean confirmado;

    //lista de productos actuales, la uso solo para validar que no se repita el codigo
    private List<Producto> listaProductosActual;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbProveedor.getItems().addAll(JsonProveedores.importar());

        cbProveedor.setConverter(new StringConverter<Proveedor>() {
            @Override
            public String toString(Proveedor proveedor) {

                if (proveedor == null) {
                    return "";
                }

                return proveedor.getId() + " - " + proveedor.getRazonSocial();
            }

            @Override
            public Proveedor fromString(String texto) {
                return null;
            }
        });

        cbTipo.getItems().addAll("Electrónico", "Alimenticio");

        //segun el tipo que elija, muestro un campo u otro (garantia o vencimiento)
        cbTipo.setOnAction(e -> actualizarCamposSegunTipo());

        actualizarCamposSegunTipo();
    }

    public Info_ProductosController() {
        System.out.println("SE CREÓ EL CONTROLADOR");
    }

    public Producto getProducto() {
        return this.confirmado ? this.producto : null;
    }

    public void setListaProductosActual(List<Producto> lista) {
        this.listaProductosActual = lista;
    }

    public void setProducto(Producto productoExistente) {

        this.producto = productoExistente;

        if (producto != null) {

            this.cbTipo.setValue(producto.getTipo());
            this.txtCodigo.setText(String.valueOf(producto.getCodigo()));
            this.txtMarca.setText(producto.getMarca());
            this.txtModelo.setText(producto.getModelo());
            this.txtPrecio.setText(String.valueOf(producto.getPrecio()));
            this.txtStock.setText(String.valueOf(producto.getStock()));

            //el codigo no se puede modificar una vez creado
            this.txtCodigo.setDisable(true);

            if (producto instanceof ProductoElectronico) {
                this.txtGarantia.setText(String.valueOf(((ProductoElectronico) producto).getGarantiaMeses()));
            } else if (producto instanceof ProductoAlimenticio) {
                this.txtVencimiento.setText(((ProductoAlimenticio) producto).getFechaVencimiento());
            }

            for (Proveedor p : cbProveedor.getItems()) { //Esto Hace que ahora se puedan guardar los objetos no fijos que tiene proveedores(nose hace para el otro porque el otros estan tipados lo que podes elegir)

                if (p.getId() == producto.getIdAsociado()) {
                    cbProveedor.setValue(p);
                    break;
                }
            }

            actualizarCamposSegunTipo();
        }
    }

    //muestra/oculta los campos de garantia o vencimiento segun el tipo elegido
    private void actualizarCamposSegunTipo() {

        String tipo = cbTipo.getValue();

        boolean esElectronico = "Electrónico".equals(tipo);
        boolean esAlimenticio = "Alimenticio".equals(tipo);

        lblGarantia.setVisible(esElectronico);
        lblGarantia.setManaged(esElectronico);
        txtGarantia.setVisible(esElectronico);
        txtGarantia.setManaged(esElectronico);

        lblVencimiento.setVisible(esAlimenticio);
        lblVencimiento.setManaged(esAlimenticio);
        txtVencimiento.setVisible(esAlimenticio);
        txtVencimiento.setManaged(esAlimenticio);
    }

    @FXML
    private void aceptar(ActionEvent event) {
        //utilizo la clase del error para ciertas variables
        try {

            if (txtCodigo.getText().isEmpty()) {
                throw new ErrorProducto("Debe ingresar el código.");
            }

            if (txtMarca.getText().isEmpty()) {
                throw new ErrorProducto("Debe ingresar la marca.");
            }

            if (cbProveedor.getValue() == null) {
                throw new ErrorProducto("Debe seleccionar un proveedor.");
            }

            if (cbTipo.getValue() == null) {
                throw new ErrorProducto("Debe seleccionar el tipo de producto.");
            }

            int codigo = Integer.parseInt(txtCodigo.getText());
            String marca = txtMarca.getText();
            String modelo = txtModelo.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());
            int idAsociado = cbProveedor.getValue().getId();

            if (precio <= 0) {
                throw new ErrorProducto("El precio debe ser mayor a cero.");
            }

            if (stock < 0) {
                throw new ErrorProducto("El stock no puede ser negativo.");
            }

            //valido que no exista otro producto con el mismo codigo (solo al agregar uno nuevo)
            if (producto == null && listaProductosActual != null) {
                for (Producto p : listaProductosActual) {
                    if (p.getCodigo() == codigo) {
                        throw new ErrorProducto("Ya existe un producto con ese código.");
                    }
                }
            }

            String tipo = cbTipo.getValue();

            if ("Electrónico".equals(tipo)) {

                if (txtGarantia.getText().isEmpty()) {
                    throw new ErrorProducto("Debe ingresar la garantía en meses.");
                }

                int garantia = Integer.parseInt(txtGarantia.getText());

                if (garantia <= 0) {
                    throw new ErrorProducto("La garantía debe ser mayor a cero.");
                }

                producto = new ProductoElectronico(codigo, marca, modelo, precio, stock, idAsociado, garantia);

            } else {

                if (txtVencimiento.getText().isEmpty()) {
                    throw new ErrorProducto("Debe ingresar la fecha de vencimiento.");
                }

                producto = new ProductoAlimenticio(codigo, marca, modelo, precio, stock, idAsociado, txtVencimiento.getText());
            }

            confirmado = true;
            cerrarVentana();

        } catch (ErrorProducto e) {
            //te da el mensaje
            System.out.println(e.getMessage());
            mostrarError(e.getMessage());
        } catch (NumberFormatException e) {
            mostrarError("Precio, stock y garantía deben ser numéricos.");
        }
    }

    private void mostrarError(String mensaje) {
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void cancelar(ActionEvent e) {

        this.confirmado = false;
        this.cerrarVentana();
    }

    private void cerrarVentana() {
        //es para reducir el codigo que tiene cancelar
        Stage stage = (Stage) this.btncancelar.getScene().getWindow();

        stage.close();

    }
}
