package algeo01_23002.cli.menus.submenus;

import algeo01_23002.mathmodels.Regression;
import algeo01_23002.types.LinearSystemSolution;
import algeo01_23002.types.Matrix;
import algeo01_23002.types.ParametricSolution;
import algeo01_23002.types.UniqueSolution;

import java.io.FileNotFoundException;
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

        int choice;
        Matrix matrix = new Matrix(rows, cols);
        while(true){
            System.out.println("\n" + ARROW + "  Input from file or from CLI? ");
            System.out.println("1.  Input Matrix from File");
            System.out.println("2.  Input Matrix from CLI");

             choice= getChoice(1, 2);
            switch(choice) {
                case 1 -> inputMatrixFromFileDriver(matrix);
                case 2 -> matrix = inputMatrixFromCLIDriver(matrix);
            }
            Matrix temp = new Matrix(rows, cols);
            if (!temp.equals(matrix)){
                break;
            }
        }

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

        if(choice == 1 && result instanceof UniqueSolution) {
            Matrix solution = ((UniqueSolution) result).getSolution();
            saveUniqueResultToFile(solution);
        }
        else if(choice == 1 && result instanceof ParametricSolution) {
            // saveParametricResultToFile()
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
    //

    private static void inputMatrixFromFileDriver(Matrix matrix) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        System.out.println("You entered: " + input);
        try{
            matrix.inputMatrixFromFile(input);
        } catch(IllegalArgumentException e) {
            if(e.getMessage() == "File not found"){
                System.out.println(YELLOW + "\nFile not found");
            }else if (e.getMessage().startsWith("File matrix dimensions")){
                System.out.println(YELLOW + "\nInvalid Dimension");
            } else{
                System.out.println(YELLOW + "\nInvalid Input");
            }
        } catch (Exception e) {

        }
    }

    private static Matrix inputMatrixFromCLIDriver(Matrix matrix) {
        System.out.println("\n" + ARROW + "  Enter each element of the matrix: ");
        return inputMatrixDriver(matrix);
    }
}
