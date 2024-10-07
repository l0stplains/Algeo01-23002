package algeo01_23002.library;
import java.util.Arrays;


public class Matrix {
  private double[][] data;





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
