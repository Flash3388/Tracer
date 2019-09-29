package calculus.functions;

public class FirstConstantException extends Exception{
    private static final String MSG = "'a' must not be zero";
    public FirstConstantException() {
        super(MSG);
    }
}
