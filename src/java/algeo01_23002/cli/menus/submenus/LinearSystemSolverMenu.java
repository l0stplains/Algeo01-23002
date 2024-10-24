package algeo01_23002.cli.menus.submenus;

import algeo01_23002.solvers.LinearSystemSolver;
import algeo01_23002.types.LinearSystemSolution;
import algeo01_23002.types.Matrix;
import algeo01_23002.types.ParametricSolution;
import algeo01_23002.types.UniqueSolution;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.*;
import static algeo01_23002.cli.Utilities.printMatrixWithBorder;

public class LinearSystemSolverMenu {


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("LINEAR SYSTEM SOLVER MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Gaussian Elimination Method");
            System.out.println("2.  Gauss-Jordan Elimination Method");
            System.out.println("3.  Cramer's Rule Method");
            System.out.println("4.  Inverse Method");
            System.out.println("5.  Back to main menu");
            System.out.print(ARROW +"  Select an option (1-5): " + RESET);

            int choice = getChoice(1, 5);
            switch (choice){
                case 1 -> gaussianEliminationMethodDriver();
                case 2 -> gaussJordanEliminationMethodDriver();
                case 3 -> cramersRuleMethodDriver();
                case 4 -> inverseMethodDriver();
                case 5 -> { isRunning = false; }
            }
        }
    }

    private static void gaussianEliminationMethodDriver() {
        System.out.print("\n" + ARROW + "  Enter number of rows: ");
        int rows = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols: ");
        int cols = getChoice(1, 100);

        int choice;
        Matrix matrix = new Matrix(rows, cols);

        // Input Matrix from File or From CLI
        inputMatrixChoiceDriver(matrix);

        System.out.print("\nFitting the data...");

        LinearSystemSolver solver = new LinearSystemSolver();
        LinearSystemSolution result = solver.gaussianElimination(matrix);

        //Print File
        if(result instanceof UniqueSolution) {
            System.out.println(YELLOW + "\nResult: " + RESET);
            System.out.println(YELLOW + result + RESET);

        } else if(result instanceof ParametricSolution) {
            System.out.println(YELLOW + "\nParametric solution found " + RESET);
            System.out.println(result);
        }
        else {
            System.out.println(YELLOW + "\nGaussian Elimination can't be performed " + RESET);
            System.out.println(YELLOW + result + RESET);
        }

        // Save File
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no))");
        choice = getChoice(0,1);
        if(choice == 1 && result instanceof UniqueSolution) {
            Matrix solution = ((UniqueSolution) result).getSolution();
            saveUniqueResultToFile(solution);
        }
        else if(choice == 1 && result instanceof ParametricSolution) {
            saveParametricResultToFile((ParametricSolution) result);
        }

        System.out.println();
    }

    private static void gaussJordanEliminationMethodDriver() {
        System.out.print("\n" + ARROW + "  Enter number of rows: ");
        int rows = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols: ");
        int cols = getChoice(1, 100);

        int choice;
        Matrix matrix = new Matrix(rows, cols);

        // Input Matrix from File or From CLI
        inputMatrixChoiceDriver(matrix);

        System.out.print("\nFitting the data...");

        LinearSystemSolver solver = new LinearSystemSolver();
        LinearSystemSolution result = solver.gaussJordanElimination(matrix);

        //Print Data
        if(result instanceof UniqueSolution) {
            System.out.println(YELLOW + "\nResult: " + RESET);
            System.out.println(YELLOW + result + RESET);

        } else if(result instanceof ParametricSolution) {
            System.out.println(YELLOW + "\nParametric solution found " + RESET);
            System.out.println(result);
        }
        else {
            System.out.println(YELLOW + "\nGauss-Jordan Elimination can't be performed " + RESET);
        }

        // Save File
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no))");
        choice = getChoice(0,1);
        if(choice == 1 && result instanceof UniqueSolution) {
            Matrix solution = ((UniqueSolution) result).getSolution();
            saveUniqueResultToFile(solution);
        }
        else if(choice == 1 && result instanceof ParametricSolution) {
            saveParametricResultToFile((ParametricSolution) result);
        }

        System.out.println();
    }

    private static void cramersRuleMethodDriver() {
        System.out.print("\n" + ARROW + "  Enter number of rows: ");
        int rows = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols: ");
        int cols = getChoice(1, 100);

        int choice;
        Matrix matrix = new Matrix(rows, cols);

        // Input Matrix from File or From CLI
        inputMatrixChoiceDriver(matrix);

        System.out.print("\nFitting the data...");

        LinearSystemSolver solver = new LinearSystemSolver();
        LinearSystemSolution result;
        try {
            result = solver.cramersRule(matrix);
        } catch (IllegalArgumentException e) {
            System.out.println(YELLOW + "\nCramer's Rule can't be performed " + RESET);
            System.out.println();
            return;
        }

        //Print Data
        if(result instanceof UniqueSolution) {
            System.out.println(YELLOW + "\nResult: " + RESET);
            System.out.println(YELLOW + result + RESET);

        } else if(result instanceof ParametricSolution) {
            System.out.println(YELLOW + "\nParametric solution found " + RESET);
            System.out.println(result);
        }
        else {
            System.out.println(YELLOW + "\nCramer's Rule Method can't be performed " + RESET);
        }

        // Save Data
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no))");
        choice = getChoice(0,1);
        if(choice == 1 && result instanceof UniqueSolution) {
            Matrix solution = ((UniqueSolution) result).getSolution();
            saveUniqueResultToFile(solution);
        }
        else if(choice == 1 && result instanceof ParametricSolution) {
            saveParametricResultToFile((ParametricSolution) result);
        }

        System.out.println();
    }

    private static void inverseMethodDriver() {
        System.out.print("\n" + ARROW + "  Enter number the length of the matrix (n x n+1): ");
        int rows = getChoice(1, 12);
        int cols = rows+1;

        int choice;
        Matrix matrix = new Matrix(rows, cols);

        // Input Matrix from File or From CLI
        inputMatrixChoiceDriver(matrix);

        System.out.print("\nFitting the data...");

        LinearSystemSolver solver = new LinearSystemSolver();
        LinearSystemSolution result;

        //Print Data;
        try {
            result = solver.inverseMethod(matrix);
        } catch (IllegalArgumentException e) {
            System.out.println(YELLOW + "\nInverse Method can't be performed " + RESET);
            System.out.println();
            return;
        }
        if(result instanceof UniqueSolution) {
            System.out.println(YELLOW + "\nResult: " + RESET);
            System.out.println(YELLOW + result + RESET);

        } else if(result instanceof ParametricSolution) {
            System.out.println(YELLOW + "\nParametric solution found " + RESET);
            System.out.println(result);
        }
        else {
            System.out.println(YELLOW + "\nInverse Method can't be performed " + RESET);
        }

        // Save Data
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no))");
        choice = getChoice(0,1);
        if(choice == 1 && result instanceof UniqueSolution) {
            Matrix solution = ((UniqueSolution) result).getSolution();
            saveUniqueResultToFile(solution);
        }
        else if(choice == 1 && result instanceof ParametricSolution) {
            saveParametricResultToFile((ParametricSolution) result);
        }

        System.out.println();
    }
}
