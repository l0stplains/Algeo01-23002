package algeo01_23002.solvers;

import algeo01_23002.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class LinearSystemSolverTest {
    private Matrix matrix;

    @BeforeEach
    public void setUp() {
        Matrix matrix = new Matrix(2, 3);
        matrix.setAllData(new double[][] {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        });

    }
    @Nested
    @DisplayName("Cramer Test Test")
    class CramersRuleTests {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("cramersRuleTestCases")
        void testCramersRule(String description, Matrix a, Matrix expected) {
            LinearSystemSolver solver = new LinearSystemSolver();
            LinearSystemSolution solution = solver.cramersRule(a);
            Matrix uniqueSolution = ((UniqueSolution) solution).getSolution();
            assertArrayEquals(expected.getAllData(), uniqueSolution.getAllData(), "Solution Wrong");
        }


        static Stream<Object[]> cramersRuleTestCases() {
            return Stream.of(
                    new Object[]{"3x4 (3x3 augmented)",
                            createMatrix(new double[][]{
                                    {1, 1, -1, 6},
                                    {3, -2, 1, -5},
                                    {1, 3, -2, 14}}),
                            createMatrix(new double[][]{
                                    {1.0},
                                    {3.0},
                                    {-2.0}})
                            },
                    new Object[]{"3x4 (3x3 augmented)",
                            createMatrix(new double[][]{
                                    {4, 7, 8, 9},
                                    {22, 1, 5, 9},
                                    {1, 8, 8, 1}}),
                            createMatrix(new double[][]{
                                    {-2.52},
                                    {-15.56},
                                    {16}})
                            },
                            new Object[]{"2x3",
                                    createMatrix(new double[][]{
                                            {12,-10,46},
                                            {3,20,-11}}),
                            createMatrix(new double[][]{
                                    {3},
                                    {-1}})
                            }
                    );
            }
        }

    @Test
    public void testInverseMethod(){
        LinearSystemSolver solver = new LinearSystemSolver();
        Matrix matrix1 = new Matrix(3,3);
        Matrix constant = new Matrix(1,3);
        matrix1.inputMatrixFromFile("test/resources/CramersTest.txt");
        constant.inputMatrixFromFile("test/resources/Constant.txt");

        Matrix coba = new Matrix(3,4);

        for(int i = 0; i < coba.getRowsCount(); i++){
            for(int j = 0; j < coba.getColsCount(); j++){
                if(j == coba.getColsCount() - 1){
                    coba.setData(i, j, constant.getData(0, i));
                } else {
                    coba.setData(i, j, matrix1.getData(i, j));
                }
            }
        }

        LinearSystemSolution solution = solver.inverseMethod(coba);

        double[][] expectedData = {
                {1.0},
                {3.0},
                {-2.0}
        };

        if(solution instanceof UniqueSolution) {
            assertArrayEquals(expectedData, ((UniqueSolution) solution).getSolution().getAllData());
        }
    }
    @Nested
    @DisplayName("Gaussian Elimination Test")
    class gaussianEliminationTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("gaussianEliminationUniqueSolutionTestCases")
        void testGaussianEliminationUniqueSolution(String description, Matrix a, double[][] expected) {
            LinearSystemSolver solver = new LinearSystemSolver();
            LinearSystemSolution solution = solver.gaussianElimination(a);
            assertArrayEquals(expected, ((UniqueSolution) solution).getSolution().getAllData(), "Solution Wrong");
        }


        static Stream<Object[]> gaussianEliminationUniqueSolutionTestCases() {
            return Stream.of(
                    new Object[]{"4x4 matrix",
                            createMatrix(new double[][]{{3, 2, -1, -15}, {5, 3, 2, 0}, {3, 1, 3, 11}, {11, 7, 0, -30}}),
                            new double[][]{{-4.003, 2, 7}}
                    },
                    new Object[]{"4x5 matrix",
                            createMatrix(new double[][]{{4, -1, -1, 6, 1}, {0, 0, -3, 0, 0}, {4, 1, 0, 0, 0}, {4, 0, 3, 2, 0}}),
                            new double[][]{{-0.25, 1, 0, 0.5}}
                    }
            );
        }

        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("gaussianEliminationParametricSolutionTestCases")
        void testGaussianEliminationParametricSolution(String description, Matrix a, String[][] expected) {
            LinearSystemSolver solver = new LinearSystemSolver();
            LinearSystemSolution solution = solver.gaussianElimination(a);

            String[][] actualData = ((ParametricSolution) solution).getParametricForm();

            for (int i = 0; i < expected.length; i++) {
                assertArrayEquals(expected[i], actualData[i], "Solution Wrong at row " + i);
            }
        }


        static Stream<Object[]> gaussianEliminationParametricSolutionTestCases() {
            return Stream.of(
                    new Object[]{"4x6 matrix",
                            createMatrix(new double[][]{{1, 7, -2, 0, -8, -3}, {1, 7, -1, 1, -2, 2}, {2, 14, -4, 1, -13, 3}, {1, 7, -2, 0, -8, -3}}),
                            new String[][]{{"-11.0", "2.0s", "-7.0r"}, {"0.0", "0.0s", "1.0r"}, {"-4.0", "-3.0s", "0.0r"}, {"9.0", "-3.0s", "0.0r"}, {"0.0", "1.0s", "0.0r"}}
                    },
                    new Object[]{"4x6 matrix",
                            createMatrix(new double[][]{{1, 7, -2, 0, -8, -3}, {1, 7, -1, 1, -2, 2}, {2, 14, -4, 1, -13, 3}, {1, 7, -2, 0, -8, -3}}),
                            new String[][]{{"-11.0", "2.0s", "-7.0r"}, {"0.0", "0.0s", "1.0r"}, {"-4.0", "-3.0s", "0.0r"}, {"9.0", "-3.0s", "0.0r"}, {"0.0", "1.0s", "0.0r"}}
                    }
            );
        }

        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("gaussianEliminationNoSolutionTestCases")
        void testGaussianEliminationNoSolution(String description, Matrix a) {
            LinearSystemSolver solver = new LinearSystemSolver();
            NoSolution solution = (NoSolution) solver.gaussianElimination(a);
            assertEquals("No solution exists for the given system.", solution.toString(), "Solution Wrong");
        }


        static Stream<Object[]> gaussianEliminationNoSolutionTestCases() {
            return Stream.of(
                    new Object[]{"2x3 matrix",
                            createMatrix(new double[][]{{1, 2, 3}, {0, 0, 5}})
                    },
                    new Object[]{"3x3 matrix",
                            createMatrix(new double[][]{{1, 2, 3}, {0, 0, 5}, {0, 0, 5}})
                    }
            );
        }


    }



    @Nested
    @DisplayName("GaussJordan Elimination Test")
    class gaussJordanEliminationTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("gaussJordanEliminationUniqueSolutionTestCases")
        void testGaussianEliminationUniqueSolution(String description, Matrix a, double[][] expected) {
            LinearSystemSolver solver = new LinearSystemSolver();
            LinearSystemSolution solution = solver.gaussJordanElimination(a);
            assertArrayEquals(expected, ((UniqueSolution) solution).getSolution().getAllData(), "Solution Wrong");
        }


        static Stream<Object[]> gaussJordanEliminationUniqueSolutionTestCases() {
            return Stream.of(
                    new Object[]{"4x4 matrix",
                            createMatrix(new double[][]{{3, 2, -1, -15}, {5, 3, 2, 0}, {3, 1, 3, 11}, {11, 7, 0, -30}}),
                            new double[][]{{-4.003, 2, 7}}
                    },
                    new Object[]{"4x5 matrix",
                            createMatrix(new double[][]{{4, -1, -1, 6, 1}, {0, 0, -3, 0, 0}, {4, 1, 0, 0, 0}, {4, 0, 3, 2, 0}}),
                            new double[][]{{-0.25, 1, 0, 0.5}}
                    }
            );
        }

        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("gaussJordanEliminationParametricSolutionTestCases")
        void testGaussJordanEliminationParametricSolution(String description, Matrix a, String[][] expected) {
            LinearSystemSolver solver = new LinearSystemSolver();
            LinearSystemSolution solution = solver.gaussJordanElimination(a);
            String[][] actualData = ((ParametricSolution) solution).getParametricForm();

            for (int i = 0; i < expected.length; i++) {
                assertArrayEquals(expected[i], actualData[i], "Solution Wrong at row " + i);
            }
        }


        static Stream<Object[]> gaussJordanEliminationParametricSolutionTestCases() {
            return Stream.of(
                    new Object[]{"4x6 matrix",
                            createMatrix(new double[][]{{1, 7, -2, 0, -8, -3}, {1, 7, -1, 1, -2, 2}, {2, 14, -4, 1, -13, 3}, {1, 7, -2, 0, -8, -3}}),
                            new String[][]{{"-11.0", "2.0s", "-7.0r"}, {"0.0", "0.0s", "1.0r"}, {"-4.0", "-3.0s", "0.0r"}, {"9.0", "-3.0s", "0.0r"}, {"0.0", "1.0s", "0.0r"}}
                    },
                    new Object[]{"4x6 matrix",
                            createMatrix(new double[][]{{1, 7, -2, 0, -8, -3}, {1, 7, -1, 1, -2, 2}, {2, 14, -4, 1, -13, 3}, {1, 7, -2, 0, -8, -3}}),
                            new String[][]{{"-11.0", "2.0s", "-7.0r"}, {"0.0", "0.0s", "1.0r"}, {"-4.0", "-3.0s", "0.0r"}, {"9.0", "-3.0s", "0.0r"}, {"0.0", "1.0s", "0.0r"}}
                    }
            );
        }

        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("gaussJordanEliminationNoSolutionTestCases")
        void testGaussJordanEliminationNoSolution(String description, Matrix a) {
            LinearSystemSolver solver = new LinearSystemSolver();
            NoSolution solution = (NoSolution) solver.gaussianElimination(a);
            assertEquals("No solution exists for the given system.", solution.toString(), "Solution Wrong");
        }


        static Stream<Object[]> gaussJordanEliminationNoSolutionTestCases() {
            return Stream.of(
                    new Object[]{"2x3 matrix",
                            createMatrix(new double[][]{{1, 2, 3}, {0, 0, 5}})
                    },
                    new Object[]{"3x3 matrix",
                            createMatrix(new double[][]{{1, 2, 3}, {0, 0, 5}, {0, 0, 5}})
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
