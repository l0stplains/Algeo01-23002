package algeo01_23002.mathmodels;
import algeo01_23002.types.Matrix;
public class Regression {

    public static Matrix multipleLinearRegression (Matrix inputPoints){
        int n = inputPoints.getRowsCount(); //n of points
        int k = inputPoints.getColsCount() - 1; //k of variables

        Matrix X = new Matrix(k+1, k+1);

        //process to make X matrix
        X.setData(0,0,n);//for top left corner of the X
        //for top edge of the X
        for (int i = 1; i <= k; i++) {
            for (int h=0; h<n; h++) {
                double val = X.getData(0,i) + inputPoints.getData(h,i-1);
                X.setData(0,i,val);
            }
        }
        //for left edge of the X
        for (int j = 1; j <= k; j++) {
            for (int h=0; h<n; h++) {
                double val = X.getData(j,0) + inputPoints.getData(h,j-1);
                X.setData(j,0,val);
            }
        }
        //for the whole X except top edge and left edge
        for (int j=1; j<=k; j++){
            for(int i=1; i<=k; i++){
                for (int h=0; h<n; h++){
                    double val = X.getData(j,i) + inputPoints.getData(h,j-1) * inputPoints.getData(h,i-1);
                    X.setData(j,i,val);
                }
            }
        }

        Matrix y = new Matrix(k+1, 1);
        for (int h=0; h<n; h++) {
            double val = y.getData(0,0) + inputPoints.getData(h,k);
            y.setData(0,0,val);
        }
        for (int j = 1; j <= k; j++) {
            for (int h=0; h<n; h++) {
                double val = y.getData(j,0) + inputPoints.getData(h,j-1) * inputPoints.getData(h,k);
                y.setData(j,0,val);
            }
        }

        Matrix b;
        b = X.getInverseWithAdjoint().multiplyByMatrix(y);
        return b;
    }

    // public static Matrix multipleQuadraticRegression
}
