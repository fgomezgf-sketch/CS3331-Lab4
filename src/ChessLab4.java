import java.util.Scanner;
import figures.*;
import figures.Bishop;
import exceptions.*;

//Created By Evan Horn, Sebastian Luevano, Fernando Gomez
//
// CHANGELOG
// * Refactored Lab 3 architecture using interfaces and abstract classes
// * Implemented IntFigure, IntBishop, IntChessBoard
// * Added exception handling for invalid input and invalid moves
// * Created packages for organization
// * Updated to handle new Figure hierarchy
// * fixed a few errors with error handling and file names.
// * moved the files into a java project for easy testing
// * finished the bishop moving in the main method


public class ChessLab4 {

    // enums for parsing the users input
    public enum Color {WHITE, BLACK}
    public enum FigureName {PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING}

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        Chessboard board = new Chessboard();

        // the array of chess figures that have been created
        Figure[] figures = new Figure[6];
        int figuresCreated = 0;

        // loop for creating each of the chess figures
        while (figuresCreated < 5) {
            System.out.println("Enter one of the following figures (ROOK, KNIGHT, QUEEN, PAWN, KING, BISHOP):");

            String createdFiguresString;
            if (figuresCreated == 0) {
                createdFiguresString = "No Figures have been created yet";
            } else {
                createdFiguresString = "These Figures have already been created: ";
                for (Figure f : figures) {
                    if (f != null) {
                        createdFiguresString += f.getClass().getSimpleName() + " ";
                    }
                }
            }
            System.out.println(createdFiguresString);

            FigureName figureType;
            try {
                figureType = FigureName.valueOf(scnr.nextLine().toUpperCase());
            } catch (Exception e) {
                System.out.println("Invalid name. Must be either ROOK, KNIGHT, QUEEN, PAWN, KING, or BISHOP. Try again.");
                continue;
            }

            // check if Bishop is already handled separately
            if (figureType == FigureName.BISHOP) {
                System.out.println("Bishop cannot be added to the array. It will be tested separately later.");
                continue;
            }

            // check if figure already created
            if (figures[figureType.ordinal()] != null) {
                System.out.println("Figure: " + figureType + " has already been created.");
                continue;
            }

            // Prompt user for figure color
            System.out.println("Enter figure color (WHITE, BLACK):");
            Color figureColor;
            try {
                figureColor = Color.valueOf(scnr.nextLine().toUpperCase());
            } catch (Exception e) {
                System.out.println("Invalid color. Must be WHITE or BLACK. Try again.");
                continue;
            }

            // Prompt user for column
            System.out.println("Enter figure column (a-h):");
            char figureColumn = scnr.nextLine().toLowerCase().charAt(0);

            System.out.println("Enter figure row (1-8):");
            int figureRow;
            try {
                figureRow = Integer.parseInt(scnr.nextLine());
                board.verifyCoordinate(figureColumn, figureRow);
            } catch (InvalidCoordinateException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (NumberFormatException e) {
                System.out.println("Invalid row input. Try again.");
                continue;
            }

            // create figure based on type
            Figure figure = null;
            try {
                switch (figureType) {
                    case ROOK:
                        figure = new Rook(figureColor.toString(), figureColumn, figureRow);
                        break;
                    case KNIGHT:
                        figure = new Knight(figureColor.toString(), figureColumn, figureRow);
                        break;
                    case QUEEN:
                        figure = new Queen(figureColor.toString(), figureColumn, figureRow);
                        break;
                    case PAWN:
                        figure = new Pawn(figureColor.toString(), figureColumn, figureRow);
                        break;
                    case KING:
                        figure = new King(figureColor.toString(), figureColumn, figureRow);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error creating figure: " + e.getMessage());
                continue;
            }

            // add to array
            figures[figureType.ordinal()] = figure;
            figuresCreated++;
        }

        // Target selection
        char targetColumn = 'a';
        int targetRow = 1;
        boolean selectingTarget = true;

        while (selectingTarget) {
            System.out.println("Enter target column (a-h):");
            targetColumn = scnr.nextLine().toLowerCase().charAt(0);

            System.out.println("Enter target row (1-8):");
            try {
                targetRow = Integer.parseInt(scnr.nextLine());
                board.verifyCoordinate(targetColumn, targetRow);
            } catch (InvalidCoordinateException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (NumberFormatException e) {
                System.out.println("Invalid row input. Try again.");
                continue;
            }

            selectingTarget = false;
        }

        // Check each figure's movement
        for (Figure f : figures) {
            if (f == null) continue;

            try {
                boolean valid = f.moveTo(targetColumn, targetRow);
                if (valid) {
                    System.out.println(f.toString() + " CAN move to " + targetColumn + "," + targetRow);
                } else {
                    System.out.println(f.toString() + " CANNOT move to " + targetColumn + "," + targetRow);
                }
            } catch (InvalidMoveException e) {
                System.out.println("Invalid move for " + f.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }

        // Test Bishop separately
        System.out.println("\nTesting Bishop separately:");
        Bishop bishop = null;
        while (bishop == null) {
            // Prompt user for figure color
            System.out.println("Enter figure color (WHITE, BLACK):");
            Color figureColor;
            try {
                figureColor = Color.valueOf(scnr.nextLine().toUpperCase());
            } catch (Exception e) {
                System.out.println("Invalid color. Must be WHITE or BLACK. Try again.");
                continue;
            }

            // Prompt user for column
            System.out.println("Enter figure column (a-h):");
            char figureColumn = scnr.nextLine().toLowerCase().charAt(0);

            System.out.println("Enter figure row (1-8):");
            int figureRow;
            try {
                figureRow = Integer.parseInt(scnr.nextLine());
                board.verifyCoordinate(figureColumn, figureRow);
            } catch (InvalidCoordinateException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (NumberFormatException e) {
                System.out.println("Invalid row input. Try again.");
                continue;
            }
            bishop = new Bishop(figureColor.toString(), figureColumn, figureRow);
        }

        try {
            boolean validBishopMove = bishop.moveToBishop(targetColumn, targetRow);
            System.out.println("Bishop " + (validBishopMove ? "CAN" : "CANNOT") + "move from c1 to "+ targetColumn + targetRow);
        } catch (Exception e) {
            System.out.println("Error testing Bishop: " + e.getMessage());
        }

        scnr.close();
    }
}

