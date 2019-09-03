package tracer;

import com.flash3388.flashlib.time.Time;

public class MoreThanOneCorrespondingProfileException extends Exception{
    private final static String EXCEPTION_MSG_FORMAT = "More than one corresponding value for t = %f s";

    public MoreThanOneCorrespondingProfileException(Time t) {
        super(String.format(EXCEPTION_MSG_FORMAT, t.valueAsMillis()/1000.0));
    }
}
