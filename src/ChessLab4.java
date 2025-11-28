import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// NOTE: You must have your figures package (Figure, Rook, Knight, etc.),
// exceptions package, and Chessboard class accessible for this code to compile.
import figures.*;
import figures.Bishop;
import exceptions.*;

/**
 * The {@code ChessLabGUI} class provides a Graphical User Interface (GUI)
 * for testing chess figure movement validation from the original ChessLab4.
 * It uses Swing components and a 8x8 grid to simulate a chessboard.
 */
public class ChessLab4 {

    // --- ChessLab4 Enums & Utility Classes (Copied) ---

    /** Enum representing the two chess piece colors */
    public enum Color {WHITE, BLACK}

    /** Enum representing available chess figure types */
    public enum FigureName {PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING}

    private static final int BOARD_SIZE = 8;
    private static final String[] COLUMNS = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private static final String[] ROWS = {"1", "2", "3", "4", "5", "6", "7", "8"};

    // GUI Components
    private JFrame frame;
    private JPanel boardContainerPanel;
    private JPanel[][] boardCells = new JPanel[BOARD_SIZE][BOARD_SIZE];
    private JTextArea outputArea;
    private JComboBox<FigureName> figureSelector;
    private JComboBox<Color> colorSelector;
    private JComboBox<String> colSelector, rowSelector, targetColSelector, targetRowSelector;

    // Backend State
    private Chessboard board = new Chessboard(); // Assumes Chessboard class exists
    private Figure currentFigure = null;
    private boolean bishopTested = false;

    // --- Main Entry Point ---

    public static void main(String[] args) {
        // Executes runnable on AWT dispatch thread
        // Note: Changed `new ChessLab4().createAndShowGUI()` since the class name is ChessLab4
        SwingUtilities.invokeLater(() -> new ChessLab4().createAndShowGUI());
    }

    // --- GUI Setup Methods ---

    private void createAndShowGUI() {
        frame = new JFrame("Chess Figure Movement Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        // 1. Create the Chessboard Panel
        boardContainerPanel = createChessboardPanel();
        frame.add(boardContainerPanel, BorderLayout.CENTER);

        // 2. Create the Control Panel (South)
        JPanel controlPanel = createControlPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        // 3. Create the Output/Log Panel (East)
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Movement Log"));
        frame.add(scrollPane, BorderLayout.EAST);

        // Finalize and display
        frame.setVisible(true);
        log("Welcome to the Chess Figure Tester GUI!");
    }

    private JPanel createChessboardPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        
        // Add coordinates labels to the grid layout
        // The labels will be added to the border of the board
        
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                // Chess rows are 1-8 (bottom to top), but array rows are 0-7 (top to bottom)
                // Need to invert the row index for color logic: (BOARD_SIZE - 1 - row)
                boolean isWhite = ((BOARD_SIZE - 1 - row) + col) % 2 == 0;
                
                JPanel cell = new JPanel(new GridBagLayout()); // Use GridBagLayout for centering
                
                // FIX: Use java.awt.Color for GUI background
                cell.setBackground(isWhite ? new java.awt.Color(240, 217, 181) : new java.awt.Color(181, 136, 99)); // Standard chess colors
                
                // FIX: Use java.awt.Color for border
                cell.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
                
                boardCells[row][col] = cell; // Save reference
                boardPanel.add(cell); 
            }
        }
        
        return boardPanel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Setup Dropdowns for Figure Creation
        figureSelector = new JComboBox<>(FigureName.values());
        colorSelector = new JComboBox<>(Color.values());
        colSelector = new JComboBox<>(COLUMNS);
        rowSelector = new JComboBox<>(ROWS);

        // Setup Dropdowns for Target Coordinates
        targetColSelector = new JComboBox<>(COLUMNS);
        targetRowSelector = new JComboBox<>(ROWS);

        // Setup Buttons
        JButton createButton = new JButton("Create Figure");
        JButton testMoveButton = new JButton("Test Move");
        JButton clearBoardButton = new JButton("Clear Board");

        // Add components to panel
        panel.add(new JLabel("Figure:"));
        panel.add(figureSelector);
        panel.add(new JLabel("Color:"));
        panel.add(colorSelector);
        panel.add(new JLabel("Start Col:"));
        panel.add(colSelector);
        panel.add(new JLabel("Start Row:"));
        panel.add(rowSelector);
        panel.add(createButton);
        
        panel.add(new JSeparator(SwingConstants.VERTICAL));

        panel.add(new JLabel("Target Col:"));
        panel.add(targetColSelector);
        panel.add(new JLabel("Target Row:"));
        panel.add(targetRowSelector);
        panel.add(testMoveButton);

        panel.add(clearBoardButton);
        
        // Add Listeners
        createButton.addActionListener(e -> createFigure());
        testMoveButton.addActionListener(e -> testFigureMove());
        clearBoardButton.addActionListener(e -> clearBoard());

        return panel;
    }

    // --- GUI Logic & Event Handlers ---

    private void log(String message) {
        outputArea.append(message + "\n");
        // Auto-scroll to the bottom
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    private void clearBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                boardCells[row][col].removeAll();
                boardCells[row][col].revalidate();
                boardCells[row][col].repaint();
            }
        }
        currentFigure = null;
        bishopTested = false;
        log("\n--- Board Cleared. Ready for new figure. ---");
    }

    private void placeFigureIcon(char colChar, int rowInt, FigureName type, Color color) {
        // Convert chess coordinates (A1, H8) to array indices (row, col)
        int col = colChar - 'a'; // 'a' -> 0, 'h' -> 7
        int row = BOARD_SIZE - rowInt; // 1 -> 7, 8 -> 0

        // Basic validation check
        if (col < 0 || col >= BOARD_SIZE || row < 0 || row >= BOARD_SIZE) {
            log("Error: Invalid coordinates for placement.");
            return;
        }

        // Clear the cell before placing
        boardCells[row][col].removeAll();

        // 🖼️ Create a simple text label for the piece
        String pieceText = (color == Color.WHITE ? "W" : "B") + type.name().charAt(0);
        JLabel pieceLabel = new JLabel(pieceText);
        
        // FIX: Use java.awt.Font for Font
        pieceLabel.setFont(new java.awt.Font("Serif", java.awt.Font.BOLD, 48));
        
        // FIX: Use java.awt.Color for foreground colors
        pieceLabel.setForeground(color == Color.WHITE ? java.awt.Color.WHITE : java.awt.Color.BLACK);

        // A more advanced version would load actual chess piece images.
        // For example: placeImage(boardCells, row, col, "images/" + type.name().toLowerCase() + "_" + color.name().toLowerCase() + ".png");

        boardCells[row][col].add(pieceLabel);
        boardCells[row][col].revalidate();
        boardCells[row][col].repaint();
    }
    
    // NOTE: This method is a simplified version of your lab's logic, 
    // tailored to the GUI's single-figure-at-a-time testing flow.
    private void createFigure() {
        // Check if a figure is already on the board
        if (currentFigure != null) {
            JOptionPane.showMessageDialog(frame, "Only one figure can be tested at a time. Click 'Clear Board' first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        FigureName figureType = (FigureName) figureSelector.getSelectedItem();
        Color figureColor = (Color) colorSelector.getSelectedItem();
        char figureColumn = ((String) colSelector.getSelectedItem()).toLowerCase().charAt(0);
        int figureRow = Integer.parseInt((String) rowSelector.getSelectedItem());

        // Bishop is treated specially in the original lab, we follow that here.
        if (figureType == FigureName.BISHOP) {
            JOptionPane.showMessageDialog(frame, "The Bishop must be tested separately after a main figure. Try testing a different figure first.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            // Validate coordinate using the existing utility class
            board.verifyCoordinate(figureColumn, figureRow);

            // Create figure based on type
            switch (figureType) {
                case ROOK:
                    currentFigure = new Rook(figureColor.toString(), figureColumn, figureRow);
                    break;
                case KNIGHT:
                    currentFigure = new Knight(figureColor.toString(), figureColumn, figureRow);
                    break;
                case QUEEN:
                    currentFigure = new Queen(figureColor.toString(), figureColumn, figureRow);
                    break;
                case PAWN:
                    currentFigure = new Pawn(figureColor.toString(), figureColumn, figureRow);
                    break;
                case KING:
                    currentFigure = new King(figureColor.toString(), figureColumn, figureRow);
                    break;
                default:
                    return;
            }

            // Place the figure icon on the board and log
            placeFigureIcon(figureColumn, figureRow, figureType, figureColor);
            log("Created Figure: " + currentFigure.toString());
            log("Ready to test movement...");

        } catch (InvalidCoordinateException e) {
            JOptionPane.showMessageDialog(frame, "Invalid Coordinate: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            log("Error: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error creating figure: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            log("Error creating figure: " + e.getMessage());
        }
    }

    private void testFigureMove() {
        char targetColumn = ((String) targetColSelector.getSelectedItem()).toLowerCase().charAt(0);
        int targetRow = Integer.parseInt((String) targetRowSelector.getSelectedItem());
        
        try {
            // 1. Validate target coordinate
            board.verifyCoordinate(targetColumn, targetRow);
            
            // 2. Test the selected primary figure
            if (currentFigure != null) {
                boolean valid = currentFigure.moveTo(targetColumn, targetRow);
                String result = currentFigure.toString() + " **" + (valid ? "CAN" : "CANNOT") + "** move to " + targetColumn + targetRow;
                log(result);
            } else {
                log("No main figure has been created yet.");
            }
            
            // 3. Test Bishop separately, if a figure has been tested and bishop wasn't.
            if (currentFigure != null && !bishopTested) {
                // A simplified way to test the Bishop for the GUI version.
                // It creates a WHITE Bishop at C1 (a common starting position)
                // and tests its movement against the same target.
                Bishop bishop = new Bishop(Color.WHITE.toString(), 'c', 1);
                boolean validBishopMove = bishop.moveToBishop(targetColumn, targetRow);
                String result = "Bishop (W C1) **" + (validBishopMove ? "CAN" : "CANNOT") + "** move to " + targetColumn + targetRow;
                log("\n--- Testing Bishop Separately ---");
                log(result);
                bishopTested = true; // Mark as tested for this move cycle
            }
            
        } catch (InvalidCoordinateException e) {
            JOptionPane.showMessageDialog(frame, "Invalid Target Coordinate: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            log("Error: " + e.getMessage());
        } catch (InvalidMoveException e) {
            String result = currentFigure.getClass().getSimpleName() + " **CANNOT** move: " + e.getMessage();
            log(result);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "An unexpected error occurred during move test: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            log("General Error: " + e.getMessage());
        }
    }
}
