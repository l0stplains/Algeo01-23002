package algeo01_23002.cli.menus.submenus;

import algeo01_23002.mathmodels.Regression;
import algeo01_23002.types.LinearSystemSolution;
import algeo01_23002.types.Matrix;
import algeo01_23002.types.ParametricSolution;
import algeo01_23002.types.UniqueSolution;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.*;
import static algeo01_23002.cli.Utilities.printMatrixWithBorder;

public class RegressionMenu {


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("REGRESSION MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Multiple Linear Regression");
            System.out.println("2.  Multiple Quadratic Regression");
            System.out.println("3.  Back to main menu");
            System.out.print(ARROW +"  Select an option (1-3): " + RESET);

            int choice = getChoice(1, 3);
            switch(choice) {
                case 1 -> multipleLinearRegressionDriver();
                case 2 -> multipleQuadraticRegressionDriver();
                case 3 -> { isRunning = false; }
            }
        }
    }

    private static void multipleLinearRegressionDriver() {
        System.out.print("\n" + ARROW + "  Enter number of rows: ");
        int rows = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols: ");
        int cols = getChoice(1, 100);

        Matrix matrix = new Matrix(rows, cols);

        System.out.println("\n" + ARROW + "  Enter each element of the matrix: ");
        matrix = inputMatrixDriver(matrix);

        System.out.print("\nFitting the data...");
        LinearSystemSolution result = Regression.multipleLinearRegression(matrix);

        if(result instanceof UniqueSolution) {
            Matrix solution = ((UniqueSolution) result).getSolution();
            System.out.println(YELLOW + "\nResult: " + RESET);
            printMatrixWithBorder(solution);
        } else if(result instanceof ParametricSolution) {
            System.out.println(YELLOW + "\nParametric solution found " + RESET);
            System.out.println(result);
        }
        else {
            System.out.println(YELLOW + "\nRegression can't be performed " + RESET);
        }

        System.out.println();
    }

    private static void multipleQuadraticRegressionDriver() {
        System.out.print("\n" + ARROW + "  Enter number of rows: ");
        int rows = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols: ");
        int cols = getChoice(1, 100);

        Matrix matrix = new Matrix(rows, cols);

        System.out.println("\n" + ARROW + "  Enter each element of the matrix: ");
        matrix = inputMatrixDriver(matrix);

        System.out.print("\nFitting the data...");
        LinearSystemSolution result = Regression.multipleQuadraticRegression(matrix);

        if(result instanceof UniqueSolution) {
            Matrix solution = ((UniqueSolution) result).getSolution();
            System.out.println(YELLOW + "\nResult: " + RESET);
            printMatrixWithBorder(solution);
        } else if(result instanceof ParametricSolution) {
            System.out.println(YELLOW + "\nParametric solution found " + RESET);
            System.out.println(result);
        }
        else {
            System.out.println(YELLOW + "\nRegression can't be performed " + RESET);
        }

        System.out.println();
    }
}
