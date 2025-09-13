package com.studentorganizer.model;

import java.time.LocalDateTime;

public class SesionEstudio {
	private Tarea tareaAsociada;
    private LocalDateTime horaInicio;
    private int duracionMinutos;
    private TecnicaEstudio tecnica;
    private boolean activa;
    
    public SesionEstudio(Tarea tarea, int duracion, TecnicaEstudio tecnica) {
        this.tareaAsociada = tarea;
        this.duracionMinutos = duracion;
        this.tecnica = tecnica;
        this.activa = false;
    }
    
    // Métodos de negocio
    public void iniciar() {
        this.horaInicio = LocalDateTime.now();
        this.activa = true;
    }
    
    public void finalizar() {
        this.activa = false;
    }
    
    public long getTiempoTranscurrido() {
        if (horaInicio == null) return 0;
        return java.time.Duration.between(horaInicio, LocalDateTime.now()).toMinutes();
    }
    
    // Getters y Setters
    public Tarea getTareaAsociada() { return tareaAsociada; }
    public void setTareaAsociada(Tarea tareaAsociada) { this.tareaAsociada = tareaAsociada; }
    
    public LocalDateTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalDateTime horaInicio) { this.horaInicio = horaInicio; }
    
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    
    public TecnicaEstudio getTecnica() { return tecnica; }
    public void setTecnica(TecnicaEstudio tecnica) { this.tecnica = tecnica; }
    
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

}
