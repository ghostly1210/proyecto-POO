package com.studentorganizer.gui;

import com.studentorganizer.model.Estudiante;
import com.studentorganizer.service.EstudianteService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField nombreField;
    private JTextField carreraField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton switchModeButton;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private EstudianteService estudianteService;
    
    public LoginFrame() {
        estudianteService = new EstudianteService();
        initializeComponents();
        setupUI();
        setupEventListeners();
    }
    
    private void initializeComponents() {
        setTitle("StudyOrganizer - Organiza tu vida académica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
    }
    
    private void setupUI() {
        
        JPanel loginPanel = createLoginPanel();
        
        JPanel registerPanel = createRegisterPanel();
        
        cardPanel.add(loginPanel, "LOGIN");
        cardPanel.add(registerPanel, "REGISTER");
        
        add(cardPanel);
    }
    
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setBackground(new Color(102, 126, 234));
        
  
        JLabel titleLabel = new JLabel("Iniciar Sesión");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
     
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        
      
        loginButton = new JButton("Iniciar Sesión");
        loginButton.setBackground(new Color(6, 214, 160));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        
        switchModeButton = new JButton("¿No tienes cuenta? Regístrate");
        switchModeButton.setContentAreaFilled(false);
        switchModeButton.setBorderPainted(false);
        switchModeButton.setForeground(Color.WHITE);
        
  
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(createLabeledField("Email:", emailField));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(createLabeledField("Contraseña:", passwordField));
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(switchModeButton);
        
        return panel;
    }
    
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panel.setBackground(new Color(118, 75, 162));
        
 
        JLabel titleLabel = new JLabel("Registro");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        nombreField = new JTextField(20);
        carreraField = new JTextField(20);
        JTextField emailRegField = new JTextField(20);
        JPasswordField passwordRegField = new JPasswordField(20);
       
        registerButton = new JButton("Registrarse");
        registerButton.setBackground(new Color(6, 214, 160));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        
        JButton backButton = new JButton("¿Ya tienes cuenta? Inicia sesión");
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setForeground(Color.WHITE);
        
        registerButton.addActionListener(e -> {
            String nombre = nombreField.getText().trim();
            String carrera = carreraField.getText().trim();
            String email = emailRegField.getText().trim();
            String password = new String(passwordRegField.getPassword());
            
            if (nombre.isEmpty() || carrera.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (estudianteService.registrarEstudiante(email, password, nombre, carrera)) {
                JOptionPane.showMessageDialog(this, "Registro exitoso! Ahora puedes iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(cardPanel, "LOGIN");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "El email ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "LOGIN"));
        
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createLabeledField("Nombre:", nombreField));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createLabeledField("Carrera:", carreraField));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createLabeledField("Email:", emailRegField));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createLabeledField("Contraseña:", passwordRegField));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(registerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(backButton);
        
        return panel;
    }
    
    private JPanel createLabeledField(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        
        field.setPreferredSize(new Dimension(200, 30));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void setupEventListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());
                
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this, 
                        "Por favor completa todos los campos", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Estudiante estudiante = estudianteService.autenticarEstudiante(email, password);
                if (estudiante != null) {
                    new MainFrame(estudiante).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, 
                        "Credenciales incorrectas", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        switchModeButton.addActionListener(e -> cardLayout.show(cardPanel, "REGISTER"));
    }
    
    private void clearFields() {
        emailField.setText("");
        passwordField.setText("");
        if (nombreField != null) nombreField.setText("");
        if (carreraField != null) carreraField.setText("");
    }
}