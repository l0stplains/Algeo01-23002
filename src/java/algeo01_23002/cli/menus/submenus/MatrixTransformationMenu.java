package algeo01_23002.cli.menus.submenus;

import algeo01_23002.types.Matrix;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.*;

public class MatrixTransformationMenu {


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("MATRIX TRANSFORMATION MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Matrix Row Echelon Form");
            System.out.println("2.  Matrix Reduced Row Echelon Form");
            System.out.println("3.  Matrix Adjoint");
            System.out.println("4.  Matrix Inverse with Adjoint");
            System.out.println("5.  Matrix Inverse with Row Reduction");
            System.out.println("6.  Back to main menu");
            System.out.print(ARROW + "  Select an option (1-6): " + RESET);

            int choice = getChoice(1, 6);
            switch (choice){
                case 1 -> matrixRowEchelonFormDriver();
                case 2 -> matrixReducedRowEchelonFormDriver();
                case 3 -> matrixAdjointDriver();
                case 4 -> matrixInverseWithAdjointDriver();
                case 5 -> matrixInverseWithRowReductionDriver();
                case 6 -> { isRunning = false; }
            }
        }
    }

    private static void matrixRowEchelonFormDriver() {
        System.out.print("\n" + ARROW + "  Enter number of rows: ");
        int rows = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols: ");
        int cols = getChoice(1, 100);

        int choice;
        Matrix matrix = new Matrix(rows, cols);

        // Input Matrix from File or From CLI
        inputMatrixChoiceDriver(matrix);

        System.out.print("\nTransforming...");
        Matrix result = matrix.getRowEchelonForm();

        System.out.println(YELLOW + "\nResult: " + RESET);
        printMatrixWithBorder(result);

        // Save Data
        System.out.println();
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no)");
        choice = getChoice(0,1);
        if(choice == 1){
            saveMatrixResultToFile(result);
        }

        System.out.println();
    }

    private static void matrixReducedRowEchelonFormDriver() {
        System.out.print("\n" + ARROW + "  Enter number of rows: ");
        int rows = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols: ");
        int cols = getChoice(1, 100);

        int choice;
        Matrix matrix = new Matrix(rows, cols);

        // Input Matrix from File or From CLI
        inputMatrixChoiceDriver(matrix);

        System.out.print("\nTransforming...");
        Matrix result = matrix.getReducedRowEchelonForm();

        System.out.println(YELLOW + "\nResult: " + RESET);
        printMatrixWithBorder(result);

        // Save Data
        System.out.println();
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no)");
        choice = getChoice(0,1);
        if(choice == 1){
            saveMatrixResultToFile(result);
        }

        System.out.println();
    }

    private static void matrixAdjointDriver() {
        System.out.print("\n" + ARROW + "  Enter number the length of the matrix (square): ");
        int rows = getChoice(1, 12);
        int cols = rows;

        int choice;
        Matrix matrix = new Matrix(rows, cols);

        // Input Matrix from File or From CLI
        inputMatrixChoiceDriver(matrix);

        System.out.print("\nTransforming...");
        Matrix result = matrix.getAdjoint();

        System.out.println(YELLOW + "\nResult: " + RESET);
        printMatrixWithBorder(result);

        // Save Data
        System.out.println();
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no)");
        choice = getChoice(0,1);
        if(choice == 1){
            saveMatrixResultToFile(result);
        }

        System.out.println();
    }

    private static void matrixInverseWithAdjointDriver() {
        System.out.print("\n" + ARROW + "  Enter number the length of the matrix (square): ");
        int rows = getChoice(1, 12);
        int cols = rows;

        int choice;
        Matrix matrix = new Matrix(rows, cols);

        // Input Matrix from File or From CLI
        inputMatrixChoiceDriver(matrix);

        System.out.print("\nTransforming...");
        Matrix result;
        try {
            result = matrix.getInverseWithAdjoint();
        } catch (ArithmeticException e) {
            System.out.println(YELLOW + "\n!!!  Matrix is singular (determinant zero)" + RESET);
            System.out.println();
            return;
        }

        System.out.println(YELLOW + "\nResult: " + RESET);
        printMatrixWithBorder(result);

        // Save Data
        System.out.println();
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no)");
        choice = getChoice(0,1);
        if(choice == 1){
            saveMatrixResultToFile(result);
        }

        System.out.println();
    }

    private static void matrixInverseWithRowReductionDriver() {
        System.out.print("\n" + ARROW + "  Enter number the length of the matrix (square): ");
        int rows = getChoice(1, 100);
        int cols = rows;

        int choice;
        Matrix matrix = new Matrix(rows, cols);

        // Input Matrix from File or From CLI
        inputMatrixChoiceDriver(matrix);

        System.out.print("\nTransforming...");
        Matrix result;
        try {
            result = matrix.getInverseWithRowReduction();
        } catch (ArithmeticException e) {
            System.out.println(YELLOW + "\n!!!  Matrix is not invertible" + RESET);
            System.out.println();
            return;
        }

        System.out.println(YELLOW + "\nResult: " + RESET);
        printMatrixWithBorder(result);

        // Save Data
        System.out.println();
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no)");
        choice = getChoice(0, 1);
        if(choice == 1){
            saveMatrixResultToFile(result);
        }

        System.out.println();
    }
}
