package algeo01_23002.cli.menus.submenus;

import algeo01_23002.types.Matrix;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.*;

public class MatrixOperationsMenu {


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("MATRIX OPERATIONS MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Matrix Multiplication");
            System.out.println("2.  Matrix Addition");
            System.out.println("3.  Matrix Subtraction");
            System.out.println("4.  Back to main menu");
            System.out.print(ARROW +"  Select an option (1-4): " + RESET);

            int choice = getChoice(1, 4);
            switch(choice) {
                case 1 -> matrixMultiplicationDriver();
                case 2 -> matrixAdditionDriver();
                case 3 -> matrixSubtractionDriver();
                case 4 -> { isRunning = false; }
            }
        }
    }

    private static void matrixAdditionDriver(){
        System.out.print("\n" + ARROW + "  Enter number of rows: ");
        int rows = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols: ");
        int cols = getChoice(1, 100);

        Matrix matrix_a = new Matrix(rows, cols);
        Matrix matrix_b = new Matrix(rows, cols);

        System.out.println("\n" + ARROW + " Fill Element for FIRST Matrix");
        inputMatrixChoiceDriver(matrix_a);

        System.out.println("\n" + ARROW + " Fill Element for SECOND Matrix");
        inputMatrixChoiceDriver(matrix_b);

        System.out.print("\nCalculating...");
        Matrix result = matrix_a.add(matrix_b);

        System.out.println(YELLOW + "\nResult: " + RESET);
        printMatrixWithBorder(result);

        // Save Data
        int choice;
        System.out.println();
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no)");
        choice = getChoice(0,1);
        if(choice == 1){
            saveMatrixResultToFile(result);
        }

        System.out.println();
    }

    private static void matrixSubtractionDriver(){
        System.out.print("\n" + ARROW + "  Enter number of rows: ");
        int rows = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols: ");
        int cols = getChoice(1, 100);

        Matrix matrix_a = new Matrix(rows, cols);
        Matrix matrix_b = new Matrix(rows, cols);

        System.out.println("\n" + ARROW + " Fill Element for FIRST Matrix");
        inputMatrixChoiceDriver(matrix_a);

        System.out.println("\n" + ARROW + " Fill Element for SECOND Matrix");
        inputMatrixChoiceDriver(matrix_b);

        System.out.print("\nCalculating...");
        Matrix result = matrix_a.subtract(matrix_b);

        System.out.println(YELLOW + "\nResult: " + RESET);
        printMatrixWithBorder(result);

        // Save Data
        int choice;
        System.out.println();
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no)");
        choice = getChoice(0,1);
        if(choice == 1){
            saveMatrixResultToFile(result);
        }
        System.out.println();
    }

    private static void matrixMultiplicationDriver(){
        int rows_a, cols_a, rows_b, cols_b;
        System.out.print("\n" + ARROW + "  Enter number of rows of the first matrix: ");
        rows_a = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols of the first matrix or rows of the second matrix: ");
        cols_a = getChoice(1, 100);
        rows_b = cols_a;
        System.out.print("\n" + ARROW + "  Enter number of cols of the second matrix: ");
        cols_b = getChoice(1, 100);

        Matrix matrix_a = new Matrix(rows_a, cols_a);
        Matrix matrix_b = new Matrix(rows_b, cols_b);

        System.out.println("\n" + ARROW + " Fill Element for FIRST Matrix");
        inputMatrixChoiceDriver(matrix_a);

        System.out.println("\n" + ARROW + " Fill Element for SECOND Matrix");
        inputMatrixChoiceDriver(matrix_b);

        System.out.print("\nCalculating...");
        Matrix result = matrix_a.multiplyByMatrix(matrix_b);

        System.out.println(YELLOW + "\nResult: " + RESET);
        printMatrixWithBorder(result);

        // Save Data
        int choice;
        System.out.println();
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no)");
        choice = getChoice(0,1);
        if(choice == 1){
            saveMatrixResultToFile(result);
        }

        System.out.println();
    }

}
