package calculus.splines;

public class LengthOutsideOfFunctionBoundsException extends RuntimeException {
    public LengthOutsideOfFunctionBoundsException() {
        super("Length outside of according function");
    }
}
