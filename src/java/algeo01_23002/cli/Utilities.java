package algeo01_23002.cli;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import algeo01_23002.types.Matrix;
import algeo01_23002.types.ParametricSolution;
import algeo01_23002.types.QuadraticRegressionResult;

import java.util.ArrayList;
import java.util.List;
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
    
    public static double getDouble(){
        double choice;
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                choice = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print(YELLOW + "!!!  Not a valid number! Try again: " + RESET);
                continue;
            } catch (Exception e) {
                System.out.print(RED + "!!!  ERROR! Try again: " + RESET);
                continue;
            }
            break;
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

    public static void inputMatrixDriver(Matrix matrix) {
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
    }

    public static void inputMatrixFromFileDriver(Matrix matrix) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        System.out.println("You entered: " + input);
        try{
            matrix.inputMatrixFromFile(input);
        } catch(IllegalArgumentException e) {
            if(e.getMessage() == "File not found"){
                System.out.println(YELLOW + "\nFile not found");
            }else if (e.getMessage().startsWith("File matrix dimensions")){
                System.out.println(YELLOW + "\nInvalid Dimension");
            } else{
                System.out.println(YELLOW + "\nInvalid Input");
            }
        } catch (Exception e) {
            System.out.println(YELLOW + "\nSystem Error");
        }
    }

    public static void inputMatrixFromCLIDriver(Matrix matrix) {
        System.out.println("\n" + ARROW + "  Enter each element of the matrix: ");
        inputMatrixDriver(matrix);
    }

    public static void inputMatrixChoiceDriver(Matrix matrix){
        int choice;
        while(true){
            System.out.println("\n" + ARROW + "  Input from file or from CLI? ");
            System.out.println("1.  Input Matrix from File");
            System.out.println("2.  Input Matrix from CLI");

            choice= getChoice(1, 2);
            switch(choice) {
                case 1 -> inputMatrixFromFileDriver(matrix);
                case 2 -> inputMatrixFromCLIDriver(matrix);
            }
            Matrix temp = new Matrix(matrix.getRowsCount(), matrix.getColsCount());
            if (!temp.equals(matrix)){
                break;
            }
            else {
                System.out.println(YELLOW + "You've Input Zero Matrix" + RESET);
            }
        }
    }

    public static void saveUniqueResultToFile(Matrix matrix){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter save file name: ");
        String filepath = scanner.nextLine();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (int i = 0; i < matrix.getColsCount(); i++) {
                    writer.write("X" + (i + 1) + " = " + matrix.getData(0,i));
                if (i < matrix.getColsCount() - 1) {
                    writer.newLine();
                }
            }
            System.out.println("Matrix saved to file: " + filepath);
        } catch (IOException e) {
            System.err.println("Error saving matrix to file: " + e.getMessage());
        }
    }

    public static void saveParametricResultToFile(ParametricSolution result) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter save file name: ");
        String filepath = scanner.nextLine();

        String[][] parametricForm = result.getParametricForm();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            writer.write("Parametric solution:\n");

            for (int i = 0; i < parametricForm.length; i++) {
                writer.write("X" + (i + 1) + " = ");
                boolean hasPrinted = false;

                for (int j = 0; j < parametricForm[i].length; j++) {
                    String term = parametricForm[i][j];

                    if (term.matches("0\\.0[a-zA-Z]+")) {
                        continue;
                    }

                    if (j == 0) {
                        if(!term.equals("0.0")){
                            writer.write(term);
                            hasPrinted = true;
                        }
                    } else {
                        if (hasPrinted) {
                            writer.write(" + ");
                        }
                        if (term.matches("1\\.0[a-zA-Z]+")) {
                            writer.write(term.substring(3));
                        } else if (term.matches("-1\\.0[a-zA-Z]+")) {
                            writer.write("-" + term.substring(4));
                        } else {
                            writer.write(term);
                        }
                        hasPrinted = true;
                    }
                }
                if (!hasPrinted) {
                    writer.write("0.0");
                }
                if (i < parametricForm.length - 1) {
                    writer.newLine();
                }
            }

            System.out.println("Parametric solution saved to file: " + filepath);
        } catch (IOException e) {
            System.err.println("Error saving parametric solution to file: " + e.getMessage());
        }
    }

    public static void saveMatrixResultToFile(Matrix matrix) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter save file name: ");
        String filepath = scanner.nextLine();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (int i = 0; i < matrix.getRowsCount(); i++) {
                for(int j = 0; j < matrix.getColsCount(); j++) {
                    writer.write(""+ matrix.getData(i,j));
                    if (j < matrix.getColsCount() - 1) {
                        writer.write(" ");
                    };
                }
                writer.newLine();
            }
            System.out.println("Matrix saved to file: " + filepath);
        } catch (IOException e) {
            System.err.println("Error saving matrix to file: " + e.getMessage());
        }
    }

    public static void saveDoubleResultToFile(String message, double value) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter save file name: ");
        String filepath = scanner.nextLine();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            writer.write(message + " " + value);
            System.out.println("Matrix saved to file: " + filepath);
        } catch (IOException e) {
            System.err.println("Error saving matrix to file: " + e.getMessage());
        }
    }

    public static void saveUniqueLinearRegressionResultToFile(Matrix matrix){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter save file name: ");
        String filepath = scanner.nextLine();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (int i = 0; i < matrix.getColsCount(); i++) {
                if(i == 0){
                    writer.write("Constant" + " = " + matrix.getData(0,i));
                } else {
                    writer.write("X" + (i) + " = " + matrix.getData(0,i));
                }
                if (i < matrix.getColsCount() - 1) {
                    writer.newLine();
                }
            }
            System.out.println("Matrix saved to file: " + filepath);
        } catch (IOException e) {
            System.err.println("Error saving matrix to file: " + e.getMessage());
        }
    }

    public static void saveParametricLinearRegressionToFile(ParametricSolution result){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter save file name: ");
        String filepath = scanner.nextLine();
        String[][] parametricForm = result.getParametricForm();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            writer.write("Parametric solution:\n");

            for (int i = 0; i < parametricForm.length; i++) {
                if(i == 0){
                    writer.write("Constant" + " = ");
                } else {
                    writer.write("X" + (i) + " = ");
                }
                boolean hasPrinted = false;

                for (int j = 0; j < parametricForm[i].length; j++) {
                    String term = parametricForm[i][j];

                    if (term.matches("0\\.0[a-zA-Z]+")) {
                        continue;
                    }

                    if (j == 0) {
                        if(!term.equals("0.0")){
                            writer.write(term);
                            hasPrinted = true;
                        }
                    } else {
                        if (hasPrinted) {
                            writer.write(" + ");
                        }
                        if (term.matches("1\\.0[a-zA-Z]+")) {
                            writer.write(term.substring(3));
                        } else if (term.matches("-1\\.0[a-zA-Z]+")) {
                            writer.write("-" + term.substring(4));
                        } else {
                            writer.write(term);
                        }
                        hasPrinted = true;
                    }
                }
                if (!hasPrinted) {
                    writer.write("0.0");
                }
                if (i < parametricForm.length - 1) {
                    writer.newLine();
                }
            }

            System.out.println("Parametric solution saved to file: " + filepath);
        } catch (IOException e) {
            System.err.println("Error saving parametric solution to file: " + e.getMessage());
        }
    }
    public static String printParametricLinearRegression(ParametricSolution parametric){
        String [][] parametricForm = parametric.getParametricForm();
        StringBuilder result = new StringBuilder("Parametric solution:\n");

        for (int i = 0; i < parametricForm.length; i++) {
            if(i == 0){
                result.append("Constant").append(" = ");
            } else {
                result.append("X").append(i).append(" = ");
            }

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

    public static void saveUniqueQuadraticRegressionToFile(Matrix result){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter save file name: ");
        String filepath = scanner.nextLine();
        int k = (int) Math.floor(QuadraticRegressionResult.findK(result.getColsCount()));

        List<String> terms = new ArrayList<>();

        // Add linear terms (x1, x2, ...)
        for (int i = 1; i <= k; i++) {
            terms.add("x" + i);
        }

        // Add interaction terms (x1x2, x1x3, ...)
        for (int i = 1; i <= k; i++) {
            for (int j = i ; j <= k; j++) {
                if(i == j){
                    terms.add("x" + i + "^2");
                }
                else{
                    terms.add("x" + i + "x" + j);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (int i = 0; i < result.getColsCount(); i++) {
                if(i == 0){
                    writer.write("Constant" + " = " + result.getData(0,i));
                } else {
                    writer.write(terms.get(i-1) + " = " + result.getData(0,i));
                }
                if (i < result.getColsCount() - 1) {
                    writer.newLine();
                }
            }
            System.out.println("Matrix saved to file: " + filepath);
        } catch (IOException e) {
            System.err.println("Error saving matrix to file: " + e.getMessage());
        }

//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
//            int coeffIndex = 0;
//
//            // Write constant term
//            writer.write("Coefficient: " + result.getData(0, coeffIndex) + " (constant)");
//            coeffIndex++;
//            writer.newLine();
//
//            // Write linear terms: x1, x2, x3, ...
//            for (int i = 1; i <= k; i++) {
//                writer.write("Coefficient: " + result.getData(0, coeffIndex) + " * x" + i);
//                coeffIndex++;
//                writer.newLine();
//            }
//
//            // Write cross terms: x1x2, x1x3, ..., x(n-1)x(n)
//            for (int i = 1; i <= k; i++) {
//                for (int j = i; j <= k; j++) {
//                    if(i == j){
//                        writer.write("Coefficient: " + result.getData(0, coeffIndex) + " * x" + i + "^2");
//                    } else {
//                        writer.write("Coefficient: " + result.getData(0, coeffIndex) + " * x" + i + "x" + j);
//                    }
//                    coeffIndex++;
//                    writer.newLine();
//                }
//            }
//        } catch (IOException e) {
//            System.err.println("Error saving matrix to file: " + e.getMessage());
//        }


    }

    public static String printParametricQuadraticRegression(ParametricSolution parametric){
        String [][] parametricForm = parametric.getParametricForm();
        StringBuilder result = new StringBuilder("Parametric solution:\n");

        int k = (int) Math.floor(QuadraticRegressionResult.findK(parametricForm.length));

        List<String> terms = new ArrayList<>();

        // Add linear terms (x1, x2, ...)
        for (int i = 1; i <= k; i++) {
            terms.add("x" + i);
        }

        // Add interaction terms (x1x2, x1x3, ...)
        for (int i = 1; i <= k; i++) {
            for (int j = i ; j <= k; j++) {
                if(i == j){
                    terms.add("x" + i + "^2");
                }
                else{
                    terms.add("x" + i + "x" + j);
                }
            }
        }

        for (int i = 0; i < parametricForm.length; i++) {
            if(i == 0){
                result.append("Constant").append(" = ");
            } else {
                result.append(terms.get(i-1)).append(" = ");
            }
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

    public static void saveParametricQuadraticRegressionToFile(ParametricSolution parametric) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter save file name: ");
        String filepath = scanner.nextLine();

        String[][] parametricForm = parametric.getParametricForm();

        int k = (int) Math.floor(QuadraticRegressionResult.findK(parametricForm.length));

        List<String> terms = new ArrayList<>();

        // Add linear terms (x1, x2, ...)
        for (int i = 1; i <= k; i++) {
            terms.add("x" + i);
        }
        // Add interaction terms (x1x2, x1x3, ...)
        for (int i = 1; i <= k; i++) {
            for (int j = i; j <= k; j++) {
                if (i == j) {
                    terms.add("x" + i + "^2");
                } else {
                    terms.add("x" + i + "x" + j);
                }
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            writer.write("Parametric solution:\n");

            for (int i = 0; i < parametricForm.length; i++) {
                if(i == 0){
                    writer.write("Constant" + " = ");
                } else {
                    writer.write(terms.get(i-1) + " = ");
                }
                boolean hasPrinted = false;

                for (int j = 0; j < parametricForm[i].length; j++) {
                    String term = parametricForm[i][j];

                    if (term.matches("0\\.0[a-zA-Z]+")) {
                        continue;
                    }
                    if (j == 0) {
                        if(!term.equals("0.0")){
                            writer.write(term);
                            hasPrinted = true;
                        }
                    } else {
                        if (hasPrinted) {
                            writer.write(" + ");
                        }
                        if (term.matches("1\\.0[a-zA-Z]+")) {
                            writer.write(term.substring(3));
                        } else if (term.matches("-1\\.0[a-zA-Z]+")) {
                            writer.write("-" + term.substring(4));
                        } else {
                            writer.write(term);
                        }
                        hasPrinted = true;
                    }
                }
                if (!hasPrinted) {
                    writer.write("0.0");
                }
                if (i < parametricForm.length - 1) {
                    writer.newLine();
                }
            }
            System.out.println("Parametric solution saved to file: " + filepath);
        } catch (IOException e) {
            System.err.println("Error saving parametric solution to file: " + e.getMessage());
        }
    }

}
