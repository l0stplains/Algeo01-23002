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
        StringBuilder result = new StringBuilder("Parametric solution:\n");

        for (int i = 0; i < parametricForm.length; i++) {
            result.append("X").append(i + 1).append(" = ");
            boolean hasPrinted = false;

            for (int j = 0; j < parametricForm[i].length; j++) {
                String term = parametricForm[i][j];

                if (term.matches("0\\.0[a-zA-Z]+")) {
                    continue;
                }

                if (j == 0) {
                    if(!term.equals("0.0")){
                        result.append(term);
                        hasPrinted = true;
                    }
                } else {
                    if (hasPrinted) {
                        result.append(" + ");
                    }
                    if (term.matches("1\\.0[a-zA-Z]+")) {
                        result.append(term.substring(3));
                    } else if (term.matches("-1\\.0[a-zA-Z]+")) {
                        result.append("-" + term.substring(4));
                    } else {
                        result.append(term);
                    }
                    hasPrinted = true;
                }
            }
            if (!hasPrinted) {
                result.append("0.0");
            }
            if (i < parametricForm.length - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }
}