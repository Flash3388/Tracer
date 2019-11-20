package calculus.functions.polynomial;

public class IncorrectNumberOfConstantsException extends IllegalArgumentException {
    private final static String MSG = "Number of constants is incorrect for polynomial degree of %d";

    public IncorrectNumberOfConstantsException(int degree) {
        super(String.format(MSG, degree));
    }
}
