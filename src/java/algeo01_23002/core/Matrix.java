package algeo01_23002.core;
import java.util.Arrays;


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


  // Input and Output


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
