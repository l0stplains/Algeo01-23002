package algeo01_23002.types;

import java.util.Arrays;

public final class UniqueSolution extends LinearSystemSolution {
    private final Matrix solution;

    public UniqueSolution(Matrix solution) {
        this.solution = solution;
    }

    public Matrix getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        return "Unique solution: " + Arrays.deepToString(solution.getAllData()); // changed
    }
}