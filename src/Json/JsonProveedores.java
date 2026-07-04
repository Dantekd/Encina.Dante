/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Json;


import Controller.Proveedor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Los proveedores vienen cargados por otro sistema en un archivo externo
 * (proveedores.json), por eso esta clase solo importa. No hay metodo
 * exportar porque el enunciado dice que los proveedores no se pueden
 * crear, modificar ni eliminar desde esta aplicacion.
 */
public class JsonProveedores {

    private static final String RUTA = "src/Json/proveedores.json";

    public static List<Proveedor> importar() {

        try (Reader reader = new FileReader(RUTA)) {

            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Proveedor>>() {}.getType();

            List<Proveedor> lista = gson.fromJson(reader, tipoLista);

            return lista != null ? lista : new ArrayList<>();

        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de proveedores.");
            return new ArrayList<>();
        }
    }
}