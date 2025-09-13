package com.studentorganizer.gui;

import javax.swing.*;
import java.awt.*;

public class PomodoroSettingsDialog extends JDialog {
    private JSpinner workTimeSpinner;
    private JSpinner shortBreakSpinner;
    private JSpinner longBreakSpinner;
    private boolean settingsChanged = false;
    
    public PomodoroSettingsDialog(Frame parent, int workTime, int shortBreak, int longBreak) {
        super(parent, "Configuración Pomodoro", true);
        
        initializeComponents(workTime, shortBreak, longBreak);
        setupUI();
        setupEventListeners();
    }
    
    private void initializeComponents(int workTime, int shortBreak, int longBreak) {
        setSize(400, 250);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        workTimeSpinner = new JSpinner(new SpinnerNumberModel(workTime, 1, 60, 1));
        shortBreakSpinner = new JSpinner(new SpinnerNumberModel(shortBreak, 1, 30, 1));
        longBreakSpinner = new JSpinner(new SpinnerNumberModel(longBreak, 1, 60, 1));
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Tiempo de trabajo
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Tiempo de trabajo (min):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(workTimeSpinner, gbc);
        
        // Descanso corto
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Descanso corto (min):"), gbc);
        gbc.gridx = 1;
        formPanel.add(shortBreakSpinner, gbc);
        
        // Descanso largo
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Descanso largo (min):"), gbc);
        gbc.gridx = 1;
        formPanel.add(longBreakSpinner, gbc);
        
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
        
        saveButton.addActionListener(e -> {
            settingsChanged = true;
            dispose();
        });
        
        cancelButton.addActionListener(e -> dispose());
    }
    
    private void setupEventListeners() {
        // Listeners adicionales si es necesario
    }
    
    public boolean isSettingsChanged() {
        return settingsChanged;
    }
    
    public int getWorkTime() {
        return (Integer) workTimeSpinner.getValue();
    }
    
    public int getShortBreak() {
        return (Integer) shortBreakSpinner.getValue();
    }
    
    public int getLongBreak() {
        return (Integer) longBreakSpinner.getValue();
    }
}