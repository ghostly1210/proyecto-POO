package com.studentorganizer.model;

import java.time.LocalDateTime;

public class Recordatorio {
	 private String mensaje;
	    private LocalDateTime fechaHora;
	    private Tarea tareaAsociada;
	    private boolean activo;
	    
	    public Recordatorio(String mensaje, LocalDateTime fechaHora, Tarea tareaAsociada) {
	        this.mensaje = mensaje;
	        this.fechaHora = fechaHora;
	        this.tareaAsociada = tareaAsociada;
	        this.activo = true;
	    }
	    
	    public boolean debeActivarse() {
	        return activo && LocalDateTime.now().isAfter(fechaHora);
	    }
	    
	    public void desactivar() {
	        this.activo = false;
	    }
	    
	    // Getters y Setters
	    public String getMensaje() { 
			return mensaje; 
		}
	    public void setMensaje(String mensaje) { 
			this.mensaje = mensaje; 
		}
	    
	    public LocalDateTime getFechaHora() { 
			return fechaHora; 
		}
	    public void setFechaHora(LocalDateTime fechaHora) {
			this.fechaHora = fechaHora;
		}
	    
	    public Tarea getTareaAsociada() { 
			return tareaAsociada;
		}
	    public void setTareaAsociada(Tarea tareaAsociada) {
			this.tareaAsociada = tareaAsociada;
		}
	    
	    public boolean isActivo() { 
			return activo;
		}
	    public void setActivo(boolean activo) {
			this.activo = activo; 
		}

}
