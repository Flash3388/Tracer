package calculus.functions;

public class UnsolveableFunctionParametersException extends IllegalArgumentException{
    private static final String MSG = "'a' must not be zero";
    public UnsolveableFunctionParametersException() {
        super(MSG);
    }
}
