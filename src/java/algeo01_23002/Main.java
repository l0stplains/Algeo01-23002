package algeo01_23002;
import algeo01_23002.types.Matrix;
import algeo01_23002.solvers.LinearSystemSolver;


public class Main {

    public static void main(String[] args) {
        Matrix matrix = new Matrix(3,4);
        matrix.inputMatrix();

        Matrix res = LinearSystemSolver.gaussJordanElimination(matrix);
        res.printMatrix();
    }

}
