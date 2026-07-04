/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Json;

import Controller.Producto;
import Controller.ProductoAlimenticio;
import Controller.ProductoElectronico;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda y recupera la lista de productos en productos.json usando Gson.
 * Como Producto es una jerarquia (Electronico/Alimenticio), al leer tengo
 * que fijarme el campo "tipo" de cada objeto para saber a que subclase
 * convertirlo, cosa que Gson no puede adivinar solo.
 */
public class JsonProductos {

    private static final String RUTA = "src/Json/productos.json";

    public static void exportar(List<Producto> listaProductos) {

        try (Writer writer = new FileWriter(RUTA)) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaProductos, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Producto> importar() {

        List<Producto> listaProductos = new ArrayList<>();

        try (Reader reader = new FileReader(RUTA)) {

            Gson gson = new Gson();
            JsonArray arreglo = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement elemento : arreglo) {

                JsonObject obj = elemento.getAsJsonObject();
                String tipo = obj.get("tipo").getAsString();

                if ("Electrónico".equals(tipo)) {
                    listaProductos.add(gson.fromJson(obj, ProductoElectronico.class));
                } else {
                    listaProductos.add(gson.fromJson(obj, ProductoAlimenticio.class));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo productos.json todavia, se va a crear al guardar.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return listaProductos;
    }
}