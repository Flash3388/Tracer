package calculus.variables;

public class BiggerPowerException extends IllegalArgumentException {
    private static final String MSG = "other variable's power is bigger then the original";
    public BiggerPowerException() {
        super(MSG);
    }
}
