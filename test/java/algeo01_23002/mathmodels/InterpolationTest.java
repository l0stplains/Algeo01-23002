package algeo01_23002.mathmodels;

import algeo01_23002.types.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import algeo01_23002.solvers.LinearSystemSolver;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class InterpolationTest {
        private Matrix matrix;

        @BeforeEach
        public void setUp() {
            matrix = new Matrix(2,3);
            matrix.setAllData(new double[][] {
                    {1.0, 2.0, 3.0},
                    {4.0, 5.0, 6.0}
            });

        }

    @Nested
    @DisplayName("Bicubic Spline Interpolation Test")
    class BiucbicSplineInterpolationTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("bicubicSplineInterpolationTestCases")
        public void testBicubicSplineInterpolation(String description, Matrix expected) {
            assertArrayEquals(expected.getAllData(), Interpolation.bicubicSplineInterpolation().getAllData(), "Bicubic Spline Interpolation failed for " + description);
        }
        static Stream<Object[]> bicubicSplineInterpolationTestCases() {
            return Stream.of(
                    new Object[][]{new Object[]{"Test bicubic spline interpolation with predefined matrix",
                            createMatrix(new double[][]{
                                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                    {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
                                    {0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3},
                                    {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0},
                                    {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3},
                                    {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0},
                                    {0, 0, 0, 0, 0, 1, 2, 3, 0, 2, 4, 6, 0, 3, 6, 9},
                            })
                    }}
            );
        }
    }

    // Helper method to create matrices using data arrays
    private static Matrix createMatrix(double[][] data) {
        Matrix matrix = new Matrix(data.length, data[0].length);
        matrix.setAllData(data);
        return matrix;
    }
}

