/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Usuario
 */
public class ProductoAlimenticio extends Producto {

    private String fechaVencimiento;

    public ProductoAlimenticio(int codigo, String marca, String modelo, double precio, int stock, int idAsociado, String fechaVencimiento) {
        super(codigo, marca, modelo, precio, stock, idAsociado, "Alimenticio");
        this.fechaVencimiento = fechaVencimiento;
    }

   

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String getTipo() {
        return "Alimenticio";
    }

    @Override
    public String getDatoExtra() {
        return "vencimiento=" + fechaVencimiento;
    }

}
