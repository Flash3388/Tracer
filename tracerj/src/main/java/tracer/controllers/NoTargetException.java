package tracer.controllers;

public class NoTargetException extends RuntimeException {
    public NoTargetException() {
        super("No target provided for the controller");
    }
}
