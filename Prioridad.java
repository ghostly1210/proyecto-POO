package com.studentorganizer.model;

public enum Prioridad {
	ALTA("Alta"),
    MEDIA("Media"),
    BAJA("Baja");
    
    private final String displayName;
    
    Prioridad(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }

}
