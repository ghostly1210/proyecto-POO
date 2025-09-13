package com.studentorganizer.model;

import java.time.LocalDate;

public class Tarea {
	private String nombre;
    private String descripcion;
    private LocalDate fechaEntrega;
    private Prioridad prioridad;
    private Estado estado;
    private Curso curso;
    
    public Tarea(String nombre, LocalDate fechaEntrega, Curso curso) {
        this.nombre = nombre;
        this.fechaEntrega = fechaEntrega;
        this.curso = curso;
        this.estado = Estado.PENDIENTE;
        this.prioridad = Prioridad.MEDIA;
        this.descripcion = "";
    }
    
    public Tarea(String nombre, String descripcion, LocalDate fechaEntrega, Prioridad prioridad, Curso curso) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.prioridad = prioridad;
        this.curso = curso;
        this.estado = Estado.PENDIENTE;
    }
    
    // Métodos de negocio
    public void marcarComoCompletada() {
        this.estado = Estado.COMPLETADA;
    }
    
    public void editarTarea(String nombre, String descripcion, LocalDate fechaEntrega, Prioridad prioridad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.prioridad = prioridad;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    
    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }
    
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
    
    @Override
    public String toString() {
        return nombre + " - " + fechaEntrega + " (" + prioridad + ")";
    }

}
