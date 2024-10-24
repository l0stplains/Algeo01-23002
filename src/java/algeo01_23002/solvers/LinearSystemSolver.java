package algeo01_23002.solvers;
import algeo01_23002.types.*;

public class LinearSystemSolver {
    private boolean isAllZero(double[] row){
        int len = row.length;
        for (int i = 0; i < len; i++){
            if (row[i] != 0) return false;
        }
        return true;
    }
    private double[] substractArray (double[] arr1, double[] arr2){
        double[] res = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++){
            res[i] = arr1[i] - arr2[i];
        }
        return res;
    }

    private double[] multArrayWithConst (double[] arr1, double k){
        double[] res = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++){
            res[i] = arr1[i] * k;
        }
        return res;
    }

    private boolean isNULL(double[] row){
        int len = row.length;
        if (row[0] != -999999999){
            return false;
        }
        for (int i = 1; i < len; i++){
            if (row[i] != 0) return false;
        }
        return true;
    }

    public LinearSystemSolution gaussianElimination (Matrix matrix){
        int rows = matrix.getRowsCount();
        int cols = matrix.getColsCount();

        matrix = matrix.getRowEchelonForm();
        double[][] data = matrix.getAllData();

        boolean isNoSolution = false;
        //get the index of last row that not all zero and count how many zero rows
        int countZeroRows = 0;
        int idxlastRowNotZero = rows-1; // this is used as starting row in backward elimination
        for (int row = rows-1; row >= 0; row--){ //check if zero solution or many solution or not both
            boolean allZeroCoefficients = true; // Flag to check if all coefficients are zero in this row
            for (int col = 0; col < cols - 1; col++) { // Check each coefficient in the row
                if (data[row][col] != 0) {
                    allZeroCoefficients = false;
                    break;
                }
            }

            // If all coefficients are zero and the constant term is non-zero, it's a no-solution case
            if (allZeroCoefficients && data[row][cols - 1] != 0) {
                isNoSolution = true;
            }
            if (isAllZero(data[row])){
                countZeroRows++;
            } else {
                idxlastRowNotZero = row;
                break;
            }
        }

        boolean isManySolutions = rows - countZeroRows < cols-1;
        //if last row contains all zero but the rows
        // then there are many solutions


        if (!isManySolutions && !isNoSolution){ //if there is only one solution

            double[][] result = new double[1][cols-1]; //initialize array to save the result

            result[0][cols-2] = data[rows-1][cols-1]; //data of last row and lat column for last variable
            for (int row = cols-2 ; row >= 0; row--){ //backward elimination
                result[0][row] = data[row][cols - 1];
                for (int col = cols-2; col>row; col--){
                    result[0][row] -= data[row][col]*result[0][col];
                }
            }

            Matrix resultMatrixUniqueSolution = new Matrix(1,cols-1);
            resultMatrixUniqueSolution.setAllData(result);

            return new UniqueSolution(resultMatrixUniqueSolution);

        } else if (isNoSolution){
            return new NoSolution();

        } else { // if there are many solutions

            String[][] resultParametric;

            //count how many parameter is needed
            int countParameter = (cols - 1) - (rows - countZeroRows);
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

            if (countZeroRows == rows){
                for(int i=0; i<cols-1; i++){
                    result[i][0] = 0;
                }
                for (int i=cols-2; i>=0; i--){
                    result[i][cols-1-i] = 1;
                }
            } else {
                int parameter = 1; //initiate parameter index

                //iterate through all rows starting from last row that not all zero
                for (int row = idxlastRowNotZero; row >= 0; row--) {
                    //find leading one index
                    int indexOfLeadingOne = cols;
                    for (int col = 0; col < cols; col++) {
                        if (data[row][col] == 1) {
                            indexOfLeadingOne = col;
                            break;
                        }
                    }

                    //find solution by iterating through cols in a certain row
                    result[indexOfLeadingOne][0] = data[row][cols - 1];
                    for (int col = indexOfLeadingOne + 1; col < cols - 1; col++) {

                        if (isNULL(result[col]) && parameter < countParameter + 1) { //if the result of a variable is null, assign parameter
                            result[col][0] = 0;
                            result[col][parameter] = 1;
                            parameter++;
                        }
                        result[indexOfLeadingOne] = substractArray(result[indexOfLeadingOne], multArrayWithConst(result[col], data[row][col]));
                    }
                }
            }


            //move the result to resultParametric that has type String
            resultParametric = new String[cols-1][countParameter+1];

            //for constant, just move result to resultParametric
            for (int i=0; i<resultParametric.length; i++){
                resultParametric[i][0] = String.valueOf(result[i][0]);
            }

            //for parametric coefficient, append result with a character first, then assign it to resultParametric
            for (int i=0; i<resultParametric.length; i++) {
                int ascii = 114;
                for (int j = 1; j < resultParametric[0].length; j++) {
                    resultParametric[i][j] = String.valueOf(result[i][j]) + String.valueOf((char) ascii); //append result with ascii character
                    ascii++;
                }

            }
            return new ParametricSolution(resultParametric);
        }
    }

    public LinearSystemSolution gaussJordanElimination(Matrix matrix) {
        int rows = matrix.getRowsCount();
        int cols = matrix.getColsCount();

        matrix = matrix.getReducedRowEchelonForm();
        double[][] data = matrix.getAllData();

        boolean isNoSolution = false;
        //get the index of last row that not all zero and count how many zero rows
        int countZeroRows = 0;
        int idxlastRowNotZero = rows-1; // this is used as starting row in backward elimination
        for (int row = rows-1; row >= 0; row--){ //check if zero solution or many solution or not both
            boolean allZeroCoefficients = true; // Flag to check if all coefficients are zero in this row
            for (int col = 0; col < cols - 1; col++) { // Check each coefficient in the row
                if (data[row][col] != 0) {
                    allZeroCoefficients = false;
                    break;
                }
            }

            // If all coefficients are zero and the constant term is non-zero, it's a no-solution case
            if (allZeroCoefficients && data[row][cols - 1] != 0) {
                isNoSolution = true;
            }
            if (isAllZero(data[row])){
                countZeroRows++;
            } else {
                idxlastRowNotZero = row;
                break;
            }
        }

        boolean isManySolutions = rows - countZeroRows < cols-1;
        //if last row contains all zero but the rows
        // then there are many solutions


        if (!isManySolutions && !isNoSolution){ //if there is only one solution

            double[][] result = new double[1][cols-1]; //initialize array to save the result

            for(int row=0; row<rows-countZeroRows; row++){
                result[0][row] = data[row][cols-1];
            }

            //move the result to resultParametric that has type String
            Matrix resultMatrixUniqueSolution = new Matrix(1, cols-1);
            resultMatrixUniqueSolution.setAllData(result);

            return new UniqueSolution(resultMatrixUniqueSolution);


        } else if (isNoSolution) {
            return new NoSolution();

        } else { // if there are many solutions

            String[][] resultParametric;

            //count how many parameter is needed (Edited)
            int countParameter = (cols - 1) - (rows - countZeroRows);

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

            if (countZeroRows == rows){
                for(int i=0; i<cols-1; i++){
                    result[i][0] = 0;
                }
                for (int i=cols-2; i>=0; i--){
                    result[i][cols-1-i] = 1;
                }
            } else {
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

                        if (isNULL(result[col]) && parameter < countParameter+1) { //if the result of a variable is null, assign parameter
                            result[col][0] = 0;
                            result[col][parameter] = 1;
                            parameter++;
                        }
                        result[indexOfLeadingOne] = substractArray(result[indexOfLeadingOne], multArrayWithConst(result[col], data[row][col]));
                    }

                }
            }




            //move the result to resultParametric that has type String
            resultParametric = new String[cols-1][countParameter+1];

            //for constant, just move result to resultParametric
            for (int i=0; i<resultParametric.length; i++){
                resultParametric[i][0] = String.valueOf(result[i][0]);
            }

            //for parametric coefficient, append result with a character first, then assign it to resultParametric
            for (int i=0; i<resultParametric.length; i++) {
                int ascii = 114;
                for (int j = 1; j < resultParametric[0].length; j++) {
                    resultParametric[i][j] = String.valueOf(result[i][j]) + String.valueOf((char) ascii); //append result with ascii character
                    ascii++;
                }

            }
            return new ParametricSolution(resultParametric);
        }
    }

    public LinearSystemSolution cramersRule(Matrix matrix){
        Matrix X = new Matrix(matrix.getRowsCount(), matrix.getColsCount()-1);
        Matrix Y = new Matrix(matrix.getRowsCount(), 1);

        for(int i = 0; i < matrix.getColsCount()-1; i++){
            X.setCol(i, matrix.getCol(i));
        }
        Y.setCol(0, matrix.getCol(matrix.getColsCount()-1));

        double actualDeterminant = X.getDeterminantWithCofactor(); // Can be change with rowReduction methode
        if(actualDeterminant == 0 || matrix.getColsCount() < 3){
            throw new IllegalArgumentException("cramersRule() : Solution could not be calculated, Invalid Determinant or Invalid Matrix Dimension");
        }
        double tempDeterminant;
        Matrix solutions = new Matrix(1, X.getRowsCount());

        for(int i = 0; i < X.getColsCount(); i++){
            Matrix temp = X.getCopy();
            for(int j = 0; j < X.getColsCount(); j++){
                temp.getAllData()[j][i] = Y.getAllData()[j][0]; // fill the columns with constant
            }
            tempDeterminant = temp.getDeterminantWithCofactor();
            solutions.getAllData()[0][i] = tempDeterminant / actualDeterminant;
        }
        return new UniqueSolution(solutions.transpose());
    }

    public LinearSystemSolution inverseMethod(Matrix matrix){
        Matrix equationVariables = new Matrix(matrix.getRowsCount(), matrix.getColsCount() - 1);
        if(!equationVariables.isSquare()){
            throw new IllegalArgumentException("inverseMethod() : Solution could not be calculated (wrong dimension for inverse method)");
        }
        Matrix equationResult = new Matrix(matrix.getRowsCount(), 1);

        // Fill the equation matrix
        for(int i = 0; i < matrix.getRowsCount(); i++){
            for(int j = 0; j < matrix.getColsCount(); j++){
                if(j == matrix.getColsCount() - 1){
                    equationResult.setData(i, 0, matrix.getData(i, j));
                } else {
                    equationVariables.setData(i, j, matrix.getData(i, j));
                }
            }
        }
        Matrix result = equationVariables.getInverseWithRowReduction().multiplyByMatrix(equationResult);

        return new UniqueSolution(result);
    }
}
