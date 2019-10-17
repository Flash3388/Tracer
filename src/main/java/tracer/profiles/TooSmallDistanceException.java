package tracer.profiles;

public class TooSmallDistanceException extends IllegalArgumentException {
    private final static String MSG = "Too small distance compared to maximum jerk and acceleration";

    public TooSmallDistanceException() {
        super(MSG);
    }
}
