package algeo01_23002.types;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class Matrix {
  private double[][] data;
  private int rows;
  private int cols;

  // Constructor
  // ================================
  public Matrix(int rows, int cols) {
    if(rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Matrix() : Invalid matrix size. Rows and columns must be positive.");
    }
    this.rows = rows;
    this.cols = cols;
    this.data = new double[rows][cols];
  }

  // Getters
  // ================================
  public int getRowsCount() { return rows; }
  public int getColsCount() { return cols; }
  public double[] getRow(final int row) {return data[row];}
  public double[] getCol(final int col) {
    double[] colData = new double[this.rows];
    for(int i = 0; i < this.rows; i++) {
      colData[i] = data[i][col];
    }
    return colData;
  }
  public double[][] getAllData() { return data; }
  public double getData(int row, int col) { return data[row][col]; }

  // Setters
  // ================================
  public void setAllData(double[][] data) {
    if(data.length == 0 || data[0].length == 0){
      throw new IllegalArgumentException("setAllData() : Matrix is empty!");
    }
    this.data = data;
    this.rows = data.length;
    this.cols = data[0].length;
  }

  public void setData(int row, int col, double value) {
    this.data[row][col] = value;
  }

  public void setRow(int row, double[] rowData) {
    this.data[row] = rowData;
  }

  public void setCol(int col, double[] colData) {
    for(int i = 0; i < this.rows; i++) {
      this.data[i][col] = colData[i];
    }
  }

  // =================================
  // Matrix Operations
  // =================================

  public Matrix add(Matrix other) {
    validateDimensions(other);
    Matrix result = this.getCopy();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        result.data[i][j] = data[i][j] + other.data[i][j];
      }
    }
    return result;
  }

  public Matrix subtract(Matrix other) {
    validateDimensions(other);
    Matrix result = this.getCopy();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        result.data[i][j] = data[i][j] - other.data[i][j];
      }
    }
    return result;
  }

  public Matrix multiplyByScalar(int scalar) {
    Matrix result = this.getCopy();
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < cols; j++) {
        result.data[i][j] = data[i][j] * scalar;
      }
    }
    return result;
  }

  public Matrix multiplyByScalar(double scalar) {
    Matrix result = this.getCopy();
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < cols; j++) {
        result.data[i][j] = data[i][j] * scalar;
      }
    }
    return result;
  }

  public Matrix divideByScalar(int scalar) {
    Matrix result = this.getCopy();
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < cols; j++) {
        result.data[i][j] = data[i][j] / scalar;
      }
    }
    return result;
  }

  public Matrix divideByScalar(double scalar) {
    Matrix result = this.getCopy();
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < cols; j++) {
        result.data[i][j] = data[i][j] / scalar;
      }
    }
    return result;
  }

  public Matrix multiplyByMatrix(Matrix other) {
    if(this.cols != other.rows){
      throw new IllegalArgumentException("multiplyByMatrix() : Matrix multiplication could not be performed (dimension incompatible)");
    }
    int otherCols = other.getColsCount();
    Matrix result = new Matrix(rows, otherCols);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < otherCols; j++) {
        for (int k = 0; k < cols; k++) {
          result.getAllData()[i][j] += data[i][k] * other.getAllData()[k][j];
        }
      }
    }
    return result.getValidatedMatrixPrecision();
  }

  public Matrix multiplyRowByScalar(int row, int scalar) {
    Matrix result = this.getCopy();
    validateRowIndex(row);
    for(int i = 0; i < cols; i++){
      result.data[row][i] = data[row][i] * scalar;
    }
    return result;
  }


  public Matrix multiplyRowByScalar(int row, double scalar) {
    Matrix result = this.getCopy();
    validateRowIndex(row);
    for(int i = 0; i < cols; i++){
      result.data[row][i] = data[row][i] * scalar;
    }
    return result;
  }

  public Matrix multiplyColByScalar(int col, int scalar) {
    Matrix result = this.getCopy();
    validateColIndex(col);
    for(int i = 0; i < rows; i++){
      result.data[i][col] = data[i][col] * scalar;
    }
    return result;
  }

  public Matrix multiplyColByScalar(int col, double scalar) {
    Matrix result = this.getCopy();
    validateColIndex(col);
    for(int i = 0; i < rows; i++){
      result.data[i][col] = data[i][col] * scalar;
    }
    return result;
  }

  public Matrix transpose() { //Possible error due to mutable array
    Matrix result = new Matrix(this.cols, this.rows);
    for (int i = 0; i < this.cols; i++) {
      for (int j = 0; j < this.rows; j++) {
        result.data[i][j] = data[j][i];
      }
    }
    return result;
 }

  public Matrix swapRow(int row1, int row2){
    Matrix result = this.getCopy();
    validateRowIndex(row1);
    validateRowIndex(row2);
    result.data[row1] = data[row2];
    result.data[row2] = data[row1];

    return result;
  }

  public Matrix swapCol(int col1, int col2){
    Matrix result = this.getCopy();
    validateColIndex(col1);
    validateColIndex(col2);
    for(int i = 0; i < rows; i++){
      result.data[i][col1] = data[i][col2];
      result.data[i][col2] = data[i][col1];
    }
    return result;
  }

  // ==================================
  // Matrix Calculation
  // ==================================



  // ==================================
  // Matrix Transformation
  // ==================================
  public double getDeterminantWithCofactor(){
    return getDeterminantWithCofactor(this);
  }

  public double getDeterminantWithCofactor(Matrix matrix){
    if(!(matrix.isSquare())){
      throw new IllegalArgumentException("getDeterminantWithCofactor() : Determinant could not be calculated (dimension incompatible)");
    }

    if(matrix.rows == 1){
      return matrix.data[0][0];
    }

    if(matrix.rows == 2){
      return (matrix.data[0][0] * matrix.data[1][1]) - (matrix.data[1][0] * matrix.data[0][1]);
    }

    Matrix temp = new Matrix(matrix.rows-1, matrix.cols-1);
    double determinant = 0;

    for(int i = 0; i < matrix.cols; i++){
      int row = 0;
      for(int j = 1; j < matrix.rows; j++) {
        int col = 0;
        for (int k = 0; k < matrix.cols; k++) {
          if (k != i) {
            temp.data[row][col] = matrix.data[j][k];
            col += 1;
          }
        }
        row += 1;
      }
      determinant += this.adjustPrecision(matrix.data[0][i] * getDeterminantWithCofactor(temp) * (i % 2 == 0 ? 1 : -1));
    }
    return determinant;
  }

  public double getDeterminantWithRowReduction(){
    if(!(this.isSquare())){
      throw new IllegalArgumentException("getDeterminantWithRowReduction() : Determinant could not be calculated (dimension incompatible)");
    }
    Matrix matrix = this.getCopy();

    boolean isSwap = false;
    int[] indexOfLeadingOne = new int[rows];
    double determinant = 1;

    for (int iterasi=0; iterasi<cols; iterasi++){
      for (int row=iterasi; row<rows; row++){
        if (row == iterasi){ //step 1: make leading 1
          indexOfLeadingOne[row] = cols;
          if(!isAllZero(matrix.data[row])){
            double pivot = 0;
            for (int col=0; col<cols; col++){//searching for pivot that !=0
              if (matrix.data[row][col] != 0){
                pivot = matrix.data[row][col];
                determinant *= pivot;
                indexOfLeadingOne[row] = col;
                break;
              }
            }
            for (int col = 0; col < cols; col++) { //divide all cols in that row with pivot
              matrix.data[row][col] =matrix.data[row][col] / pivot;
            }
          } else {
            return 0;
          }
        }
        else {//step 2: make 0 below leading 1 col
          double multiplier = matrix.data[row][indexOfLeadingOne[iterasi]];
          for (int col=0; col<cols; col++) {
            matrix.data[row][col] -= multiplier*matrix.data[iterasi][col];
          }
        }
      }
    }
    //sorting rows of matrix using bubble sort
    for (int i=0; i<rows-1; i++){
      for (int j=i+1; j<rows-1; j++){
        if (indexOfLeadingOne[i] > indexOfLeadingOne[j]){
          int temp = indexOfLeadingOne[i];
          isSwap = !isSwap;
          indexOfLeadingOne[i] = indexOfLeadingOne[j];
          indexOfLeadingOne[j] = temp;

          for (int col=0; col<cols; col++){//swap row
            double temp1 = matrix.data[i][col];
            matrix.data[i][col] = matrix.data[j][col];
            matrix.data[j][col] = temp1;
          }
        }
      }
    }
    determinant = adjustPrecision(determinant);

    return (isSwap? determinant * -1 : determinant);

  }

  public Matrix getRowEchelonForm (){

    Matrix matrix = this.getCopy();
    int[] indexOfLeadingOne = new int[rows];

    for (int iterasi=0; iterasi<cols; iterasi++){
      for (int row=iterasi; row<rows; row++){
        if (row == iterasi){ //step 1: make leading 1
          indexOfLeadingOne[row] = cols;
          if(!isAllZero(matrix.data[row])){
            double pivot = 0;
            for (int col=0; col<cols; col++){//searching for pivot that !=0
              if (matrix.data[row][col] != 0){
                pivot = matrix.data[row][col];
                indexOfLeadingOne[row] = col;
                break;
              }
            }
            for (int col = 0; col < cols; col++) { //divide all cols in that row with pivot
                matrix.data[row][col] = matrix.data[row][col] / pivot;
            }
          } else {
            break;
          }
        }
        else {//step 2: make 0 below leading 1 col
            double multiplier = matrix.data[row][indexOfLeadingOne[iterasi]];
            for (int col=0; col<cols; col++) {
              matrix.data[row][col] -= multiplier*matrix.data[iterasi][col];
          }
        }
      }
    }
    //sorting rows of matrix using bubble sort
    for (int i=0; i<rows-1; i++){
      for (int j=i+1; j<rows-1; j++){
        if (indexOfLeadingOne[i] > indexOfLeadingOne[j]){
          int temp = indexOfLeadingOne[i];
          indexOfLeadingOne[i] = indexOfLeadingOne[j];
          indexOfLeadingOne[j] = temp;

          for (int col=0; col<cols; col++){//swap row
            double temp1 = matrix.data[i][col];
            matrix.data[i][col] = matrix.data[j][col];
            matrix.data[j][col] = temp1;
          }
        }
      }
    }

    return matrix.getValidatedMatrixPrecision();
  }

  public Matrix getReducedRowEchelonForm() {

    Matrix matrix = this.getCopy();
    Matrix matrixReduced = matrix.getRowEchelonForm();

    for(int iterasi = rows-1; iterasi >=0; iterasi--){
      if (!isAllZero(matrixReduced.data[iterasi])) {
        int pivot = 0;
        for (int row = iterasi; row >= 0; row--) {
          if (row == iterasi) {

            for (int col = 0; col < cols; col++) {
              if (matrixReduced.data[row][col] == 1) {
                pivot = col;
                break;
              }
            }
          } else {
            double multiplier = matrixReduced.data[row][pivot];
            for (int col = 0; col < cols; col++) {
              matrixReduced.data[row][col] -= multiplier * matrixReduced.data[iterasi][col];
            }
          }
        }
      }
    }
    return matrixReduced.getValidatedMatrixPrecision();
  }

  public Matrix getAdjoint(){
    Matrix result = new Matrix(rows, cols);
    if(rows == 1 && cols == 1){
      result.setAllData(new double[][]{{1}});
      return result;
    }
    Matrix temp = new Matrix(rows - 1, cols - 1);
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < cols; j++){
        int row = 0;
        for(int k = 0; k < rows; k++){
          if(k == i){
            continue;
          }
          int col = 0;
          for(int l = 0; l < cols; l++){
            if(l == j){
              continue;
            }
              temp.data[row][col] = this.data[k][l];
              col++;
          }
          row++;
        }
        result.data[i][j] = temp.getDeterminantWithRowReduction(); // tried to use row reduction method but got wrong answer for some case
      }
    }
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < cols; j++){
        if((i + j) % 2 == 1){
          result.data[i][j] *= -1;
        }
      }
    }
    return result.transpose().getValidatedMatrixPrecision();
  }

  public Matrix getInverseWithAdjoint(){
    if(!isSquare()) {
      throw new IllegalArgumentException("getInverseWithAdjoint(): Matrix is not square");
    }
    double determinant = this.getDeterminantWithRowReduction();
    if (determinant == 0) {
      throw new ArithmeticException("getInverseWithAdjoint(): Matrix is singular (determinant is zero)");
    }

    return getAdjoint().divideByScalar(determinant);
  }

  public Matrix getInverseWithRowReduction(){
    if(!isSquare()) {
      throw new IllegalArgumentException("getInverseWithRowReduction() : Matrix is not square");
    }
    Matrix augmentedIdentity = new Matrix(rows, cols * 2);

    for(int i = 0; i < rows; i++){
      for(int j = 0; j < cols; j++){
        augmentedIdentity.data[i][j] = this.data[i][j];
      }
    }
    // Augment the identity to the matrix
    for(int i = 0; i < rows; i++){
      augmentedIdentity.data[i][i + cols] = 1;
    }

    Matrix reducedMatrix = augmentedIdentity.getReducedRowEchelonForm();

    // Check if the left half is the identity matrix
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (i == j && Math.abs(reducedMatrix.data[i][j] - 1) > 1e-10) {
          throw new ArithmeticException("getInverseWithRowReduction(): Matrix is not invertible");
        } else if (i != j && Math.abs(reducedMatrix.data[i][j]) > 1e-10) {
          throw new ArithmeticException("getInverseWithRowReduction(): Matrix is not invertible");
        }
      }
    }

    Matrix result = new Matrix(rows, cols);
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < cols; j++){
        result.data[i][j] = reducedMatrix.data[i][j + cols];
      }
    }
    return result.getValidatedMatrixPrecision();

  }

  // =================================
  // Matrix Properties
  // =================================

  public boolean isSquare(){
    return rows == cols;
  }

  private static boolean isAllZero(double[] row){
    int len = row.length;
    for (int i = 0; i < len; i++){
      if (row[i] != 0) return false;
    }
    return true;
  }

  private void validateRowIndex(int row) {
    if (row < 0 || row >= rows) {
      throw new IllegalArgumentException("validateRowIndex() : Row index " + row + " out of bounds.");
    }
  }

  private void validateColIndex(int col) {
    if (col < 0 || col >= cols) {
      throw new IllegalArgumentException("validateColIndex() : Column index " + col + " out of bounds.");
    }
  }

  private void validateDimensions(Matrix other) {
    if (this.rows != other.rows || this.cols != other.cols) {
      throw new IllegalArgumentException("validateDimensions() : Matrix dimensions do not match.");
    }
  }

  // =================================
  // Utility Methods
  // =================================

  public Matrix getCopy() {
    Matrix copyMatrix = new Matrix(this.rows, this.cols);
    for (int i = 0; i < this.rows; i++) {
      System.arraycopy(this.data[i], 0, copyMatrix.data[i], 0, this.cols);
    }
    return copyMatrix;
  }

  private double adjustPrecision(double value) {
    int decimalPlaces = 3;
    // Scale factor for decimal places, e.g., 100.0 for 2 decimal places, 1000.0 for 3 decimal places
    double scaleFactor = Math.pow(10, decimalPlaces);

    // Round the value to the specified number of decimal places
    double adjusted = Math.round(value * scaleFactor) / scaleFactor;

    // If the result is very close to an integer, round it to the nearest integer
    if (Math.abs(adjusted - Math.round(adjusted)) < 2 * Math.pow(10, -1 * decimalPlaces)) {
      adjusted = Math.round(adjusted);
    }

    return adjusted;
  }

  public Matrix getValidatedMatrixPrecision() {
    Matrix result = new Matrix(this.rows, this.cols);
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        result.data[i][j] = adjustPrecision(this.data[i][j]);
      }
    }
    return result;
  }

  public void printMatrix() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        System.out.print(data[i][j] + " ");
      }
      System.out.println();
    }
  }

  public void inputMatrix(){
    Scanner scanner = new Scanner(System.in);
    for(int i = 0; i < rows; i++){
      String[] rowValues = scanner.nextLine().split(" ");
      if (rowValues.length != cols) {
        throw new IllegalArgumentException("Input row has incorrect number of columns.");
      }
      for(int j = 0; j < cols; j++){
        data[i][j] = Double.parseDouble(rowValues[j]);
      }
    }
  }

  public void inputMatrixFromFile(String fileName){
    try{
      File file = new File(fileName);
      Scanner scanner = new Scanner(file);

      // Validate dimensions in the file
      int fileRows = 0;
      int fileCols = 0;
      if (scanner.hasNextLine()) {
        String[] firstLine = scanner.nextLine().split(" ");
        fileCols = firstLine.length;
        fileRows = (int) scanner.tokens().count() / fileCols + 1;
        scanner.close();
        scanner = new Scanner(file); // Reopen scanner to reset its position
      }

      // Check dimension match
      if (fileRows != this.rows || fileCols != this.cols) {
        throw new IllegalArgumentException("File matrix dimensions (" + fileRows + "x" + fileCols +
                ") do not match the initialized matrix (" + this.rows + "x" + this.cols + ").");
      }

      int row = 0;
      while (scanner.hasNextLine() && row < rows) {
        String line = scanner.nextLine();
        String[] value = line.split(" ");
        for (int col = 0; col < value.length && col < cols; col++) {
          data[row][col] = Double.parseDouble(value[col]);
        }
        row++;
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + fileName);
    }

  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Matrix other))
      return false;
    if (this.data.length != other.data.length || this.data[0].length != other.data[0].length) {
      return false;
    }
    return Arrays.deepEquals(this.data, other.data);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(data);
  }

}
