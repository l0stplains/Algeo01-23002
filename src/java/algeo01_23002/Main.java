package algeo01_23002;
import algeo01_23002.types.Matrix;
import algeo01_23002.solvers.LinearSystemSolver;


public class Main {

    public static void main(String[] args) {
        Matrix matrix = new Matrix(4,5);
        matrix.inputMatrix();


        String[][] res = LinearSystemSolver.gaussianElimination(matrix);
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }
    }

}
