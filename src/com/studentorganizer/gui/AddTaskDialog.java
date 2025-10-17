package com.studentorganizer.gui;

import com.studentorganizer.model.*;
import com.studentorganizer.service.TareaService;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddTaskDialog extends JDialog {
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JTextField dateField;
    private JComboBox<Prioridad> priorityCombo;
    private JComboBox<Curso> courseCombo;
    private boolean taskCreated = false;
    private Estudiante estudiante;
    
    public AddTaskDialog(Frame parent, Estudiante estudiante) {
        super(parent, "Nueva Tarea", true);
        this.estudiante = estudiante;
        
        initializeComponents();
        setupUI();
        setupEventListeners();
    }
    
    private void initializeComponents() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        nameField = new JTextField(20);
        descriptionArea = new JTextArea(5, 20);
        dateField = new JTextField(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
        priorityCombo = new JComboBox<>(Prioridad.values());
        
        // Crear algunos cursos por defecto si no existen
        List<Curso> cursos = new ArrayList<>();
        cursos.add(new Curso("Matemáticas", "Curso de matemáticas"));
        cursos.add(new Curso("Programación", "Curso de programación"));
        cursos.add(new Curso("Historia", "Curso de historia"));
        cursos.add(new Curso("Ciencias", "Curso de ciencias"));
        
        courseCombo = new JComboBox<>(cursos.toArray(new Curso[0]));
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(nameField, gbc);
        
        // Descripción
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(new JScrollPane(descriptionArea), gbc);
        
        // Fecha
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Fecha (yyyy-mm-dd):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(dateField, gbc);
        
        // Prioridad
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Prioridad:"), gbc);
        gbc.gridx = 1;
        formPanel.add(priorityCombo, gbc);
        
        // Curso
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1;
        formPanel.add(courseCombo, gbc);
        
        // Botones
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");
        
        saveButton.setBackground(new Color(40, 167, 69));
        saveButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(108, 117, 125));
        cancelButton.setForeground(Color.WHITE);
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Event listeners
        saveButton.addActionListener(e -> saveTask());
        cancelButton.addActionListener(e -> dispose());
    }
    
    private void setupEventListeners() {
        // Validation listeners podrían ir aquí
    }
    
    private void saveTask() {
        try {
            String nombre = nameField.getText().trim();
            String descripcion = descriptionArea.getText().trim();
            LocalDate fecha = LocalDate.parse(dateField.getText());
            Prioridad prioridad = (Prioridad) priorityCombo.getSelectedItem();
            Curso curso = (Curso) courseCombo.getSelectedItem();
            
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es requerido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            TareaService tareaService = new TareaService(estudiante);
            tareaService.crearTarea(nombre, descripcion, fecha, prioridad, curso);
            
            taskCreated = true;
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear la tarea: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isTaskCreated() {
        return taskCreated;
    }
}
