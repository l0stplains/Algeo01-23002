package algeo01_23002.cli.menus.submenus;

import algeo01_23002.mathmodels.Interpolation;
import algeo01_23002.types.*;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.*;

public class InterpolationMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("INTERPOLATION MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Polynomial Interpolation");
            System.out.println("2.  Bicubic Spline Interpolation");
            System.out.println("3.  Back to main menu");
            System.out.print(ARROW +"  Select an option (1-3): " + RESET);

            int choice = getChoice(1, 3);
            switch(choice) {
                case 1 -> polynomialInterpolationDriver();
                case 2 -> bicubicSplineInterpolationDriver();
                case 3 -> {isRunning = false;}
            }
        }
    }

    private static void polynomialInterpolationDriver() {
        System.out.print("\n" + ARROW + "  Enter number of points: ");
        int n_points = getChoice(1, 100);

        Matrix x_points = new Matrix(1, n_points);
        Matrix y_points = new Matrix(1, n_points);

        for(int i = 0; i < n_points; i++) {
            System.out.print("\n" + ARROW + "  Enter x value for point number " + (i + 1) + " : ");
            x_points.setData(0, i, getDouble());
            System.out.print("\n" + ARROW + "  Enter y value for point number " + (i + 1) + " : ");
            y_points.setData(0, i, getDouble());
        }

        System.out.print("\nFitting the data...");
        PolynomialResult result;
        try {
            result = Interpolation.polynomialInterpolation(x_points, y_points);
        } catch (Exception e) {
            System.out.println(YELLOW + "\nInterpolation can't be performed " + RESET);
            return;
        }

        while(true) {
            System.out.print("\n" + ARROW + "  Enter x value for point to be interpolated : ");
            double inp = getDouble();
            double res = result.evaluate(inp);
            System.out.println(YELLOW + "\nResult: " + RESET);
            System.out.println(res);
            System.out.print("\n" + ARROW + "  Do you wish to continue (1 for continue/0 to stop): ");
            int choice = getChoice(0, 1);
            if(choice == 0) {
                break;
            }
        }

        System.out.println();
    }

    private static void bicubicSplineInterpolationDriver() {
        System.out.print("\n" + ARROW + "  Enter number of rows: ");
        int rows = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols: ");
        int cols = getChoice(1, 100);

        Matrix matrix = new Matrix(rows, cols);

        System.out.println("\n" + ARROW + "  Enter each element of the matrix: ");
        inputMatrixDriver(matrix);

        System.out.print("\n" + ARROW + "  Enter x value for point to be interpolated : ");
        double x = getDouble();
        System.out.print("\n" + ARROW + "  Enter y value for point to be interpolated : ");
        double y = getDouble();

        Matrix Y = new Matrix(1, 2);
        Y.setData(0,0,x); Y.setData(0,1,y);


        System.out.print("\nFitting the data...");
        double result =0;
        try{
            matrix = Interpolation.getXInverseBicubicSpline();
            result = Interpolation.bicubicSplineInterpolation(Y, x, y, matrix);
        } catch (Exception e) {
            System.out.println(YELLOW + "\nInterpolation can't be performed " + RESET);
        }


        System.out.println(YELLOW + "\nResult: " + RESET);
        System.out.println(result);

        System.out.println();
    }
}
