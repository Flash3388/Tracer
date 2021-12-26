package util;

import com.flash3388.flashlib.time.Time;

import java.util.concurrent.TimeUnit;

public class TimeConversion {
    public static double toSeconds(Time t) {
        return t.toUnit(TimeUnit.MICROSECONDS).value()/1000000.0;
    }
}
