package com.studentorganizer.gui;

import com.studentorganizer.model.*;
import com.studentorganizer.service.EstudianteService;
import com.studentorganizer.service.TareaService;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainFrame extends JFrame {
	private Estudiante estudiante;
    private TareaService tareaService;
    private EstudianteService estudianteService;
    
    // Componentes principales
    private JPanel leftPanel, centerPanel, rightPanel;
    private JList<Tarea> tareasList;
    private DefaultListModel<Tarea> tareasModel;
    private JLabel welcomeLabel, pendingCountLabel, completedCountLabel;
    
    // Timer Pomodoro
    private PomodoroPanel pomodoroPanel;
    
    public MainFrame(Estudiante estudiante, EstudianteService estudianteService) {
        this.estudiante = estudiante;
        this.tareaService = new TareaService(estudiante);
        this.estudianteService = estudianteService;
        
        initializeComponents();
        setupUI();
        setupEventListeners();
        updateTaskCounts();
        loadTasks();
    }
    
    private void initializeComponents() {
        setTitle("StudyOrganizer - Dashboard de " + estudiante.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        tareasModel = new DefaultListModel<>();
        tareasList = new JList<>(tareasModel);
        tareasList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tareasList.setCellRenderer(new TareaListCellRenderer());
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        // Panel superior
        add(createTopPanel(), BorderLayout.NORTH);
        
        // Panel principal con tres secciones
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Sidebar izquierdo
        leftPanel = createLeftPanel();
        mainPanel.add(leftPanel, BorderLayout.WEST);
        
        // Panel central
        centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Panel derecho (Timer Pomodoro)
        rightPanel = createRightPanel();
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel inferior
        add(createBottomPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(102, 126, 234));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        welcomeLabel = new JLabel("¡Hola, " + estudiante.getNombre() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        
        JLabel dateLabel = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")));
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(new Color(102, 126, 234));
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> logout());
        
        JPanel leftInfo = new JPanel(new BorderLayout());
        leftInfo.setOpaque(false);
        leftInfo.add(welcomeLabel, BorderLayout.NORTH);
        leftInfo.add(dateLabel, BorderLayout.SOUTH);
        
        panel.add(leftInfo, BorderLayout.WEST);
        panel.add(logoutButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de información del usuario
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBorder(BorderFactory.createTitledBorder("Mi Perfil"));
        userInfoPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel("Nombre: " + estudiante.getNombre());
        JLabel careerLabel = new JLabel("Carrera: " + estudiante.getCarrera());
        JLabel emailLabel = new JLabel("Email: " + estudiante.getEmail());
        
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        userInfoPanel.add(careerLabel);
        userInfoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        userInfoPanel.add(emailLabel);
        
        // Panel de estadísticas
        JPanel statsPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        
        pendingCountLabel = new JLabel("Tareas Pendientes: 0");
        completedCountLabel = new JLabel("Tareas Completadas: 0");
        
        pendingCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        completedCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pendingCountLabel.setForeground(new Color(255, 193, 7));
        completedCountLabel.setForeground(new Color(40, 167, 69));
        
        statsPanel.add(pendingCountLabel);
        statsPanel.add(completedCountLabel);
        
        // Botones de acción
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Acciones"));
        
        JButton addTaskButton = new JButton("➕ Nueva Tarea");
        JButton viewCalendarButton = new JButton("📅 Ver Calendario");
        JButton manageCoursesButton = new JButton("📚 Gestionar Cursos");
        
        addTaskButton.setBackground(new Color(40, 167, 69));
        addTaskButton.setForeground(Color.WHITE);
        addTaskButton.setFocusPainted(false);
        
        viewCalendarButton.setBackground(new Color(23, 162, 184));
        viewCalendarButton.setForeground(Color.WHITE);
        viewCalendarButton.setFocusPainted(false);
        
        manageCoursesButton.setBackground(new Color(102, 126, 234));
        manageCoursesButton.setForeground(Color.WHITE);
        manageCoursesButton.setFocusPainted(false);
        
        actionsPanel.add(addTaskButton);
        actionsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionsPanel.add(viewCalendarButton);
        actionsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionsPanel.add(manageCoursesButton);
        
        // Event listeners para botones
        addTaskButton.addActionListener(e -> openAddTaskDialog());
        viewCalendarButton.addActionListener(e -> openCalendarView());
        manageCoursesButton.addActionListener(e -> openCoursesDialog());
        
        panel.add(userInfoPanel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        panel.add(actionsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de filtros
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtros"));
        
        JComboBox<String> statusFilter = new JComboBox<>(new String[]{"Todas", "Pendientes", "Completadas"});
        JComboBox<String> priorityFilter = new JComboBox<>(new String[]{"Todas", "Alta", "Media", "Baja"});
        JButton refreshButton = new JButton("🔄 Actualizar");
        
        filterPanel.add(new JLabel("Estado:"));
        filterPanel.add(statusFilter);
        filterPanel.add(new JLabel("Prioridad:"));
        filterPanel.add(priorityFilter);
        filterPanel.add(refreshButton);
        
        // Lista de tareas
        JScrollPane scrollPane = new JScrollPane(tareasList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Mis Tareas"));
        
        // Panel de acciones para tareas
        JPanel taskActionsPanel = new JPanel(new FlowLayout());
        
        JButton editButton = new JButton("✏️ Editar");
        JButton deleteButton = new JButton("🗑️ Eliminar");
        JButton completeButton = new JButton("✅ Completar");
        
        editButton.setBackground(new Color(255, 193, 7));
        editButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        completeButton.setBackground(new Color(40, 167, 69));
        completeButton.setForeground(Color.WHITE);
        
        editButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);
        completeButton.setFocusPainted(false);
        
        taskActionsPanel.add(editButton);
        taskActionsPanel.add(deleteButton);
        taskActionsPanel.add(completeButton);
        
        // Event listeners
        statusFilter.addActionListener(e -> filterTasks(statusFilter, priorityFilter));
        priorityFilter.addActionListener(e -> filterTasks(statusFilter, priorityFilter));
        refreshButton.addActionListener(e -> loadTasks());
        
        editButton.addActionListener(e -> editSelectedTask());
        deleteButton.addActionListener(e -> deleteSelectedTask());
        completeButton.addActionListener(e -> completeSelectedTask());
        
        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(taskActionsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(350, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Timer Pomodoro
        pomodoroPanel = new PomodoroPanel();
        panel.add(pomodoroPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel statusLabel = new JLabel("Listo para organizar tu día de estudio");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(108, 117, 125));
        
        panel.add(statusLabel, BorderLayout.WEST);
        
        return panel;
    }
    
    private void setupEventListeners() {
        // Double-click en lista de tareas para editar
        tareasList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    editSelectedTask();
                }
            }
        });
    }
    
    // Métodos de acción
    private void openAddTaskDialog() {
        AddTaskDialog dialog = new AddTaskDialog(this, estudiante);
        dialog.setVisible(true);
        if (dialog.isTaskCreated()) {
            loadTasks();
            updateTaskCounts();
            estudianteService.saveAllData();
        }
    }
    
    private void openCalendarView() {
        CalendarFrame calendar = new CalendarFrame(estudiante);
        calendar.setVisible(true);
    }
    
    private void openCoursesDialog() {
        CoursesDialog dialog = new CoursesDialog(this);
        dialog.setVisible(true);
    }
    
    private void editSelectedTask() {
        Tarea selectedTask = tareasList.getSelectedValue();
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona una tarea para editar.", 
                "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        EditTaskDialog dialog = new EditTaskDialog(this, selectedTask);
        dialog.setVisible(true);
        if (dialog.isTaskEdited()) {
            loadTasks();
            updateTaskCounts();
            estudianteService.saveAllData();
        }
    }
    
    private void deleteSelectedTask() {
        Tarea selectedTask = tareasList.getSelectedValue();
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona una tarea para eliminar.", 
                "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int option = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de que quieres eliminar la tarea '" + selectedTask.getNombre() + "'?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            estudiante.eliminarTarea(selectedTask);
            loadTasks();
            updateTaskCounts();
            estudianteService.saveAllData();
        }
    }
    
    private void completeSelectedTask() {
        Tarea selectedTask = tareasList.getSelectedValue();
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona una tarea para completar.", 
                "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (selectedTask.getEstado() == Estado.COMPLETADA) {
            JOptionPane.showMessageDialog(this, "Esta tarea ya está completada.", 
                "Tarea completada", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        selectedTask.marcarComoCompletada();
        loadTasks();
        updateTaskCounts();
        estudianteService.saveAllData();
        
        JOptionPane.showMessageDialog(this, "¡Tarea completada! ¡Bien hecho!", 
            "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void filterTasks(JComboBox<String> statusFilter, JComboBox<String> priorityFilter) {
        String status = (String) statusFilter.getSelectedItem();
        String priority = (String) priorityFilter.getSelectedItem();
        
        tareasModel.clear();
        
        List<Tarea> allTasks = estudiante.getListaDeTareas();
        for (Tarea tarea : allTasks) {
            boolean statusMatch = status.equals("Todas") || 
                (status.equals("Pendientes") && tarea.getEstado() == Estado.PENDIENTE) ||
                (status.equals("Completadas") && tarea.getEstado() == Estado.COMPLETADA);
                
            boolean priorityMatch = priority.equals("Todas") || 
                tarea.getPrioridad().toString().equals(priority);
            
            if (statusMatch && priorityMatch) {
                tareasModel.addElement(tarea);
            }
        }
    }
    
    private void loadTasks() {
        tareasModel.clear();
        List<Tarea> tasks = estudiante.getListaDeTareas();
        for (Tarea tarea : tasks) {
            tareasModel.addElement(tarea);
        }
    }
    
    private void updateTaskCounts() {
        int pendingCount = estudiante.obtenerTareasPendientes().size();
        int completedCount = estudiante.obtenerTareasCompletadas().size();
        
        pendingCountLabel.setText("Tareas Pendientes: " + pendingCount);
        completedCountLabel.setText("Tareas Completadas: " + completedCount);
    }
    
    private void logout() {
        int option = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de que quieres cerrar sesión?",
            "Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
        	estudianteService.saveAllData();
            dispose();
            new LoginFrame().setVisible(true);
        }
    }
}
