package algeo01_23002.cli;

import algeo01_23002.types.Matrix;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;

public class Utilities {

    public static String getCenteredText(String text, int width){
        int rem = width - text.length();
        StringBuilder res = new StringBuilder();
        if(rem > 0){
            res.append(" ".repeat(rem / 2 + (rem % 2)));
        }
        res.append(text);
        if(rem > 0){
            res.append(" ".repeat(rem / 2));
        }
        return res.toString();
    }

    // Ensures valid input
    public static int getChoice(int min, int max) {
        int choice;
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    break;
                } else {
                    System.out.print(YELLOW + "!!!  Invalid choice! Please choose again (" + min + "-" + max + "): " + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.print(YELLOW + "!!!  Not a valid number! Try again: " + RESET);
            } catch (Exception e) {
                System.out.print(RED + "!!!  ERROR! Try again: " + RESET);
            }
        }
        return choice;
    }

    public static void printMatrixWithBorder(Matrix matrix) {
        int rows = matrix.getRowsCount();
        int cols = matrix.getColsCount();

        double[][] data = matrix.getAllData();

        // Find the maximum length of the matrix element when formatted to two decimal places
        int maxLength = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String formatted = String.format("%.2f", data[i][j]);
                if (formatted.length() > maxLength) {
                    maxLength = formatted.length();
                }
            }
        }

        // Print top-side border
        System.out.print("  ╔");
        for (int i = 0; i < (maxLength + 1) * cols; i++) {
            System.out.print("═");
        }
        System.out.println();

        // Print each row of the matrix with left-side border and padding
        for (int i = 0; i < rows; i++) {
            System.out.print("  ║ "); // Left-side border

            for (int j = 0; j < cols; j++) {
                String formatted = String.format("%" + maxLength + ".2f", data[i][j]);
                System.out.print(formatted + " "); // Print element with padding
            }
            System.out.println();
        }
    }

    public static Matrix inputMatrixDriver(Matrix matrix) {
        boolean isValid = false;
        while(!isValid){
            try {
                matrix.inputMatrix();
            } catch (Exception e) {
                System.out.println(YELLOW + "!!!  Please input with the right format, rows, and cols: " + RESET);
                continue;
            }
            isValid = true;
        }
        return matrix;
    }

}
