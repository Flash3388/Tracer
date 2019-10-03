package calculus.variables;

public class SmallerPowerException extends IllegalArgumentException{
    private static final String MSG = "other variable's power is bigger then the original";
    public SmallerPowerException() {
        super(MSG);
    }
}
