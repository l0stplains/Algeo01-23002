package algeo01_23002.library;
import java.util.Arrays;


public class Matrix {
  private double[][] data;

  public Matrix(double[][] data) {
    this.data = data;
  }

  public double[][] getData() {
    return data;
  }

  public void setData(double[][] data) {
    this.data = data;
  }

  public void println() {
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        System.out.print(data[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static Matrix add(Matrix a, Matrix b) {
    double[][] dataA = a.getData();
    double[][] dataB = b.getData();
    double[][] dataC = new double[dataA.length][dataA[0].length];
    for (int i = 0; i < dataA.length; i++) {
      for (int j = 0; j < dataA[0].length; j++) {
        dataC[i][j] = dataA[i][j] + dataB[i][j];
      }
    }
    return new Matrix(dataC);
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
