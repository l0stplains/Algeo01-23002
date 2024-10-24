package algeo01_23002.gui;

import algeo01_23002.types.Matrix;
import javafx.scene.control.Hyperlink;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

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
                String formatted = String.format("%.8f", data[i][j]);
                if (formatted.length() > maxLengths[j]) {
                    maxLengths[j] = formatted.length();
                }
            }
        }

        // Print each row of the matrix with left-side border and column-specific padding
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Use the maxLength for the current column
                String formatted = String.format(Locale.US, "%" + maxLengths[j] + ".8f", data[i][j]);
                sb.append(formatted + " "); // Print element with padding
            }
            if (i != rows - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    // Function to load and display the matrix from the file
    public static Matrix loadMatrixFromTxt(File file) {
        try {
            // Read the contents of the file
            String matrixData = new String(Files.readAllBytes(Paths.get(file.getPath())));
            // Do something with the matrix data (e.g., display or process)
            Matrix result = new Matrix(1, 1);
            result.inputMatrixFromString(matrixData);
            return result;
        } catch (Exception e){
            return null;
        }
    }

    public static void saveTextToFile(File file, String content) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(content);  // Write the string to the file
        writer.close();
    }
}
