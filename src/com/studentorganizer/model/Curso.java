package com.studentorganizer.model;

import java.awt.Color;

public class Curso {
	private String nombre;
    private String descripcion;
    private transient Color color;
    
    public Curso(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.color = Color.BLUE; // Color por defecto
    }
    
    public Curso(String nombre, String descripcion, Color color) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.color = color;
    }
    
    // Getters y Setters
    public String getNombre() { 
		return nombre; 
	}
    public void setNombre(String nombre) { 
		this.nombre = nombre; 
	}
    
    public String getDescripcion() { 
		return descripcion; 
	}
    public void setDescripcion(String descripcion) { 
		this.descripcion = descripcion; 
	}
    
    public Color getColor() { 
    	if (color == null) {
    		color = Color.BLUE;
    	}
    	return color; 
    }
    public void setColor(Color color) { 
		this.color = color; 
	}
    
    @Override
    public String toString() {
        return nombre;
    }

}
