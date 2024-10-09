package algeo01_23002.core;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class Matrix {
  private double[][] data;
  private int rows;
  private int cols;

  // Constructor
  public Matrix(int rows, int cols) {
    if(rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Invalid matrix size. Rows and columns must be positive.");
    }
    this.rows = rows;
    this.cols = cols;
    this.data = new double[rows][cols];
  }

  // Selector
  public int getRows() { return rows; }
  public int getCols() { return cols; }
  public double[][] getData() { return data; }

  public void setData(double[][] data) {
    if(data.length == 0 || data[0].length == 0){
      throw new IllegalArgumentException("Matrix is empty!");
    }
    this.data = data;
    this.rows = data.length;
    this.cols = data[0].length;
  }

  // Operations
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

  public void multiplyColByScalar(int col, int scalar) {
    validateColIndex(col);
    for(int i = 0; i < rows; i++){
      data[i][col] = data[i][col] * scalar;
    }
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

  // Input and Output
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

  public void printMatrix() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        System.out.print(data[i][j] + " ");
      }
      System.out.println();
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

  // Utility Methods
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Matrix other))
      return false;
    return Arrays.deepEquals(this.data, other.data);
  }
}
