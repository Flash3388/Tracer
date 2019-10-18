package tracer.profiles;

import com.flash3388.flashlib.time.Time;

public class OutsideOfTimeBoundsException extends Exception {
    private final static String EXCEPTION_MSG_FORMAT = "No corresponding values for t = %s";

    public OutsideOfTimeBoundsException(Time t) {
        super(String.format(EXCEPTION_MSG_FORMAT, t.toString()));
    }
}
