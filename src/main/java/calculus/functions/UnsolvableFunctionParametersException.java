package calculus.functions;

public class UnsolvableFunctionParametersException extends IllegalArgumentException{
    private static final String MSG = "'a' must not be zero";
    public UnsolvableFunctionParametersException() {
        super(MSG);
    }
}
