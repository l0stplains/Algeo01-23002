package algeo01_23002.solvers;
import algeo01_23002.types.Matrix;

import java.util.AbstractMap;

public class LinearSystemSolver {
    private static boolean isAllZero(double[] row){
        int len = row.length;
        for (int i = 0; i < len; i++){
            if (row[i] != 0) return false;
        }
        return true;
    }
    private static double[] substractArray (double[] arr1, double[] arr2){
        double[] res = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++){
            res[i] = arr1[i] - arr2[i];
        }
        return res;
    }

    private static double[] multArrayWithConst (double[] arr1, double k){
        double[] res = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++){
            res[i] = arr1[i] * k;
        }
        return res;
    }

    private static boolean isNULL(double[] row){
        int len = row.length;
        if (row[0] != -999999999){
            return false;
        }
        for (int i = 1; i < len; i++){
            if (row[i] != 0) return false;
        }
        return true;
    }

    public static String[][] gaussianElimination (Matrix matrix){
        String[][] resultParametrik;
        int rows = matrix.getRows();
        int cols = matrix.getCols();

        matrix.getRowEchelonForm();
        double[][] data = matrix.getData();

        boolean isManySolutions = (isAllZero(data[rows - 1]) && rows < cols) || rows < cols - 1;
        //if last row contains all zero but the rows
        // then there are many solutions


        if (!isManySolutions){ //if there is only one solution

            double[][] result = new double[1][cols-1]; //initialize array to save the result

            result[0][cols-2] = data[rows-1][cols-1]; //data of last row and lat column for last variable
            for (int row = cols-2 ; row >= 0; row--){ //backward elimination
                result[0][row] = data[row][cols - 1];
                for (int col = cols-2; col>row; col--){
                    result[0][row] -= data[row][col]*result[0][col];
                }
            }

            //move the result to resultParametrik that has type String
            resultParametrik = new String[1][cols-1];

            for (int j = 0; j < cols-1; j++) {
                resultParametrik[0][j] = String.valueOf(result[0][j]);
            }

        } else { // if there are many solutions

            //get the index of last row that not all zero and count how many zero rows
            int countZeroRows = 0;
            int idxlastRowNotZero = rows-1; // this is used as starting row in backward elimination
            for (int row = rows-1; row >= 0; row--){
                if (isAllZero(data[row])){
                    countZeroRows++;
                } else {
                    idxlastRowNotZero = row;
                    break;
                }
            }

            //count how many parameter is needed
            int countParameter = (rows - 1) - countZeroRows;

            //make result array
            //index 0 of result array is used to store constant and the rest is used to store the coefficient of parameter
            double[][] result = new double[cols-1][countParameter+1];

            //initiate the result array with null mark (-999999999 for constant, and 0 for parameter's coefficient)
            for(int i=0; i<cols-1; i++){
                result[i][0] = -999999999;
            }
            for (int i=0; i<cols-1; i++){
                for(int j=1; j<=countParameter; j++){
                    result[i][j] = 0;
                }
            }


            int parameter = 1; //initiate parameter index

            //iterate through all rows starting from last row that not all zero
            for (int row = idxlastRowNotZero; row >= 0; row--){
                //find leading one index
                int indexOfLeadingOne = cols;
                for(int col=0; col < cols; col++){
                    if (data[row][col] == 1){
                        indexOfLeadingOne = col;
                        break;
                    }
                }

                //find solution by iterating through cols in a certain row
                result[indexOfLeadingOne][0] = data[row][cols-1];
                for (int col = indexOfLeadingOne+1; col < cols-1; col++){

                    if (isNULL(result[col])) { //if the result of a variable is null, assign parameter
                        result[col][0] = 0;
                        result[col][parameter] = 1;
                        parameter++;
                    }
                    result[indexOfLeadingOne] = substractArray(result[indexOfLeadingOne], multArrayWithConst(result[col], data[row][col]));
                }

            }

            //move the result to resultParametrik that has type String
            resultParametrik = new String[cols-1][countParameter+1];

            //for constant, just move result to resultParametrik
            for (int i=0; i<resultParametrik.length; i++){
                resultParametrik[i][0] = String.valueOf(result[i][0]);
            }

            //for parametric's coefficient, append result with a character first, then assign it to resultParametrik
            for (int i=0; i<resultParametrik.length; i++) {
                int ascii = 113+countParameter;
                for (int j = 1; j < resultParametrik[0].length; j++) {
                    resultParametrik[i][j] = String.valueOf(result[i][j]) + String.valueOf((char) ascii); //append result with ascii character
                    ascii--;
                }

            }
        }

        return resultParametrik;
    }

    public static String[][] gaussJordanElimination(Matrix matrix) {
        String[][] resultParametrik;
        int rows = matrix.getRows();
        int cols = matrix.getCols();

        matrix.getRowEchelonForm();
        double[][] data = matrix.getData();

        boolean isManySolutions = (isAllZero(data[rows - 1]) && rows < cols) || rows < cols - 1;
        //if last row contains all zero but the rows
        // then there are many solutions


        if (!isManySolutions){ //if there is only one solution

            double[][] result = new double[1][cols-1]; //initialize array to save the result

            for(int row=0; row<rows; row++){
                result[0][row] = data[row][cols-1];
            }

            //move the result to resultParametrik that has type String
            resultParametrik = new String[1][cols-1];

            for (int j = 0; j < cols-1; j++) {
                resultParametrik[0][j] = String.valueOf(result[0][j]);
            }

        } else { // if there are many solutions

            //get the index of last row that not all zero and count how many zero rows
            int countZeroRows = 0;
            int idxlastRowNotZero = rows-1; // this is used as starting row in backward elimination
            for (int row = rows-1; row >= 0; row--){
                if (isAllZero(data[row])){
                    countZeroRows++;
                } else {
                    idxlastRowNotZero = row;
                    break;
                }
            }

            //count how many parameter is needed
            int countParameter = (rows - 1) - countZeroRows;

            //make result array
            //index 0 of result array is used to store constant and the rest is used to store the coefficient of parameter
            double[][] result = new double[cols-1][countParameter+1];

            //initiate the result array with null mark (-999999999 for constant, and 0 for parameter's coefficient)
            for(int i=0; i<cols-1; i++){
                result[i][0] = -999999999;
            }
            for (int i=0; i<cols-1; i++){
                for(int j=1; j<=countParameter; j++){
                    result[i][j] = 0;
                }
            }


            int parameter = 1; //initiate parameter index

            //iterate through all rows starting from last row that not all zero
            for (int row = idxlastRowNotZero; row >= 0; row--){
                //find leading one index
                int indexOfLeadingOne = cols;
                for(int col=0; col < cols; col++){
                    if (data[row][col] == 1){
                        indexOfLeadingOne = col;
                        break;
                    }
                }

                //find solution by iterating through cols in a certain row
                result[indexOfLeadingOne][0] = data[row][cols-1];
                for (int col = indexOfLeadingOne+1; col < cols-1; col++){

                    if (isNULL(result[col])) { //if the result of a variable is null, assign parameter
                        result[col][0] = 0;
                        result[col][parameter] = 1;
                        parameter++;
                    }
                    result[indexOfLeadingOne] = substractArray(result[indexOfLeadingOne], multArrayWithConst(result[col], data[row][col]));
                }

            }

            //move the result to resultParametrik that has type String
            resultParametrik = new String[cols-1][countParameter+1];

            //for constant, just move result to resultParametrik
            for (int i=0; i<resultParametrik.length; i++){
                resultParametrik[i][0] = String.valueOf(result[i][0]);
            }

            //for parametric's coefficient, append result with a character first, then assign it to resultParametrik
            for (int i=0; i<resultParametrik.length; i++) {
                int ascii = 113+countParameter;
                for (int j = 1; j < resultParametrik[0].length; j++) {
                    resultParametrik[i][j] = String.valueOf(result[i][j]) + String.valueOf((char) ascii); //append result with ascii character
                    ascii--;
                }

            }
        }

        return resultParametrik;
    }

    public static Matrix cramersRule(Matrix matrix, Matrix constant){

        // Cramer's rule can be used IF MATRIX is SQUARE and CONSTANT have same LENGTH as MATRIX
        if(!(matrix.isSquare() && matrix.getRows() == constant.getCols())){
            throw new IllegalArgumentException("Solution could not be calculated (dimension incompatible)");
        }
        double actualDeterminant = matrix.getDeterminantWithCofactor(); // Can be change with rowReduction methode
        if(actualDeterminant == 0){
            throw new IllegalArgumentException("Solution could not be calculated");
        }
        double tempDeterminant;
        Matrix solutions = new Matrix(1, matrix.getCols());

        for(int i = 0; i < constant.getCols(); i++){
            Matrix temp = matrix.getCopy();
            for(int j = 0; j < matrix.getCols(); j++){
                temp.getData()[j][i] = constant.getData()[0][j]; // fill the columns with constant
            }
            tempDeterminant = temp.getDeterminantWithCofactor();
            solutions.getData()[0][i] = tempDeterminant / actualDeterminant;
        }
        return solutions;
    }
}
