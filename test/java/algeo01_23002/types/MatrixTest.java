package algeo01_23002.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;


import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {
    private Matrix matrix;

    @BeforeEach
    public void setUp() {
        matrix = new Matrix(2,3);
        matrix.setData(new double[][] {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        });

    }

    //  Input / Output Test
    @Test
    public void testInputMatrix() {
        // Simulate user input
        String input = "1.0 2.0 3.0\n4.0 5.0 6.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        matrix.inputMatrix();

        double[][] expectedData = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };

        assertArrayEquals(expectedData, getMatrixData(matrix));
    }

    @Test
    public void testPrintMatrix() {
//        5// Capture the output of printMatrix
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        matrix.printMatrix(); // Call the printMatrix method

        // Reset the standard output
        System.setOut(originalOut);
        String actualOutput = outputStream.toString().replace("\r\n", "\n").replace("\r", "\n");
        // Check the printed output
        String expectedOutput = "1.0 2.0 3.0 \n4.0 5.0 6.0 \n"; // Output based on your matrix
        assertEquals(expectedOutput, actualOutput);
    }

    //  Operation Test
    @Test
    public void testAddMatrix() {
        matrix.add(matrix);
        double[][] expectedData = {
                {2.0, 4.0, 6.0},
                {8.0, 10.0, 12.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix));

    }
    @Test
    public void testSubtractMatrix() {

        matrix.subtract(matrix);
        double[][] expectedData = {
                {0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix));
    }
    @Test
    public void testMultiplyByMatrixMatrix() {
        String input1 = "1.0 2.0 3.0\n4.0 5.0 6.0\n";
        System.setIn(new ByteArrayInputStream(input1.getBytes()));
        Matrix matrix1 = new Matrix(2, 3);
        matrix1.inputMatrix();

        String input2 = "7.0 8.0\n9.0 10.0\n11.0 12.0\n";
        System.setIn(new ByteArrayInputStream(input2.getBytes()));
        Matrix matrix2 = new Matrix(3, 2);
        matrix2.inputMatrix();


        double[][] expected = {
                {58, 64},
                {139, 154}
        };

        Matrix result = matrix1.multiplyByMatrix(matrix2);

        assertArrayEquals(expected, result.getData(), "Matrix multiplication result is incorrect.");
    }
    @Test
    public void testMultiplyByScalarMatrix() {

        matrix.multiplyByScalar(3);
        double[][] expectedData = {
                {3.0, 6.0, 9.0},
                {12.0, 15.0, 18.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix));
    }
    @Test
    public void testTransposeMatrix() {

        matrix.transpose(); // Test mutable for transpose
        matrix.add(matrix);

        double[][] expectedData = {
                {2.0,8.0},
                {4.0,10.0},
                {6.0,12.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix));
    }

    @Test
    public void testInputMatrixFromFile(){

        Matrix matrix1 = new Matrix(3,3);
        matrix1.inputMatrixFromFile("test/resources/FileTest.txt");

        double[][] expectedData = {
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };

        assertArrayEquals(expectedData, matrix1.getData(), "Parsed matrix is incorrect");
    }

    @Test
    // testGetDeterminantWithCofactor
    public void testGetDeterminantWithCofactor(){

        Matrix matrix1 = new Matrix(3,3);
        matrix1.inputMatrixFromFile("test/resources/FileTest.txt");

        double determinant = matrix1.getDeterminantWithCofactor();

        double expectedValue = 0;

        assertEquals(expectedValue,determinant);
    }

    // Test multiplyRowByScalar
    @Test
    public void testMultiplyRowByScalar() {

        matrix.multiplyRowByScalar(0, 2);

        double[][] expectedData = {
                {2.0, 4.0, 6.0},
                {4.0, 5.0, 6.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix), "Result of multiplying row by scalar is incorrect.");
    }

    // Test multiplyColumnByScalar
    @Test
    public void testMultiplyColByScalar() {

        matrix.multiplyColByScalar(1, 3);

        double[][] expectedData = {
                {1.0, 6.0, 3.0},
                {4.0, 15.0, 6.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix), "Result of multiplying column by scalar is incorrect.");
    }

    // Test swapRow
    @Test
    public void testSwapRow() {

        matrix.swapRow(0, 1);

        double[][] expectedData = {
                {4.0, 5.0, 6.0},
                {1.0, 2.0, 3.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix), "Result of swapping rows is incorrect.");
    }

    // Test swapCol
    @Test
    public void testSwapCol() {

        matrix.swapCol(0, 2);

        double[][] expectedData = {
                {3.0, 2.0, 1.0},
                {6.0, 5.0, 4.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix), "Result of swapping columns is incorrect.");
    }

    //  Helper
    private double[][] getMatrixData(Matrix matrix) {
    try {
        Field field = Matrix.class.getDeclaredField("data");
        field.setAccessible(true); // Make the private field accessible
        return (double[][]) field.get(matrix);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

}
