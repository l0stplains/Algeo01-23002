package algeo01_23002.cli.menus.submenus;

import algeo01_23002.types.Matrix;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.*;

public class MatrixPropertiesMenu {


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("MATRIX PROPERTIES MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Matrix Determinant with Cofactor");
            System.out.println("2.  Matrix Determinant with Row Reduction");
            System.out.println("3.  Back to main menu");
            System.out.print(ARROW +"  Select an option (1-3): " + RESET);

            int choice = getChoice(1, 3);
            switch (choice){
                case 1 -> matrixDeterminantWithCofactorDriver();
                case 2 -> matrixDeterminantWithRowReductionDriver();
                case 3 -> { isRunning = false; }
            }
        }
    }

    private static void matrixDeterminantWithCofactorDriver() {
        System.out.print("\n" + ARROW + "  Enter number the length of the matrix (square): ");
        int rows = getChoice(1, 12);
        int cols = rows;

        Matrix matrix = new Matrix(rows, cols);

        System.out.println("\n" + ARROW + "  Enter each element of the matrix: ");
        matrix = inputMatrixDriver(matrix);

        System.out.print("\nCalculating...");
        double result = matrix.getDeterminantWithCofactor();

        System.out.println(YELLOW + "\nResult: " + RESET);
        System.out.println("  " + result);
        System.out.println();
    }

    private static void matrixDeterminantWithRowReductionDriver() {
        System.out.print("\n" + ARROW + "  Enter number the length of the matrix (square): ");
        int rows = getChoice(1, 12);
        int cols = rows;

        Matrix matrix = new Matrix(rows, cols);

        System.out.println("\n" + ARROW + "  Enter each element of the matrix: ");
        matrix = inputMatrixDriver(matrix);

        System.out.print("\nCalculating...");
        double result = matrix.getDeterminantWithRowReduction();

        System.out.println(YELLOW + "\nResult: " + RESET);
        System.out.println("  " + result);
        System.out.println();
    }
}
