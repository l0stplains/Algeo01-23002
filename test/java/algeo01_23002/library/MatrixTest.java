import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import algeo01_23002.library.Matrix;

public class MatrixTest {
    private Matrix matrix;

    @BeforeEach
    public void setUp() {
        matrix = new Matrix(new double[][] {{1, 2}, {3, 4}});
    }

    @Test
    public void testAddition() {
        Matrix other = new Matrix(new double[][] {{5, 6}, {7, 8}});
        Matrix expected = new Matrix(new double[][] {{6, 8}, {10, 12}});
        assertEquals(expected, Matrix.add(other, matrix));
    }


    // Add more tests for other functionalities
}
