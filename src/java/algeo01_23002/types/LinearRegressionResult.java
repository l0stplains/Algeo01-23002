package algeo01_23002.types;

import java.util.Arrays;

public class LinearRegressionResult {
	private final Matrix parameters;

	// Constructor
	public LinearRegressionResult(Matrix parameters) {
		this.parameters = parameters;
	}

	// Method to print the regression equation
	public void printEquation() {
		StringBuilder equation = new StringBuilder();

		equation.append("f(x) = ").append(parameters.getData(0,0));  // Intercept at index 0
		for (int i = 1; i < parameters.getColsCount(); i++) {
			equation.append(" + ").append(parameters.getData(0,i)).append("x").append(i);
		}
		System.out.println(equation.toString());
	}

	// Method to get the regression equation
	public String getEquation() {
		StringBuilder equation = new StringBuilder();

		equation.append("f(x) = ").append(parameters.getData(0,0));  // Intercept at index 0
		for (int i = 1; i < parameters.getColsCount(); i++) {
			equation.append(" + ").append(parameters.getData(0,i)).append("x").append(i);
		}
		return equation.toString();
	}

	// Method to evaluate the result for new x values
	public double estimate(Matrix xValues) {
		if (xValues.getColsCount() != parameters.getColsCount() - 1) {
			throw new IllegalArgumentException("Number of x values must match the number of coefficients");
		}

		double result = parameters.getData(0,0);
		for (int i = 0; i < xValues.getColsCount(); i++) {
			result += parameters.getData(0,i+1)* xValues.getData(0,i);
		}

		return result;
	}

	// Getter for parameters (optional)
	public Matrix getParameters() {
		return parameters;
	}
}
