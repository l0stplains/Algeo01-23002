package algeo01_23002.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixTest {
    private Matrix matrix;

    @BeforeEach
    public void SetUp() {
        matrix = new Matrix(2,3);
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
        // Capture the output of printMatrix
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        String input = "1.0 2.0 3.0 \n4.0 5.0 6.0 \n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Set some data to the matrix for printing
        matrix.inputMatrix(); // Input values here as needed
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
    public void testAdditionMatrix() {
        String input = "1.0 2.0 3.0\n4.0 5.0 6.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        matrix.inputMatrix();

        matrix.addition(matrix);
        double[][] expectedData = {
                {2.0, 4.0, 6.0},
                {8.0, 10.0, 12.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix));

    }
    @Test
    public void testSubtractionMatrix() {
        String input = "1.0 2.0 3.0\n4.0 5.0 6.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        matrix.inputMatrix();

        matrix.subtraction(matrix);
        double[][] expectedData = {
                {0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix));
    }
    @Test
    public void testScalarMultiplicationMatrix() {
        String input = "1.0 2.0 3.0\n4.0 5.0 6.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        matrix.inputMatrix();

        matrix.scalarMultiplication(3);
        double[][] expectedData = {
                {3.0, 6.0, 9.0},
                {12.0, 15.0, 18.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix));
    }
    @Test
    public void testTransposeMatrix() {
        String input = "1.0 2.0 3.0\n4.0 5.0 6.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        matrix.inputMatrix();

        matrix.transpose(); // Test mutable for transpose
        matrix.addition(matrix);

        double[][] expectedData = {
                {2.0,8.0},
                {4.0,10.0},
                {6.0,12.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix));
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
