package com.studentorganizer.model;

public enum TecnicaEstudio {
	POMODORO("Pomodoro"),
    LIBRE("Libre");
    
    private final String displayName;
    
    TecnicaEstudio(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
