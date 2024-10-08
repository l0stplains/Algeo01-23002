package algeo01_23002.core;

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
//        5// Capture the output of printMatrix
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

    }
    @Test
    public void testSubtractionMatrix() {

    }
    @Test
    public void testMultiplicationMatrix() {
        // Arrange: Create two matrices with known values
        Matrix matrix1 = new Matrix(2, 3);
        matrix1.getData()[0] = new double[]{1, 2, 3};
        matrix1.getData()[1] = new double[]{4, 5, 6};

        Matrix matrix2 = new Matrix(3, 2);
        matrix2.getData()[0] = new double[]{7, 8};
        matrix2.getData()[1] = new double[]{9, 10};
        matrix2.getData()[2] = new double[]{11, 12};

        // Expected result of the multiplication
        Matrix expected = new Matrix(2, 2);
        expected.getData()[0] = new double[]{58, 64};
        expected.getData()[1] = new double[]{139, 154};

        // Act: Perform the multiplication
        Matrix result = matrix1.multiplication(matrix2);

        // Assert: Check if the result matches the expected matrix
        assertEquals(expected, result, "Matrix multiplication result is incorrect.");
    }
    @Test
    public void testScalarMultiplicationMatrix() {

    }
    @Test
    public void testTransposeMatrix() {

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
