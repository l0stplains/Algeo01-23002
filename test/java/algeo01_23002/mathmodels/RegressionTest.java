package algeo01_23002.mathmodels;

import algeo01_23002.solvers.LinearSystemSolver;
import algeo01_23002.types.Matrix;
import algeo01_23002.types.LinearSystemSolution;
import algeo01_23002.types.NoSolution;
import algeo01_23002.types.UniqueSolution;
import algeo01_23002.types.ParametricSolution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


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

    @Nested
    @DisplayName("Multiple Quadratic Regression Test")
    class MultipleQuadraticRegressionTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("multipleQuadraticRegressionTestCases")

        public void testMultipleQuadraticRegression(String description, Matrix matrix, Matrix expected) {
            LinearSystemSolver solver = new LinearSystemSolver();
            LinearSystemSolution actualSolution = Regression.multipleQuadraticRegression(matrix);
            LinearSystemSolution expectedSolution = solver.gaussJordanElimination(expected);

            if (actualSolution instanceof NoSolution) {
                System.out.println("No solution found");
                System.out.println(actualSolution); // No expectedSolution exists

            } else if (actualSolution instanceof UniqueSolution) {
                Matrix expectedUniqueSolution = ((UniqueSolution) expectedSolution).getSolution();
                Matrix actualUniqueSolution = ((UniqueSolution) actualSolution).getSolution();

                actualUniqueSolution.printMatrix();

                assertArrayEquals(expectedUniqueSolution.getAllData(),
                        actualUniqueSolution.getAllData(),
                        "Multiple Quadratic Regression failed for " + description);

            } else if (actualSolution instanceof ParametricSolution) {
                System.out.println("Parametric Solution found");
                System.out.println(actualSolution);
            }
        }
        static Stream<Object[]> multipleQuadraticRegressionTestCases() {
            return Stream.of(
                    new Object[]{
                            "3x3 matrix example",
                            createMatrix(new double[][]{
                                    {1.0, 2.0, 3.0},
                                    {4.0, 5.0, 6.0},
                                    {7.0, 8.0, 9.0}
                            }),
                            createMatrix(new double[][]{
                                    {3.0, 12.0, 15.0, 66.0, 93.0, 78.0, 18.0},
                                    {12.0, 66.0, 78.0, 408.0, 552.0, 474.0, 90.0},
                                    {15.0, 78.0, 93.0, 474.0, 645.0, 552.0, 108.0},
                                    {66.0, 408.0, 474.0, 2658.0, 3540.0, 3066.0, 540.0},
                                    {93.0, 552.0, 645.0, 3540.0, 4737.0, 4092.0, 738.0},
                                    {78.0, 474.0, 552.0, 3066.0, 4092.0, 3540.0, 630.0}
                            })
                            },
                            new Object[]{
                                    "1 variable example",
                                    createMatrix(new double[][]{
                                            {10, 13},
                                            {12, 17},
                                            {11, 12},
                                            {15, 22},
                                            {11, 14},
                                            {14, 11},
                                    }),
                                    createMatrix(new double[][]{
                                            {6, 73, 907, 89},
                                            {73, 907, 11509,1104},
                                            {907, 11509, 149059, 14000},
                                    })
                            }
//                                    new Object[]{ // Belum tau jawaban aslinya (Mungkin di pake)
//                                            "20x4 matrix Nitrous Oxide Data",
//                                            createMatrix(new double[][] {
//                                                    {72.4,76.3,29.18,0.90},
//                                                    {41.6, 70.30, 29.35,0.91},
//                                                    {34.3, 77.1, 29.24, 0.96},
//                                                    {35.1, 68.0, 29.27, 0.89},
//                                                    {10.7, 79.0, 29.78, 1.00},
//                                                    {12.9, 67.4, 29.39, 1.10},
//                                                    {8.3, 66.8, 29.69, 1.15},
//                                                    {20.1, 76.9, 29.48, 1.03},
//                                                    {72.2, 77.7, 29.09, 0.77},
//                                                    {24.0, 67.7, 29.60, 1.07},
//                                                    {23.2, 76.8, 29.38, 1.07},
//                                                    {47.4, 86.6, 29.35, 0.94},
//                                                    {31.5, 76.9, 29.63, 1.10},
//                                                    {10.6, 86.3, 29.56, 1.10},
//                                                    {11.2, 86.0, 29.48, 1.10},
//                                                    {73.3, 76.3, 29.40, 0.91},
//                                                    {75.4, 77.9, 29.28, 0.87},
//                                                    {96.6, 78.7, 29.29, 0.78},
//                                                    {107.4, 86.8, 29.03, 0.82},
//                                                    {54.9, 70.9, 29.37, 0.95}
//                                            }),
//                                            createMatrix(new double[][]{
//                                                    {20, 863.1, 1530.4, 587.84, 19.42},
//                                                    {863.1, 54876.89, 67000.09, 25283.395, 779.477},
//                                                    {1530.4 ,67000.09 ,117912.32, 44976.867, 1483.437},
//                                                    {587.84, 25283.395, 44976.867, 17278.5086, 571.1219}
//                                            })
//                                    }
            );
        }
    }
    @Nested
    @DisplayName("Multiple Linear Regression Test")
    class MultipleLinearRegressionTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("multipleLinearRegressionTestCases")
        public void testMultipleLinearRegression(String description, Matrix matrix, Matrix expected) {
            LinearSystemSolver solver = new LinearSystemSolver();
            LinearSystemSolution expectedSolution = solver.gaussJordanElimination(expected);
            LinearSystemSolution actualSolution = Regression.multipleLinearRegression(matrix);

            if (expectedSolution instanceof NoSolution) {
                System.out.println(actualSolution); // No expectedSolution exists

            } else if (expectedSolution instanceof UniqueSolution) {
                Matrix expectedUniqueSolution = ((UniqueSolution) expectedSolution).getSolution();
                Matrix actualUniqueSolution = ((UniqueSolution) actualSolution).getSolution();
                assertArrayEquals(expectedUniqueSolution.getAllData(),
                        actualUniqueSolution.getAllData(),
                        "Multiple Linear Regression failed for " + description);

            } else if (expectedSolution instanceof ParametricSolution) {
                System.out.println(actualSolution);
            }
        }

        static Stream<Object[]> multipleLinearRegressionTestCases() {
            return Stream.of(
                    new Object[]{
                            "20x4 matrix Nitrous Oxide Data",
                            createMatrix(new double[][]{
                                    {72.4, 76.3, 29.18, 0.90},
                                    {41.6, 70.30, 29.35, 0.91},
                                    {34.3, 77.1, 29.24, 0.96},
                                    {35.1, 68.0, 29.27, 0.89},
                                    {10.7, 79.0, 29.78, 1.00},
                                    {12.9, 67.4, 29.39, 1.10},
                                    {8.3, 66.8, 29.69, 1.15},
                                    {20.1, 76.9, 29.48, 1.03},
                                    {72.2, 77.7, 29.09, 0.77},
                                    {24.0, 67.7, 29.60, 1.07},
                                    {23.2, 76.8, 29.38, 1.07},
                                    {47.4, 86.6, 29.35, 0.94},
                                    {31.5, 76.9, 29.63, 1.10},
                                    {10.6, 86.3, 29.56, 1.10},
                                    {11.2, 86.0, 29.48, 1.10},
                                    {73.3, 76.3, 29.40, 0.91},
                                    {75.4, 77.9, 29.28, 0.87},
                                    {96.6, 78.7, 29.29, 0.78},
                                    {107.4, 86.8, 29.03, 0.82},
                                    {54.9, 70.9, 29.37, 0.95}
                            }),
                            createMatrix(new double[][]{
                                    {20, 863.1, 1530.4, 587.84, 19.42},
                                    {863.1, 54876.89, 67000.09, 25283.395, 779.477},
                                    {1530.4, 67000.09, 117912.32, 44976.867, 1483.437},
                                    {587.84, 25283.395, 44976.867, 17278.5086, 571.1219}
                            })
                    },
                    new Object[]{
                            "1 variable example",
                            createMatrix(new double[][]{
                                    {10, 13},
                                    {12, 17},
                                    {11, 12},
                                    {15, 22},
                                    {11, 14},
                                    {14, 11},
                            }),
                            createMatrix(new double[][]{
                                    {6, 73, 89},
                                    {73, 907,1104},
                            })
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
