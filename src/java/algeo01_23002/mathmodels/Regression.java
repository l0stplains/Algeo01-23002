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

    public static Matrix multipleQuadraticRegression(Matrix inputPoints){
        int n = inputPoints.getRowsCount(); //n of points
        int k = inputPoints.getColsCount() - 1; //k of variables

        int XRows = 1 + 2*k + k*(k-1)/2; //number of rows (and cols) in matrix X
        System.out.println(XRows);
        Matrix X = new Matrix(XRows, XRows);

        //process to make X matrix
        X.setData(0,0,n); //for top left corner of the X

        //for top edge of the X

        //for linear variable
        for (int i = 1; i <= k; i++) {
            for (int h=0; h<n; h++) {
                double val = X.getData(0,i) + inputPoints.getData(h,i-1);
                X.setData(0,i,val);
            }
        }
        //for quadratic variable
        for (int i = k+1; i <= 2*k+1; i++) {
            for (int h=0; h<n; h++) {
                double val = X.getData(0,i) + inputPoints.getData(h,i-(k+1))*inputPoints.getData(h,i-(k+1));
                X.setData(0,i,val);
            }
        }
        //for interaction variable
        for (int i = 2*k+2; i < XRows; i++) {
            for (int j = i+1; j < k; j++){
                for (int h=0; h<n; h++) {
                    double val = X.getData(0,i) + inputPoints.getData(h,i-(2*k+2))*inputPoints.getData(h,i-(2*k+2));
                    X.setData(0,i,val);
                }
            }

        }
        //for left edge of the X
        for (int i = 0; i <XRows; i++) {
            X.setData(i, 0, X.getData(0,i));
        }

        //for the whole X except top edge and left edge
        for (int j=1; j<=k; j++){
            for(int i=1; i<=k; i++){
                for (int h=0; h<n; h++){
                    double val = X.getData(j,i) + inputPoints.getData(h,j-1) * inputPoints.getData(h,i-1);
                    X.setData(j,i,0); //set 0 first only for checking top edge and left edge
                    // you can replace 0 with val after checking them (top edge and left edge)
                }
            }
        }
        return X;
    }
}
