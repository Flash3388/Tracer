package calculus.splines;

public class LengthOutsideOfFunctionBoundsException extends Exception {
    private final static String MSG = "Length outside of according function";

    public LengthOutsideOfFunctionBoundsException() {
        super(MSG);
    }
}
