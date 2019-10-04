package calculus.functions.polynomialFunctions;

public class IncorrectNumberOfConstantsException extends IllegalArgumentException {
    private final static String MSG = "Number of constants is incorrect for polynomial degree of %f";

    public IncorrectNumberOfConstantsException(int degree) {
        super(String.format(MSG, degree));
    }
}