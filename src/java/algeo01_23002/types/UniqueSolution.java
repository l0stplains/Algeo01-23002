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
        StringBuilder result = new StringBuilder("Unique solution:\n");
        solution.printMatrix();

        for (int i = 0; i < solution.getColsCount(); i++) {
            result.append("X").append(i + 1).append(" = ").append(solution.getData(0,i));
            if (i < solution.getColsCount() - 1) {
                result.append("\n");
            }
        }

        return result.toString();
    }

}