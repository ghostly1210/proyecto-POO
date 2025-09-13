package com.studentorganizer.model;

public enum Estado {
	PENDIENTE("Pendiente"),
    COMPLETADA("Completada");
    
    private final String displayName;
    
    Estado(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }

}
