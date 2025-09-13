package com.studentorganizer.model;

import java.time.LocalDate;
import java.util.*;

public class Agenda {
private Map<LocalDate, List<Tarea>> eventos;
    
    public Agenda() {
        this.eventos = new HashMap<>();
    }
    
    public void agregarEvento(Tarea evento, LocalDate fecha) {
        if (!eventos.containsKey(fecha)) {
            eventos.put(fecha, new ArrayList<>());
        }
        eventos.get(fecha).add(evento);
    }
    
    public List<Tarea> verEventosDelDia(LocalDate fecha) {
        return eventos.getOrDefault(fecha, new ArrayList<>());
    }
    
    public Map<LocalDate, List<Tarea>> verEventosDelMes(LocalDate fecha) {
        Map<LocalDate, List<Tarea>> eventosMes = new HashMap<>();
        LocalDate inicioMes = fecha.withDayOfMonth(1);
        LocalDate finMes = fecha.withDayOfMonth(fecha.lengthOfMonth());
        
        for (Map.Entry<LocalDate, List<Tarea>> entry : eventos.entrySet()) {
            LocalDate fechaEvento = entry.getKey();
            if (!fechaEvento.isBefore(inicioMes) && !fechaEvento.isAfter(finMes)) {
                eventosMes.put(fechaEvento, entry.getValue());
            }
        }
        
        return eventosMes;
    }
    
    public Map<LocalDate, List<Tarea>> getEventos() {
        return eventos;
    }
}

