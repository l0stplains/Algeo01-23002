package algeo01_23002;
import algeo01_23002.mathmodels.Regression;
import algeo01_23002.types.Matrix;
import algeo01_23002.solvers.LinearSystemSolver;
import algeo01_23002.mathmodels.Interpolation;

public class Main {

    public static void main(String[] args) {
        Matrix matrix = new Matrix(6,4);
        matrix.inputMatrix();


        Matrix X = Regression.multipleQuadraticRegression(matrix);
        X.printMatrix();
    }

}
