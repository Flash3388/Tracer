package calculus;

public class NotMatchingPowersException extends IllegalArgumentException {
    private final static String ERROR_MSG = "To add two variable their powers must match";

    public NotMatchingPowersException() {
        super(ERROR_MSG);
    }
}