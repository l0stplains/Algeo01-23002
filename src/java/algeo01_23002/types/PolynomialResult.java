package algeo01_23002.types;

public class PolynomialResult {
    private Matrix coefficients;

    // Constructor accepts the coefficient matrix from Interpolation
    public PolynomialResult(Matrix coefficients) {
        this.coefficients = coefficients;
    }

    public Matrix getCoefficients() {
        return coefficients;
    }

    // Method to evaluate the polynomial at any value of x
    public double evaluate(double x) {
        double result = 0;
        int degree = coefficients.getRowsCount() - 1;  // Assuming one-column matrix

        for (int i = 0; i <= degree; i++) {
            result += coefficients.getData(i, 0) * Math.pow(x, i);
        }

        return result;
    }
}
