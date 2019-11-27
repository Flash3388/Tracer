package util;

import com.flash3388.flashlib.time.Time;

import java.util.concurrent.TimeUnit;

public class TimeConversion {
    public static double toSeconds(Time t) {
        return t.valueAsMillis()/1000.0;
    }
}
