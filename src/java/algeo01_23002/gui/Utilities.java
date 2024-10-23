package algeo01_23002.gui;

import algeo01_23002.types.Matrix;

import java.util.Locale;

public class Utilities {
    public static String outputPaddedMatrix(Matrix matrix) {
        int rows = matrix.getRowsCount();
        int cols = matrix.getColsCount();
        StringBuilder sb = new StringBuilder();
        double[][] data = matrix.getAllData();

        // Create an array to store the maximum length of each column
        int[] maxLengths = new int[cols];

        // Find the maximum length of each column when formatted to two decimal places
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                String formatted = String.format("%.2f", data[i][j]);
                if (formatted.length() > maxLengths[j]) {
                    maxLengths[j] = formatted.length();
                }
            }
        }

        // Print each row of the matrix with left-side border and column-specific padding
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Use the maxLength for the current column
                String formatted = String.format(Locale.US, "%" + maxLengths[j] + ".2f", data[i][j]);
                sb.append(formatted + " "); // Print element with padding
            }
            if (i != rows - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }


}
