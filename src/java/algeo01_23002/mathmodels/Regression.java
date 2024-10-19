package algeo01_23002.mathmodels;
import algeo01_23002.solvers.LinearSystemSolver;
import algeo01_23002.types.LinearSystemSolution;
import algeo01_23002.types.Matrix;

public class Regression {

    public static LinearSystemSolution multipleLinearRegression (Matrix inputPoints){
        int n = inputPoints.getRowsCount(); //n of points
        int k = inputPoints.getColsCount() - 1; //k of variables

        Matrix X = new Matrix(k+1, k+1);

        //process to make X matrix
        X.setData(0,0,n);//for top left corner of the X
        //for top edge of the X
        for (int i = 1; i <= k; i++) {
            for (int h=0; h<n; h++) {
                double val = X.getData(0,i) + inputPoints.getData(h,i-1);
                X.setData(0,i,val);
            }
            X.setData(i-1, 0, X.getData(0,i-1));
        }
        //for left edge of the X
        for(int j = 1; j <=k; j++){
            X.setData(j, 0, X.getData(0,j)); // refactor
        }
        //for the whole X except top edge and left edge
        for (int j=1; j<=k; j++){
            for(int i=1; i<=k; i++){
                for (int h=0; h<n; h++){
                    double val = X.getData(j,i) + inputPoints.getData(h,j-1) * inputPoints.getData(h,i-1);
                    X.setData(j,i,val);
                }
            }
        }

        Matrix y = new Matrix(k+1, 1);
        for (int h=0; h<n; h++) {
            double val = y.getData(0,0) + inputPoints.getData(h,k);
            y.setData(0,0,val);
        }
        for (int j = 1; j <= k; j++) {
            for (int h=0; h<n; h++) {
                double val = y.getData(j,0) + inputPoints.getData(h,j-1) * inputPoints.getData(h,k);
                y.setData(j,0,val);
            }
        }

        Matrix augmentedMatrix = new Matrix(X.getRowsCount(),X.getColsCount()+1);
        for (int i = 0; i < X.getRowsCount(); i++) {
            for (int j = 0; j < X.getColsCount(); j++) {
                augmentedMatrix.setData(i,j,X.getData(i,j));
            }
            augmentedMatrix.setData(i,X.getColsCount(),y.getData(i,0));
        }
        LinearSystemSolver solver = new LinearSystemSolver();
        return solver.gaussJordanElimination(augmentedMatrix);
    }

    public static LinearSystemSolution multipleQuadraticRegression(Matrix inputPoints) {
        int n = inputPoints.getRowsCount(); //n of points
        int k = inputPoints.getColsCount() - 1; //k of variables

        int Xrows = 1 + 2 * k + k * (k - 1) / 2;
        Matrix X = new Matrix(Xrows, Xrows);
        Matrix stored = new Matrix(n, Xrows);
        Matrix Y = new Matrix(Xrows, 1);

        double val, storedVal = 0;

        for (int i = 0; i < Xrows; i++) {
            val = 0;
            for (int r = 0; r < n; r++) {
                if (i == 0) {
                    X.setData(0, i, n);
                    continue;
                } else if (i < k + 1) { // linear variable
                    val += inputPoints.getData(r, (i - 1) % k);
                    storedVal = inputPoints.getData(r, (i - 1) % k);
                } else if (i > k && i < 2 * k + 1) { //quadratic variable
                    val += inputPoints.getData(r, (i - 1) % k) * inputPoints.getData(r, (i - 1) % k);
                    storedVal = inputPoints.getData(r, (i - 1) % k) * inputPoints.getData(r, (i - 1) % k);
                } else { // interaction variable
                    for (int v = (i - 1) % k + 1; v < k; v++) {
                        val += inputPoints.getData(r, (i - 1) % k) * inputPoints.getData(r, ((i - 1) % k) + v);
                        storedVal = inputPoints.getData(r, (i - 1) % k) * inputPoints.getData(r, ((i - 1) % k) + v);
                    }
                }
                X.setData(0, i, val);
                stored.setData(r, i, storedVal);
            }
            X.setData(i, 0, X.getData(0, i));
        }

        // Sum for X values
        double sumVal;
        for (int i = 1; i < X.getRowsCount(); i++) {
            for (int j = 1; j < X.getColsCount(); j++) {
                sumVal = 0;
                for (int l = 0; l < stored.getRowsCount(); l++) {
                    sumVal += stored.getData(l, i) * stored.getData(l, j);
                }
                X.setData(i, j, sumVal);
            }
        }

        // Sum for Y values
        double yVal;
        for(int i = 0; i < Y.getRowsCount(); i++){
            yVal = 0;
            for(int j = 0; j < n; j++){
                if(i ==0){
                    yVal += inputPoints.getData(j, k);
                }
                else {
                    yVal += inputPoints.getData(j, k) * stored.getData(j, i);
                }
            }
            Y.setData(i,0,yVal);
        }

        //  Augmented X Y
        Matrix augmentedMatrix = new Matrix(Xrows,Xrows+1);
        for (int i = 0; i < Xrows; i++) {
            for (int j = 0; j < Xrows; j++) {
                augmentedMatrix.setData(i,j,X.getData(i,j));
            }
            augmentedMatrix.setData(i,Xrows,Y.getData(i,0));
        }

        LinearSystemSolver solver = new LinearSystemSolver();
        return solver.gaussianElimination(augmentedMatrix);
    }
}