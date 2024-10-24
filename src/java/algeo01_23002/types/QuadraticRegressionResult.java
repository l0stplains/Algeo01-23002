package algeo01_23002.types;

import javax.xml.validation.Validator;
import java.util.Arrays;

public class QuadraticRegressionResult {
	private final Matrix parameters;

	// Constructor
	public QuadraticRegressionResult(Matrix parameters) {
		this.parameters = parameters;
	}

	// Method to print the regression equation
	public void printEquation() {
		StringBuilder equation = new StringBuilder();
		equation.append("f(x) = ").append(parameters.getData(0, 0));

		int k = (int) Math.floor(findK(parameters.getColsCount()));

		int varCount = 1;

		// First-order terms (x1, x2, ..., xn)
		for (int i = 1; i <= k; i++) {
			equation.append(" + ").append(parameters.getData(0, varCount)).append(" ").append("x").append(i);
			varCount++;
		}
		// Print squared-values and combination (x1^2, x1x2, etc..)
		for (int i = 1; i <= k; i++) {
			for (int j = i; j <= k; j++) {
				if(j == i){
					equation.append(" + ").append(parameters.getData(0, varCount)).append(" ").append("x").append(i).append("^2");
					varCount++;
				} else {
					equation.append(" + ").append(parameters.getData(0, varCount)).append(" ").append("x").append(i).append("x").append(j);
					varCount++;
				}
			}
		}
		System.out.println(equation.toString());
	}

	// Method to evaluate the result for new x values
	public double estimate(Matrix xValues) {
		int k = (int) Math.floor(findK(parameters.getColsCount()));
		if (xValues.getColsCount() != k) {
			throw new IllegalArgumentException("Number of x values must match the number of coefficients");
		}

		double result = parameters.getData(0,0);

		int coeffIndex = 1;

		// Iterate over first-order variables
		for (int i = 0; i < k; i++) {
			// Add the linear term: x1, x2, ...
			result += parameters.getData(0,coeffIndex) * xValues.getData(0,i);
			coeffIndex++;
		}

		// Iterate over combination variables e.g x1^2, x1x2..
		for (int i = 0; i < k; i++) {
			for (int j = i; j < k; j++) {
				if(i == j){
					result += parameters.getData(0,coeffIndex) * Math.pow(xValues.getData(0,i), 2);
				} else {
					result += parameters.getData(0,coeffIndex) * xValues.getData(0,i) * xValues.getData(0,j);
				}
				coeffIndex++;
			}
		}
		return result;
	}

	// Getter for parameters (optional)
	public Matrix getParameters() {
		return parameters;
	}

	public static Double findK(double Xrows) {
		// From k formula in Multiple Quadratic Regression
		double a = 1.0;
		double b = 3.0;
		double c = 2.0 - 2.0 * Xrows;

		double discriminant = b * b - 4 * a * c;

		if (discriminant < 0) {
			return null;
		}
		double k1 = (-b + Math.sqrt(discriminant)) / (2 * a);
		double k2 = (-b - Math.sqrt(discriminant)) / (2 * a);

		// We only want the positive solution for k
		if (k1 >= 0) {
			return k1;
		} else if (k2 >= 0) {
			return k2;
		} else {
			return null;
		}
	}

}
