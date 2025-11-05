package com.studentorganizer.gui;

// Asegúrate de tener todas estas importaciones
import com.studentorganizer.model.*; // Si usas PomodoroSettingsDialog desde aquí
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
        timerLabel.setForeground(new Color(220, 53, 69)); // Color para "trabajo"
        
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
        
        // El Timer se dispara cada 1000ms (1 segundo)
        timer = new Timer(1000, e -> updateTimer());
    }
    
    private void setupUI() {
        JPanel timerPanel = new JPanel(new BorderLayout());
        timerPanel.setOpaque(false);
        timerPanel.add(timerLabel, BorderLayout.CENTER);
        timerPanel.add(statusLabel, BorderLayout.SOUTH);
        
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setOpaque(false); // Hacer el fondo del panel de botones transparente
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(settingsButton);
        
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        statsPanel.setOpaque(false); // Hacer el fondo del panel de stats transparente
        
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
    
    /**
     * Este es el método "combinado" con la lógica automática 25/5.
     */
    private void updateTimer() {
        timeRemaining--;
        updateTimerDisplay();
        
        if (timeRemaining <= 0) {
            timer.stop(); // Detiene el timer actual
            playNotification(); // Suena la notificación

            if (!isBreak) { 
                // --- SE ACABÓ EL TIEMPO DE TRABAJO ---
                sessionCount++;
                
                // Revisa si es un descanso largo o corto
                if (sessionCount % 4 == 0) {
                    timeRemaining = longBreak * 60;
                    JOptionPane.showMessageDialog(this, 
                        "¡Sesión de trabajo terminada! Comienza un descanso largo (" + longBreak + " min).", 
                        "Pomodoro", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    timeRemaining = shortBreak * 60;
                    JOptionPane.showMessageDialog(this, 
                        "¡Sesión de trabajo terminada! Comienza un descanso corto (" + shortBreak + " min).", 
                        "Pomodoro", JOptionPane.INFORMATION_MESSAGE);
                }
                
                isBreak = true; // Marca que ahora estamos en un descanso
                updateTimerDisplay(); // Actualiza el número en el reloj
                startTimer(); // ¡Inicia el timer de descanso automáticamente!

            } else { 
                // --- SE ACABÓ EL TIEMPO DE DESCANSO ---
                timeRemaining = workTime * 60; // Resetea al tiempo de trabajo
                isBreak = false; // Marca que volvemos al trabajo
                
                JOptionPane.showMessageDialog(this, 
                    "¡Descanso terminado! Listo para la siguiente sesión de trabajo.", 
                    "Pomodoro", JOptionPane.INFORMATION_MESSAGE);
                
                updateTimerDisplay(); // Actualiza el número en el reloj
                
                // Resetea los botones para que el usuario inicie la próxima sesión manualmente
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);
                isRunning = false;
            }
        }
    }
    
    private void updateTimerDisplay() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        // Cambia el color del reloj si es descanso o trabajo
        timerLabel.setForeground(isBreak ? new Color(40, 167, 69) : new Color(220, 53, 69));
    }
    
    private void playNotification() {
        // Genera un sonido simple de "beep"
        Toolkit.getDefaultToolkit().beep();
    }
    
    private void openSettings() {
        // Asume que tienes una clase PomodoroSettingsDialog en el mismo paquete
        PomodoroSettingsDialog dialog = new PomodoroSettingsDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this), 
            workTime, shortBreak, longBreak);
        dialog.setVisible(true);
        
        if (dialog.isSettingsChanged()) {
            workTime = dialog.getWorkTime();
            shortBreak = dialog.getShortBreak();
            longBreak = dialog.getLongBreak();
            resetTimer(); // Aplica la nueva configuración reseteando el timer
        }
    }
}