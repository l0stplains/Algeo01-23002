package algeo01_23002;

import algeo01_23002.library.Matrix;

public class Main {

    public static void main(String[] args) {
        double[][] data = {{1, 2}, {3, 4}};
        double[][] data2 = {{5, 6}, {7, 8}};
        Matrix matrix = new Matrix(data);
        Matrix matrix2 = new Matrix(data2);
        matrix.println();

        Matrix matrix3 = Matrix.add(matrix, matrix2);
        matrix3.println();
    }
}
