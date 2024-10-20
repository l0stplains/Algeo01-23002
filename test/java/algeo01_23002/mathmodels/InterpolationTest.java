package algeo01_23002.mathmodels;

import algeo01_23002.types.Matrix;
import algeo01_23002.types.PolynomialResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import algeo01_23002.solvers.LinearSystemSolver;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

     /*
    @Nested
    @DisplayName("Bicubic Spline Interpolation Test")
    class BicubicSplineInterpolationTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("bicubicSplineInterpolationTestCases")
        public void testBicubicSplineInterpolation(String description, Matrix matrix, double x, double y, double expected) {
            assertEquals(expected, Interpolation.bicubicSplineInterpolation(matrix, x, y), "Bicubic Spline Interpolation failed for " + description);
        }
        static Stream<Object[]> bicubicSplineInterpolationTestCases() {
            return Stream.of(
                    new Object[]{"Predefined matrix (1)",
                            createMatrix(new double[][]{
                                    {21, 98, 125, 153},
                                    {51, 101, 161, 59},
                                    {0, 42, 72, 210},
                                    {16, 12, 81, 96}
                            }), 0, 0, 21
                    },
                    new Object[]{"Predefined matrix (2)",
                    createMatrix(new double[][]{
                            {21, 98, 125, 153},
                            {51, 101, 161, 59},
                            {0, 42, 72, 210},
                            {16, 12, 81, 96}
                    }), 0.5, 0.5, 68.3515625
                    },
                    new Object[]{"Predefined matrix (2)",
                            createMatrix(new double[][]{
                                    {21, 98, 125, 153},
                                    {51, 101, 161, 59},
                                    {0, 42, 72, 210},
                                    {16, 12, 81, 96}
                            }), 0.25, 0.25, 82.4072265625
                    },
                    new Object[]{"Predefined matrix (2)",
                            createMatrix(new double[][]{
                                    {21, 98, 125, 153},
                                    {51, 101, 161, 59},
                                    {0, 42, 72, 210},
                                    {16, 12, 81, 96}
                            }), 0.1, 0.9, 91.79260049999999
                    }

            );
        }
    }

    */

    @Nested
    @DisplayName("Polynomial Interpolation Test")
    class PolynomialInterpolationTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("polynomialInterpolationTestCases")
        public void testPolynomialInterpolation(String description, Matrix x, Matrix y, Matrix expected){
            assertArrayEquals(expected.getAllData(), Interpolation.polynomialInterpolation(x, y).getCoefficients().getAllData(), "Polynomial Interpolation failed for " + description);
        }
        static Stream<Object[]> polynomialInterpolationTestCases() {
            return Stream.of(
                    new Object[]{"Case study matrix (1)",
                            createMatrix(new double[][]{
                                    {0.1, 0.3, 0.5, 0.7, 0.9, 1.1, 1.3}
                            }),
                            createMatrix(new double[][]{
                                    {0.003, 0.067, 0.148, 0.248, 0.370, 0.518, 0.697}
                            }),
                            createMatrix(new double[][]{
                                    {-0.023},
                                    {0.24},
                                    {0.197},
                                    {0.0},
                                    {0.025},
                                    {0.0},
                                    {0.0}
                            }),

                    },
                    new Object[]{"Case study matrix (2)",
                            createMatrix(new double[][]{
                                    {6.567, 7,7.258, 7.451, 7.548, 7.839, 8.161, 8.484, 8.709, 9}
                            }),
                            createMatrix(new double[][]{
                                    {12624, 21807, 38391, 54517, 51952, 28228, 35764, 20813, 12408, 10534}
                            }),
                            createMatrix(new double[][]{
                                    {7.201923378231031E12},
                                    {-9.350657413581434E12},
                                    {5.334854479627366E12},
                                    {-1.756937110879606E12},
                                    {3.68569416137653E11},
                                    {-5.1133753037143E10},
                                    {4.695931102116E9},
                                    {-2.7547953399E8},
                                    {9372933.58},
                                    {-140995.051}
                            }),

                    }
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

