package util;

import com.flash3388.flashlib.math.Mathf;

public class Operations {
    private final static double FULL_CIRCLE_RADIANS = 2*Math.PI;

    public static double boundRadiansForcePositive(double angle) {
        return Mathf.translateInRange(angle, FULL_CIRCLE_RADIANS, true);
    }

    public static double boundRadians(double angle) {
        return Mathf.translateInRange(angle, FULL_CIRCLE_RADIANS, false);
    }
}
