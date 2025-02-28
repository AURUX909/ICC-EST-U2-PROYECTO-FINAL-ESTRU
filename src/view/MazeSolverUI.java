package view;

import controllers.interfaces.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import models.*;

public class MazeSolverUI extends JFrame {
    private JPanel mazePanel;
    private JTextField startRowField, startColField, endRowField, endColField;
    private JCheckBox delayCheckBox;
    private boolean[][] grid;
    private Cell startCell = null;
    private Cell endCell = null;
    private Color ORANGE_BRIGHT = new Color(255, 165, 0);

    public MazeSolverUI() {
        initializeMaze();
        buildUI();
    }

    private void initializeMaze() {
        JPanel sizePanel = new JPanel(new GridLayout(2, 2, 5, 5));
        sizePanel.setBackground(new Color(31, 41, 55));
        JLabel rowsLabel = new JLabel("Filas:");
        rowsLabel.setForeground(Color.WHITE);
        JTextField rowsField = new JTextField("5");
        styleTextField(rowsField);
        JLabel colsLabel = new JLabel("Columnas:");
        colsLabel.setForeground(Color.WHITE);
        JTextField colsField = new JTextField("5");
        styleTextField(colsField);
        sizePanel.add(rowsLabel);
        sizePanel.add(rowsField);
        sizePanel.add(colsLabel);
        sizePanel.add(colsField);

        int result = JOptionPane.showConfirmDialog(null, sizePanel, "Configurar Laberinto",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int rows = Integer.parseInt(rowsField.getText());
            int cols = Integer.parseInt(colsField.getText());
            grid = new boolean[rows][cols];
            for (int i = 0; i < rows; i++) {
                Arrays.fill(grid[i], true);
            }
        } else {
            grid = new boolean[5][5]; // Tamaño por defecto
        }
    }

    private void buildUI() {
        setTitle("Maze Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(31, 41, 55));
    
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(31, 41, 55));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        createMazeGrid();
    
        JPanel controlPanel = createControlPanel();
    
        // Color armonizado con la interfaz
        Color buttonColor = new Color(31, 41, 55); 
    
        JButton updateButton = new JButton("Actualizar Coordenadas");
        updateButton.setOpaque(true);
        updateButton.setContentAreaFilled(true);
        updateButton.setBorderPainted(true);
        updateButton.setFocusPainted(false);
        
        updateButton.setBackground(buttonColor);
        updateButton.setForeground(Color.WHITE);
        updateButton.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
        updateButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Aplicamos el mismo tratamiento que a los botones de algoritmos
        updateButton.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Forzar el color de fondo
                g2d.setColor(c.getBackground());
                g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
                
                // Forzar el texto con contraste adecuado
                g2d.setColor(c.getForeground());
                FontMetrics fm = g2d.getFontMetrics();
                String text = ((JButton)c).getText();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();
                g2d.drawString(text, (c.getWidth() - textWidth) / 2, 
                              (c.getHeight() + textHeight / 2) / 2);
                
                // Dibujar el borde
                g2d.setColor(new Color(87, 76, 222));
                g2d.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
                
                g2d.dispose();
            }
        });
        
        // Agregar efecto hover
        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Color hoverColor = new Color(
                    Math.min(buttonColor.getRed() + 20, 255),
                    Math.min(buttonColor.getGreen() + 20, 255),
                    Math.min(buttonColor.getBlue() + 20, 255)
                );
                updateButton.setBackground(hoverColor);
                updateButton.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                updateButton.setBackground(buttonColor);
                updateButton.repaint();
            }
        });
        
        updateButton.addActionListener(_ -> resetMaze());
    
        JPanel algorithmPanel = createAlgorithmPanel();
    
        mainPanel.add(mazePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(controlPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(updateButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(algorithmPanel);
    
        add(new JScrollPane(mainPanel));
        setSize(800, 900);
        setLocationRelativeTo(null);
    }

    private void createMazeGrid() {
        mazePanel = new JPanel(new GridLayout(grid.length, grid[0].length, 2, 2));
        mazePanel.setBorder(BorderFactory.createLineBorder(new Color(75, 85, 99), 1));
        mazePanel.setBackground(new Color(31, 41, 55));

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(60, 60));
                cell.setBackground(grid[i][j] ? Color.WHITE : Color.BLACK);
                JLabel label = new JLabel((i + 1) + "," + (j + 1), SwingConstants.CENTER);
                label.setForeground(Color.BLACK);
                cell.add(label);

                final int row = i;
                final int col = j;

                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) { // Click derecho
                            if (startCell == null) {
                                startCell = new Cell(row, col);
                                cell.setBackground(ORANGE_BRIGHT);
                                startRowField.setText(String.valueOf(row + 1));
                                startColField.setText(String.valueOf(col + 1));
                            } else if (endCell == null && !startCell.equals(new Cell(row, col))) {
                                endCell = new Cell(row, col);
                                cell.setBackground(ORANGE_BRIGHT);
                                endRowField.setText(String.valueOf(row + 1));
                                endColField.setText(String.valueOf(col + 1));
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON1) { // Click izquierdo
                            if (grid[row][col]) { // Solo permitir bloquear celdas transitables
                                grid[row][col] = false;
                                cell.setBackground(Color.BLACK);
                            }
                        }
                    }
                });

                mazePanel.add(cell);
            }
        }
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(31, 41, 55));

        startRowField = createTextField("1");
        startColField = createTextField("1");
        endRowField = createTextField("5");
        endColField = createTextField("5");

        delayCheckBox = new JCheckBox("Mostrar Tiempo");
        delayCheckBox.setBackground(new Color(55, 65, 81));
        delayCheckBox.setForeground(Color.WHITE);

        controlPanel.add(createLabeledField("Fila de inicio:", startRowField));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(createLabeledField("Columna de inicio:", startColField));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(createLabeledField("Fila de fin:", endRowField));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(createLabeledField("Columna de fin:", endColField));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(createLabeledField("Mostrar Tiempo:", delayCheckBox));

        return controlPanel;
    }

    private JPanel createAlgorithmPanel() {
    JPanel panel = new JPanel(new GridLayout(1, 6, 5, 0));
    panel.setBackground(new Color(31, 41, 55));
    String[] algorithms = {"BFS", "DFS", "Recursivo", "Cache", "Extra", "Reset"};

    Map<String, Runnable> actions = Map.of(
            "BFS", () -> solveMaze(new MazeSolverBFS()),
            "DFS", () -> solveMaze(new MazeSolverDFS()),
            "Recursivo", () -> solveMaze(new MazeSolverRecursivo()),
            "Cache", () -> solveMaze(new MazeSolverCache()),
            "Extra", this::solveAllPaths,
            "Reset", this::resetMaze
    );
    
    //Colores de los botones
    Map<String, Color> buttonColors = Map.of(
            "BFS", new Color(31, 41, 55),        
            "DFS", new Color(31, 41, 55),        
            "Recursivo", new Color(31, 41, 55),  
            "Cache", new Color(31, 41, 55),      
            "Extra", new Color(31, 41, 55),      
            "Reset", new Color(31, 41, 55)        
    );

    for (String algorithm : algorithms) {
        JButton button = new JButton(algorithm);
        
        Color normalColor = buttonColors.get(algorithm);
        
        // Configuración crítica para asegurar el renderizado correcto
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        
        button.setBackground(normalColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Personalización del UI delegate para forzar la apariencia
        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Forzar el color de fondo
                g2d.setColor(c.getBackground());
                g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
                
                // Forzar el texto con contraste adecuado
                g2d.setColor(c.getForeground());
                FontMetrics fm = g2d.getFontMetrics();
                String text = ((JButton)c).getText();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();
                g2d.drawString(text, (c.getWidth() - textWidth) / 2, 
                              (c.getHeight() + textHeight / 2) / 2);
                
                // Dibujar el borde
                g2d.setColor(Color.WHITE);
                g2d.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
                
                g2d.dispose();
            }
        });
        
        // Agregar efecto hover con adaptación completa
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Color hoverColor = new Color(
                    Math.min(normalColor.getRed() + 30, 255),
                    Math.min(normalColor.getGreen() + 30, 255),
                    Math.min(normalColor.getBlue() + 30, 255)
                );
                button.setBackground(hoverColor);
                button.repaint(); // Forzar repintado inmediato
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
                button.repaint(); // Forzar repintado inmediato
            }
        });
        
        button.addActionListener(_ -> actions.get(algorithm).run());
        panel.add(button);
    }

    return panel;
    }

    private JTextField createTextField(String defaultValue) {
        JTextField field = new JTextField(defaultValue);
        field.setBackground(new Color(55, 65, 81));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        return field;
    }

    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(31, 41, 55));
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(209, 213, 219));
        label.setPreferredSize(new Dimension(120, 25));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(field);
        return panel;
    }

    private boolean isValidCell(Cell cell) {
        return cell.row >= 0 && cell.row < grid.length &&
               cell.col >= 0 && cell.col < grid[0].length &&
               grid[cell.row][cell.col];
    }

    private void solveMaze(MazeSolver solver) {
        try {
            Cell[] cells = getCellsFromFields();
            Cell start = cells[0];
            Cell end = cells[1];

            if (!validateCells(start, end)) {
                JOptionPane.showMessageDialog(this, "Celdas inválidas.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            long startTime = System.nanoTime();
            List<Cell> path = solver.getPath(grid, start, end);
            long duration = (System.nanoTime() - startTime) / 1_000_000;

            if (path.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontró un camino.", "Resultado", JOptionPane.WARNING_MESSAGE);
            } else {
                displayPath(path, solver.getClass().getSimpleName(), duration, path.size());
                JOptionPane.showMessageDialog(this, "Camino encontrado con éxito.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Error en la configuración: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayPath(List<Cell> path, String algorithm, long time, int steps) {
        resetCellColors();
        Color pathColor = getPathColor(algorithm);

        if (delayCheckBox.isSelected()) {
            new SwingWorker<Void, Cell>() {
                @Override
                protected Void doInBackground() {
                    for (Cell cell : path) {
                        publish(cell);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    return null;
                }

                @Override
                protected void process(List<Cell> chunks) {
                    for (Cell cell : chunks) {
                        getCellPanel(cell.row, cell.col).setBackground(pathColor);
                        mazePanel.repaint();
                    }
                }
            }.execute();
        } else {
            for (Cell cell : path) {
                getCellPanel(cell.row, cell.col).setBackground(pathColor);
            }
        }

        highlightStartEndCells();
        showMetrics(algorithm, time, steps);
    }

    private void solveAllPaths() {
        try {
            Cell[] cells = getCellsFromFields();
            Cell start = cells[0];
            Cell end = cells[1];
    
            if (!validateCells(start, end)) {
                JOptionPane.showMessageDialog(this, "Celdas inválidas.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            MazeSolverExtra solver = new MazeSolverExtra();
            List<List<Cell>> allPaths = solver.getAllPaths(grid, start, end);
    
            if (allPaths.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontró ningún camino.", "Resultado", JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            resetCellColors();
    
            if (delayCheckBox.isSelected()) {
                // Animación con retraso
                new SwingWorker<Void, Cell>() {
                    @Override
                    protected Void doInBackground() {
                        for (List<Cell> path : allPaths) {
                            for (Cell cell : path) {
                                publish(cell);
                                try {
                                    Thread.sleep(200); // Retraso para animación
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        }
                        return null;
                    }
    
                    @Override
                    protected void process(List<Cell> chunks) {
                        for (Cell cell : chunks) {
                            JPanel cellPanel = getCellPanel(cell.row, cell.col);
    
                            // Determinar el color basado en si es el camino óptimo o no
                            boolean isOptimalPath = false;
                            for (List<Cell> path : allPaths) {
                                if (path == allPaths.get(0) && path.contains(cell)) {
                                    isOptimalPath = true;
                                    break;
                                }
                            }
    
                            cellPanel.setBackground(isOptimalPath ? Color.YELLOW : Color.GREEN);
                            mazePanel.repaint();
                        }
                    }
                }.execute();
            } else {
                // Sin animación
                for (List<Cell> path : allPaths) {
                    for (Cell cell : path) {
                        JPanel cellPanel = getCellPanel(cell.row, cell.col);
                        cellPanel.setBackground(path == allPaths.get(0) ? Color.YELLOW : Color.GREEN);
                    }
                }
            }
    
            highlightStartEndCells();
            JOptionPane.showMessageDialog(this, "Todos los caminos mostrados.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Error en la configuración: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetCellColors() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                JPanel cell = getCellPanel(i, j);
                cell.setBackground(grid[i][j] ? Color.WHITE : Color.BLACK);
            }
        }
    }

    private void highlightStartEndCells() {
        if (startCell != null) {
            getCellPanel(startCell.row, startCell.col).setBackground(ORANGE_BRIGHT);
        }
        if (endCell != null) {
            getCellPanel(endCell.row, endCell.col).setBackground(ORANGE_BRIGHT);
        }
    }

    private JPanel getCellPanel(int row, int col) {
        return (JPanel) mazePanel.getComponent(row * grid[0].length + col);
    }

    private void showMetrics(String algorithm, long time, int steps) {
        JOptionPane.showMessageDialog(this,
                "Algoritmo: " + algorithm + "\n" +
                        "Tiempo total: " + time + " ms\n" +
                        "Pasos en el camino: " + steps,
                "Métricas de Rendimiento",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetMaze() {
        startCell = null;
        endCell = null;
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], true);
        }
        resetCellColors();
        startRowField.setText("1");
        startColField.setText("1");
        endRowField.setText(String.valueOf(grid.length));
        endColField.setText(String.valueOf(grid[0].length));
    }

    private Color getPathColor(String algorithm) {
        return switch (algorithm) {
            case "MazeSolverBFS" -> Color.GREEN;
            case "MazeSolverDFS" -> Color.CYAN;
            case "MazeSolverRecursivo" -> Color.BLUE;
            case "MazeSolverCache" -> Color.MAGENTA;
            default -> Color.GRAY;
        };
    }

    private void styleTextField(JTextField field) {
        field.setBackground(new Color(55, 65, 81));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(75, 85, 99)));
    }

    private Cell[] getCellsFromFields() throws NumberFormatException {
        int startRow = Integer.parseInt(startRowField.getText()) - 1;
        int startCol = Integer.parseInt(startColField.getText()) - 1;
        int endRow = Integer.parseInt(endRowField.getText()) - 1;
        int endCol = Integer.parseInt(endColField.getText()) - 1;
        return new Cell[]{new Cell(startRow, startCol), new Cell(endRow, endCol)};
    }

    private boolean validateCells(Cell start, Cell end) {
        return isValidCell(start) && isValidCell(end);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MazeSolverUI().setVisible(true);
        });
    }
}