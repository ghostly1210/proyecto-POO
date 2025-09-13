package com.studentorganizer.model;

import java.util.ArrayList;
import java.util.List;

public class Estudiante {
	private String nombre;
    private String carrera;
    private String email;
    private String password;
    private List<Tarea> listaDeTareas;
    private Agenda agenda;
    
    public Estudiante(String nombre, String carrera, String email, String password) {
        this.nombre = nombre;
        this.carrera = carrera;
        this.email = email;
        this.password = password;
        this.listaDeTareas = new ArrayList<>();
        this.agenda = new Agenda();
    }
    
    // Métodos de negocio
    public void agregarTarea(Tarea nuevaTarea) {
        listaDeTareas.add(nuevaTarea);
        // Agregar a la agenda también
        agenda.agregarEvento(nuevaTarea, nuevaTarea.getFechaEntrega());
    }
    
    public void eliminarTarea(Tarea tareaAEliminar) {
        listaDeTareas.remove(tareaAEliminar);
    }
    
    public List<Tarea> obtenerTareasPendientes() {
        List<Tarea> tareasPendientes = new ArrayList<>();
        for (Tarea tarea : listaDeTareas) {
            if (tarea.getEstado() == Estado.PENDIENTE) {
                tareasPendientes.add(tarea);
            }
        }
        return tareasPendientes;
    }
    
    public List<Tarea> obtenerTareasCompletadas() {
        List<Tarea> tareasCompletadas = new ArrayList<>();
        for (Tarea tarea : listaDeTareas) {
            if (tarea.getEstado() == Estado.COMPLETADA) {
                tareasCompletadas.add(tarea);
            }
        }
        return tareasCompletadas;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public List<Tarea> getListaDeTareas() { return listaDeTareas; }
    public void setListaDeTareas(List<Tarea> listaDeTareas) { this.listaDeTareas = listaDeTareas; }
    
    public Agenda getAgenda() { return agenda; }
    public void setAgenda(Agenda agenda) { this.agenda = agenda; }

}
