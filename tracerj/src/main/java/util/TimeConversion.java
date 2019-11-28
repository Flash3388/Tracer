package util;

import com.flash3388.flashlib.time.Time;

public class TimeConversion {
    public static double toSeconds(Time t) {
        return t.valueAsMillis()/1000.0;
    }
}
