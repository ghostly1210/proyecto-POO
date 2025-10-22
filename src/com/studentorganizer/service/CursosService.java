package com.studentorganizer.service;

import com.google.gson.reflect.TypeToken;
import com.studentorganizer.model.Curso;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CursosService {
    private List<Curso> listaDeCursos;
    private PersistenceService<Curso> persistenceService;
    private static final String FILE_PATH = "cursos.json";

    public CursosService() {
        this.persistenceService = new PersistenceService<>(FILE_PATH);
        this.listaDeCursos = persistenceService.loadData(new TypeToken<ArrayList<Curso>>() {});

        if (this.listaDeCursos.isEmpty()) {
            cargarCursosPorDefecto();
        }
    }

    public void cargarCursosPorDefecto() {
        this.listaDeCursos.add(new Curso("Matemáticas", "Curso de matemáticas", Color.BLUE));
        this.listaDeCursos.add(new Curso("Programación", "Curso de programación", Color.RED));
        this.listaDeCursos.add(new Curso("Historia", "Curso de historia", Color.YELLOW));
        this.listaDeCursos.add(new Curso("Ciencias", "Curso de ciencias", Color.GREEN));
        saveCursos();
    }

    private void saveCursos() {
        persistenceService.saveData(listaDeCursos);
    }

    public List<Curso> getListaDeCursos() {
        return listaDeCursos;
    }

    public void agregarCurso(String nombre, String descripcion, Color color) {
        Curso nuevoCurso = new Curso(nombre, descripcion, new Color((int) (Math.random() * 0x1000000)));
        listaDeCursos.add(nuevoCurso);
        saveCursos();
    }

    public void eliminarCurso(Curso curso) {
        listaDeCursos.remove(curso);
        saveCursos();
    }
}

