package algeo01_23002.core;
import java.util.Arrays;
import java.util.Scanner;


public class Matrix {
  private double[][] data;
  private int rows;
  private int cols;

  // Constructor
  public Matrix(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.data = new double[rows][cols];
  }

  // Selector
  public int getRows() { return rows; }
  public int getCols() { return cols; }
  public double[][] getData() { return data; }

  // Operations
  public void transpose() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        data[i][j] = data[j][i];
      }
    }
  }

  public void addition(Matrix other) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        data[i][j] = data[i][j] + other.data[i][j];
      }
    }
  }

  public void subtraction(Matrix other) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        data[i][j] = data[i][j] - other.data[i][j];
      }
    }
  }

  public void scalarMultiplication(int scalar) {
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < cols; j++) {
        data[i][j] = data[i][j] * scalar;
      }
    }
  }

  // Input and Output
  public void inputMatrix(){
    if (rows == 0 || cols == 0) {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Masukkan ukuran matrix, baris: ");
      this.rows = scanner.nextInt();
      System.out.print("Masukkan ukuran matrix, kolom: ");
      this.cols = scanner.nextInt();
      this.data = new double[rows][cols];
      scanner.nextLine(); // Consume newline
    }

    Scanner scanner = new Scanner(System.in);
    System.out.println("Masukkan elemen matrix (dipisah spasi):");
    for(int i = 0; i < rows; i++){
      String[] rowValues = scanner.nextLine().split(" ");
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Matrix))
      return false;
    Matrix other = (Matrix) obj;
    return Arrays.deepEquals(this.data, other.data);
  }
}
