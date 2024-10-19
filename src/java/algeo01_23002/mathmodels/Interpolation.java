package algeo01_23002.mathmodels;
import java.lang.Math;
import algeo01_23002.types.Matrix;
public class Interpolation {

    // public static Matrix polynomialInterpolation
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

    public static Matrix bicubicSplineInterpolation (){
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
        X.getInverseWithAdjoint();
        return X;
    }
}

