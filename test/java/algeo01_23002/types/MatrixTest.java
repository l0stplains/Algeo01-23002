package algeo01_23002.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class MatrixTest {
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
    @DisplayName("Matrix Input/Output Test")
    class IOTests {
        @Test
        @DisplayName("Matrix Input Test")
        void testInputMatrix(){
            // Simulate user input
            String input = "1.0 2.0 3.0\n4.0 5.0 6.0\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));

            matrix.inputMatrix();

            double[][] expectedData = {
                    {1.0, 2.0, 3.0},
                    {4.0, 5.0, 6.0}
            };

            assertArrayEquals(expectedData, matrix.getAllData());
        }

        @Test
        @DisplayName("Matrix Print Test")
        void testPrintMatrix() {
            // Capture the output of printMatrix
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

        @Test
        public void testInputMatrixFromFile(){

            Matrix matrix1 = new Matrix(3,3);
            matrix1.inputMatrixFromFile("test/resources/FileTest.txt");

            double[][] expectedData = {
                    {1,2,3},
                    {4,5,6},
                    {7,8,9}
            };

            assertArrayEquals(expectedData, matrix1.getAllData(), "Parsed matrix is incorrect");
        }
    }

    @Nested
    @DisplayName("Matrix Addition Test")
    class AddMatrixTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("addMatrixTestCases")
        void testAddMatrix(String description, Matrix a, Matrix b, Matrix expected) {
            assertArrayEquals(expected.getAllData(), a.add(b).getAllData(), "Addition failed for " + description);
        }

        static Stream<Object[]> addMatrixTestCases() {
            return Stream.of(
                    new Object[]{"2x3 matrix",
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}}),
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}}),
                            createMatrix(new double[][]{{2, 4, 6}, {8, 10, 12}})
                    },
                    new Object[]{"3x3 matrix",
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}),
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}),
                            createMatrix(new double[][]{{2, 4, 6}, {8, 10, 12}, {14, 16, 18}})
                    }
            );
        }
    }

    @Nested
    @DisplayName("Matrix Subtraction Test")
    class SubtractMatrixTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("subtractMatrixTestCases")
        void testSubtractMatrix(String description, Matrix a, Matrix b, Matrix expected) {
            assertArrayEquals(expected.getAllData(), a.subtract(b).getAllData(), "Subtraction failed for " + description);
        }

        static Stream<Object[]> subtractMatrixTestCases() {
            return Stream.of(
                    new Object[]{"2x3 matrix",
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}}),
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}}),
                            createMatrix(new double[][]{{0, 0, 0}, {0, 0, 0}})
                    },
                    new Object[]{"3x3 matrix",
                            createMatrix(new double[][]{{2, 4, 6}, {8, 10, 12}, {14, 16, 18}}),
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}),
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}),
                    }
            );
        }
    }

    @Nested
    @DisplayName("Matrix Multiplication By Matrix Test")
    class MultiplyMatrixByMatrixTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("multiplyByMatrixTestCases")
        void testMultiplyByMatrix(String description, Matrix a, Matrix b, Matrix expected) {
            assertArrayEquals(expected.getAllData(), a.multiplyByMatrix(b).getAllData(), "Matrix by matrix multiplication failed for " + description);
        }

        static Stream<Object[]> multiplyByMatrixTestCases() {
            return Stream.of(
                    new Object[]{"2x3 matrix with 3x2 matrix",
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}}),
                            createMatrix(new double[][]{{7, 8}, {9, 10}, {11, 12}}),
                            createMatrix(new double[][]{{58, 64}, {139, 154}})
                    },
                    new Object[]{"3x3 matrix with 3x3 matrix",
                            createMatrix(new double[][]{{1, 9, 6}, {1, 3, 5}, {1, 8, 2}}),
                            createMatrix(new double[][]{{0, 0, 2}, {0, 5, 2}, {1, 1, 4}}),
                            createMatrix(new double[][]{{6, 51, 44}, {5, 20, 28}, {2, 42, 26}}),
                    }
            );
        }


    }

    @Nested
    @DisplayName("Matrix Multiplication By Scalar Test")
    class MultiplyMatrixByScalarTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("multiplyByScalarTestCases")
        public void testMultiplyByScalar(String description, Matrix matrix, double scale, Matrix expected) {
            assertArrayEquals(expected.getAllData(), matrix.multiplyByScalar(scale).getAllData(), "Matrix by scalar multiplication failed for " + description);
        }

        static Stream<Object[]> multiplyByScalarTestCases() {
            return Stream.of(
                    new Object[]{"2x3 matrix times 3",
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}}),
                            (double) 3,
                            createMatrix(new double[][]{{3, 6, 9}, {12, 15, 18}}),
                    },
                    new Object[]{"1x1 matrix times 0.135",
                            createMatrix(new double[][]{{1}}),
                            (double) 0.135,
                            createMatrix(new double[][]{{0.135}}),
                    }
            );
        }
    }

    @Nested
    @DisplayName("Matrix Transpose Test")
    class TransposeMatrixTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("transposeTestCases")
        void testTranspose(String description, Matrix matrix, Matrix expected) {
            assertArrayEquals(expected.getAllData(), matrix.transpose().getAllData(), "Matrix transpose failed for " + description);
        }

        static Stream<Object[]> transposeTestCases() {
            return Stream.of(
                    new Object[]{"2x3 matrix",
                            createMatrix(new double[][]{{2, 4, 6}, {8, 10, 12}}),
                            createMatrix(new double[][]{{2, 8}, {4, 10}, {6, 12}}),
                    },
                    new Object[]{"1x8 matrix",
                            createMatrix(new double[][]{{1, 2, 3, 4, 5, 6, 7, 8}}),
                            createMatrix(new double[][]{{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}})
                    }
            );
        }
    }

    @Nested
    @DisplayName("Matrix Determinant With Cofactor Test")
    class DeterminantWithCofactorTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("getDeterminantWithCofactorTestCases")
        void testGetDeterminantWithCofactor(String description, Matrix matrix, double expected) {
            assertEquals(expected, matrix.getDeterminantWithCofactor(), "Matrix Determinant with Cofactor failed for " + description);
        }

        static Stream<Object[]> getDeterminantWithCofactorTestCases() {
            return Stream.of(
                    new Object[]{"3x3 matrix",
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {5, 6, 7}}),
                            (double) 0,
                    },
                    new Object[]{"2x2 matrix (1)",
                            createMatrix(new double[][]{{1, 5}, {1, 5}}),
                            (double) 0,
                    },
                    new Object[]{"2x2 matrix (2)",
                            createMatrix(new double[][]{{2, 4}, {2, 9}}),
                            (double) 10,
                    },
                    new Object[]{"4x4 matrix",
                            createMatrix(new double[][] {
                                    {3.0,6.0,9.0,3.0},
                                    {-1.0,0.0,1.0,0},
                                    {1.0,3.0,2.0,-1},
                                    {-1.0,-2.0,-2.0,1}
                            }),
                            (double) -21,
                    }
            );
        }
    }

    @Nested
    @DisplayName("Matrix Determinant with Row Reduction Test")
    class DeterminantWithRowReductionTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("getDeterminantWithRowReductionTestCases")
        void testGetDeterminantWithRowReduction(String description, Matrix matrix, double expected) {
            assertEquals(expected, matrix.getDeterminantWithRowReduction(), "Matrix Determinant with Row Reduction failed for " + description);
        }

        static Stream<Object[]> getDeterminantWithRowReductionTestCases() {
            return Stream.of(
                    new Object[]{"3x3 matrix",
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {5, 6, 7}}),
                            (double) 0,
                    },
                    new Object[]{"2x2 matrix (1)",
                            createMatrix(new double[][]{{1, 5}, {1, 5}}),
                            (double) 0,
                    },
                    new Object[]{"2x2 matrix (2)",
                            createMatrix(new double[][]{{2, 4}, {2, 9}}),
                            (double) 10,
                    },
                    new Object[]{"4x4 matrix",
                            createMatrix(new double[][] {
                                    {3.0,6.0,9.0,3.0},
                                    {-1.0,0.0,1.0,0},
                                    {1.0,3.0,2.0,-1},
                                    {-1.0,-2.0,-2.0,1}
                            }),
                            (double) -21,
                    }
            );
        }
    }

    @Nested
    @DisplayName("Matrix Adjoint Test")
    class AdjointTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("getAdjointTestCases")
        void testGetAdjoint(String description, Matrix matrix, Matrix expected) {
            assertArrayEquals(expected.getAllData(), matrix.getAdjoint().getAllData(), "Matrix Adjoint failed for " + description);

        }

        static Stream<Object[]> getAdjointTestCases() {
            return Stream.of(
                    new Object[]{"3x3 matrix",
                            createMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}),
                            createMatrix(new double[][]{{-3, 6, -3}, {6, -12, 6}, {-3, 6, -3}})
                    },
                    new Object[]{"2x2 matrix",
                            createMatrix(new double[][]{{10, 2}, {7, 13}}),
                            createMatrix(new double[][]{{13, -2}, {-7, 10}}),
                    }
            );
        }
    }

    @Nested
    @DisplayName("Matrix Inverse With Adjoint Test")
    class InverseWithAdjointTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("getInverseWithAdjointTestCases")
        void testGetInverseWithAdjoint(String description, Matrix matrix, Matrix expected) {
            assertArrayEquals(expected.getAllData(), matrix.getInverseWithAdjoint().getAllData(), "Matrix Inverse with Adjoint failed for " + description);
        }

        static Stream<Object[]> getInverseWithAdjointTestCases() {
            return Stream.of(
                    new Object[]{"3x3 matrix",
                            createMatrix(new double[][]{{1, 8, 5}, {1, 9, 6}, {1, 3, 5}}),
                            createMatrix(new double[][]{{5.4, -5, 0.6}, {0.2, 0, -0.2}, {-1.2, 1, 0.2}})
                    },
                    new Object[]{"2x2 matrix",
                            createMatrix(new double[][]{{2, 9}, {2, 4}}),
                            createMatrix(new double[][]{{-0.4, 0.9}, {0.2, -0.2}}),
                    }
            );
        }
    }

    @Nested
    @DisplayName("Matrix Inverse With Row Reduction Test")
    class InverseWithRowReductionTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("getInverseWithRowReductionTestCases")
        void testGetInverseWithRowReduction(String description, Matrix matrix, Matrix expected) {
            assertArrayEquals(expected.getAllData(), matrix.getInverseWithRowReduction().getAllData(), "Matrix Inverse with Row Reduction failed for " + description);
        }

        static Stream<Object[]> getInverseWithRowReductionTestCases() {
            return Stream.of(
                    new Object[]{"3x3 matrix",
                            createMatrix(new double[][]{{1, 8, 5}, {1, 9, 6}, {1, 3, 5}}),
                            createMatrix(new double[][]{{5.4, -5, 0.6}, {0.2, 0, -0.2}, {-1.2, 1, 0.2}})
                    },
                    new Object[]{"2x2 matrix",
                            createMatrix(new double[][]{{2, 9}, {2, 4}}),
                            createMatrix(new double[][]{{-0.4, 0.9}, {0.2, -0.2}}),
                    }
            );
        }
    }

    @Nested
    @DisplayName("Matrix Row and Col Operations Test")
    class RowAndColOperationsTest {
        @Test
        public void testMultiplyRowByScalar() {

            matrix = matrix.multiplyRowByScalar(0, 2);

            double[][] expectedData = {
                    {2.0, 4.0, 6.0},
                    {4.0, 5.0, 6.0}
            };
            assertArrayEquals(expectedData, matrix.getAllData(), "Result of multiplying row by scalar is incorrect.");
        }

        // Test multiplyColumnByScalar
        @Test
        public void testMultiplyColByScalar() {

            matrix = matrix.multiplyColByScalar(1, 3);

            double[][] expectedData = {
                    {1.0, 6.0, 3.0},
                    {4.0, 15.0, 6.0}
            };
            assertArrayEquals(expectedData, matrix.getAllData(), "Result of multiplying column by scalar is incorrect.");
        }

        // Test swapRow
        @Test
        public void testSwapRow() {

            matrix = matrix.swapRow(0, 1);

            double[][] expectedData = {
                    {4.0, 5.0, 6.0},
                    {1.0, 2.0, 3.0}
            };
            assertArrayEquals(expectedData, matrix.getAllData(), "Result of swapping rows is incorrect.");
        }

        // Test swapCol
        @Test
        public void testSwapCol() {

            matrix = matrix.swapCol(0, 2);

            double[][] expectedData = {
                    {3.0, 2.0, 1.0},
                    {6.0, 5.0, 4.0}
            };
            assertArrayEquals(expectedData, matrix.getAllData(), "Result of swapping columns is incorrect.");
        }
    }

    @Nested
    @DisplayName("Matrix Row Echelon Form Test")
    class RowEchelonFormTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("getRowEchelonFormTestCases")
        void testGetRowEchelonForm(String description, Matrix matrix, Matrix expected) {
            assertArrayEquals(expected.getAllData(), matrix.getRowEchelonForm().getAllData(), "Matrix Row Echelon Form failed for " + description);
        }

        static Stream<Object[]> getRowEchelonFormTestCases() {
            return Stream.of(
                    new Object[]{"4x4 matrix",
                            createMatrix(new double[][] {
                                    {1.0,1.0,2.0,4.0},
                                    {2.0,-1.0,1.0,2.0},
                                    {1.0,2.0,3.0,7.0}
                            }),
                            createMatrix(new double[][] {
                                    {1.0,1.0,2.0,4.0},
                                    {0.0,1.0,1.0,2.0},
                                    {0.0,0.0,0.0,1.0}
                            })
                    },
                    new Object[]{"2x3 matrix",
                            createMatrix(new double[][] {{1, 2, 23}, {3, 1, 19}}),
                            createMatrix(new double[][] {{1, 2, 23}, {0, 1, 10}})
                    }
            );
        }
    }

    @Nested
    @DisplayName("Matrix Reduced Row Echelon Form Test")
    class ReducedRowEchelonFormTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("getReducedRowEchelonFormTestCases")
        public void testGetReducedRowEchelonForm(String description, Matrix matrix, Matrix expected) {
            assertArrayEquals(expected.getAllData(), matrix.getReducedRowEchelonForm().getAllData(), "Matrix Reduced Row Echelon Form failed for " + description);
        }

        static Stream<Object[]> getReducedRowEchelonFormTestCases() {
            return Stream.of(
                    new Object[]{"3x4 matrix",
                            createMatrix(new double[][] {
                                    {2.0,3.0,-1.0,5.0},
                                    {4.0,4.0,-3.0,3.0},
                                    {-2.0,3.0,-1.0,1.0}
                            }),
                            createMatrix(new double[][]{
                                    {1.0,0.0,0.0,1.0},
                                    {0.0,1.0,0.0,2.0},
                                    {0.0,0.0,1.0,3.0}
                            }),
                    },
                    new Object[]{"2x3 matrix",
                            createMatrix(new double[][] {{1, 2, 23}, {3, 1, 19}}),
                            createMatrix(new double[][] {{1, 0, 3}, {0, 1, 10}})
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

