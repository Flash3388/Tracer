package tracer.units.exceptions;

public class NotMatchingUnitsException extends IllegalArgumentException{
    public NotMatchingUnitsException() {
        super("different units cannot be added or subtracted");
    }
}
