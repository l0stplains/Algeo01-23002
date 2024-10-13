package algeo01_23002.solvers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import algeo01_23002.types.Matrix;

public class LinearSystemSolverTest {
    private Matrix matrix;

    @BeforeEach
    public void setUp() {
        Matrix matrix = new Matrix(2, 3);
        matrix.setData(new double[][] {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        });

    }

    @Test
    public void testCramersRule() {
        Matrix matrix1 = new Matrix(3,3);
        Matrix constant = new Matrix(1,3);
        Matrix solution = new Matrix(1,3);
        matrix1.inputMatrixFromFile("test/resources/CramersTest.txt");
        constant.inputMatrixFromFile("test/resources/Constant.txt");


        solution = LinearSystemSolver.cramersRule(matrix1, constant);
        double[][] expectedData = {
                {1.0, 3.0, -2.0}
        };

        assertArrayEquals(expectedData, solution.getData());
    }
}
