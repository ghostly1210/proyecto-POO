package com.studentorganizer.service;

import com.google.gson.reflect.TypeToken;
import com.studentorganizer.model.Estudiante;
import java.util.ArrayList;
import java.util.List;

public class EstudianteService {
    private List<Estudiante> estudiantes;
    private PersistenceService<Estudiante> persistenceService;
    private static final String DATA_FILE = "estudiantes.json"; //Nombre del archivo de guardado
    
    public EstudianteService() {
    	this.persistenceService = new PersistenceService<>(DATA_FILE);
    	this.estudiantes = persistenceService.loadData(new TypeToken<ArrayList<Estudiante>>() {});
    }
    
    public Estudiante autenticarEstudiante(String email, String password) {
    	for (Estudiante e : estudiantes) {
    		if (e.getEmail().equalsIgnoreCase(email) && e.getPassword().equals(password)) {
    			return e;
    		}
    	}
    	return null;    	
    }
    
    public boolean registrarEstudiante(String email, String password, String nombre, String carrera) {
    	for (Estudiante e : estudiantes) {
    		if (e.getEmail().equalsIgnoreCase(email)) {
    			return false; // El email ya existe
    		}
    	}
    	
    	Estudiante nuevoEstudiante = new Estudiante(nombre, carrera, email, password);
    	estudiantes.add(nuevoEstudiante);
    	saveAllData();
    	return true;
    }
    
    /** 
     * Un método central para guardar todos los cambios en el archivo.
     * Se debe llamar cada vez que se haga un cambio importante que deba persistir.
     */
    public void saveAllData() {
    	persistenceService.saveData(estudiantes);
    }
}