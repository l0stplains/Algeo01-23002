package algeo01_23002.solvers;
import algeo01_23002.types.Matrix;

public class LinearSystemSolver {
    private static boolean isAllZero(double[] row){
        int len = row.length;
        for (int i = 0; i < len; i++){
            if (row[i] != 0) return false;
        }
        return true;
    }
    public static Matrix gaussianElimination (Matrix matrix){
        int rows = matrix.getRows();
        int cols = matrix.getCols();

        matrix.getRowEchelonForm();
        double[][] data = matrix.getData();

        boolean isManySolutions = false;
        if (isAllZero(data[rows-1]) && rows < cols) { //if last row contains all zero
            isManySolutions = true; // then there are many solutions
        }

        double[][] result = new double[1][cols-1]; //initialize array to save the result
        if (!isManySolutions){ //if there is only one solution

            result[0][cols-2] = data[rows-1][cols-1]; //data of last row and lat column for last variable
            for (int row = cols-2 ; row >= 0; row--){ //backward elimination
                result[0][row] = data[row][cols - 1];
                for (int col = cols-2; col>row; col--){
                    result[0][row] -= data[row][col]*result[0][col];
                }
            }
        }

        Matrix resultMatrix = new Matrix(1, cols-1); //matrix of the result (one dimension)
        resultMatrix.setData(result);
        return resultMatrix;
    }

    public static Matrix gaussJordanElimination(Matrix matrix) {
        int rows = matrix.getRows();
        int cols = matrix.getCols();

        matrix.getReducedRowEchelonForm();
        double[][] data = matrix.getData();

        double[][] result = new double[1][cols-1]; //initialize array to save the result

        for (int col=0; col < cols-1; col++){
            result[0][col] = data[col][cols-1];
        }
        Matrix resultMatrix = new Matrix(1, cols-1); //matrix of the result (one dimension)
        resultMatrix.setData(result);
        return resultMatrix;
    }
}
