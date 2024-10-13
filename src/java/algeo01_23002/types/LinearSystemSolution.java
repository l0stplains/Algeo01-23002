package algeo01_23002.types;

public sealed abstract class LinearSystemSolution
        permits NoSolution, UniqueSolution, ParametricSolution {
    // common fields or methods (if needed) can be defined here
}

// EXAMPLE ON HOW TO USE THE CLASS
/*
LinearSystemSolver solver = new LinearSystemSolver();
LinearSystemResult result = solver.solve(someMatrix);

        if (result instanceof NoSolution) {
            System.out.println(result); // No solution exists
        } else if (result instanceof UniqueSolution) {
            Matrix solution = ((UniqueSolution) result).getSolution();
            System.out.println("Solution: " + Arrays.toString(solution.getData()));
        } else if (result instanceof ParametricSolution) {
            // Do something
        }
*/