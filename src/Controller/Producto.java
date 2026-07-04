/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 
 *
 * @author Usuario
 */
public abstract class Producto {

    private int codigo;
    private String marca;
    private String modelo;
    private double precio;
    private int stock;
    private int idAsociado;
    protected String tipo; //lo pone cada subclase en su constructor

    public Producto(int codigo, String marca, String modelo, double precio, int stock, int idAsociado, String tipo) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
        this.idAsociado = idAsociado;
        this.tipo = tipo;
    }

    public int getCodigo() {
        return codigo;
    }

    

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdAsociado() {
        return idAsociado;
    }

    public void setIdAsociado(int idAsociado) {
        this.idAsociado = idAsociado;
    }

    public String getTipo() {
        return tipo;
    }

    //cada subclase arma su dato extra (garantia o vencimiento) para el toString
    public abstract String getDatoExtra();

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.codigo;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Producto other = (Producto) obj;
        return this.codigo == other.codigo;
    }

    @Override
    public String toString() {
        return tipo + " | codigo=" + codigo + ", marca=" + marca + ", modelo=" + modelo+ ", precio=$" + precio + ", stock=" + stock + ", idProveedor=" + idAsociado+ ", " + getDatoExtra();
    }
}