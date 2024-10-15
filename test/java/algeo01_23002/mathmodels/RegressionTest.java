package algeo01_23002.mathmodels;

import algeo01_23002.types.Matrix;
import algeo01_23002.types.LinearSystemSolution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;


import static org.junit.jupiter.api.Assertions.*;

public class RegressionTest {
    private Matrix matrix;

    @BeforeEach
    public void setUp() {
        matrix = new Matrix(2,3);
        matrix.setAllData(new double[][] {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        });

    }

    @Test
    public void testMultipleQuadraticRegression(){
        Matrix matrix1 = new Matrix(3,3);

        matrix1.setAllData(new double[][] {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0}
        });

        Matrix temp = Regression.multipleQuadraticRegression(matrix1);
        temp.printMatrix();
        double[][] expectedData ={
                {3.0,12.0, 15.0, 66.0, 93.0,78.0, 18.0},
                {12.0,66.0, 78.0,408.0,552.0,474.0,90.0},
                {15.0,78.0,93.0,474.0,645.0,552.0,108.0},
                {66.0,408.0,474.0,2658.0,3540.0,3066.0,540.0},
                {93.0,552.0,645.0,3540.0,4737.0,4092.0,738.0},
                {78.0,474.0,552.0,3066.0,4092.0,3540.0, 630.0}
        };
        assertArrayEquals(expectedData, temp.getAllData());

    }
}
