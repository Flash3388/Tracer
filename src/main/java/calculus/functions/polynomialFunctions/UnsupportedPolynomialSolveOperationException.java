package calculus.functions.polynomialFunctions;

public class UnsupportedPolynomialSolveOperationException extends UnsupportedOperationException {
    private static final String MSG = "solve operation is not supported for polynomial of %dth degree";
    public UnsupportedPolynomialSolveOperationException(int degree) {
        super(String.format(MSG, degree));
    }
}
