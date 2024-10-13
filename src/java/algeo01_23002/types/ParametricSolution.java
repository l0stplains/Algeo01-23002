package algeo01_23002.types;

import java.util.Arrays;

public final class ParametricSolution extends LinearSystemSolution {
    private final String[][] parametricForm;

    public ParametricSolution(String[][] parametricForm) {
        this.parametricForm = parametricForm;
    }

    public String[][] getParametricForm() {
        return parametricForm;
    }

    @Override
    public String toString() {
        return "Parametric solution: " + Arrays.deepToString(parametricForm);
    }
}