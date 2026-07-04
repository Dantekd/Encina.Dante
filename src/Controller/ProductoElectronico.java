/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 * Producto de tipo electronico, ademas de los datos comunes tiene la
 * garantia en meses.
 *
 * @author Usuario
 */
public class ProductoElectronico extends Producto {

    private int garantiaMeses;

    public ProductoElectronico( int codigo, String marca, String modelo, double precio, int stock, int idAsociado,int garantiaMeses) {
        super(codigo, marca, modelo, precio, stock, idAsociado, "Electrónico");
        this.garantiaMeses = garantiaMeses;
    }

    public int getGarantiaMeses() {
        return garantiaMeses;
    }
    public void setGarantiaMeses(int garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
    }
    @Override
    public String getTipo() {
        return "Electrónico";
    }
    @Override
    public String getDatoExtra() {
        return "garantía= " + garantiaMeses + " meses";
    }

}
