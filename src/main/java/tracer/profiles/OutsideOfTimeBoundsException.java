package tracer.profiles;

import com.flash3388.flashlib.time.Time;

public class OutsideOfTimeBoundsException extends RuntimeException {
    public OutsideOfTimeBoundsException(Time t) {
        super(String.format("No corresponding values for t = %s", t.toString()));
    }
}
