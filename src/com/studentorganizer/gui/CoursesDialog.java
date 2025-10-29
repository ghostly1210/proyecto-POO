package com.studentorganizer.gui;

import com.studentorganizer.model.Curso;
import com.studentorganizer.service.CursosService;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesDialog extends JDialog {
    private DefaultListModel<Curso> coursesModel;
    private JList<Curso> coursesList;
    private CursosService cursosService;
    
    public CoursesDialog(Frame parent, CursosService cursosService) {
        super(parent, "Gestionar Cursos", true);
        this.cursosService = cursosService;
        
        initializeComponents();
        setupUI();
        loadCourses();
    }
    
    private void initializeComponents() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        coursesModel = new DefaultListModel<>();
        coursesList = new JList<>(coursesModel);
        coursesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        // Lista de cursos
        JScrollPane scrollPane = new JScrollPane(coursesList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Cursos"));
        
        // Botones
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Agregar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Eliminar");
        JButton closeButton = new JButton("Cerrar");
        
        addButton.setBackground(new Color(40, 167, 69));
        addButton.setForeground(Color.WHITE);
        editButton.setBackground(new Color(255, 193, 7));
        editButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(108, 117, 125));
        closeButton.setForeground(Color.WHITE);
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);
        
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Event listeners
        closeButton.addActionListener(e -> dispose());
        
        addButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Nombre del curso:");
            if (name != null && !name.trim().isEmpty()) {
                String description = JOptionPane.showInputDialog(this, "Descripción del curso:");
                cursosService.agregarCurso(name.trim(), description != null ? description.trim() : "");
                loadCourses();
            }
        });
        
        deleteButton.addActionListener(e -> {
            Curso selected = coursesList.getSelectedValue();
            if (selected != null) {
                int option = JOptionPane.showConfirmDialog(this, 
                    "¿Eliminar el curso '" + selected.getNombre() + "'?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    cursosService.eliminarCurso(selected);
                    loadCourses();
                }
            }
        });
    }
    
    private void loadCourses() {
        // Carga los cursos desde el servicio
        coursesModel.clear();
        List<Curso> cursos = cursosService.getListaDeCursos();
        for (Curso c : cursos) {
            coursesModel.addElement(c);
        }
    }
}     
