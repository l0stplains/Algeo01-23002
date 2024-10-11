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
      throw new IllegalArgumentException("Invalid matrix size. Rows and columns must be positive.");
    }
    this.rows = rows;
    this.cols = cols;
    this.data = new double[rows][cols];
  }

  // Getters
  // ================================
  public int getRows() { return rows; }
  public int getCols() { return cols; }
  public double[][] getData() { return data; }

  // Setters
  // ================================
  public void setData(double[][] data) {
    if(data.length == 0 || data[0].length == 0){
      throw new IllegalArgumentException("Matrix is empty!");
    }
    this.data = data;
    this.rows = data.length;
    this.cols = data[0].length;
  }

  // =================================
  // Matrix Operations
  // =================================

  public void add(Matrix other) {
    validateDimensions(other);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        data[i][j] = data[i][j] + other.data[i][j];
      }
    }
  }

  public void subtract(Matrix other) {
    validateDimensions(other);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        data[i][j] = data[i][j] - other.data[i][j];
      }
    }
  }

  public void multiplyByScalar(int scalar) {
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < cols; j++) {
        data[i][j] = data[i][j] * scalar;
      }
    }
  }

  public void multiplyByScalar(double scalar) {
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < cols; j++) {
        data[i][j] = data[i][j] * scalar;
      }
    }
  }

  public void divideByScalar(int scalar) {
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < cols; j++) {
        data[i][j] = data[i][j] / scalar;
      }
    }
  }

  public void divideByScalar(double scalar) {
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < cols; j++) {
        data[i][j] = data[i][j] / scalar;
      }
    }
  }

  public Matrix multiplyByMatrix(Matrix other) {
    if(other.cols != rows){
      throw new IllegalArgumentException("Matrix multiplication could not be performed (dimension incompatible)");
    }
    int otherCols = other.getCols();
    Matrix temp = new Matrix(rows, otherCols);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < otherCols; j++) {
        for (int k = 0; k < cols; k++) {
          temp.getData()[i][j] += data[i][k] * other.getData()[k][j];
        }
      }
    }
    return temp;
  }

  public void multiplyRowByScalar(int row, int scalar) {
    validateRowIndex(row);
    for(int i = 0; i < cols; i++){
      data[row][i] = data[row][i] * scalar;
    }
  }


  public void multiplyRowByScalar(int row, double scalar) {
    validateRowIndex(row);
    for(int i = 0; i < cols; i++){
      data[row][i] = data[row][i] * scalar;
    }
  }

  public void multiplyColByScalar(int col, int scalar) {
    validateColIndex(col);
    for(int i = 0; i < rows; i++){
      data[i][col] = data[i][col] * scalar;
    }
  }

  public void multiplyColByScalar(int col, double scalar) {
    validateColIndex(col);
    for(int i = 0; i < rows; i++){
      data[i][col] = data[i][col] * scalar;
    }
  }

  public void transpose() { //Possible error due to mutable array
    Matrix matrixTransposed = new Matrix(cols, rows);
    for (int i = 0; i < cols; i++) {
      for (int j = 0; j < rows; j++) {
        matrixTransposed.data[i][j] = data[j][i];
      }
    }
    this.data = matrixTransposed.data;
    this.rows = matrixTransposed.rows;
    this.cols = matrixTransposed.cols;
  }

  public void swapRow(int row1, int row2){
    validateRowIndex(row1);
    validateRowIndex(row2);
    double[] temp;
    temp = data[row1];
    data[row1] = data[row2];
    data[row2] = temp;
  }

  public void swapCol(int col1, int col2){
    validateColIndex(col1);
    validateColIndex(col2);
    for(int i = 0; i < rows; i++){
      double temp = data[i][col1];
      data[i][col1] = data[i][col2];
      data[i][col2] = temp;
    }
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
      throw new IllegalArgumentException("Determinant could not be calculated (dimension incompatible)");
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
      determinant += matrix.data[0][i] * getDeterminantWithCofactor(temp) * (i % 2 == 0 ? 1 : -1);
    }
    return determinant;
  }

  private static boolean isAllZero(double[] row){
    int len = row.length;
    for (int i = 0; i < len; i++){
      if (row[i] != 0) return false;
    }
    return true;
  }

  public Matrix getRowEchelonForm (){
    double[][] mat = data;
    int[] indexOfLeadingOne = new int[rows];
    for (int iterasi=0; iterasi<cols; iterasi++){
      for (int row=iterasi; row<rows; row++){
        if (row == iterasi){ //step 1: make leading 1
          indexOfLeadingOne[row] = cols;
          if(!isAllZero(mat[row])){
            double pivot = 0;
            for (int col=0; col<cols; col++){//searching for pivot that !=0
              if (mat[row][col] != 0){
                pivot = mat[row][col];
                indexOfLeadingOne[row] = col;
                break;
              }
            }

            for (int col=0; col<cols; col++) { //divide all cols in that row eith zero
              mat[row][col] /= pivot;
            }
          } else {
            break;
          }
        }
        else {//step 2: make 0 below leading 1 col
            double multiplier = mat[row][indexOfLeadingOne[iterasi]];
            for (int col=0; col<cols; col++) {
              mat[row][col] -= multiplier*mat[iterasi][col];
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
            double temp1 = mat[i][col];
            mat[i][col] = mat[j][col];
            mat[j][col] = temp1;
          }
        }
      }
    }
    Matrix matrix = new Matrix(rows, cols);
    matrix.setData(mat);
    return matrix;
  }

  public Matrix getReducedRowEchelonForm() {
    double[][] mat = data;

    for (int iterasi=0; iterasi<cols; iterasi++){
      //step 1: make leading 1
      if (iterasi < rows - 1 && isAllZero(mat[iterasi])){ //if all cols in that row is zero
        for (int col=0; col<cols; col++){ //swap with next row that not all zero
          double temp = mat[iterasi][col];
          mat[iterasi][col] = mat[iterasi+1][col];
          mat[iterasi+1][col] = temp;
        }
      }

      double pivot = mat[iterasi][iterasi];
      if (pivot !=0){ // if the pivot is not zero
        for (int col=0; col<cols; col++) { //then divide all cols in that row with itself
          mat[iterasi][col] /= pivot;
        }
      }
    }

    Matrix matrix = new Matrix(rows, cols);
    matrix.setData(mat);
    return matrix;
  }

  public Matrix getAdjoint(){
    Matrix result = new Matrix(rows, cols);
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
        result.data[i][j] = getDeterminantWithCofactor(temp);
      }
    }
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < cols; j++){
        if((i + j) % 2 == 1){
          result.data[i][j] *= -1;
        }
      }
    }
    return result;
  }

  public Matrix getInverseWithAdjoint(){
    Matrix adjoint =  getAdjoint();
    adjoint.transpose();
    double determinant = getDeterminantWithCofactor();
    adjoint.divideByScalar(determinant);
    return adjoint;
  }

  // =================================
  // Matrix Properties
  // =================================

  private boolean isSquare(){
    return rows == cols;
  }

  private void validateRowIndex(int row) {
    if (row < 0 || row >= rows) {
      throw new IllegalArgumentException("Row index " + row + " out of bounds.");
    }
  }

  private void validateColIndex(int col) {
    if (col < 0 || col >= cols) {
      throw new IllegalArgumentException("Column index " + col + " out of bounds.");
    }
  }

  private void validateDimensions(Matrix other) {
    if (this.rows != other.rows || this.cols != other.cols) {
      throw new IllegalArgumentException("Matrix dimensions do not match.");
    }
  }

  // =================================
  // Utility Methods
  // =================================

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
    return Arrays.deepEquals(this.data, other.data);
  }
}
