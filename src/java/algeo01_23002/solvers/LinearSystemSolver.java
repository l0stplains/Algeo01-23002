package algeo01_23002.solvers;
import algeo01_23002.types.Matrix;

import java.util.Scanner;
public class LinearSystemSolver {
    private static boolean isAllZero(double[] row){
        int len = row.length;
        for (int i = 0; i < len; i++){
            if (row[i] != 0) return false;
        }
        return true;
    }
    public static Matrix Gauss (Matrix matrix){
        double[][] mat = matrix.getData();
        int rows = matrix.getRows();
        int cols = matrix.getCols();

        for (int iterasi=0; iterasi<rows; iterasi++){
            for (int row=iterasi; row<rows; row++){
                if (row == iterasi){ //langkah pertama: membuat leading 1
                    if (isAllZero(mat[row]) && row < rows - 1){ //jika 0 semua
                        for (int col=0; col<cols; col++){ //tukar dgn baris setelahnya
                            double temp = mat[row][col];
                            mat[row][col] = mat[row+1][col];
                            mat[row+1][col] = temp;
                        }
                    }

                    double pivot = mat[row][row];
                    System.out.println(pivot);
                    if (pivot !=0){ //jika pivotnya tidak 0
                        for (int col=0; col<cols; col++) { //bagi tiap kolom pada baris tersebut dengan pivotnya
                            mat[row][col] /= pivot;
                        }
                    }


                } else {//langkah kedua: membuat 0 di kolom tsb
                    double pengali = mat[row][iterasi];

                    for (int col=0; col<cols; col++) {
                        mat[row][col] -= pengali*mat[iterasi][col];
                    }
                }
            }
        }
        matrix.setData(mat);
        return matrix;
    }

    public static Matrix GaussJordan(Matrix matrix) {
        int rows = matrix.getRows();
        int cols = matrix.getCols();

        double[][] mat = Gauss(matrix).getData();
        for (int iterasi = rows-1; iterasi>=0; iterasi--){
            for (int i=iterasi-1; i>=0;i--){
                double pengali = mat[i][iterasi];
                for(int col = cols-1; col>=0; col--){
                    mat[i][col] -= pengali*mat[iterasi][col];
                }
            }
        }
        matrix.setData(mat);
        return matrix;
    }
}
