package com.studentorganizer.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.studentorganizer.util.LocalDateAdapter;
import com.studentorganizer.util.LocalDateTimeAdapter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 1. Lo hacemos genérico usando <T>
public class PersistenceService<T> {

    private final Gson gson;
    private final String filePath;

    public PersistenceService(String filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
    }

    /**
     * Carga una lista genérica de datos desde un archivo JSON.
     * @param typeToken El tipo de la lista a cargar (ej. new TypeToken<ArrayList<Estudiante>>() {})
     * @return Una lista con los objetos cargados o una lista vacía si ocurre un error.
     */
    public List<T> loadData(TypeToken<ArrayList<T>> typeToken) {
        try (FileReader reader = new FileReader(filePath)) {
            Type listType = typeToken.getType();
            List<T> data = gson.fromJson(reader, listType);
            return data != null ? data : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Archivo de datos '" + filePath + "' no encontrado. Se creará uno nuevo al guardar.");
            return new ArrayList<>();
        }
    }

    /**
     * Guarda una lista genérica de datos en el archivo JSON.
     * @param data La lista de todos los objetos a guardar.
     */
    public void saveData(List<T> data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("Error crítico al guardar los datos en '" + filePath + "'.");
            e.printStackTrace();
        }
    }
}