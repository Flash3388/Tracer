package tracer.trajectories;

public class LengthOutsideOfBoundsException extends RuntimeException {
    public LengthOutsideOfBoundsException() {
        super("Length outside of according function");
    }
}
