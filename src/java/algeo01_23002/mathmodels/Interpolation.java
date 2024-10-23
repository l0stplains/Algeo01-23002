package algeo01_23002.mathmodels;
import java.lang.Math;

import algeo01_23002.solvers.LinearSystemSolver;
import algeo01_23002.types.LinearSystemSolution;
import algeo01_23002.types.Matrix;
import algeo01_23002.types.PolynomialResult;
import algeo01_23002.types.UniqueSolution;

public class Interpolation {

    public static PolynomialResult polynomialInterpolation(Matrix x, Matrix y){
        if(x.getColsCount() != y.getColsCount()){
            throw new IllegalArgumentException("polynomialInterpolation() : X and Y is not the same length");
        }
        if(x.getRowsCount() != 1 || y.getRowsCount() != 1){
            throw new IllegalArgumentException("polynomialInterpolation() : Wrong dimensions (must be 1 row only)");
        }

        Matrix linearSystem = new Matrix(x.getColsCount(), x.getColsCount() + 1);

        for(int i = 0; i < linearSystem.getRowsCount(); i++) {
            for (int j = 0; j < linearSystem.getColsCount() - 1; j++) {
                linearSystem.setData(i, j, pow(x.getData(0, i), j));

            }
            linearSystem.setData(i, linearSystem.getColsCount() - 1, y.getData(0, i));
        }
        LinearSystemSolver solver = new LinearSystemSolver();
        LinearSystemSolution solution = solver.inverseMethod(linearSystem);
        if(solution instanceof UniqueSolution uniqueSolution){
            PolynomialResult result = new PolynomialResult(uniqueSolution.getSolution());
            return result;
        } else {
            throw new ArithmeticException("polynomialInterpolation() : Solution is not unique");
        }
    }
    private static double pow (double base, double exponent) {
        if (exponent == 0){
            return 1;
        }
        if (base == 0 && exponent == -1){
            return 0;
        }
        return Math.pow(base, exponent);
    }

    private static double f (double x, double y, int i, int j) {
        return pow(x,i)*pow(y,j);
    }
    private static double fx (double x, double y, int i, int j) {
        return i*pow(x,i-1)*pow(y,j);
    }
    private static double fy (double x, double y, int i, int j) {
        return j*pow(x,i)*pow(y,j-1);
    }
    private static double fxy (double x, double y, int i, int j) {
        return i*j*pow(x,i-1)*pow(y,j-1);
    }

    public static Matrix getXInverseBicubicSpline (){


        Matrix X = new Matrix(16,16);
        double[][] data = X.getAllData();
        int x, y;
        for(int row = 0; row <=15; row++){
            if (row % 4 == 0){
                x = 0; y = 0;
            } else if (row % 4 == 1){
                x = 1; y = 0;
            } else if (row % 4 == 2) {
                x = 0; y = 1;
            } else {
                x = 1; y = 1;
            }


            int i = -1, j = -1;
            for(int col = 0; col <=15; col++) {
                if (col % 4 == 0){
                    i = 0;
                    j++;
                } else {
                    i++;
                }

                if (row >= 0 && row < 4){
                    data[row][col] = f(x, y, i, j);
                } else if (row >= 4 && row < 8){
                    data[row][col] = fx(x, y, i, j);
                } else if (row >= 8 && row < 12) {
                    data[row][col] = fy(x, y, i, j);
                } else {
                    data[row][col] = fxy(x, y, i, j);
                }

            }


        }
        X.setAllData(data);
        return X.getInverseWithRowReduction();
    }

    public static double bicubicSplineInterpolation (Matrix YInput, double a, double b, Matrix XInverse){
//        Matrix Y = new Matrix(16,1);
        Matrix Y = YInput;
        int k = 0;
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                Y.setData(k, 0, YInput.getData(i, j));
//                k++;
//            }
//        }


        Matrix coeff = XInverse.multiplyByMatrix(Y);
        double res = 0;
        k = 0;
        for (int i=0; i<=3; i++){
            for (int j=0; j<=3; j++){
                res += coeff.getData(k,0)*pow(a,i)*pow(b,j);
                k++;
            }

        }
        return res;
    }
}

