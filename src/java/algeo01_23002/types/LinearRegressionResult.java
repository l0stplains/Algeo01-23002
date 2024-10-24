package algeo01_23002.types;

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

	// Method to evaluate the result for new x values
	public double estimate(double[] xValues) {
		if (xValues.length != parameters.getColsCount() - 1) {
			throw new IllegalArgumentException("Number of x values must match the number of coefficients");
		}

		double result = parameters.getData(0,0);
		for (int i = 0; i < xValues.length; i++) {
			result += parameters.getData(0,i+1)* xValues[i];
		}

		return result;
	}

	// Getter for parameters (optional)
	public Matrix getParameters() {
		return parameters;
	}
}
