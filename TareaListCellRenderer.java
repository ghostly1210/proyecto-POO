package com.studentorganizer.gui;

import com.studentorganizer.model.*;
import javax.swing.*;
import java.awt.*;

public class TareaListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof Tarea) {
            Tarea tarea = (Tarea) value;
            
            // Formato del texto
            String texto = String.format("<html><b>%s</b><br><small>%s - %s</small><br><small style='color: gray;'>%s</small></html>",
                tarea.getNombre(),
                tarea.getFechaEntrega(),
                tarea.getPrioridad(),
                tarea.getCurso().getNombre());
            
            setText(texto);
            
            // Color de fondo según prioridad y estado
            if (!isSelected) {
                if (tarea.getEstado() == Estado.COMPLETADA) {
                    setBackground(new Color(212, 237, 218));
                    setForeground(new Color(21, 87, 36));
                } else {
                    switch (tarea.getPrioridad()) {
                        case ALTA:
                            setBackground(new Color(248, 215, 218));
                            setForeground(new Color(114, 28, 36));
                            break;
                        case MEDIA:
                            setBackground(new Color(255, 243, 205));
                            setForeground(new Color(133, 100, 4));
                            break;
                        case BAJA:
                            setBackground(new Color(209, 236, 241));
                            setForeground(new Color(12, 84, 96));
                            break;
                    }
                }
            }
        }
        
        return this;
    }
}