package com.studentorganizer.gui;

import com.studentorganizer.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PomodoroPanel extends JPanel {
    private Timer timer;
    private int timeRemaining; // en segundos
    private boolean isRunning;
    private boolean isBreak;
    
    private JLabel timerLabel;
    private JLabel statusLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private JButton settingsButton;
    
    // Configuración por defecto (en minutos)
    private int workTime = 25;
    private int shortBreak = 5;
    private int longBreak = 15;
    private int sessionCount = 0;
    
    public PomodoroPanel() {
        initializeComponents();
        setupUI();
        setupEventListeners();
        resetTimer();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Timer Pomodoro"));
        setBackground(Color.WHITE);
        
        timerLabel = new JLabel("25:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 48));
        timerLabel.setForeground(new Color(220, 53, 69));
        
        statusLabel = new JLabel("Listo para estudiar", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(108, 117, 125));
        
        startButton = new JButton("▶️ Iniciar");
        pauseButton = new JButton("⏸️ Pausar");
        resetButton = new JButton("🔄 Reiniciar");
        settingsButton = new JButton("⚙️ Configurar");
        
        startButton.setBackground(new Color(40, 167, 69));
        startButton.setForeground(Color.WHITE);
        pauseButton.setBackground(new Color(255, 193, 7));
        pauseButton.setForeground(Color.WHITE);
        resetButton.setBackground(new Color(108, 117, 125));
        resetButton.setForeground(Color.WHITE);
        settingsButton.setBackground(new Color(102, 126, 234));
        settingsButton.setForeground(Color.WHITE);
        
        startButton.setFocusPainted(false);
        pauseButton.setFocusPainted(false);
        resetButton.setFocusPainted(false);
        settingsButton.setFocusPainted(false);
        
        pauseButton.setEnabled(false);
        
        timer = new Timer(1000, e -> updateTimer());
    }
    
    private void setupUI() {
        JPanel timerPanel = new JPanel(new BorderLayout());
        timerPanel.setOpaque(false);
        timerPanel.add(timerLabel, BorderLayout.CENTER);
        timerPanel.add(statusLabel, BorderLayout.SOUTH);
        
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(settingsButton);
        
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        
        JLabel sessionsLabel = new JLabel("Sesiones completadas: 0");
        sessionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsPanel.add(sessionsLabel);
        
        add(timerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(statsPanel, BorderLayout.NORTH);
    }
    
    private void setupEventListeners() {
        startButton.addActionListener(e -> startTimer());
        pauseButton.addActionListener(e -> pauseTimer());
        resetButton.addActionListener(e -> resetTimer());
        settingsButton.addActionListener(e -> openSettings());
    }
    
    private void startTimer() {
        isRunning = true;
        timer.start();
        
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        
        statusLabel.setText(isBreak ? "¡Tiempo de descanso!" : "¡Concentrado estudiando!");
        statusLabel.setForeground(isBreak ? new Color(40, 167, 69) : new Color(220, 53, 69));
    }
    
    private void pauseTimer() {
        isRunning = false;
        timer.stop();
        
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        
        statusLabel.setText("Pausado");
        statusLabel.setForeground(new Color(255, 193, 7));
    }
    
    private void resetTimer() {
        isRunning = false;
        timer.stop();
        
        timeRemaining = workTime * 60;
        isBreak = false;
        
        updateTimerDisplay();
        
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        
        statusLabel.setText("Listo para estudiar");
        statusLabel.setForeground(new Color(108, 117, 125));
    }
    
    private void updateTimer() {
        timeRemaining--;
        updateTimerDisplay();
        
        if (timeRemaining <= 0) {
            timer.stop();
            playNotification();
            
            if (!isBreak) {
                sessionCount++;
                // Después del trabajo, iniciar descanso
                if (sessionCount % 4 == 0) {
                    timeRemaining = longBreak * 60;
                } else {
                    timeRemaining = shortBreak * 60;
                }
                isBreak = true;
                statusLabel.setText("¡Sesión completada! Tiempo de descanso");
            } else {
  
                timeRemaining = workTime * 60;
                isBreak = false;
                statusLabel.setText("¡Descanso terminado! Vamos a trabajar");
            }
            
            updateTimerDisplay();
            
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            isRunning = false;
            
            JOptionPane.showMessageDialog(this, 
                isBreak ? "¡Tiempo de trabajar!" : "¡Tiempo de descansar!",
                "Pomodoro", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateTimerDisplay() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        timerLabel.setForeground(isBreak ? new Color(40, 167, 69) : new Color(220, 53, 69));
    }
    
    private void playNotification() {
        Toolkit.getDefaultToolkit().beep();
    }
    
    private void openSettings() {
        PomodoroSettingsDialog dialog = new PomodoroSettingsDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this), 
            workTime, shortBreak, longBreak);
        dialog.setVisible(true);
        
        if (dialog.isSettingsChanged()) {
            workTime = dialog.getWorkTime();
            shortBreak = dialog.getShortBreak();
            longBreak = dialog.getLongBreak();
            resetTimer();
        }
    }
}