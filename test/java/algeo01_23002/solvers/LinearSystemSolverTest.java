package algeo01_23002.solvers;

import algeo01_23002.types.LinearSystemSolution;
import algeo01_23002.types.UniqueSolution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import algeo01_23002.types.Matrix;

public class LinearSystemSolverTest {
    private Matrix matrix;

    @BeforeEach
    public void setUp() {
        Matrix matrix = new Matrix(2, 3);
        matrix.setAllData(new double[][] {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        });

    }

    @Test
    public void testCramersRule() {
        Matrix matrix1 = new Matrix(3,3);
        Matrix constant = new Matrix(1,3);
        matrix1.inputMatrixFromFile("test/resources/CramersTest.txt");
        constant.inputMatrixFromFile("test/resources/Constant.txt");

        LinearSystemSolver solver = new LinearSystemSolver();
        LinearSystemSolution solution = solver.cramersRule(matrix1, constant);
        double[][] expectedData = {
                {1.0},
                {3.0},
                {-2.0}
        };

        if(solution instanceof UniqueSolution) {
            assertArrayEquals(expectedData, ((UniqueSolution) solution).getSolution().getAllData());
        }
    }

    @Test
    public void testInverseMethod(){
        LinearSystemSolver solver = new LinearSystemSolver();
        Matrix matrix1 = new Matrix(3,3);
        Matrix constant = new Matrix(1,3);
        matrix1.inputMatrixFromFile("test/resources/CramersTest.txt");
        constant.inputMatrixFromFile("test/resources/Constant.txt");

        Matrix coba = new Matrix(3,4);

        for(int i = 0; i < coba.getRowsCount(); i++){
            for(int j = 0; j < coba.getColsCount(); j++){
                if(j == coba.getColsCount() - 1){
                    coba.setData(i, j, constant.getData(0, i));
                } else {
                    coba.setData(i, j, matrix1.getData(i, j));
                }
            }
        }

        LinearSystemSolution solution = solver.inverseMethod(coba);

        double[][] expectedData = {
                {1.0},
                {3.0},
                {-2.0}
        };

        if(solution instanceof UniqueSolution) {
            assertArrayEquals(expectedData, ((UniqueSolution) solution).getSolution().getAllData());
        }
    }
}
